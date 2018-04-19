package com.lzp.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Schedule implements Serializable{

	/**
	 * 课程表实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//课程表编号
	private String schId;
	//上课班级名称
	private Classes schClaId;
	//所属学期
	private String schSemester;
	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Schedule(String schId, Classes schClaId, String schSemester) {
		super();
		this.schId = schId;
		this.schClaId = schClaId;
		this.schSemester = schSemester;
	}
	@Override
	public String toString() {
		return "Schedule [schId=" + schId + ", schClaId=" + schClaId + ", schSemester=" + schSemester + "]";
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getschId() {
		return schId;
	}
	public void setschId(String schId) {
		this.schId = schId;
	}
	@OneToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="aUId")
	public Classes getschClaId() {
		return schClaId;
	}
	public void setschClaId(Classes schClaId) {
		this.schClaId = schClaId;
	}
	public String getschSemester() {
		return schSemester;
	}
	public void setschSemester(String schSemester) {
		this.schSemester = schSemester;
	}
	
}
