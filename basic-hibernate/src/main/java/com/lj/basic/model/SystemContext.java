package com.lj.basic.model;



/**
 * 用来传递列表对象的Threadlocal数据
 * @author Administrator
 *
 */

public class SystemContext {
	/**
	 * 分页大小
	 */
	private static ThreadLocal<Integer> pageSize=new ThreadLocal<Integer>();
	
	/**
	 * 分页的起始页
	 */
	private static ThreadLocal<Integer> pageOffset=new ThreadLocal<Integer>();
	
	/**
	 * 分页的排序字段
	 */
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	
	/**
	 * 列表的排序方式 (升序还是降序)
	 * 如果想要降序
	 */
	private static ThreadLocal<String> order = new ThreadLocal<String>();
	
	/**
	 * 视频57_文章管理 18分钟
	 */
	private static ThreadLocal<String> realPath=new ThreadLocal<String>();
	
	
	
	/**
	 * 这个数值会在filter的SystemContextFilter里面被设置：->SystemContext.setRealPath(((HttpServletRequest)request).getSession().getServletContext().getRealPath("/"));
	 */
	public static String getRealPath()
	{
		return realPath.get();
	}

	public static void setRealPath(String _realPath)
	{
		realPath.set(_realPath);
	}

	public static Integer getPageSize() {
		return pageSize.get();
	}

	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}

	public static Integer getPageOffset() {
		return pageOffset.get();
	}

	public static void setPageOffset(Integer _pageOffset) {
		pageOffset.set(_pageOffset);
	}

	public static String getSort() {
		return sort.get();
	}
	
	/**
	 * 如果想通过id来排序， 则使用SystemContext.setOrder("id");
	 * @param _sort 排序所使用的对象名称 order by ?
	 */
	public static void setSort(String _sort) {
	 
		SystemContext.sort.set(_sort);
	}

	public static String getOrder() {
		return order.get();
	}
	
	/**
	 * 使用降序还是升序， 
	 * @param _order 如果要升序则使用"asc"作为参数，降序使用"desc"
	 */
	public static void setOrder(String _order) {
		SystemContext.order.set(_order);
	}
	
	
	
	public static void removePageSize(){
		pageSize.remove();
	}
	
	public static void removePageOffset(){
		pageOffset.remove();
	}
	
	public static void removeSort(){
		sort.remove();
	}
	
	public static void removeOrder(){
		order.remove();
	}
	
	public static void removeRealPath(){
		realPath.remove();
	}
	
	/**
	 * 清除所有SystemContext的静态内容
	 */
	public static void removeAll(){
		pageSize.remove();
		pageOffset.remove();
		sort.remove();
		order.remove();
	}
	
	
	
	
	
	
}
