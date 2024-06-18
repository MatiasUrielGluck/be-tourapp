package com.uade.be_tourapp.strategy.NotificacionStrategy.impl;

import com.uade.be_tourapp.entity.Notificacion;
import com.uade.be_tourapp.enums.notificacion.NotificacionStrategyEnum;
import com.uade.be_tourapp.strategy.NotificacionStrategy.NotificacionStrategy;
import org.springframework.stereotype.Component;

@Component
public class PushNotificacion implements NotificacionStrategy {
    @Override
    public NotificacionStrategyEnum getIdentification() {
        return NotificacionStrategyEnum.PUSH;
    }

    @Override
    public void notificar(Notificacion notificacion) {
        System.out.println("Firebase Push Notification: " + notificacion.getMensaje());
    }
}
