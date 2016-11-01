package com.bailian.entity;

import java.io.Serializable;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 推荐返回商品
 * @author haojutao
 *
 */
public class Goods implements Serializable {

	private String sid;
	private String goods_sales_name;
	private String sale_price;
	private String url;
	private String from;
	@JsonIgnore
	private String category_name;
	@JsonIgnore
	private String category_id;
	@JsonIgnore
	private String pro_sid;
	//迭代前假设每个都架上可售，先上接口后更新数据 0 不可售
	@JsonIgnore
	private int sale_status = 4;
	
	//用户mp商户商品推荐
	@JsonIgnore
	private int yun_type;
	//4000商家商品，必有store_sid
	@JsonIgnore
	private int com_sid;
	//商铺ID 
	@JsonIgnore
	private String store_sid;
	
	//1有库存 0无库存
	@JsonIgnore
	private int stock = 1;
	
	public Goods()
	{
		
	}
	@Override
	public String toString() {
		return "Goods [sid=" + sid + ", goods_sales_name=" + goods_sales_name
				+ ", sale_price=" + sale_price + ", url=" + url + ", from="
				+ from + ", category_name=" + category_name + ", category_id="
				+ category_id + ", pro_sid=" + pro_sid + "]";
	}

	public Goods(Map<String,String> map)
	{
		this.sid = map.get("sid");
		this.goods_sales_name = map.get("goods_sales_name");
		this.sale_price = map.get("sale_price");
		this.url = map.get("url");
		this.from = map.get("from");
		this.category_id = map.get("category_id");
		this.category_name = map.get("category_name");
		this.pro_sid = map.get("pro_sid");
		String _sale_status = map.get("sale_status");
		if(_sale_status!=null)
		{
			this.sale_status = Integer.parseInt(_sale_status);
		}
		String _com_sid = map.get("com_sid");
		if(_com_sid != null)
		{
			this.com_sid = (int) Double.parseDouble(_com_sid);
		}
		String _yun_type = map.get("yun_type");
		if(_yun_type != null)
		{
			this.yun_type = (int) Double.parseDouble(_yun_type);
		}
		String _store_sid = map.get("store_sid");
		if(_store_sid != null)
		{
			this.store_sid = _store_sid;
		}
		
		String _stock = map.get("stock");
		if(_stock != null)
		{
			this.stock = Integer.parseInt(_stock);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category_id == null) ? 0 : category_id.hashCode());
		result = prime * result + ((pro_sid == null) ? 0 : pro_sid.hashCode());
		return result;
	}
	/**
	 * 判断商品相等用的是品类和产品ID,避免同一产品多个sku，出现颜色、尺码不同的多个商品
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Goods other = (Goods) obj;
		if (category_id == null) {
			if (other.category_id != null)
				return false;
		} else if (!category_id.equals(other.category_id))
			return false;
		if (pro_sid == null) {
			if (other.pro_sid != null)
				return false;
		} else if (!pro_sid.equals(other.pro_sid))
			return false;
		return true;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getGoods_sales_name() {
		return goods_sales_name;
	}
	public void setGoods_sales_name(String goods_sales_name) {
		this.goods_sales_name = goods_sales_name;
	}
	public String getSale_price() {
		return sale_price;
	}
	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getPro_sid() {
		return pro_sid;
	}
	public void setPro_sid(String pro_sid) {
		this.pro_sid = pro_sid;
	}
	public int getSale_status() {
		return sale_status;
	}
	public void setSale_status(int sale_status) {
		this.sale_status = sale_status;
	}
	public int getYun_type() {
		return yun_type;
	}
	public void setYun_type(int yun_type) {
		this.yun_type = yun_type;
	}
	public int getCom_sid() {
		return com_sid;
	}
	public void setCom_sid(int com_sid) {
		this.com_sid = com_sid;
	}
	public String getStore_sid() {
		return store_sid;
	}
	public void setStore_sid(String store_sid) {
		this.store_sid = store_sid;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	
}
