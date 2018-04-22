package com.lzp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Intervals implements Serializable{

	/**
	 * 时间段实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//时间段编号
	private String inId;
	//时间段描述
	private String inContent;
	public Intervals() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Intervals(String inId, String inContent) {
		super();
		this.inId = inId;
		this.inContent = inContent;
	}
	@Override
	public String toString() {
		return "Intervals [inId=" + inId + ", inContent=" + inContent + "]";
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getinId() {
		return inId;
	}
	public void setinId(String inId) {
		this.inId = inId;
	}
	public String getinContent() {
		return inContent;
	}
	public void setinContent(String inContent) {
		this.inContent = inContent;
	}
	
	
	
	
}
