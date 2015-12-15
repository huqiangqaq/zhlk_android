package com.enjoyor.soft.product.xungeng;

public class VO {

	/**编号*/
	private Integer id;
	/**仓库*/
	private String storeHouserCode;
	/**气温*/
	private String temperature;
	/**气湿*/
	private String humidity;
	/**天气*/
	private String weather;
	/**风向*/
	private String wind;
	/**仓温*/
	private String storeTemperature;
	/**仓湿*/
	private String storeHumidity;
	/**品种*/
	private String itemName;
	/**库存数量*/
	private String number;
	/**来源*/
	private String source;
	/**入库时间*/
	private String inDate;
	/**入库水分*/
	private String inWater;
	/**现有水分*/
	private String nowWater;
	/**虫种*/
	private String insect;
	/**害虫密度*/
	private String insectNumber;
	/**查仓人员*/
	private String userName;
	/**粮情分析*/
	private String Comments;
	/**测温点温度串 ：在3列12个温度数据以”，“隔开，8行以”；“隔开*/
	private String thermometerStr;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStoreHouserCode() {
		return storeHouserCode;
	}
	public void setStoreHouserCode(String storeHouserCode) {
		this.storeHouserCode = storeHouserCode;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getStoreTemperature() {
		return storeTemperature;
	}
	public void setStoreTemperature(String storeTemperature) {
		this.storeTemperature = storeTemperature;
	}
	public String getStoreHumidity() {
		return storeHumidity;
	}
	public void setStoreHumidity(String storeHumidity) {
		this.storeHumidity = storeHumidity;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getInWater() {
		return inWater;
	}
	public void setInWater(String inWater) {
		this.inWater = inWater;
	}
	public String getNowWater() {
		return nowWater;
	}
	public void setNowWater(String nowWater) {
		this.nowWater = nowWater;
	}
	public String getInsect() {
		return insect;
	}
	public void setInsect(String insect) {
		this.insect = insect;
	}
	public String getInsectNumber() {
		return insectNumber;
	}
	public void setInsectNumber(String insectNumber) {
		this.insectNumber = insectNumber;
	}
	public String getComments() {
		return Comments;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	public String getThermometerStr() {
		return thermometerStr;
	}
	public void setThermometerStr(String thermometerStr) {
		this.thermometerStr = thermometerStr;
	}
	
	
}
