package com.uade.be_tourapp.adapter.impl;

import com.uade.be_tourapp.adapter.AdapterIAVerificador;
import com.uade.be_tourapp.entity.Credencial;

public class AdapterIAVerificadorMock implements AdapterIAVerificador {

    @Override
    public Boolean esCredencialValida(Credencial credencial) {
        return true;
    }
}
