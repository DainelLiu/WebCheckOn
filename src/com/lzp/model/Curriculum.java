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
public class Curriculum implements Serializable{

	/**
	 * 课程实体类
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//课程编号
	private String currId;
	//课程名称
	private String currName;
	//教师编号
	private Teacher currTId;
	@Override
	public String toString() {
		return "Curriculum [currId=" + currId + ", currName=" + currName + ", currTId=" + currTId + "]";
	}
	public Curriculum(String currId, String currName, Teacher currTId) {
		super();
		this.currId = currId;
		this.currName = currName;
		this.currTId = currTId;
	}
	public Curriculum() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getcurrId() {
		return currId;
	}
	public void setcurrId(String currId) {
		this.currId = currId;
	}
	public String getcurrName() {
		return currName;
	}
	public void setcurrName(String currName) {
		this.currName = currName;
	}
	@OneToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="currTId")
	public Teacher getcurrTId() {
		return currTId;
	}
	public void setcurrTId(Teacher currTId) {
		this.currTId = currTId;
	}
	
	
	
	
}
