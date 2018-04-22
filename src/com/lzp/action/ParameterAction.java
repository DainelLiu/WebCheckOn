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
import com.lzp.model.Parameter;
import com.lzp.util.JsonUtil;
import com.lzp.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
//表示继承的父包
@Namespace(value = "/parameter")
public class ParameterAction {
	
	private IParameterDao parameterDao;
	
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
		String pLeaveNum = ServletActionContext.getRequest().getParameter("pLeaveNum");
		String pAbsenceNum = ServletActionContext.getRequest().getParameter("pAbsenceNum");
		String pNewTerm = ServletActionContext.getRequest().getParameter("pNewTerm");
		Parameter parameter = new Parameter();
		parameter.setpLeaveNum(pLeaveNum);
		parameter.setpAbsenceNum(pAbsenceNum);
		parameter.setpNewTerm(pNewTerm);
		
		JSONObject jobj = new JSONObject();
		if(parameterDao.save(parameter)) {
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
		
		
		String pId = ServletActionContext.getRequest().getParameter("pId");
		Parameter parameter = parameterDao.getById(pId);
		JSONObject jobj = new JSONObject();
		if(parameterDao.delete(parameter)){
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
		
		String pId = ServletActionContext.getRequest().getParameter("pId");
		
		Parameter parameter = parameterDao.getById(pId);
		JSONObject jobj = new JSONObject();
		
		if(parameterDao.update(parameter)) {
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
		String pId = ServletActionContext.getRequest().getParameter("pId");
		Parameter parameter = parameterDao.getById(pId);
		JSONObject jobj = new JSONObject();
		if(parameter != null){
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
		List<Object> parameterTypelist = parameterDao.list();//获取所有类型数据，不带分页
		PageBean page=null;
		if(parameterTypelist.size()>0){
			page = new PageBean(parameterTypelist.size(),pageNum,5);
			list = parameterDao.listAll(page);//带分页
		}
		JSONObject jobj = new JSONObject();
		if(parameterTypelist.size() > 0){
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

		List<Object> parameterTypelist = parameterDao.list();//获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(parameterTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(parameterTypelist));
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
