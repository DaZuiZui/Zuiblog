package com.dazuizui.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


public class Article implements Serializable {
    private int    id;
    private String title;           //标题
    private String author;          //作者
    private String mdtext;          //md文件
    private String privacy;         //隐私
    private String language;        //语言
    private String technical;       //技术分类
    private String blogfileclass;   //个人分类

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date   creatingTime;      //创造时间

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date   updateTime;        //更新时间
    private String name;             //名字
    private String profilephotourl;  //作者头像
    private String describes;        //描述
    private String html;            //html文件

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", mdtext='" + mdtext + '\'' +
                ", privacy='" + privacy + '\'' +
                ", language='" + language + '\'' +
                ", technical='" + technical + '\'' +
                ", blogfileclass='" + blogfileclass + '\'' +
                ", creatingTime=" + creatingTime +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                ", profilephotourl='" + profilephotourl + '\'' +
                ", describes='" + describes + '\'' +
                ", html='" + html + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMdtext() {
        return mdtext;
    }

    public void setMdtext(String mdtext) {
        this.mdtext = mdtext;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public String getBlogfileclass() {
        return blogfileclass;
    }

    public void setBlogfileclass(String blogfileclass) {
        this.blogfileclass = blogfileclass;
    }

    public Date getCreatingTime() {
        return creatingTime;
    }

    public void setCreatingTime(Date creatingTime) {
        this.creatingTime = creatingTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilephotourl() {
        return profilephotourl;
    }

    public void setProfilephotourl(String profilephotourl) {
        this.profilephotourl = profilephotourl;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Article() {
    }

    public Article(int id, String title, String author, String mdtext, String privacy, String language, String technical, String blogfileclass, Date creatingTime, Date updateTime, String name, String profilephotourl, String describes, String html) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.mdtext = mdtext;
        this.privacy = privacy;
        this.language = language;
        this.technical = technical;
        this.blogfileclass = blogfileclass;
        this.creatingTime = creatingTime;
        this.updateTime = updateTime;
        this.name = name;
        this.profilephotourl = profilephotourl;
        this.describes = describes;
        this.html = html;
    }
}
