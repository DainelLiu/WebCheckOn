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

import com.lzp.dao.ICollegeDao;
import com.lzp.dao.ITeacherDao;
import com.lzp.model.Classes;
import com.lzp.model.College;
import com.lzp.model.Teacher;
import com.lzp.util.JsonUtil;
import com.lzp.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
//表示继承的父包
@Namespace(value = "/teacher")
public class TeacherAction {
	
	private ITeacherDao teacherDao;
	private ICollegeDao collegeDao;

	public ICollegeDao getCollegeDao() {
		return collegeDao;
	}

	@Resource(name = "CollegeDao")
	public void setCollegeDao(ICollegeDao collegeDao) {
		this.collegeDao = collegeDao;
	}
	
	public ITeacherDao getTeacherDao() {
		return teacherDao;
	}
	@Resource(name="TeacherDao")
	public void setTeacherDao(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}
	

	/**
	 * 保存缺勤信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="save")
	public String save() throws IOException{
		Teacher teacher = new Teacher();
		String tNumber = ServletActionContext.getRequest().getParameter("tNumber");
		String tPassword = ServletActionContext.getRequest().getParameter("tPassword");
		String tRealName = ServletActionContext.getRequest().getParameter("tRealName");
		String tSex = ServletActionContext.getRequest().getParameter("tSex");
		String tPhone = ServletActionContext.getRequest().getParameter("tPhone");
		String tCollId = ServletActionContext.getRequest().getParameter("tCollId");
		College college = collegeDao.getById(tCollId);
		teacher.settNumber(tNumber);
		teacher.settPassword(tPassword);
		teacher.settRealName(tRealName);
		teacher.settSex(tSex);
		teacher.settPhone(tPhone);
		teacher.settCollId(college);
		
		
		
		JSONObject jobj = new JSONObject();
		if(teacherDao.save(teacher)) {
			jobj.put("mes", "保存成功!");
			jobj.put("status", "success");
		}else {
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
		
	}
	/**
	 * 删除缺勤信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="delete")
	public String delete() throws IOException{
		
		
		String tId = ServletActionContext.getRequest().getParameter("tId");
		Teacher teacher = teacherDao.getById(tId);
		JSONObject jobj = new JSONObject();
		if(teacherDao.delete(teacher)){
			//save success
			jobj.put("mes", "删除成功!");
			jobj.put("status", "success");
		}else{
			//save failed
			jobj.put("mes", "删除失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	/**
	 * 修改缺勤信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="update")
	public String update() throws IOException{
		
		String tId = ServletActionContext.getRequest().getParameter("tId");
		
		Teacher teacher = teacherDao.getById(tId);
		JSONObject jobj = new JSONObject();
		
		if(teacherDao.update(teacher)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
		}else{
			//save failed
			jobj.put("mes", "更新失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	/**
	 * 根据id信息
	 * @return
	 * @throws IOException
	 */
	@Action(value="getById")
	public String getById() throws IOException{
		String tId = ServletActionContext.getRequest().getParameter("tId");
		Teacher teacher = teacherDao.getById(tId);
		JSONObject jobj = new JSONObject();
		if(teacher != null){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	/**
	 * 获取品牌(类型)列表
	 * @return
	 * @throws IOException
	 */
	@Action(value="list")
	public String list() throws IOException{
		//分页
		String pageNumStr = ServletActionContext.getRequest().getParameter("pageNum");
		int pageNum = 1;
		if(pageNumStr!=null && !"".equals(pageNumStr)){
			pageNum = Integer.parseInt(pageNumStr);
		}
		List<Object> list = new ArrayList<Object>();
		List<Object> teacherTypelist = teacherDao.list();//获取所有类型数据，不带分页
		PageBean page=null;
		if(teacherTypelist.size()>0){
			page = new PageBean(teacherTypelist.size(),pageNum,5);
			list = teacherDao.listAll(page);//带分页
		}
		JSONObject jobj = new JSONObject();
		if(teacherTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(list));
			jobj.put("pageTotal", page.getPageCount());
			jobj.put("pageNum", page.getPageNum());
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value="listAll")
	public String listAll() throws IOException{

		List<Object> teacherTypelist = teacherDao.list();//获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(teacherTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(teacherTypelist));
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value="listAllByCollId")
	public String listAllByCollId() throws IOException{
		String claCollId = ServletActionContext.getRequest().getParameter("claCollId");
		String hql="from Teacher where 1=1 and tCollId = '" + claCollId + "'";
		System.out.println(hql);
		List<Object> tescherListByCollId = teacherDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(tescherListByCollId.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("tescherListByCollId", JsonUtil.toJsonByListObj(tescherListByCollId));
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	

}
