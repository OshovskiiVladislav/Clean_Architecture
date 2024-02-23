package com.oshovsky.clean.architecture.image.storage.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class YandexCloudConfiguration {

    @Bean
    public AmazonS3 amazonS3(ApplicationProperties properties) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                properties.getYandexCloud().getAccessKey(),
                                properties.getYandexCloud().getSecretKey())))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                properties.getYandexCloud().getServiceEndpoint(),
                                properties.getYandexCloud().getSigningRegion()))
                .build();
    }

}
