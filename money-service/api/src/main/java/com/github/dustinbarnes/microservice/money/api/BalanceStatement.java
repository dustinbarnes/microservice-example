package com.github.dustinbarnes.microservice.money.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class BalanceStatement
{
    @JsonProperty
    @NotEmpty
    private String userid;

    @JsonProperty
    @NotEmpty
    private Float balance;

    @JsonProperty
    @NotEmpty
    private Float availableBalance;


    public BalanceStatement()
    {
        // Used for jackson deserialization
    }

    public BalanceStatement(String userid, Float balance, Float availableBalance)
    {
        this.userid = userid;
        this.balance = balance;
        this.availableBalance = availableBalance;
    }

    public String getUserid()
    {
        return userid;
    }

    public void setUserid(String userid)
    {
        this.userid = userid;
    }

    public Float getBalance()
    {
        return balance;
    }

    public void setBalance(Float balance)
    {
        this.balance = balance;
    }

    public Float getAvailableBalance()
    {
        return availableBalance;
    }

    public void setAvailableBalance(Float availableBalance)
    {
        this.availableBalance = availableBalance;
    }

    @Override
    public boolean equals(Object o)
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        BalanceStatement that = (BalanceStatement) o;

        if ( !availableBalance.equals(that.availableBalance) )
        {
            return false;
        }
        if ( !balance.equals(that.balance) )
        {
            return false;
        }
        if ( !userid.equals(that.userid) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = userid.hashCode();
        result = 31 * result + balance.hashCode();
        result = 31 * result + availableBalance.hashCode();
        return result;
    }
}
