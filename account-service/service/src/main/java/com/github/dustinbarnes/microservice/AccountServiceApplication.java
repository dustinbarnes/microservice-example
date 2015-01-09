package com.github.dustinbarnes.microservice;

import com.github.dustinbarnes.microservice.friends.client.FriendsServiceClient;
import com.github.dustinbarnes.microservice.moneyservice.MoneyServiceClient;
import com.github.dustinbarnes.microservice.photocache.PhotoCacheClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootApplication
public class AccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    public MoneyServiceClient getMoneyServiceClient(@Value("${moneyServiceBasePath}")String basePath) {
        return new MoneyServiceClient(basePath);
    }

    @Bean
    public FriendsServiceClient getFriendsServiceClient(@Value("${friendServiceBasePath}")String basePath) {
        return new FriendsServiceClient(basePath);
    }

    @Bean
    public PhotoCacheClient getPhotoCacheClient(@Value("${photoCacheBasePath}")String basePath) {
        return new PhotoCacheClient(basePath);
    }

    @Bean
    public ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }
}
