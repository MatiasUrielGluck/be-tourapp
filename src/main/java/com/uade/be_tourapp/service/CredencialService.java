package com.uade.be_tourapp.service;

import com.uade.be_tourapp.adapter.AdapterIAVerificador;
import com.uade.be_tourapp.adapter.impl.AdapterIAVerificadorMock;
import com.uade.be_tourapp.entity.Credencial;
import com.uade.be_tourapp.repository.CredencialRepository;
import org.springframework.stereotype.Service;

@Service
public class CredencialService {
    private final CredencialRepository credencialRepository;
    private final AdapterIAVerificador adapterIAVerificador;

    public CredencialService(CredencialRepository credencialRepository) {
        this.credencialRepository = credencialRepository;
        this.adapterIAVerificador = new AdapterIAVerificadorMock();
    }

    /**
     *
     * @param credencial a ser validada
     * @return <p><span style="font-weight: bold; font-style: italic;">Verdadero</span> en caso de no existir en la base de datos y de ser validada exitosamente por la IA.</p>
     * <p><span style="font-weight: bold; font-style: italic;">Falso </span>en caso contrario</p>
     */
    public Boolean esCredencialValida(Credencial credencial) {
        if (credencialRepository.existsByNumero(credencial.getNumero())) return false;
        return adapterIAVerificador.esCredencialValida(credencial);
    }
}
