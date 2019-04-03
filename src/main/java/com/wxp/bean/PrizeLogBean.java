package com.wxp.bean;
import java.io.Serializable;
import java.util.Date;


public class PrizeLogBean implements Serializable {

	//兑奖记录id
	private Integer id;

//	扫描的图片二维码信息
	private String srcId;

	//奖品id
	private Integer prizeId;

	//奖品价格
	private Integer price;

	//兑奖人id
	private Integer buyerId;

	//收货地址
	private String address;

	private String adname;

	private String adphone;

	//兑奖状态，1未兑奖，2已兑奖，3已发货， 4已过期
	private Integer status;
	//省份
	private String province;

	//	城市
	private String city;
	//	邮编
	private String postCode;


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

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getAdphone() {
        return adphone;
    }

    public void setAdphone(String adphone) {
        this.adphone = adphone;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}