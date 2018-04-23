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

import com.lzp.dao.IIntervalsDao;
import com.lzp.dao.ILeaveDao;
import com.lzp.dao.IScheduleDetailsDao;
import com.lzp.dao.IStudentDao;
import com.lzp.model.Intervals;
import com.lzp.model.Leave;
import com.lzp.model.ScheduleDetails;
import com.lzp.model.Student;
import com.lzp.util.JsonUtil;
import com.lzp.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/leave")
public class LeaveAction {

	private ILeaveDao leaveDao;

	public ILeaveDao getLeaveDao() {
		return leaveDao;
	}

	@Resource(name = "LeaveDao")
	public void setLeaveDao(ILeaveDao leaveDao) {
		this.leaveDao = leaveDao;
	}

	private IScheduleDetailsDao scheduleDetailsDao;

	public IScheduleDetailsDao getScheduleDetailsDao() {
		return scheduleDetailsDao;
	}

	@Resource(name = "ScheduleDetailsDao")
	public void setScheduleDetailsDao(IScheduleDetailsDao scheduleDetailsDao) {
		this.scheduleDetailsDao = scheduleDetailsDao;
	}

	private IStudentDao studentDao;

	public IStudentDao getStudentDao() {
		return studentDao;
	}

	@Resource(name = "StudentDao")
	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
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

		Leave leave = new Leave();
		String dTime = ServletActionContext.getRequest().getParameter("dTime");
		String inContent = ServletActionContext.getRequest().getParameter("inContent");
		String lReason = ServletActionContext.getRequest().getParameter("lReason");
		String lSId = ServletActionContext.getRequest().getParameter("lSId");

		String hql = "from Intervals where 1 = 1 and inContent ='"+inContent+"'";
		List intervalsList = intervalDao.getAllByConds(hql);
		Intervals intervals = (Intervals) intervalsList.get(0);
		String dInId = intervals.getinId();
		String hqlToDetails = "from ScheduleDetails where 1 = 1 and dInId ='"+dInId+"' and dTime = '"+dTime +"'";
		System.out.println(hqlToDetails);
		List scheduleDetails = scheduleDetailsDao.getAllByConds(hqlToDetails);
		ScheduleDetails scheduleDetail = (ScheduleDetails) scheduleDetails.get(0);
		Student student = studentDao.getById(lSId);
		leave.setlDId(scheduleDetail);
		leave.setlReason(lReason);
		leave.setlSId(student);
		leave.setlSign(0);

		JSONObject jobj = new JSONObject();
		if (leaveDao.save(leave)) {
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

		String lId = ServletActionContext.getRequest().getParameter("lId");
		Leave leave = leaveDao.getById(lId);
		JSONObject jobj = new JSONObject();
		if (leaveDao.delete(leave)) {
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

		String lId = ServletActionContext.getRequest().getParameter("lId");

		Leave leave = leaveDao.getById(lId);
		JSONObject jobj = new JSONObject();

		if (leaveDao.update(leave)) {
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
		String lId = ServletActionContext.getRequest().getParameter("lId");
		Leave leave = leaveDao.getById(lId);
		JSONObject jobj = new JSONObject();
		if (leave != null) {
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
		List<Object> leaveTypelist = leaveDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (leaveTypelist.size() > 0) {
			page = new PageBean(leaveTypelist.size(), pageNum, 5);
			list = leaveDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (leaveTypelist.size() > 0) {
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

		List<Object> leaveTypelist = leaveDao.list();// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (leaveTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(leaveTypelist));
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
