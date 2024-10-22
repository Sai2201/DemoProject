package com.esproject.demo.clients.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.esproject.demo.clients.vault.VaultData;
import com.esproject.demo.clients.vault.VaultEsCreds;
import com.esproject.demo.clients.vault.VaultService;
import jakarta.annotation.PreDestroy;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;

@Configuration
public class ESConfig {
    private static final String BASE_PATH = "elastic.client.";
    private final String vaultPath;
    private final VaultService vaultService;
    private ElasticsearchTransport transport;

    public ESConfig(Environment env, VaultService vaultService) {
        this.vaultPath = env.getProperty(BASE_PATH + "vaultPath");
        this.vaultService = vaultService;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // Retrieve secrets from vault for ES
        VaultData vaultData = vaultService.readSecret(this.vaultPath);
        VaultEsCreds creds = vaultData.getData();

        RestClient restClient = RestClient
                .builder(HttpHost.create(creds.getEndpointUri()))
                .setDefaultHeaders(new Header[] {new BasicHeader("Authorization", "ApiKey " + creds.getApikey())})
                .build();

        this.transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    @PreDestroy
    public void cleanup() throws IOException {
        if (transport != null) {
            transport.close();
        }
    }
}
