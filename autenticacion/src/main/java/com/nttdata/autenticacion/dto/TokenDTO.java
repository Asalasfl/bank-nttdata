package com.nttdata.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class TokenDTO {
    private String token;
}
