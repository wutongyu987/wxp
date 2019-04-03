package com.wxp.bean;
import java.io.Serializable;
import java.util.Date;


public class AddressBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
	//id
	private Integer id;

	//购买人id
	private Integer buyerId;
//省份
	private String province;

//	城市
	private String city;

	//收货地址
	private String address;

//	邮编
	private String postCode;
//	收货人姓名
	private String name;
	//收货人电话号码
	private String phone;

//	是否是默认收货地址
	private Integer def;

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

	public Integer getBuyerId(){
		return this.buyerId;
	}

	public void setBuyerId(Integer buyerId){
		this.buyerId = buyerId;
	}

	public String getAddress(){
		return this.address;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setPhone(String phone){
		this.phone = phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Integer getDef() {
		return def;
	}

	public void setDef(Integer def) {
		this.def = def;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}