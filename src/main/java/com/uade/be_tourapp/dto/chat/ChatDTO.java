package com.uade.be_tourapp.dto.chat;

import com.uade.be_tourapp.dto.usuario.AccountInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ChatDTO {
    private Long id;
    private AccountInfoDTO turista;
    private AccountInfoDTO guia;
}
