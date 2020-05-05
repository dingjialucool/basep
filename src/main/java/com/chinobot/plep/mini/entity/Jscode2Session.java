package com.chinobot.plep.mini.entity;

/**
 * Created by yaoxf on 2019-1-22.
 */
public class Jscode2Session {
    private String openid;
    private String session_key;
    private String unionid;
    private Integer errcode;
    private String errmsg;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Integer getErrcode() {
        return errcode == null?0:errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Jscode2Session() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jscode2Session that = (Jscode2Session) o;

        if (openid != null ? !openid.equals(that.openid) : that.openid != null) return false;
        if (session_key != null ? !session_key.equals(that.session_key) : that.session_key != null) return false;
        if (unionid != null ? !unionid.equals(that.unionid) : that.unionid != null) return false;
        if (errcode != null ? !errcode.equals(that.errcode) : that.errcode != null) return false;
        return errmsg != null ? errmsg.equals(that.errmsg) : that.errmsg == null;
    }

    @Override
    public int hashCode() {
        int result = openid != null ? openid.hashCode() : 0;
        result = 31 * result + (session_key != null ? session_key.hashCode() : 0);
        result = 31 * result + (unionid != null ? unionid.hashCode() : 0);
        result = 31 * result + (errcode != null ? errcode.hashCode() : 0);
        result = 31 * result + (errmsg != null ? errmsg.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Jscode2Session{" +
                "openid='" + openid + '\'' +
                ", session_key='" + session_key + '\'' +
                ", unionid='" + unionid + '\'' +
                ", errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
