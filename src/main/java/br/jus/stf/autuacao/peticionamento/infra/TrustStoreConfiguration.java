package br.jus.stf.autuacao.peticionamento.infra;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.annotation.PostConstruct;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Configurações necessárias para comunicação segura com o gateway.
 * 
 * @author Rafael Alencar
 *
 */
@Configuration
@EnableAutoConfiguration
public class TrustStoreConfiguration {

	@Value("${http.client.ssl.trust-store}")
	private String trustStore;

	@Value("${http.client.ssl.trust-store-password}")
	private char[] trustStorePassword;
	
	@PostConstruct
	public void configureTrustStore() throws Exception {
		String keystoreType = "JKS";
		InputStream keystoreLocation = null;
		char[] keystorePassword = null;
		char[] keyPassword = null;

		KeyStore keystore = KeyStore.getInstance(keystoreType);
		keystore.load(keystoreLocation, keystorePassword);
		KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmfactory.init(keystore, keyPassword);

		InputStream truststoreLocation = new FileInputStream(trustStore);
		char[] truststorePassword = trustStorePassword;
		String truststoreType = "JKS";

		KeyStore truststore = KeyStore.getInstance(truststoreType);
		truststore.load(truststoreLocation, truststorePassword);
		TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmfactory.init(truststore);

		KeyManager[] keymanagers = kmfactory.getKeyManagers();
		TrustManager[] trustmanagers = tmfactory.getTrustManagers();

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(keymanagers, trustmanagers, new SecureRandom());
		SSLContext.setDefault(sslContext);
	}

}