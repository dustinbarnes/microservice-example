package com.github.dustinbarnes.microservice.money.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;


public class MoneyTransactionTest {
    private ObjectMapper JSON = new ObjectMapper();

    @Before
    public void setupNullHandling() {
        JSON = JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    public void MoneyTransactionSerializesCorrectly() throws Exception {
        MoneyTransaction transaction = new MoneyTransaction("foo", 100.00, null);
        assertThat("Serialization works correctly",
                JSON.writeValueAsString(transaction),
                equalToIgnoringWhiteSpace(jsonFixture()));
    }

    @Test
    public void MoneyTransactionDeserializesCorrectly() throws Exception
    {
        MoneyTransaction transaction = new MoneyTransaction("foo", 100.00, null);
        assertThat("Deserialization works correctly",
                JSON.readValue(jsonFixture(), MoneyTransaction.class),
                is(equalTo(transaction)));
    }

    private String jsonFixture() {
        try {
            return new String(Files.readAllBytes(Paths.get(getClass().getResource("/fixtures/transaction.json").toURI())));
        } catch ( Exception e ) {
            e.printStackTrace();
            return "";
        }
    }
}
