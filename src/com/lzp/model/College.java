package com.lzp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class College implements Serializable{

	/**
	 * 院系编号
	 */
	private static final long serialVersionUID = 8364319449645007823L;
	//院系编号
	private String collId;
	//院系名称
	private String collName;
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getcollId() {
		return collId;
	}
	public void setcollId(String collId) {
		this.collId = collId;
	}
	public String getcollName() {
		return collName;
	}
	public void setcollName(String collName) {
		this.collName = collName;
	}
	@Override
	public String toString() {
		return "College [collId=" + collId + ", collName=" + collName + "]";
	}
	public College(String collId, String collName) {
		super();
		this.collId = collId;
		this.collName = collName;
	}
	public College() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
