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

import com.lzp.dao.ICurriculumDao;
import com.lzp.dao.IIntervalsDao;
import com.lzp.dao.IScheduleDetailsDao;
import com.lzp.dao.ITeacherDao;
import com.lzp.model.Curriculum;
import com.lzp.model.Intervals;
import com.lzp.model.ScheduleDetails;
import com.lzp.model.Teacher;
import com.lzp.util.JsonUtil;
import com.lzp.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
//表示继承的父包
@Namespace(value = "/curriculum")
public class CurriculumAction {
	
	private ICurriculumDao curriculumDao;
	
	public ICurriculumDao getCurriculumDao() {
		return curriculumDao;
	}
	@Resource(name="CurriculumDao")
	public void setCurriculumDao(ICurriculumDao curriculumDao) {
		this.curriculumDao = curriculumDao;
	}
	

	private IScheduleDetailsDao scheduleDetailsDao;

	public IScheduleDetailsDao getScheduleDetailsDao() {
		return scheduleDetailsDao;
	}

	@Resource(name = "ScheduleDetailsDao")
	public void setScheduleDetailsDao(IScheduleDetailsDao scheduleDetailsDao) {
		this.scheduleDetailsDao = scheduleDetailsDao;
	}
	
	private ITeacherDao teacherDao;
	
	public ITeacherDao getTeacherDao() {
		return teacherDao;
	}
	@Resource(name="TeacherDao")
	public void setTeacherDao(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
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
		Curriculum curriculum = new Curriculum();
		
		String currName = ServletActionContext.getRequest().getParameter("currName");
		String currTId = ServletActionContext.getRequest().getParameter("currTId");
		Teacher teacher = teacherDao.getById(currTId);
		curriculum.setcurrName(currName);
		curriculum.setcurrTId(teacher);
		
		JSONObject jobj = new JSONObject();
		if(curriculumDao.save(curriculum)) {
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
		
		
		String currId = ServletActionContext.getRequest().getParameter("currId");
		Curriculum curriculum = curriculumDao.getById(currId);
		JSONObject jobj = new JSONObject();
		if(curriculumDao.delete(curriculum)){
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
		
		String currId = ServletActionContext.getRequest().getParameter("currId");
		
		Curriculum curriculum = curriculumDao.getById(currId);
		JSONObject jobj = new JSONObject();
		
		if(curriculumDao.update(curriculum)) {
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
		String currId = ServletActionContext.getRequest().getParameter("currId");
		Curriculum curriculum = curriculumDao.getById(currId);
		JSONObject jobj = new JSONObject();
		if(curriculum != null){
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
		List<Object> curriculumTypelist = curriculumDao.list();//获取所有类型数据，不带分页
		PageBean page=null;
		if(curriculumTypelist.size()>0){
			page = new PageBean(curriculumTypelist.size(),pageNum,5);
			list = curriculumDao.listAll(page);//带分页
		}
		JSONObject jobj = new JSONObject();
		if(curriculumTypelist.size() > 0){
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

		List<Object> curriculumTypelist = curriculumDao.list();//获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(curriculumTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(curriculumTypelist));
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value="listAllByTId")
	public String listAllByTId() throws IOException{
		String currTId = ServletActionContext.getRequest().getParameter("currTId");
		String hql="from Curriculum where 1=1 and currTId = '" + currTId + "'";
		List<Object> currListByTId = curriculumDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(currListByTId.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("currListByTId", JsonUtil.toJsonByListObj(currListByTId));
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value="listByLeave")
	public String listByLeave() throws IOException{
		String dTime = URLDecoder.decode(ServletActionContext.getRequest().getParameter("dTime"), "utf-8");
		String inContent = ServletActionContext.getRequest().getParameter("inContent");
		
		String hql = "from Intervals where 1 = 1 and inContent ='"+inContent+"'";
		List intervalsList = intervalDao.getAllByConds(hql);
		Intervals intervals = (Intervals) intervalsList.get(0);
		String dInId = intervals.getinId();
		String hqlToDetails = "from ScheduleDetails where 1 = 1 and dInId ='"+dInId+"' and dTime = '"+dTime +"'";
		System.out.println(hqlToDetails);
		List scheduleDetails = scheduleDetailsDao.getAllByConds(hqlToDetails);
		ScheduleDetails scheduleDetail = (ScheduleDetails) scheduleDetails.get(0);
		Curriculum curriculum = scheduleDetail.getdCurrId();
		JSONObject jobj = new JSONObject();
		if(curriculum != null){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("curriculum", JsonUtil.objectToJson(curriculum));
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
		
	}
	
	@Action(value="listAllBySemester")
	public String listAllBySemester() throws IOException{
		
		String currTId = ServletActionContext.getRequest().getParameter("currTId");
		String schSemester = URLDecoder.decode(ServletActionContext.getRequest().getParameter("schSemester"), "utf-8");
		String hql;
		/*
		SELECT * from curriculum where 1=1 and currId In(
			Select dCurrId from scheduledetails where 1=1 and dSchId IN(
			Select schId from Schedule where 1=1 and schSemester = '2017-2018第一学期'))
			and currTId = '123456789321456'
		 */
		hql="from Curriculum where 1=1 and currId In(Select dCurrId from ScheduleDetails where 1=1 and dSchId IN(Select schId from Schedule where 1=1 and schSemester = '"+
				schSemester+"')) and currTId = '" +currTId+"'";

		List<Object> collegeTypelist = curriculumDao.getAllByConds(hql);//获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(collegeTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(collegeTypelist));
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
