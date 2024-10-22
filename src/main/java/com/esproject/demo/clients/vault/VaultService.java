package com.esproject.demo.clients.vault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
@Slf4j
public class VaultService {
    private static final String BASE_PATH = "vault.basePath";
    private static final String TOKEN_PATH = BASE_PATH + ".token";
    private static final String X_VAULT_TOKEN = "X-Vault-Token";
    private final String vaultToken;
    private final RestClient restClient;

    public VaultService(Environment env) {
        String path = env.getProperty(BASE_PATH);
        vaultToken = env.getProperty(TOKEN_PATH);
        restClient = RestClient.builder().baseUrl(Objects.requireNonNull(path)).build();
    }

    public VaultData readSecret(String path) {
        // Add logging around this
        VaultResponse response = null;
        try {
            response = this.restClient.get()
                    .uri(path)
                    .header(X_VAULT_TOKEN, vaultToken)
                    .retrieve()
                    .body(VaultResponse.class);
        } catch (Exception ex) {
            log.error(String.format("There was error retrieving credentials from path %s with error %s", path, ex.getMessage()));
        }
        return response != null ? response.getData() : null;
    }

}
