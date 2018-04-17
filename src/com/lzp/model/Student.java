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
public class Student implements Serializable{

	/**
	 * 学生实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//学生变化
	private String sId;
	//学号
	private String sNumber;
	//密码
	private String sPassword;
	//真实姓名
	private String sRealName;
	//性别
	private String sSex;
	//联系电话
	private String sPhone;
	//所属班级
	private Classes sClaId;
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String sId, String sNumber, String sPassword, String sRealName, String sSex, String sPhone,
			Classes sClaId) {
		super();
		this.sId = sId;
		this.sNumber = sNumber;
		this.sPassword = sPassword;
		this.sRealName = sRealName;
		this.sSex = sSex;
		this.sPhone = sPhone;
		this.sClaId = sClaId;
	}
	@Override
	public String toString() {
		return "Student [sId=" + sId + ", sNumber=" + sNumber + ", sPassword=" + sPassword + ", sRealName=" + sRealName
				+ ", sSex=" + sSex + ", sPhone=" + sPhone + ", sClaId=" + sClaId + "]";
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getsNumber() {
		return sNumber;
	}
	public void setsNumber(String sNumber) {
		this.sNumber = sNumber;
	}
	public String getsPassword() {
		return sPassword;
	}
	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}
	public String getsRealName() {
		return sRealName;
	}
	public void setsRealName(String sRealName) {
		this.sRealName = sRealName;
	}
	public String getsSex() {
		return sSex;
	}
	public void setsSex(String sSex) {
		this.sSex = sSex;
	}
	public String getsPhone() {
		return sPhone;
	}
	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="sClaId")
	public Classes getsClaId() {
		return sClaId;
	}
	public void setsClaId(Classes sClaId) {
		this.sClaId = sClaId;
	}
	
	
	
	
	
}
