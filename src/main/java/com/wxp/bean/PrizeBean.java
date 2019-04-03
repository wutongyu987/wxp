package com.wxp.bean;
import java.io.Serializable;
import java.util.Date;


public class PrizeBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
	//奖品id
	private Integer id;

	//奖品名称
	private String name;

	//奖品类型
	private String type;

	//奖品数量
	private Integer num;

//	奖品状态
	private Integer status;

//	奖品图片
	private String pimg;

	//兑奖开始日期
	private Date startTime;

	//兑奖截止日期
	private Date endTime;

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

	private String zipPath;

	public String getZipPath() {
		return zipPath;
	}

	public void setZipPath(String zipPath) {
		this.zipPath = zipPath;
	}

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getType(){
		return this.type;
	}

	public void setType(String type){
		this.type = type;
	}

	public Integer getNum(){
		return this.num;
	}

	public void setNum(Integer num){
		this.num = num;
	}

	public Date getStartTime(){
		return this.startTime;
	}

	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}

	public Date getEndTime(){
		return this.endTime;
	}

	public void setEndTime(Date endTime){
		this.endTime = endTime;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }
}