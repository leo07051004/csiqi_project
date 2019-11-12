package com.csiqi.utils.test;

/**
 * Created by cl_kd-user47823 on 2019/11/12.
 */
public class Stationery {
    private String pen;
    private String notebook;

    public String getPen() {
        return pen;
    }

    public void setPen(String pen) {
        this.pen = pen;
    }

    public String getNotebook() {
        return notebook;
    }

    public void setNotebook(String notebook) {
        this.notebook = notebook;
    }

    public Stationery(String pen, String notebook) {
        this.pen = pen;
        this.notebook = notebook;
    }
}
