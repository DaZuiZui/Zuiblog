package com.dazuizui.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Blogroll implements Serializable {
    private String id;                     //id
    private String name;                //中文名子
    private String englishName;         //英文名字
    private String englishIntroduce;    //英文介绍
    private String introduce;           //中文介绍
    private String introduceImageUrl;   //介绍图片的url

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   creationTime;        //创建时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   modificationTime;    //修改时间

    @Override
    public String toString() {
        return "Blogroll{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", englishName='" + englishName + '\'' +
                ", englishIntroduce='" + englishIntroduce + '\'' +
                ", introduce='" + introduce + '\'' +
                ", introduceImageUrl='" + introduceImageUrl + '\'' +
                ", creationTime=" + creationTime +
                ", modificationTime=" + modificationTime +
                ", url='" + url + '\'' +
                '}';
    }

    private String url;                 //引导链接


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getEnglishIntroduce() {
        return englishIntroduce;
    }

    public void setEnglishIntroduce(String englishIntroduce) {
        this.englishIntroduce = englishIntroduce;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIntroduceImageUrl() {
        return introduceImageUrl;
    }

    public void setIntroduceImageUrl(String introduceImageUrl) {
        this.introduceImageUrl = introduceImageUrl;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Blogroll() {
    }

    public Blogroll(String id, String name, String englishName, String englishIntroduce, String introduce, String introduceImageUrl, Date creationTime, Date modificationTime, String url) {
        this.id = id;
        this.name = name;
        this.englishName = englishName;
        this.englishIntroduce = englishIntroduce;
        this.introduce = introduce;
        this.introduceImageUrl = introduceImageUrl;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
        this.url = url;
    }
}
