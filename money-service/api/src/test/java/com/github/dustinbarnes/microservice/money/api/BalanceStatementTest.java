package com.github.dustinbarnes.microservice.money.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;


public class BalanceStatementTest {
    private ObjectMapper JSON = new ObjectMapper();

    @Test
    public void BalanceSerializesCorrectly() throws Exception {
        BalanceStatement balance = new BalanceStatement("foo", 125.50, -118.25);
        assertThat("Serialization works correctly",
                JSON.writeValueAsString(balance),
                equalToIgnoringWhiteSpace(jsonFixture()));
    }

    @Test
    public void BalanceDeserializesCorrectly() throws Exception {
        BalanceStatement balance = new BalanceStatement("foo", 125.50, -118.25);
        assertThat("Deserialization works correctly",
                JSON.readValue(jsonFixture(), BalanceStatement.class),
                is(equalTo(balance)));
    }

    private String jsonFixture() {
        try {
            return new String(Files.readAllBytes(Paths.get(getClass().getResource("/fixtures/balance.json").toURI())));
        } catch ( Exception e ) {
            e.printStackTrace();
            return "";
        }
    }
}
