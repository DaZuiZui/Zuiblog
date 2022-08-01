package com.dazuizui.api.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class JourneyBo implements Serializable {
    private int id;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date   updateTime;
    private String updateBy;
    private String createBy;
    private int    delFlag;
    private int    status;
    private String journeyType;

    @Override
    public String toString() {
        return "JourneyVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                ", createBy='" + createBy + '\'' +
                ", delFlag=" + delFlag +
                ", status=" + status +
                ", journeyType='" + journeyType + '\'' +
                ", journeyorder=" + journeyorder +
                ", replenish='" + replenish + '\'' +
                ", icon='" + icon + '\'' +
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public Integer getJourneyorder() {
        return journeyorder;
    }

    public void setJourneyorder(Integer journeyorder) {
        this.journeyorder = journeyorder;
    }

    public String getReplenish() {
        return replenish;
    }

    public void setReplenish(String replenish) {
        this.replenish = replenish;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public JourneyBo() {
    }

    public JourneyBo(int id, String title, String content, Date createTime, Date updateTime, String updateBy, String createBy, int delFlag, int status, String journeyType, Integer journeyorder, String replenish, String icon) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.createBy = createBy;
        this.delFlag = delFlag;
        this.status = status;
        this.journeyType = journeyType;
        this.journeyorder = journeyorder;
        this.replenish = replenish;
        this.icon = icon;
    }

    private Integer journeyorder;
    //补充
    private String replenish;
    private String icon;
}
