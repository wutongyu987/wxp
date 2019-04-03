package com.wxp.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * create by ff on 2018/12/2
 */
public class PrizeCodeBean implements Serializable {
    private static final long serialVersionUID = 2L;

//    主键id
    private Integer id;
//  二维码code
    private String uid;
// 使用状态
    private Integer status;
//    二维码图片
    private String img;

    //是否使用，1使用，0禁用
    private Integer useflag;

    //插入时间
    private Date insertTime;

    //插入用户id
    private Integer insertId;

    //更新时间
    private Date updateTime;

    //更新人id
    private Integer updateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUseflag() {
        return useflag;
    }

    public void setUseflag(Integer useflag) {
        this.useflag = useflag;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getInsertId() {
        return insertId;
    }

    public void setInsertId(Integer insertId) {
        this.insertId = insertId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
