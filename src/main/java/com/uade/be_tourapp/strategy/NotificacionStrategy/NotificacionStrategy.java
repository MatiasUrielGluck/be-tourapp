package com.uade.be_tourapp.strategy.NotificacionStrategy;

import com.uade.be_tourapp.entity.Notificacion;
import com.uade.be_tourapp.enums.notificacion.NotificacionStrategyEnum;

public interface NotificacionStrategy {
    NotificacionStrategyEnum getIdentification();
    void notificar(Notificacion notificacion);
}
