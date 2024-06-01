package com.uade.be_tourapp.strategy.UserManagementStrategy;

import com.uade.be_tourapp.dto.LoginRequestDTO;
import com.uade.be_tourapp.dto.RegistroRequestDTO;
import com.uade.be_tourapp.entity.Usuario;
import com.uade.be_tourapp.enums.AuthStrategiesEnum;

public interface AuthStrategy {
    AuthStrategiesEnum getIdentification();
    Usuario loguear(LoginRequestDTO loginRequestDTO);
    Usuario registrar(RegistroRequestDTO registroRequestDTO);
}
