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

import com.lzp.dao.IParameterDao;
import com.lzp.dao.IStudentDao;
import com.lzp.dao.ITeacherDao;
import com.lzp.dao.IUsersDao;
import com.lzp.model.Parameter;
import com.lzp.model.Student;
import com.lzp.model.Teacher;
import com.lzp.model.Users;
import com.lzp.util.JsonUtil;
import com.lzp.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
//表示继承的父包
@Namespace(value = "/users")
public class UsersAction {
	
	private IUsersDao usersDao;
	private IStudentDao studentDao;
	private ITeacherDao teacherDao;
	private IParameterDao parameterDao;
	
	public IUsersDao getUsersDao() {
		return usersDao;
	}
	@Resource(name="UsersDao")
	public void setUsersDao(IUsersDao usersDao) {
		this.usersDao = usersDao;
	}
	
	public IStudentDao getStudentDao() {
		return studentDao;
	}
	@Resource(name="StudentDao")
	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}
	public ITeacherDao getTeacherDao() {
		return teacherDao;
	}
	@Resource(name="TeacherDao")
	public void setTeacherDao(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}
	public IParameterDao getParameterDao() {
		return parameterDao;
	}
	@Resource(name="ParameterDao")
	public void setParameterDao(IParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}
	

	/**
	 * 保存缺勤信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="save")
	public String save() throws IOException{
		Users users = new Users();
		JSONObject jobj = new JSONObject();
		if(usersDao.save(users)) {
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
		
		
		String uId = ServletActionContext.getRequest().getParameter("uId");
		Users users = usersDao.getById(uId);
		JSONObject jobj = new JSONObject();
		if(usersDao.delete(users)){
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
		
		String uId = ServletActionContext.getRequest().getParameter("uId");
		
		Users users = usersDao.getById(uId);
		JSONObject jobj = new JSONObject();
		
		if(usersDao.update(users)) {
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
		String uId = ServletActionContext.getRequest().getParameter("uId");
		Users users = usersDao.getById(uId);
		JSONObject jobj = new JSONObject();
		if(users != null){
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
		List<Object> usersTypelist = usersDao.list();//获取所有类型数据，不带分页
		PageBean page=null;
		if(usersTypelist.size()>0){
			page = new PageBean(usersTypelist.size(),pageNum,5);
			list = usersDao.listAll(page);//带分页
		}
		JSONObject jobj = new JSONObject();
		if(usersTypelist.size() > 0){
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

		List<Object> usersTypelist = usersDao.list();//获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(usersTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(usersTypelist));
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
	 * 登录
	 * @return
	 * @throws IOException
	 */
	@Action(value="loginUsers")
	public String loginUsers() throws IOException{
		String username = ServletActionContext.getRequest().getParameter("username");
		String password = ServletActionContext.getRequest().getParameter("password");
		String role = ServletActionContext.getRequest().getParameter("role");
		String hql = "";
		Student student = null;
		Teacher teacher = null;
		Users users = null;
		if("0".equals(role)){
			//学生登录
			hql = "from Student where sNumber='"+username+"' and sPassword='"+password+"'";
			List<Object> studentList = studentDao.getAllByConds(hql);
			student= (Student) studentList.get(0);
		}else if("1".equals(role)){
			//教师登录
			hql = "from Teacher where tNumber='"+username+"' and tPassword='"+password+"'";
			List<Object>  teacherList= teacherDao.getAllByConds(hql);
			teacher = (Teacher) teacherList.get(0);
		}else{
			//管理员登录
			hql = "from Users where uName='"+username+"' and uPassword='"+password+"'";
			List<Object> usersList = usersDao.getAllByConds(hql);
			users = (Users) usersList.get(0);
		}
		
		String term = ((Parameter) parameterDao.list().get(0)).getpNewTerm();
		JSONObject jobj = new JSONObject();
		if(users != null){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", users);
			jobj.put("role", role);
			jobj.put("term", term);
		}else if(student != null){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", student);
			jobj.put("role", role);
			jobj.put("term", term);
		}else if(teacher != null){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", teacher);
			jobj.put("role", role);
			jobj.put("term", term);
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
