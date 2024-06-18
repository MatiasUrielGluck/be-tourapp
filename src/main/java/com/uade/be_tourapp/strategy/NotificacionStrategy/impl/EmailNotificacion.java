package com.uade.be_tourapp.strategy.NotificacionStrategy.impl;

import com.uade.be_tourapp.adapter.AdapterMail.AdapterMail;
import com.uade.be_tourapp.adapter.AdapterMail.impl.JavaMail;
import com.uade.be_tourapp.entity.Notificacion;
import com.uade.be_tourapp.enums.notificacion.NotificacionStrategyEnum;
import com.uade.be_tourapp.strategy.NotificacionStrategy.NotificacionStrategy;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificacion implements NotificacionStrategy {
    private final AdapterMail adapterMail;

    public EmailNotificacion() {
        this.adapterMail = new JavaMail();
    }

    @Override
    public NotificacionStrategyEnum getIdentification() {
        return NotificacionStrategyEnum.EMAIL;
    }

    @Override
    public void notificar(Notificacion notificacion) {
        adapterMail.notificar(notificacion);
    }
}
