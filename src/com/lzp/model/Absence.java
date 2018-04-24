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
public class Absence implements Serializable{

	/**
	 * 缺勤表实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//缺勤表编号
	private String aId;
	//学生编号
	private Student aSId;
	//课程表编号
	private ScheduleDetails aDId;
	public Absence() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Absence(String aId, Student aSId, ScheduleDetails aDId) {
		super();
		this.aId = aId;
		this.aSId = aSId;
		this.aDId = aDId;
	}
	@Override
	public String toString() {
		return "Absence [aId=" + aId + ", aSId=" + aSId + ", aDId=" + aDId + "]";
	}
	
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getaId() {
		return aId;
	}
	public void setaId(String aId) {
		this.aId = aId;
	}
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="aSId")
	public Student getaSId() {
		return aSId;
	}
	public void setaSId(Student aSId) {
		this.aSId = aSId;
	}
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="aDId")
	public ScheduleDetails getaDId() {
		return aDId;
	}
	public void setaDId(ScheduleDetails aDId) {
		this.aDId = aDId;
	}
	
	
	
}
