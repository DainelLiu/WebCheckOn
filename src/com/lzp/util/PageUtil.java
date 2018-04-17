package com.lzp.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

	private int totalCount = 0;// 总记录数
	private int pageCount;// 总页�?
	private int pageSize = 5;// 每页显示记录�?
	private int page = 1;// 当前�?
	private int num = 5;// 当前页之前和之后显示的页数个�? 如：假设当前页是 6 共有11�? 那么 显示分页条会显示 1 2 3 4
	// 5 [6] 7 8 9 10 11
	private List items = new ArrayList();// 当前页记录内容集�?
	private int prev;// 前一�?
	private int next;// 后一�?
	private int last;// �?后一�?
	private List<Integer> prevPages;// 得到前num页的数据集合
	private List<Integer> nextPages;// 得到后num页的数据集合

	/**
	 * 计算总页�?
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			this.pageCount = (totalCount + pageSize - 1) / pageSize;
		}
	}

	/**
	 * 判断是否有前�?�?
	 * 
	 * @return boolean
	 */
	public boolean getIsPrev() {
		if (page > 1) {
			return true;
		}
		return false;
	}

	/**
	 * 获取前一�?
	 * 
	 * @return int
	 */
	public int getPrev() {
		if (getIsPrev()) {
			return page - 1;
		} else {
			return page;
		}
	}

	/**
	 * 判断是否有后�?�?
	 * 
	 * @return boolean
	 */
	public boolean getIsNext() {
		if (page < pageCount) {
			return true;
		}
		return false;
	}

	/**
	 * 获取后一�?
	 * 
	 * @return int
	 */
	public int getNext() {
		if (getIsNext()) {
			return page + 1;
		}
		return getPageCount();
	}

	/**
	 * 获取�?后一�?
	 * 
	 * @return int
	 */
	public int getLast() {
		return pageCount;
	}

	/**
	 * 当前页的前num条页 假设当前页是 6 共有11�? 如：1 2 3 4 5
	 * 
	 * @return List<Integer>
	 */
	public List<Integer> getPrevPages() {
		List<Integer> list = new ArrayList<Integer>();
		int _frontStart = 1;
		if (page > num) {
			_frontStart = page - num;
		} else if (page <= num) {
			_frontStart = 1;
		}
		for (int i = _frontStart; i < page; i++) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 当前页的后num条页 假设当前页是 6 共有11�? 如：7 8 9 10 11
	 * 
	 * @return List<Integer>
	 */
	public List<Integer> getNextPages() {
		List<Integer> list = new ArrayList<Integer>();
		int _endCount = num;
		if (num < pageCount && (page + num) < pageCount) {
			_endCount = page + _endCount;
		} else if ((page + num) >= pageCount) {
			_endCount = pageCount;
		}
		for (int i = page + 1; i <= _endCount; i++) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 获取每页显示记录�?
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示记录�?
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 得到当前页数
	 * 
	 * @return int
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置当前页数
	 * 
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 获取当前页之前或之后显示的页数个�?
	 * 
	 * @return int
	 */
	public int getNum() {
		return num;
	}

	/**
	 * 设置当前页之前或之后显示的页数个�?
	 * 
	 * @param num
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * 获取当前页记录内容集�?
	 * 
	 * @return List
	 */
	public List getItems() {
		return items;
	}

	/**
	 * 设置当前页记录内容集�?
	 * 
	 * @param items
	 */
	public void setItems(List items) {
		this.items = items;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 得到总页�?
	 * 
	 * @return int
	 */
	public int getPageCount() {
		return pageCount;
	}

}
