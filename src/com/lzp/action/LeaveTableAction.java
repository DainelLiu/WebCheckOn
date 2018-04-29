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

import com.lzp.dao.IIntervalsDao;
import com.lzp.dao.ILeaveTableDao;
import com.lzp.dao.IScheduleDetailsDao;
import com.lzp.dao.IStudentDao;
import com.lzp.model.Intervals;
import com.lzp.model.LeaveTable;
import com.lzp.model.ScheduleDetails;
import com.lzp.model.Student;
import com.lzp.util.JsonUtil;
import com.lzp.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/leave")
public class LeaveTableAction {

	private ILeaveTableDao leavesDao;

	public ILeaveTableDao getLeaveTableDao() {
		return leavesDao;
	}

	@Resource(name = "leaveTableDao")
	public void setLeaveTableDao(ILeaveTableDao leavesDao) {
		this.leavesDao = leavesDao;
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

		LeaveTable LeaveTable = new LeaveTable();
		String dTime = URLDecoder.decode(ServletActionContext.getRequest().getParameter("dTime"), "utf-8");
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
		LeaveTable.setlDId(scheduleDetail);
		LeaveTable.setlReason(lReason);
		LeaveTable.setlSId(student);
		LeaveTable.setlSign("0");

		JSONObject jobj = new JSONObject();
		if (leavesDao.save(LeaveTable)) {
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
		LeaveTable leaves = leavesDao.getById(lId);
		JSONObject jobj = new JSONObject();
		if (leavesDao.delete(leaves)) {
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

		LeaveTable leaves = leavesDao.getById(lId);
		JSONObject jobj = new JSONObject();

		if (leavesDao.update(leaves)) {
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
		LeaveTable leaves = leavesDao.getById(lId);
		JSONObject jobj = new JSONObject();
		if (leaves != null) {
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
		List<Object> leavesTypelist = leavesDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (leavesTypelist.size() > 0) {
			page = new PageBean(leavesTypelist.size(), pageNum, 5);
			list = leavesDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (leavesTypelist.size() > 0) {
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

		List<Object> leavesTypelist = leavesDao.list();// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (leavesTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(leavesTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value = "listAllByStudent")
	public String listAllByStudent() throws IOException {
		
		String schSemester = URLDecoder.decode(ServletActionContext.getRequest().getParameter("schSemester"), "utf-8");
		String sId = ServletActionContext.getRequest().getParameter("sId");
		String hql;
		/*SELECT *FROM LeaveTable where 1=1 and lsId ='4028470662f265820162f27fc0df0029' and lDId In
		(SELECT dId FROM scheduledetails WHERE dSchId IN(SELECT schId FROM schedule where schSemester = '2017-2018第一学期'))*/
		hql ="from LeaveTable where 1=1 and lsId ='"+sId+
				"' and lDId In(SELECT dId FROM ScheduleDetails WHERE dSchId IN(SELECT schId FROM Schedule where schSemester = '"
				+schSemester+"'))";

		List<Object> leavesTypelist = leavesDao.getAllByConds(hql);
		JSONObject jobj = new JSONObject();
		if (leavesTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(leavesTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	
	@Action(value = "listAllByTeacher")
	public String listAllByTeacher() throws IOException {
		
		String schSemester = URLDecoder.decode(ServletActionContext.getRequest().getParameter("schSemester"), "utf-8");
		String tId = ServletActionContext.getRequest().getParameter("tId");
		String hql;
		/*SELECT *FROM leavetable where 1=1 and lDId In(SELECT dId FROM scheduledetails where dCurrId in(SELECT currId FROM curriculum where currTId = '123456789321456
		 * ') and dSchId in (SELECT schId FROM schedule where schSemester = '2017-2018第一学期'))*/
		hql ="from  LeaveTable  where 1=1 and lDId In(SELECT dId FROM ScheduleDetails where dCurrId in(SELECT currId FROM Curriculum where currTId = '"+tId+
				"') and dSchId in (SELECT schId FROM Schedule where schSemester = '"+schSemester+"'))";

		List<Object> leavesTypelist = leavesDao.getAllByConds(hql);
		JSONObject jobj = new JSONObject();
		if (leavesTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(leavesTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	
	@Action(value = "updateToSign")
	public String updateToSign() throws IOException {

		JSONObject jobj = new JSONObject();
		boolean sign = false;
		String lId = ServletActionContext.getRequest().getParameter("lId");
		String lSign = ServletActionContext.getRequest().getParameter("lSign");
		String date[]=lId.split(","); 
		
		for (int i = 0; i < date.length; i++) {
			LeaveTable leaves = leavesDao.getById(date[i]);
			leaves.setlSign(lSign);
			if(leavesDao.update(leaves)){
				sign = true;
			}else{
				sign = false;
			}
		}
		if (sign) {
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

	@Action(value="searchAll")
	public String searchAll() throws IOException{
		String schSemester = URLDecoder.decode(ServletActionContext.getRequest().getParameter("schSemester"), "utf-8");
		String tId = ServletActionContext.getRequest().getParameter("tId");
		String lSign = ServletActionContext.getRequest().getParameter("lSign");
		String hql ="from  LeaveTable  where 1=1 and lSign = '"+lSign+"' and lDId In(SELECT dId FROM ScheduleDetails where dCurrId in(SELECT currId FROM Curriculum where currTId = '"+tId+
				"') and dSchId in (SELECT schId FROM Schedule where schSemester = '"+schSemester+"'))";
		List<Object> leaveTablelist = leavesDao.getAllByConds(hql);//获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(leaveTablelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(leaveTablelist));
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
