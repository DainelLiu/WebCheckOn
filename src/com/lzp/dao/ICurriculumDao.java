package com.lzp.dao;

import java.util.List;

import com.lzp.model.Curriculum;
import com.lzp.util.PageBean;

public interface ICurriculumDao {
	/**
	 * 新增缺勤数据
	 * @param Curriculum
	 * @return
	 */
	public boolean save(Curriculum curriculum);
	
	/**
	 * 删除缺勤数据
	 * @param Curriculum
	 * @return
	 */
	public boolean delete(Curriculum curriculum);
	
	/**
	 * 更新缺勤数据
	 * @param Curriculum
	 * @return
	 */
	public boolean update(Curriculum curriculum);
	
	/**
	 * 查询所有缺勤数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有缺勤数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询缺勤数据
	 * @param id
	 * @return
	 */
	public Curriculum getById(String id);
	
	/**
	 * 根据其他条件查询缺勤数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询缺勤数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
