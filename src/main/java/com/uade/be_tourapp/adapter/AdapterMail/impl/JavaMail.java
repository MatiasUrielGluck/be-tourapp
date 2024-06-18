package com.uade.be_tourapp.adapter.AdapterMail.impl;

import com.uade.be_tourapp.adapter.AdapterMail.AdapterMail;
import com.uade.be_tourapp.entity.Notificacion;
import org.springframework.stereotype.Component;

@Component
public class JavaMail implements AdapterMail {
    @Override
    public void notificar(Notificacion notificacion) {
        System.out.println("Email Notification (JavaMail): " + notificacion.getMensaje());
    }
}
