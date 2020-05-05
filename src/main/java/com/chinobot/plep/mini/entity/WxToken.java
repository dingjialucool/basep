package com.chinobot.plep.mini.entity;

/**
 * Created by yaoxf on 2019-1-24.
 */
public class WxToken {
    private String  access_token;
    private int  expires_in;
    private String  errmsg;
    private int  errcode;
    private long post_timne;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public long getPost_timne() {
        return post_timne;
    }

    public void setPost_timne(long post_timne) {
        this.post_timne = post_timne;
    }


}
