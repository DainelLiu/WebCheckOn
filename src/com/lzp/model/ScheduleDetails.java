package com.lzp.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class ScheduleDetails implements Serializable{

	/**
	 *课程表详情实体类 
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//详情编号
	private String dId;
	//课程表编号
	private Schedule dSchId;
	//时间
	private String dTime;
	//课程编号
	private Curriculum dCurrId;
	//上课时段编号
	private Interval dInId;
	public ScheduleDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ScheduleDetails(String dId, Schedule dSchId, String dTime, Curriculum dCurrId, Interval dInId) {
		super();
		this.dId = dId;
		this.dSchId = dSchId;
		this.dTime = dTime;
		this.dCurrId = dCurrId;
		this.dInId = dInId;
	}
	@Override
	public String toString() {
		return "ScheduleDetails [dId=" + dId + ", dSchId=" + dSchId + ", dTime=" + dTime + ", dCurrId=" + dCurrId
				+ ", dInId=" + dInId + "]";
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getdId() {
		return dId;
	}
	public void setdId(String dId) {
		this.dId = dId;
	}
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="dSchId")
	public Schedule getdSchId() {
		return dSchId;
	}
	public void setdSchId(Schedule dSchId) {
		this.dSchId = dSchId;
	}
	public String getdTime() {
		return dTime;
	}
	public void setdTime(String dTime) {
		this.dTime = dTime;
	}
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="dCurrId")
	public Curriculum getdCurrId() {
		return dCurrId;
	}
	public void setdCurrId(Curriculum dCurrId) {
		this.dCurrId = dCurrId;
	}
	@OneToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="dInId")
	public Interval getdInId() {
		return dInId;
	}
	public void setdInId(Interval dInId) {
		this.dInId = dInId;
	}
	
	
	
	
	
}
