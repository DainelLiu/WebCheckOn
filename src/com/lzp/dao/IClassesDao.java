package com.lzp.dao;

import java.util.List;

import com.lzp.model.Classes;
import com.lzp.util.PageBean;

public interface IClassesDao {
	/**
	 * 新增班级数据
	 * @param Classes
	 * @return
	 */
	public boolean save(Classes classes);
	
	/**
	 * 删除班级数据
	 * @param Classes
	 * @return
	 */
	public boolean delete(Classes classes);
	
	/**
	 * 更新班级数据
	 * @param Classes
	 * @return
	 */
	public boolean update(Classes classes);
	
	/**
	 * 查询所有班级数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有班级数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询班级数据
	 * @param id
	 * @return
	 */
	public Classes getById(String id);
	
	/**
	 * 根据其他条件查询班级数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询班级数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);
	
	//public List<Object> getByClaCollId(String claCollId);

}
