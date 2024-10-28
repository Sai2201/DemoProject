package com.esproject.demo.clients.vault.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VaultEsCreds {
    private String apikey;
    private String endpointUri;
    private String password;
    private String username;
}
