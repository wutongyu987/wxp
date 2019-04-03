package com.wxp.bean;

import java.io.Serializable;
import java.util.Date;

public class BuyerBean implements Serializable {
    //id
    private Integer id;

    //用户名
    private String nickName;

    //微信openId
    private String openId;

    //性别，1时是男性，2时是女性，0时是未知
    private Integer sex;

    //头像链接
    private String avatarUrl;

//    积分
    private Integer points;

//    点赞数量
    private Integer zan;

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


    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getNickName(){
        return this.nickName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String getOpenId(){
        return this.openId;
    }

    public void setOpenId(String openId){
        this.openId = openId;
    }

    public Integer getSex(){
        return this.sex;
    }

    public void setSex(Integer sex){
        this.sex = sex;
    }

    public String getAvatarUrl(){
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }

    public Integer getUseflag(){
        return this.useflag;
    }

    public void setUseflag(Integer useflag){
        this.useflag = useflag;
    }

    public Date getInsertTime(){
        return this.insertTime;
    }

    public void setInsertTime(Date insertTime){
        this.insertTime = insertTime;
    }

    public Integer getInsertId(){
        return this.insertId;
    }

    public void setInsertId(Integer insertId){
        this.insertId = insertId;
    }

    public Date getUpdateTime(){
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    public Integer getUpdateId(){
        return this.updateId;
    }

    public void setUpdateId(Integer updateId){
        this.updateId = updateId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getZan() {
        return zan;
    }

    public void setZan(Integer zan) {
        this.zan = zan;
    }
}
