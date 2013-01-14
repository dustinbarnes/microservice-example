package com.github.dustinbarnes.microservice.photocache.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class Photo
{
    @JsonProperty
    @NotEmpty
    private String userid;

    @JsonProperty
    @NotEmpty
    private String fileSystemPath;

    @JsonProperty
    @NotEmpty
    private String webUrlPath;

    @JsonProperty
    private String caption;

    public Photo()
    {
        // Used for jackson deserialization
    }

    public Photo(String userid, String fileSystemPath, String webUrlPath, String caption)
    {
        this.userid = userid;
        this.fileSystemPath = fileSystemPath;
        this.webUrlPath = webUrlPath;
        this.caption = caption;
    }

    public String getUserid()
    {
        return userid;
    }

    public void setUserid(String userid)
    {
        this.userid = userid;
    }

    public String getFileSystemPath()
    {
        return fileSystemPath;
    }

    public void setFileSystemPath(String fileSystemPath)
    {
        this.fileSystemPath = fileSystemPath;
    }

    public String getWebUrlPath()
    {
        return webUrlPath;
    }

    public void setWebUrlPath(String webUrlPath)
    {
        this.webUrlPath = webUrlPath;
    }

    public String getCaption()
    {
        return caption;
    }

    public void setCaption(String caption)
    {
        this.caption = caption;
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

        Photo photo = (Photo) o;

        if ( caption != null ? !caption.equals(photo.caption) : photo.caption != null )
        {
            return false;
        }
        if ( !fileSystemPath.equals(photo.fileSystemPath) )
        {
            return false;
        }
        if ( !userid.equals(photo.userid) )
        {
            return false;
        }
        if ( !webUrlPath.equals(photo.webUrlPath) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = userid.hashCode();
        result = 31 * result + fileSystemPath.hashCode();
        result = 31 * result + webUrlPath.hashCode();
        result = 31 * result + (caption != null ? caption.hashCode() : 0);
        return result;
    }
}
