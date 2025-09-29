package com.example.securitystarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtProperties {
    private String signerKey;

    public String getSignerKey() {
        return signerKey;
    }

    public void setSignerKey(String signerKey) {
        this.signerKey = signerKey;
    }
}
