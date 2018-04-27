package com.lzp.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;

import com.lzp.dao.IAbsenceDao;
import com.lzp.dao.ILeaveTableDao;
import com.lzp.dao.IStudentDao;
import com.lzp.model.Absence;
import com.lzp.model.LeaveTable;
import com.lzp.model.Student;
import com.lzp.util.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
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

	@Resource(name = "AbsenceDao")
	public void setAbsenceDao(IAbsenceDao absenceDao) {
		this.absenceDao = absenceDao;
	}
	
	private IStudentDao studentDao;

	public IStudentDao getStudentDao() {
		return studentDao;
	}

	@Resource(name = "StudentDao")
	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Action(value = "statisticalDataByTeacher")
	public String statisticalDataByTeacher() throws IOException {

		String schSemester = URLDecoder.decode(ServletActionContext.getRequest().getParameter("schSemester"), "utf-8");
		String tId = ServletActionContext.getRequest().getParameter("tId");
		String currId = ServletActionContext.getRequest().getParameter("currId");// 课程id
		String claId = ServletActionContext.getRequest().getParameter("claId");// 班级id
		String hql;
		String hqlByCla;
		String hqlTwo;
		/*
		 * 
		 SELECT * from Absence where 1=1 and aDId In (SELECT dId From ScheduleDetails where 1=1 and dCurrId ='
		 4028470662e65a170162e65a811c0000' and dSchId IN ( SELECT schId From Schedule where 1=1 and schClaId = '
		 4028470662e1c5560162e1c96d9d0002'and schSemester = '2017-2018第一学期' ))
		 */
		hql = "from Absence where 1=1 and aDId In (SELECT dId From ScheduleDetails where 1=1 and dCurrId ='"
				+ currId + "' and dSchId IN ( SELECT schId From Schedule where 1=1 and schClaId = '" 
				+ claId + "'and schSemester = '"+schSemester+"' ))";
		hqlByCla="from Student where 1=1 and sClaId = '"+claId+"'";
		hqlTwo ="from LeaveTable where 1=1 and lDId In (SELECT dId From ScheduleDetails where 1=1 and dCurrId ='"
				+ currId + "' and dSchId IN ( SELECT schId From Schedule where 1=1 and schClaId = '" 
				+ claId + "'and schSemester = '"+schSemester+"' ))";
		List<Object> absenceTypelist = absenceDao.getAllByConds(hql);//缺勤的list
		List<Object> studentlist = studentDao.getAllByConds(hqlByCla);//学生的list
		List<Object> leavesTypelist= leavesDao.getAllByConds(hqlTwo);
		
		JSONArray nums = new JSONArray();
		for(int i=0;i<studentlist.size();i++){
			JSONObject temp = new JSONObject();
			int qqNum = 0;
			int qjNum = 0;
			for(int j=0;j<absenceTypelist.size();j++){
				Student student = (Student) studentlist.get(i);
				Absence absence = (Absence) absenceTypelist.get(j);
				if(student.getsId().equals(absence.getaSId().getsId())){
					qqNum++;
				}
			}
			for(int h=0;h<leavesTypelist.size();h++){
				Student student = (Student) studentlist.get(i);
				LeaveTable absence = (LeaveTable) leavesTypelist.get(h);
				if(student.getsId().equals(absence.getlSId().getsId())){
					qjNum++;
				}
			}
			temp.put("qqNum", qqNum);
			temp.put("qjNum", qjNum);
			nums.add(temp);
		}
		JSONObject jobj = new JSONObject();
		if (studentlist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("studentlist", JsonUtil.toJsonByListObj(studentlist));
			jobj.put("nums", nums);
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	@Action(value = "statisticalDataByStudent")
	public String statisticalDataByStudent() throws IOException {
		String schSemester = URLDecoder.decode(ServletActionContext.getRequest().getParameter("schSemester"), "utf-8");
		String sId = ServletActionContext.getRequest().getParameter("sId");
		String hql;
		String hqlTwo;
		/*
		 * SELECT *FROM LeaveTable where 1=1 and lsId
		 * ='4028470662f265820162f27fc0df0029' and lDId In (SELECT dId FROM
		 * scheduledetails WHERE dSchId IN(SELECT schId FROM schedule where
		 * schSemester = '2017-2018第一学期'))
		 */
		hql = "from LeaveTable where 1=1 and lsId ='" + sId
				+ "' and lDId In(SELECT dId FROM ScheduleDetails WHERE dSchId IN(SELECT schId FROM Schedule where schSemester = '"
				+ schSemester + "'))";
		hqlTwo = "from Absence where 1=1 and aSId ='" + sId
				+ "' and aDId In(SELECT dId FROM ScheduleDetails WHERE dSchId IN(SELECT schId FROM Schedule where schSemester = '"
				+ schSemester + "'))";

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
