package com.lzp.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;

import com.lzp.dao.IClassesDao;
import com.lzp.dao.ICollegeDao;
import com.lzp.model.Classes;
import com.lzp.model.College;
import com.lzp.model.Student;
import com.lzp.util.JsonUtil;
import com.lzp.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/classes")
public class ClassesAction {

	private IClassesDao classesDao;

	private ICollegeDao collegeDao;

	public ICollegeDao getCollegeDao() {
		return collegeDao;
	}

	@Resource(name = "CollegeDao")
	public void setCollegeDao(ICollegeDao collegeDao) {
		this.collegeDao = collegeDao;
	}

	public IClassesDao getClassesDao() {
		return classesDao;
	}

	@Resource(name = "ClassesDao")
	public void setClassesDao(IClassesDao classesDao) {
		this.classesDao = classesDao;
	}

	/**
	 * 保存缺勤信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "save")
	public String save() throws IOException {
		Classes classes = new Classes();
		String claName = ServletActionContext.getRequest().getParameter("claName");
		String claCollId = ServletActionContext.getRequest().getParameter("claCollId");
		College college = collegeDao.getById(claCollId);
		classes.setclaName(claName);
		classes.setclaCollId(college);
		JSONObject jobj = new JSONObject();
		if (classesDao.save(classes)) {
			jobj.put("mes", "保存成功!");
			jobj.put("status", "success");
		} else {
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;

	}

	/**
	 * 删除缺勤信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "delete")
	public String delete() throws IOException {

		String claId = ServletActionContext.getRequest().getParameter("claId");
		Classes classes = classesDao.getById(claId);
		JSONObject jobj = new JSONObject();
		if (classesDao.delete(classes)) {
			// save success
			jobj.put("mes", "删除成功!");
			jobj.put("status", "success");
		} else {
			// save failed
			jobj.put("mes", "删除失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	/**
	 * 修改缺勤信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "update")
	public String update() throws IOException {

		String claId = ServletActionContext.getRequest().getParameter("claId");

		Classes classes = classesDao.getById(claId);
		JSONObject jobj = new JSONObject();

		if (classesDao.update(classes)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
		} else {
			// save failed
			jobj.put("mes", "更新失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	/**
	 * 根据id信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getById")
	public String getById() throws IOException {
		String claId = ServletActionContext.getRequest().getParameter("claId");
		Classes classes = classesDao.getById(claId);
		JSONObject jobj = new JSONObject();
		if (classes != null) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	/**
	 * 获取品牌(类型)列表
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "list")
	public String list() throws IOException {
		// 分页
		String pageNumStr = ServletActionContext.getRequest().getParameter("pageNum");
		int pageNum = 1;
		if (pageNumStr != null && !"".equals(pageNumStr)) {
			pageNum = Integer.parseInt(pageNumStr);
		}
		List<Object> list = new ArrayList<Object>();
		List<Object> classesTypelist = classesDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (classesTypelist.size() > 0) {
			page = new PageBean(classesTypelist.size(), pageNum, 5);
			list = classesDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (classesTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(list));
			jobj.put("pageTotal", page.getPageCount());
			jobj.put("pageNum", page.getPageNum());
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	@Action(value = "listAll")
	public String listAll() throws IOException {

		List<Object> classesTypelist = classesDao.list();// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		JSONArray list = new JSONArray();
		if (classesTypelist.size() > 0) {
			for (int i = 0; i < classesTypelist.size(); i++) {
				Classes classes = (Classes) classesTypelist.get(i);
				String collegeName = classes.getclaCollId().getcollName();
				String classesName = classes.getclaName();
				String claId = classes.getclaId();
				jobj.put("claId",claId);
				jobj.put("collegeName", collegeName);
				jobj.put("claName", classesName);
				list.add(jobj);
			}
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(list));
		} else {
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}

		/*
		 * if(classesTypelist.size() > 0){ //save success jobj.put("mes",
		 * "获取成功!"); jobj.put("status", "success"); jobj.put("data",
		 * JsonUtil.toJsonByListObj(classesTypelist)); jobj.put("college",
		 * JsonUtil.toJsonByListObj(collegelist)); }else{ //save failed
		 * jobj.put("mes", "获取失败!"); jobj.put("status", "error"); }
		 */
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value = "getByClaCollId")
	public String getByClaCollId() throws IOException {
		String claCollId = ServletActionContext.getRequest().getParameter("claCollId");
		String hql="from Classes where 1=1 and claCollId = '" + claCollId + "'";
		System.out.println(hql);
		List<Object> studentListByCollId = classesDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (studentListByCollId.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("studentListByCollId", JsonUtil.toJsonByListObj(studentListByCollId));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}


}
