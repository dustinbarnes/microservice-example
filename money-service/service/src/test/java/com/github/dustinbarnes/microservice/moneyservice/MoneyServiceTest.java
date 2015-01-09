package com.github.dustinbarnes.microservice.moneyservice;

import com.github.dustinbarnes.microservice.money.api.BalanceStatement;
import com.github.dustinbarnes.microservice.money.api.MoneyTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MoneyServiceApplication.class)
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class MoneyServiceTest {
    @Value("${local.server.port}")
    int port;

    private final BalanceStatement balance = new BalanceStatement("test", 1500.0, 1300.0);
    private final MoneyTransaction transaction = new MoneyTransaction("test", 150.0, "debit card");

    RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testExpecting404OnPost() {
        String url = "http://localhost:" + port + "/404foo/money";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MoneyTransaction> entity = new HttpEntity<>(transaction, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, String.class);

        assertThat("Posting with a 404 prefix for user id generates 404",
                response.getStatusCode(),
                equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testExpecting500OnPost() {
        String url = "http://localhost:" + port + "/500foo/money";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MoneyTransaction> entity = new HttpEntity<>(transaction, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, String.class);

        assertThat("Posting with a 500 prefix for user id generates server error",
                response.getStatusCode(),
                equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void testExpecting201OnPost() {
        String url = "http://localhost:" + port + "/doggy/money";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MoneyTransaction> entity = new HttpEntity<>(transaction, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void testGetBalance() {
        String url = "http://localhost:" + port + "/doggy/money";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<BalanceStatement> response = restTemplate.exchange(url,
                HttpMethod.GET, new HttpEntity<Void>(headers), BalanceStatement.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        BalanceStatement balance = response.getBody();

        assertThat(balance.getUserid(), equalTo("doggy"));
        assertThat(balance.getBalance(), equalTo(100.0));
    }

}
