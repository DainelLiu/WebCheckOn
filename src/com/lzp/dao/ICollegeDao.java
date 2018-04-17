package com.lzp.dao;

import java.util.List;

import com.lzp.model.College;
import com.lzp.util.PageBean;

public interface ICollegeDao {
	/**
	 * 新增院系数据
	 * @param College
	 * @return
	 */
	public boolean save(College college);
	
	/**
	 * 删除院系数据
	 * @param College
	 * @return
	 */
	public boolean delete(College college);
	
	/**
	 * 更新院系数据
	 * @param College
	 * @return
	 */
	public boolean update(College college);
	
	/**
	 * 查询所有院系数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有院系数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询院系数据
	 * @param id
	 * @return
	 */
	public College getById(String id);
	
	/**
	 * 根据其他条件查询院系数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询院系数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
