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
public class Teacher implements Serializable{

	/**
	 * 教师实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//教师编号
	private String tId;
	//工号
	private String tNumber;
	//密码
	private String tPassword;
	//真实姓名
	private String tRealName;
	//性别
	private String tSex;
	//联系方式
	private String tPhone;
	//所属院系
	private College tCollId;
	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Teacher(String tId, String tNumber, String tPassword, String tRealName, String tSex, String tPhone,
			College tCollId) {
		super();
		this.tId = tId;
		this.tNumber = tNumber;
		this.tPassword = tPassword;
		this.tRealName = tRealName;
		this.tSex = tSex;
		this.tPhone = tPhone;
		this.tCollId = tCollId;
	}
	@Override
	public String toString() {
		return "Teacher [tId=" + tId + ", tNumber=" + tNumber + ", tPassword=" + tPassword + ", tRealName=" + tRealName
				+ ", tSex=" + tSex + ", tPhone=" + tPhone + ", tCollId=" + tCollId + "]";
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String gettId() {
		return tId;
	}
	public void settId(String tId) {
		this.tId = tId;
	}
	public String gettNumber() {
		return tNumber;
	}
	public void settNumber(String tNumber) {
		this.tNumber = tNumber;
	}
	public String gettPassword() {
		return tPassword;
	}
	public void settPassword(String tPassword) {
		this.tPassword = tPassword;
	}
	public String gettRealName() {
		return tRealName;
	}
	public void settRealName(String tRealName) {
		this.tRealName = tRealName;
	}
	public String gettSex() {
		return tSex;
	}
	public void settSex(String tSex) {
		this.tSex = tSex;
	}
	public String gettPhone() {
		return tPhone;
	}
	public void settPhone(String tPhone) {
		this.tPhone = tPhone;
	}
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="tCollId")
	public College gettCollId() {
		return tCollId;
	}
	public void settCollId(College tCollId) {
		this.tCollId = tCollId;
	}
	
	
	
	
	
}
