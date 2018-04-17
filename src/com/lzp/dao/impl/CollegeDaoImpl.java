package com.lzp.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.lzp.dao.ICollegeDao;
import com.lzp.model.College;
import com.lzp.util.PageBean;

@Component(value="CollegeDao")
public class CollegeDaoImpl implements ICollegeDao {
	
	private SessionFactory sessionFactory;
	
	@Resource(name="sessionFactory")//sessionFactory注入
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean save(College college) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String returnId = (String) session.save(college);
		session.getTransaction().commit();
		session.close();
		if(!"".equals(returnId) && null != returnId){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean delete(College college) {
		boolean result = false;
		try{
			if(college != null){
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				session.delete(college);
				session.getTransaction().commit();
				session.close();
				result = true;
			}
		}catch(HibernateException e){
			result = false;
		}
		return result;
	}

	@Override
	public boolean update(College college) {
		boolean result = false;
		try{
			if(college != null){
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				session.update(college);
				session.getTransaction().commit();
				session.close();
				result = true;
			}
		}catch(HibernateException e){
			result = false;
		}
		return result;
	}
	
	@Override
	public List<Object> list() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from College");
		List<Object> list = query.list();
		session.close();
		return list;
	}

	@Override
	public List<Object> listAll(PageBean page) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from College");
		query.setFirstResult(page.getRowStart());
		query.setMaxResults(page.getPageSize());
		List<Object> list = query.list();
		session.close();
		return list;
	}

	@Override
	public College getById(String id) {
		Session session = sessionFactory.openSession();
		College dto = (College)session.get(College.class, id);
		session.close();
		return dto;
	}

	@Override
	public List<Object> getByConds(String hql,PageBean page) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(page.getRowStart());
		query.setMaxResults(page.getPageSize());
		List<Object> list = query.list();
		session.close();
		return list;
	}
	
	@Override
	public List<Object> getAllByConds(String hql) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		session.close();
		return list;
	}

}
