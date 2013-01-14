package com.github.dustinbarnes.microservice.money.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class MoneyTransaction
{
    @JsonProperty
    @NotEmpty
    private String userid;

    @JsonProperty
    @NotEmpty
    private Float amount;

    @JsonProperty
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String comment = null;

    public MoneyTransaction()
    {
    }

    public MoneyTransaction(String userid, Float amount, String comment)
    {
        this.userid = userid;
        this.amount = amount;
        this.comment = comment;
    }

    public String getUserid()
    {
        return userid;
    }

    public void setUserid(String userid)
    {
        this.userid = userid;
    }

    public Float getAmount()
    {
        return amount;
    }

    public void setAmount(Float amount)
    {
        this.amount = amount;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
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

        MoneyTransaction that = (MoneyTransaction) o;

        if ( !amount.equals(that.amount) )
        {
            return false;
        }
        if ( comment != null ? !comment.equals(that.comment) : that.comment != null )
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
        result = 31 * result + amount.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
