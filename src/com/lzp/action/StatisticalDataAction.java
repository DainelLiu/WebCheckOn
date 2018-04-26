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

import com.lzp.dao.IAbsenceDao;
import com.lzp.dao.ILeaveTableDao;
import com.lzp.dao.IScheduleDao;
import com.lzp.dao.IStudentDao;
import com.lzp.model.Classes;
import com.lzp.model.Schedule;
import com.lzp.model.Student;
import com.lzp.util.JsonUtil;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
//表示继承的父包
@Namespace(value = "/statisticalData")
public class StatisticalDataAction {
	
	private ILeaveTableDao leavesDao;

	public ILeaveTableDao getLeaveTableDao() {
		return leavesDao;
	}

	@Resource(name = "leaveTableDao")
	public void setLeaveTableDao(ILeaveTableDao leavesDao) {
		this.leavesDao = leavesDao;
	}
	
	private IAbsenceDao absenceDao;
	
	public IAbsenceDao getAbsenceDao() {
		return absenceDao;
	}
	@Resource(name="AbsenceDao")
	public void setAbsenceDao(IAbsenceDao absenceDao) {
		this.absenceDao = absenceDao;
	}
	
	
	@Action(value="statisticalDataByTeacher")
	public String statisticalDataByTeacher() throws IOException{
		
		String schSemester = URLDecoder.decode(ServletActionContext.getRequest().getParameter("schSemester"), "utf-8");
		String tId = ServletActionContext.getRequest().getParameter("tId");
		String hql;
		String hqlTwo;
		/*SELECT *FROM leavetable where 1=1 and lDId In(SELECT dId FROM scheduledetails where dCurrId in(SELECT currId FROM curriculum where currTId = '123456789321456
		 * ') and dSchId in (SELECT schId FROM schedule where schSemester = '2017-2018第一学期'))*/
		hql ="from  LeaveTable  where 1=1 and lDId In (SELECT dId FROM ScheduleDetails where dCurrId in(SELECT currId FROM Curriculum where currTId = '"+tId+
				"') and dSchId in (SELECT schId FROM Schedule where schSemester = '"+schSemester+"'))";

		hqlTwo ="from  Absence  where 1=1 and lDId In(SELECT dId FROM ScheduleDetails where dCurrId in(SELECT currId FROM Curriculum where currTId = '"+tId+
				"') and dSchId in (SELECT schId FROM Schedule where schSemester = '"+schSemester+"'))";
		List<Object> leavesTypelist = leavesDao.getAllByConds(hql);
		List<Object> absenceTypelist = leavesDao.getAllByConds(hqlTwo);
		JSONObject jobj = new JSONObject();
		if (leavesTypelist.size() > 0 || absenceTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(leavesTypelist));
			jobj.put("dataTwo", JsonUtil.toJsonByListObj(absenceTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	
	@Action(value="statisticalDataByStudent")
	public String statisticalDataByStudent() throws IOException{
		String schSemester = URLDecoder.decode(ServletActionContext.getRequest().getParameter("schSemester"), "utf-8");
		String sId = ServletActionContext.getRequest().getParameter("sId");
		String hql;
		String hqlTwo;
		/*SELECT *FROM LeaveTable where 1=1 and lsId ='4028470662f265820162f27fc0df0029' and lDId In
		(SELECT dId FROM scheduledetails WHERE dSchId IN(SELECT schId FROM schedule where schSemester = '2017-2018第一学期'))*/
		hql ="from LeaveTable where 1=1 and lsId ='"+sId+
				"' and lDId In(SELECT dId FROM ScheduleDetails WHERE dSchId IN(SELECT schId FROM Schedule where schSemester = '"
				+schSemester+"'))";
		hqlTwo ="from Absence where 1=1 and aSId ='"+sId+
				"' and aDId In(SELECT dId FROM ScheduleDetails WHERE dSchId IN(SELECT schId FROM Schedule where schSemester = '"
				+schSemester+"'))";
		
		List<Object> leavesTypelist = leavesDao.getAllByConds(hql);
		List<Object> absenceTypelist = absenceDao.getAllByConds(hqlTwo);
		JSONObject jobj = new JSONObject();
		
		if (leavesTypelist.size() > 0 || absenceTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(leavesTypelist));
			jobj.put("dataTwo", JsonUtil.toJsonByListObj(absenceTypelist));
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
