package com.lzp.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;

import com.lzp.dao.IClassesDao;
import com.lzp.dao.IIntervalsDao;
import com.lzp.dao.IScheduleDetailsDao;
import com.lzp.dao.IStudentDao;
import com.lzp.model.Classes;
import com.lzp.model.Intervals;
import com.lzp.model.Schedule;
import com.lzp.model.ScheduleDetails;
import com.lzp.model.Student;
import com.lzp.util.JsonUtil;
import com.lzp.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/student")
public class StudentAction {

	private IStudentDao studentDao;

	public IStudentDao getStudentDao() {
		return studentDao;
	}

	@Resource(name = "StudentDao")
	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}

	private IClassesDao classesDao;

	public IClassesDao getClassesDao() {
		return classesDao;
	}

	@Resource(name = "ClassesDao")
	public void setClassestDao(IClassesDao classesDao) {
		this.classesDao = classesDao;
	}

	private IScheduleDetailsDao scheduleDetailsDao;

	public IScheduleDetailsDao getScheduleDetailsDao() {
		return scheduleDetailsDao;
	}

	@Resource(name = "ScheduleDetailsDao")
	public void setScheduleDetailsDao(IScheduleDetailsDao scheduleDetailsDao) {
		this.scheduleDetailsDao = scheduleDetailsDao;
	}
	
	private IIntervalsDao intervalDao;

	public IIntervalsDao getIntervalsDao() {
		return intervalDao;
	}

	@Resource(name = "IntervalsDao")
	public void setIntervalsDao(IIntervalsDao intervalDao) {
		this.intervalDao = intervalDao;
	}

	/**
	 * 保存缺勤信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="save")
	public String save() throws IOException{
		
		Student student = new Student();
		
		String sNumber = ServletActionContext.getRequest().getParameter("sNumber");
		String sPassword = ServletActionContext.getRequest().getParameter("sPassword");
		String sRealName = ServletActionContext.getRequest().getParameter("sRealName");
		String sSex = ServletActionContext.getRequest().getParameter("sSex");
		String sPhone = ServletActionContext.getRequest().getParameter("sPhone");
		String sClaId = ServletActionContext.getRequest().getParameter("sClaId");
		Classes classes = classesDao.getById(sClaId);
		student.setsNumber(sNumber);
		student.setsPassword(sPassword);
		student.setsRealName(sRealName);
		student.setsSex(sSex);
		student.setsPhone(sPhone);
		student.setsClaId(classes);

		
		JSONObject jobj = new JSONObject();
		if(studentDao.save(student)) {
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
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "delete")
	public String delete() throws IOException {

		String sId = ServletActionContext.getRequest().getParameter("sId");
		Student student = studentDao.getById(sId);
		JSONObject jobj = new JSONObject();
		if (studentDao.delete(student)) {
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

		String sId = ServletActionContext.getRequest().getParameter("sId");
		String sPassword = ServletActionContext.getRequest().getParameter("sPassword");
		String sRealName = ServletActionContext.getRequest().getParameter("sRealName");
		String sPhone = ServletActionContext.getRequest().getParameter("sPhone");
		String sSex = ServletActionContext.getRequest().getParameter("sSex");

		Student student = studentDao.getById(sId);
		
		JSONObject jobj = new JSONObject();
		student.setsPassword(sPassword);
		student.setsRealName(sRealName);
		student.setsPhone(sPhone);
		student.setsSex(sSex);
		if (studentDao.update(student)) {
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
		String sId = ServletActionContext.getRequest().getParameter("sId");
		Student student = studentDao.getById(sId);
		JSONObject jobj = new JSONObject();
		if (student != null) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("date", student);
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
		List<Object> studentTypelist = studentDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (studentTypelist.size() > 0) {
			page = new PageBean(studentTypelist.size(), pageNum, 5);
			list = studentDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (studentTypelist.size() > 0) {
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

		List<Object> studentTypelist = studentDao.list();// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (studentTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(studentTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	
	@Action(value = "listStudentByAbence")
	public String listStudentByAbence() throws IOException {
		
		String dTime = URLDecoder.decode(ServletActionContext.getRequest().getParameter("dTime"), "utf-8");
		String inContent = ServletActionContext.getRequest().getParameter("inContent");
		
		String hql = "from Intervals where 1 = 1 and inContent ='"+inContent+"'";
		List intervalsList = intervalDao.getAllByConds(hql);
		Intervals intervals = (Intervals) intervalsList.get(0);
		String dInId = intervals.getinId();
		String hqlToDetails = "from ScheduleDetails where 1 = 1 and dInId ='"+dInId+"' and dTime = '"+dTime +"'";
		List scheduleDetails = scheduleDetailsDao.getAllByConds(hqlToDetails);
		ScheduleDetails scheduleDetail = (ScheduleDetails) scheduleDetails.get(0);
		Schedule schedule =scheduleDetail.getdSchId();
		Classes classes = schedule.getschClaId();
		String claId = classes.getclaId();
		String hqlFromClasses = "from Student where 1 = 1 and sclaId ='"+claId+"'";
		List<Object> studentTypelist = studentDao.getAllByConds(hqlFromClasses);
		JSONObject jobj = new JSONObject();
		if (studentTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(studentTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	
	@Action(value = "searchAll")
	public String searchAll() throws IOException {
		String sRealName = URLDecoder.decode(ServletActionContext.getRequest().getParameter("sRealName"),"utf-8");
		String sClaId = ServletActionContext.getRequest().getParameter("sClaId");
		String hql="from Student where 1=1 and sClaId = '" + sClaId + "'";
		if(!"".equals(sRealName)){
			hql += " and sRealName like '%" + sRealName + "%'";
		}
		List<Object> studemtTypelist = studentDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		JSONArray list = new JSONArray();
		if (studemtTypelist.size() > 0) {
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(studemtTypelist));
		
		} else {
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
}
