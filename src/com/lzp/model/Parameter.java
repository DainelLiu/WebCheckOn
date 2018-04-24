package com.lzp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Parameter implements Serializable{

	/**
	 * 系统参数实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//系统参数编号
	private String pId;
	//请假次数
	private String pLeaveTableNum;
	//缺勤次数
	private String pAbsenceNum;
	//当前学期
	private String pNewTerm;
	public Parameter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Parameter(String pId, String pLeaveTableNum, String pAbsenceNum,String pNewTerm) {
		super();
		this.pId = pId;
		this.pLeaveTableNum = pLeaveTableNum;
		this.pAbsenceNum = pAbsenceNum;
		this.pNewTerm = pNewTerm;
	}
	
	@Override
	public String toString() {
		return "Parameter [pId=" + pId + ", pLeaveTableNum=" + pLeaveTableNum + ", pAbsenceNum=" + pAbsenceNum + ", pNewTerm="
				+ pNewTerm + "]";
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getpLeaveTableNum() {
		return pLeaveTableNum;
	}
	public void setpLeaveTableNum(String pLeaveTableNum) {
		this.pLeaveTableNum = pLeaveTableNum;
	}
	public String getpAbsenceNum() {
		return pAbsenceNum;
	}
	public void setpAbsenceNum(String pAbsenceNum) {
		this.pAbsenceNum = pAbsenceNum;
	}
	public String getpNewTerm() {
		return pNewTerm;
	}
	public void setpNewTerm(String pNewTerm) {
		this.pNewTerm = pNewTerm;
	}
	
	
	
	
}
