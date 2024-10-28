package com.esproject.demo.clients.vault.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VMetadata {
    @JsonProperty(value = "created_time")
    private String createdTime;
    @JsonProperty(value = "custom_metadata")
    private String customMetadata;
    @JsonProperty(value = "deletion_time")
    private String deletionTime;
}
