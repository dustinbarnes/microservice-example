package com.github.dustinbarnes.microservice.money.api;

import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MoneyTransactionTest
{
    @Test
    public void MoneyTransactionSerializesCorrectly() throws Exception
    {
        MoneyTransaction transaction = new MoneyTransaction("foo", 100.00f, null);
        assertThat("Serialization works correctly",
                asJson(transaction),
                is(equalTo(jsonFixture("fixtures/transaction.json"))));
    }

    @Test
    public void MoneyTransactionDeserializesCorrectly() throws Exception
    {
        MoneyTransaction transaction = new MoneyTransaction("foo", 100.00f, null);
        assertThat("Deserialization works correctly",
                fromJson(jsonFixture("fixtures/transaction.json"), MoneyTransaction.class),
                is(equalTo(transaction)));
    }
}
