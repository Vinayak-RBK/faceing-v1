package com.iss.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

@Service
public class SecretService {

	private final SecretClient secretClient;
	
	@Value("${AZURE_VAULT_URL")
	private String azureVaultURL;

	public SecretService() {
		this.secretClient = new SecretClientBuilder().vaultUrl("https://my-secret-vault.vault.azure.net/")
				.credential(new DefaultAzureCredentialBuilder().build()).buildClient();
	}
	
	public String getSecret(String secretName)
	{
		KeyVaultSecret secret=secretClient.getSecret(secretName);
		
		return secret.getValue();
	}

}
