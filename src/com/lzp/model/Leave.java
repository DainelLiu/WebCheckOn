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
public class Leave implements Serializable{

	/**
	 * 请假表实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//请假表编号
	private String lId;
	//课程表详情编号
	private ScheduleDetails lDId;
	//请假事由
	private String lReason;
	//学生编号
	private Student lSId;
	//请假状态
	private String lSign;
	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Leave(String lId, ScheduleDetails lDId, String lReason, Student lSId, String lSign) {
		super();
		this.lId = lId;
		this.lDId = lDId;
		this.lReason = lReason;
		this.lSId = lSId;
		this.lSign = lSign;
	}
	@Override
	public String toString() {
		return "Leave [lId=" + lId + ", lDId=" + lDId + ", lReason=" + lReason + ", lSId=" + lSId + ", lSign=" + lSign
				+ "]";
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getlId() {
		return lId;
	}
	public void setlId(String lId) {
		this.lId = lId;
	}
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="lDId")
	public ScheduleDetails getlDId() {
		return lDId;
	}
	public void setlDId(ScheduleDetails lDId) {
		this.lDId = lDId;
	}
	public String getlReason() {
		return lReason;
	}
	public void setlReason(String lReason) {
		this.lReason = lReason;
	}
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="lSId")
	public Student getlSId() {
		return lSId;
	}
	public void setlSId(Student lSId) {
		this.lSId = lSId;
	}
	public String getlSign() {
		return lSign;
	}
	public void setlSign(String lSign) {
		this.lSign = lSign;
	}
	
	
	
	
	
	
}
