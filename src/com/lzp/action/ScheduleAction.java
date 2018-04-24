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
import com.lzp.dao.ICurriculumDao;
import com.lzp.dao.IIntervalsDao;
import com.lzp.dao.IScheduleDao;
import com.lzp.dao.IScheduleDetailsDao;
import com.lzp.model.Classes;
import com.lzp.model.Curriculum;
import com.lzp.model.Intervals;
import com.lzp.model.Schedule;
import com.lzp.model.ScheduleDetails;
import com.lzp.util.JsonUtil;
import com.lzp.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/schedule")
public class ScheduleAction {

	private IScheduleDao scheduleDao;

	private IClassesDao classesDao;

	public IScheduleDao getScheduleDao() {
		return scheduleDao;
	}

	@Resource(name = "ScheduleDao")
	public void setScheduleDao(IScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	private IScheduleDetailsDao scheduleDetailsDao;

	public IScheduleDetailsDao getScheduleDetailsDao() {
		return scheduleDetailsDao;
	}

	@Resource(name = "ScheduleDetailsDao")
	public void setScheduleDetailsDao(IScheduleDetailsDao scheduleDetailsDao) {
		this.scheduleDetailsDao = scheduleDetailsDao;
	}

	public IClassesDao getClassesDao() {
		return classesDao;
	}

	@Resource(name = "ClassesDao")
	public void setClassesDao(IClassesDao classesDao) {
		this.classesDao = classesDao;
	}

	private ICurriculumDao curriculumDao;

	public ICurriculumDao getCurriculumDao() {
		return curriculumDao;
	}

	@Resource(name = "CurriculumDao")
	public void setCurriculumDao(ICurriculumDao curriculumDao) {
		this.curriculumDao = curriculumDao;
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
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "save")
	public String save() throws IOException {
		Schedule schedule = new Schedule();
		String tableConds = ServletActionContext.getRequest().getParameter("tableConds");
		JSONObject jsonTemp = JSONObject.fromObject(tableConds);
		JSONObject json = new JSONObject();
		// json.get(key)
		JSONArray jsonArray = new JSONArray();
		json = (JSONObject) jsonTemp.get("info");// 课表对象属性
		jsonArray = (JSONArray) jsonTemp.get("details");
		Classes classes = classesDao.getById(json.getString("schClaId"));
		schedule.setschClaId(classes);
		schedule.setschSemester(json.getString("schSemester"));
		
		JSONObject jobj = new JSONObject();
		String schId = scheduleDao.save(schedule);
		Schedule scheduleByDetails = scheduleDao.getById(schId);
		boolean sign = false;
		if (schId != null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				ScheduleDetails scheduleDetails = new ScheduleDetails();
				JSONObject job = jsonArray.getJSONObject(i);
				if (!"0".equals(job.get("dCurrId"))) {
					scheduleDetails.setdSchId(scheduleByDetails);
					scheduleDetails.setdTime(job.getString("dTime"));
					Curriculum curriculum = curriculumDao.getById(job.getString("dCurrId"));
					scheduleDetails.setdCurrId(curriculum);
					Intervals interval = intervalDao.getById(job.getString("dInId"));
					scheduleDetails.setdInId(interval);
					if (scheduleDetailsDao.save(scheduleDetails)) {
						sign = true;
					} else {
						sign = false;
					}
				}
			}
			if (sign) {
				jobj.put("mes", "保存成功!");
				jobj.put("status", "success");
			} else {
				jobj.put("mes", "获取失败!");
				jobj.put("status", "error");
			}
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

		String schId = ServletActionContext.getRequest().getParameter("schId");
		Schedule schedule = scheduleDao.getById(schId);
		JSONObject jobj = new JSONObject();
		if (scheduleDao.delete(schedule)) {
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

		String schId = ServletActionContext.getRequest().getParameter("schId");

		Schedule schedule = scheduleDao.getById(schId);
		JSONObject jobj = new JSONObject();

		if (scheduleDao.update(schedule)) {
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
		String schId = ServletActionContext.getRequest().getParameter("schId");
		Schedule schedule = scheduleDao.getById(schId);
		JSONObject jobj = new JSONObject();
		if (schedule != null) {
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
		List<Object> scheduleTypelist = scheduleDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (scheduleTypelist.size() > 0) {
			page = new PageBean(scheduleTypelist.size(), pageNum, 5);
			list = scheduleDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (scheduleTypelist.size() > 0) {
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
		JSONObject jobj = new JSONObject();
		String sClaId = ServletActionContext.getRequest().getParameter("sClaId");
		String schSemester = URLDecoder.decode(ServletActionContext.getRequest().getParameter("schSemester"), "utf-8");
		String tId = ServletActionContext.getRequest().getParameter("tId");
		JSONArray list = new JSONArray();
		JSONArray listToTeacher = new JSONArray();
		List<Object> scheduleDetailslist = new ArrayList<Object>();
		String hql;
		String hqlToDetial;
		
		if (sClaId == null  && tId != null) {  	//教师查看课表
			hql  = "from Curriculum where 1 = 1 and currTId='"+tId+"'";
			List<Object> curriculumList = curriculumDao.getAllByConds(hql);//查出该老师所有的课
			for(int i = 0; i<curriculumList.size();i++){
				Curriculum curriculum = (Curriculum) curriculumList.get(i);
				String currId = curriculum.getcurrId();
				hqlToDetial =  "from ScheduleDetails where 1 = 1 and dCurrId ='"+currId+
						"' and dSchId IN(SELECT schId from Schedule WHERE 1=1 and schSemester ='"+ schSemester + "')";
				scheduleDetailslist =  scheduleDetailsDao.getAllByConds(hqlToDetial);//查出该老师所有课的详情
				list.add(scheduleDetailslist);
				/*for(int j = 0; j<scheduleDetailslist.size();j++){
					ScheduleDetails scheduleDetails = (ScheduleDetails) scheduleDetailslist.get(j);
					Schedule schedule = scheduleDetails.getdSchId();
					listToTeacher.add(schedule);
				}*/
				
			}
			//hql = "from Schedule where 1 = 1 and schSemester='"+schSemester+"'";
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			//jobj.put("info",JsonUtil.toJsonByListObj(listToTeacher));//存放是的课程的信息，里面包含班级信息
			jobj.put("details", JsonUtil.toJsonByListObj(list));//存放课程表详细信息，包含时段、星期？，课程编号
		}else if(sClaId != null && tId == null){		//学生查看课表
			
			hql = "from Schedule where 1 = 1 and schSemester='"+schSemester+" ' and schClaId= '" + sClaId + "'";
			List<Object> schedulelist = scheduleDao.getAllByConds(hql);
			for (int i = 0; i < schedulelist.size(); i++) {
				Schedule schedule = (Schedule) schedulelist.get(i);
				String scheduleId = schedule.getschId();
				hqlToDetial =  "from ScheduleDetails where 1 = 1 and dSchId ='"+scheduleId+"'";
				scheduleDetailslist =  scheduleDao.getAllByConds(hqlToDetial);
				list.add(scheduleDetailslist);
			}
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("info",JsonUtil.toJsonByListObj(schedulelist));
			jobj.put("details", JsonUtil.toJsonByListObj(list));
		
		
		}else{
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

}
