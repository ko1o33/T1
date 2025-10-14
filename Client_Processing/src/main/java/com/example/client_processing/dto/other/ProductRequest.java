package com.example.client_processing.dto.other;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class ProductRequest {

    @NotBlank
    String name;

    @NotBlank
    @Pattern(regexp = "DC|CC|AC|IPO|PC|PENS|NS|INS|BS")
    String key;
}
