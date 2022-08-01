package com.dazuizui.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Journey implements Serializable {
    private int id;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date   create_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date   update_time;
    private String update_by;
    private String create_by;
    private int    del_flag;
    private int    status;
    private String journey_type;
    private int    sort_order;
    //补充
    private String replenish;
    private String icon;

    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", update_by='" + update_by + '\'' +
                ", create_by='" + create_by + '\'' +
                ", del_flag=" + del_flag +
                ", status=" + status +
                ", journey_type='" + journey_type + '\'' +
                ", sort_order=" + sort_order +
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJourney_type() {
        return journey_type;
    }

    public void setJourney_type(String journey_type) {
        this.journey_type = journey_type;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
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

    public Journey() {
    }

    public Journey(int id, String title, String content, Date create_time, Date update_time, String update_by, String create_by, int del_flag, int status, String journey_type, int sort_order, String replenish, String icon) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
        this.update_time = update_time;
        this.update_by = update_by;
        this.create_by = create_by;
        this.del_flag = del_flag;
        this.status = status;
        this.journey_type = journey_type;
        this.sort_order = sort_order;
        this.replenish = replenish;
        this.icon = icon;
    }
}
