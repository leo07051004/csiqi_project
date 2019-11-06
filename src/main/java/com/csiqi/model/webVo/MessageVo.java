package com.csiqi.model.webVo;

/**
 * Created by cl_kd-user47823 on 2019/11/6.
 */
public class MessageVo {
    private int f_message_id;
    private  int  f_message_fromUId;
    private int f_message_toUId;
    private String f_message_content;
    private String f_message_status;
    private String f_message_uTime;
    private String f_message_cTime;

    public int getF_message_id() {
        return f_message_id;
    }

    public void setF_message_id(int f_message_id) {
        this.f_message_id = f_message_id;
    }

    public int getF_message_fromUId() {
        return f_message_fromUId;
    }

    public void setF_message_fromUId(int f_message_fromUId) {
        this.f_message_fromUId = f_message_fromUId;
    }

    public int getF_message_toUId() {
        return f_message_toUId;
    }

    public void setF_message_toUId(int f_message_toUId) {
        this.f_message_toUId = f_message_toUId;
    }

    public String getF_message_content() {
        return f_message_content;
    }

    public void setF_message_content(String f_message_content) {
        this.f_message_content = f_message_content;
    }

    public String getF_message_status() {
        return f_message_status;
    }

    public void setF_message_status(String f_message_status) {
        this.f_message_status = f_message_status;
    }

    public String getF_message_uTime() {
        return f_message_uTime;
    }

    public void setF_message_uTime(String f_message_uTime) {
        this.f_message_uTime = f_message_uTime;
    }

    public String getF_message_cTime() {
        return f_message_cTime;
    }

    public void setF_message_cTime(String f_message_cTime) {
        this.f_message_cTime = f_message_cTime;
    }
}
