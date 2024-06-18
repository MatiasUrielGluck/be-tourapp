package com.uade.be_tourapp.adapter.AdapterIAVerificador.impl;

import com.uade.be_tourapp.adapter.AdapterIAVerificador.AdapterIAVerificador;
import com.uade.be_tourapp.entity.Credencial;

public class AdapterIAVerificadorMock implements AdapterIAVerificador {

    @Override
    public Boolean esCredencialValida(Credencial credencial) {
        return true;
    }
}
