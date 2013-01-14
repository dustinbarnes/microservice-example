package com.github.dustinbarnes.microservice.money.api;

import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BalanceStatementTest
{
    @Test
    public void BalanceSerializesCorrectly() throws Exception
    {
        BalanceStatement balance = new BalanceStatement("foo", 125.50f, -118.25f);
        assertThat("Serialization works correctly",
                asJson(balance),
                is(equalTo(jsonFixture("fixtures/balance.json"))));
    }

    @Test
    public void BalanceDeserializesCorrectly() throws Exception
    {
        BalanceStatement balance = new BalanceStatement("foo", 125.50f, -118.25f);
        assertThat("Deserialization works correctly",
                fromJson(jsonFixture("fixtures/balance.json"), BalanceStatement.class),
                is(equalTo(balance)));
    }
}
