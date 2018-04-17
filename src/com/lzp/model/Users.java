package com.lzp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Users implements Serializable{

	/**
	 * 管理员实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//编号
	private String uId;
	//用户名
	private String uName;
	//密码
	private String uPassword;
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Users(String uId, String uName, String uPassword) {
		super();
		this.uId = uId;
		this.uName = uName;
		this.uPassword = uPassword;
	}
	@Override
	public String toString() {
		return "Users [uId=" + uId + ", uName=" + uName + ", uPassword=" + uPassword + "]";
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPassword() {
		return uPassword;
	}
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}
	
	
	
	
	
	
}
