package com.github.dustinbarnes.microservice.moneyservice;

import com.github.dustinbarnes.microservice.money.api.BalanceStatement;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MoneyServiceClient {
    private String basePath;

    public MoneyServiceClient(String basePath)
    {
        this.basePath = basePath;
    }

    public BalanceStatement getBalance(String user) throws IOException {
        String url = basePath + "/" + user + "/money";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<BalanceStatement> response = new RestTemplate().exchange(url,
                HttpMethod.GET, new HttpEntity<Void>(headers), BalanceStatement.class);

        return response.getBody();
    }

    public Future<BalanceStatement> getBalanceAsync(final String user, ExecutorService executorService) throws IOException    {
        return executorService.submit(() -> getBalance(user));
    }
}
