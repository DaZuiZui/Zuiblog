package com.dazuizui.api.pojo;

/**
 * @author 杨易达
 * @Time   2020/6/22
 * @Text   失败返回实体类
 */
public class RequestJson {
    private String id;
    private String errorinformation;
    private boolean booleanJson;

    @Override
    public String toString() {
        return "RequestJson{" +
                "id='" + id + '\'' +
                ", errorinformation='" + errorinformation + '\'' +
                ", booleanJson=" + booleanJson +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorinformation() {
        return errorinformation;
    }

    public void setErrorinformation(String errorinformation) {
        this.errorinformation = errorinformation;
    }

    public boolean isBooleanJson() {
        return booleanJson;
    }

    public void setBooleanJson(boolean booleanJson) {
        this.booleanJson = booleanJson;
    }

    public RequestJson() {
    }

    public RequestJson(String id, String errorinformation, boolean booleanJson) {
        this.id = id;
        this.errorinformation = errorinformation;
        this.booleanJson = booleanJson;
    }
}
