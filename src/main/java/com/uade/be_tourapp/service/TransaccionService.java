package com.uade.be_tourapp.service;

import com.uade.be_tourapp.dto.FacturaDTO;
import com.uade.be_tourapp.dto.GenericResponseDTO;
import com.uade.be_tourapp.entity.Devolucion;
import com.uade.be_tourapp.entity.Factura;
import com.uade.be_tourapp.entity.Viaje;
import com.uade.be_tourapp.enums.DocumentoEnum;
import com.uade.be_tourapp.exception.BadRequestException;
import com.uade.be_tourapp.repository.DevolucionRepository;
import com.uade.be_tourapp.repository.DocumentoRepository;
import com.uade.be_tourapp.repository.FacturaRepository;
import com.uade.be_tourapp.utils.Stripe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransaccionService {
    private final DocumentoRepository documentoRepository;
    private final FacturaRepository facturaRepository;
    private final DevolucionRepository devolucionRepository;

    public TransaccionService(DocumentoRepository documentoRepository, FacturaRepository facturaRepository, DevolucionRepository devolucionRepository) {
        this.documentoRepository = documentoRepository;
        this.facturaRepository = facturaRepository;
        this.devolucionRepository = devolucionRepository;
    }

    public FacturaDTO generarFactura(Viaje viaje, DocumentoEnum tipo) {
        Factura factura = Factura.builder()
                .viaje(viaje)
                .motivo(tipo)
                .build();
        factura.configurar();

        Factura facturaGuardada = facturaRepository.save(factura);

        return FacturaDTO.builder()
                .id(facturaGuardada.getId())
                .viajeId(facturaGuardada.getViaje().getId())
                .total(facturaGuardada.getTotal())
                .precio(facturaGuardada.getPrecio())
                .comision(facturaGuardada.getComision())
                .motivo(facturaGuardada.getMotivo())
                .pagada(facturaGuardada.getPagada())
                .build();
    }

    public GenericResponseDTO pagarFactura(Integer id) {
        Factura factura = (Factura) documentoRepository.findById(id).orElseThrow(() -> new BadRequestException("Factura inexistente."));

        if (factura.getPagada()) {
            throw new BadRequestException("La factura ya se encuentra pagada.");
        }

        Boolean resultado = Stripe.pagar(factura.getTotal());
        if (resultado) {
            factura.setPagada(true);
            documentoRepository.save(factura);
            return GenericResponseDTO.builder()
                    .message("Pago exitoso.")
                    .build();
        }

        return GenericResponseDTO.builder()
                .message("Pago declinado.")
                .build();
    }

    public List<FacturaDTO> obtenerFacturasDeViaje(Integer viajeId) {
        List<Factura> facturas = facturaRepository.findAllByViajeId(viajeId);
        List<FacturaDTO> respuesta = new ArrayList<>();
        facturas.forEach(factura -> {
            FacturaDTO facturaDTO = FacturaDTO.builder()
                    .id(factura.getId())
                    .viajeId(factura.getViaje().getId())
                    .total(factura.getTotal())
                    .precio(factura.getPrecio())
                    .comision(factura.getComision())
                    .motivo(factura.getMotivo())
                    .pagada(factura.getPagada())
                    .build();
            respuesta.add(facturaDTO);
        });
        return respuesta;
    }

    public Devolucion generarDevolucion(Viaje viaje) {
        Factura facturaToCancel = facturaRepository.findByViajeIdAndMotivo(viaje.getId(), DocumentoEnum.ANTICIPO)
                .orElseThrow(() -> new BadRequestException("Documento inexistente."));

        Devolucion devolucion = Devolucion.builder()
                .viaje(viaje)
                .montoCancelable(facturaToCancel.getTotal())
                .build();
        devolucion.totalizar();
        return devolucionRepository.save(devolucion);
    }
}
