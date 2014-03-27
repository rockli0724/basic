package com.lj.basic.model;

import java.util.List;

public class Pager<T> {
	/**
	 * 分页的大小
	 */
	private int size;
	
	/**
	 * 分页的起始页
	 */
	private int offset;
	
	/**
	 * 总记录数
	 */
	private long total;
	
	/**
	 * 分页对象。 List容器里包含了T对象。
	 * @author Administrator
	 * 
	 * @param<T>
	 */
	private List<T> datas;
	
	/**
	 * 获取分页的size，也就是一页显示多少个数据。
	 * @return
	 */
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	/**
	 * 获取分页的总记录数
	 * @return
	 */
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	/**
	 * 获取T类型的List对象。也就是分页所显示的具体对象信息
	 * @return
	 */
	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
	
	
}
