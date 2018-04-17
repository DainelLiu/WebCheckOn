package com.lzp.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Classes implements Serializable{

	/**
	 * 班级实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//班级编号
	private String claId;
	//班级名称
	private String claName;
	//院系名称
	private College claCollId;
	@Override
	public String toString() {
		return "Classes [claId=" + claId + ", claName=" + claName + ", claCollId=" + claCollId + "]";
	}
	public Classes(String claId, String claName, College claCollId) {
		super();
		this.claId = claId;
		this.claName = claName;
		this.claCollId = claCollId;
	}
	public Classes() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getclaId() {
		return claId;
	}
	public void setclaId(String claId) {
		this.claId = claId;
	}
	public String getclaName() {
		return claName;
	}
	public void setclaName(String claName) {
		this.claName = claName;
	}
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="claCollId")
	public College getclaCollId() {
		return claCollId;
	}
	public void setclaCollId(College claCollId) {
		this.claCollId = claCollId;
	}
	
	
	
	
}
