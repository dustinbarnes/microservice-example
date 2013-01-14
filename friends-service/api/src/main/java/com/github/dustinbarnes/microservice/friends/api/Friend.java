package com.github.dustinbarnes.microservice.friends.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

public class Friend
{
    @JsonProperty
    @NotEmpty
    private String userid;

    @JsonProperty
    @NotEmpty
    private String profileLocation;

    public Friend()
    {
    }

    public Friend(String userid, String profileLocation)
    {
        this.userid = userid;
        this.profileLocation = profileLocation;
    }

    public String getUserid()
    {
        return userid;
    }

    public void setUserid(String userid)
    {
        this.userid = userid;
    }

    public String getProfileLocation()
    {
        return profileLocation;
    }

    public void setProfileLocation(String profileLocation)
    {
        this.profileLocation = profileLocation;
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

        Friend friend = (Friend) o;

        if ( !profileLocation.equals(friend.profileLocation) )
        {
            return false;
        }
        if ( !userid.equals(friend.userid) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = userid.hashCode();
        result = 31 * result + profileLocation.hashCode();
        return result;
    }
}
