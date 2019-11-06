package com.csiqi.model.webVo;

/**
 * Created by cl_kd-user47823 on 2019/11/6.
 */
public class FriendsVo {
    private int f_friends_id;
    private  int  f_friends_uId;
    private int f_friends_fId;
    private String f_friends_status;
    private String f_friends_utime;
    private String f_friends_ctime;
    private int pageNum;
    private int pageSize;

    public int getF_friends_id() {
        return f_friends_id;
    }

    public void setF_friends_id(int f_friends_id) {
        this.f_friends_id = f_friends_id;
    }

    public int getF_friends_uId() {
        return f_friends_uId;
    }

    public void setF_friends_uId(int f_friends_uId) {
        this.f_friends_uId = f_friends_uId;
    }

    public int getF_friends_fId() {
        return f_friends_fId;
    }

    public void setF_friends_fId(int f_friends_fId) {
        this.f_friends_fId = f_friends_fId;
    }

    public String getF_friends_status() {
        return f_friends_status;
    }

    public void setF_friends_status(String f_friends_status) {
        this.f_friends_status = f_friends_status;
    }

    public String getF_friends_utime() {
        return f_friends_utime;
    }

    public void setF_friends_utime(String f_friends_utime) {
        this.f_friends_utime = f_friends_utime;
    }

    public String getF_friends_ctime() {
        return f_friends_ctime;
    }

    public void setF_friends_ctime(String f_friends_ctime) {
        this.f_friends_ctime = f_friends_ctime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
