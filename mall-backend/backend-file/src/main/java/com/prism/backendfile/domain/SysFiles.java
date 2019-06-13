package com.prism.backendfile.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_files")
public class SysFiles implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String filename;
    private String path;
    private String md5;
    private Long volume;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getMd5()
    {
        return md5;
    }

    public void setMd5(String md5)
    {
        this.md5 = md5;
    }

    public Long getVolume()
    {
        return volume;
    }

    public void setVolume(Long volume)
    {
        this.volume = volume;
    }
}
