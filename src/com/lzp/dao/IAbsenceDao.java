package com.lzp.dao;

import java.util.List;

import com.lzp.model.Absence;
import com.lzp.util.PageBean;

public interface IAbsenceDao {
	/**
	 * 新增缺勤数据
	 * @param Absence
	 * @return
	 */
	public boolean save(Absence absence);
	
	/**
	 * 删除缺勤数据
	 * @param Absence
	 * @return
	 */
	public boolean delete(Absence absence);
	
	/**
	 * 更新缺勤数据
	 * @param Absence
	 * @return
	 */
	public boolean update(Absence absence);
	
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
	public Absence getById(String id);
	
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
