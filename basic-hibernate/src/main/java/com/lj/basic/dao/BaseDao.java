/**
 * 
 */
package com.lj.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.service.spi.InjectService;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.transaction.annotation.Transactional;

import com.lj.basic.model.Pager;
import com.lj.basic.model.SystemContext;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T>
{

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	@Inject
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession()
	{
		// return sessionFactory.openSession();
		return sessionFactory.getCurrentSession();
	}

	private Class<T> clz;

	public Class<T> getClz()
	{
		if (clz == null)
		{
			// 获取泛型的class对象
			clz = ((Class<T>) (((ParameterizedType) (this.getClass()
					.getGenericSuperclass())).getActualTypeArguments()[0]));
		}

		return clz;
	}
	
	/**
	 * 这是2/24写的。目的是完成分页查询的通用方法。</br>
	 * 由于分页查询都需要些一个find("from XXX")的方法，比较麻烦，我想通过泛型来省略掉。</br>
	 * 
	 * @return
	 */
	private String getTvalue(){
		Class<T> t=getClz();
		String className=t.getSimpleName();
		return className;
	}
	
	/**
	 * 结合上面的getTvalue()使用
	 * @return
	 */
	@Override
	public Pager<T>findPager(){
		return find("from "+getTvalue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#add(java.lang.Object)
	 */
	@Override
	public T add(T t)
	{
		// TODO Auto-generated method stub

		getSession().save(t);
		System.out.println("T add (T t)");
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(T t)
	{
		// TODO Auto-generated method stub
		getSession().update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#delete(int)
	 */
	@Override
	public void delete(int id)
	{
		// TODO Auto-generated method stub
		getSession().delete(this.load(id));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#load(int)
	 */
	@Override
	public T load(int id)
	{
		// TODO Auto-generated method stub

		return (T) getSession().load(getClz(), id);
	}
	
	@Override
	public T get(int id)
	{
		// XXX Auto-generated method stub
		return (T) getSession().get(getClz(), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#list(java.lang.String, java.lang.Object[])
	 */
	public List<T> list(String hql, Object[] args)
	{
		// TODO Auto-generated method stub

		return this.list(hql, args, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#list(java.lang.String, java.lang.Object)
	 */
	public List<T> list(String hql, Object arg)
	{
		// TODO Auto-generated method stub
		return this.list(hql, new Object[] { arg });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#list(java.lang.String)
	 */
	public List<T> list(String hql)
	{
		// TODO Auto-generated method stub
		return list(hql, null);
	}
	

	
	/**
	 * 初始化排序信息。传入参数hql语句</br>
	 * 首先通过SystemContext.getOrder()和SystemContext.getSort()来获取排序的基本信息。</br>
	 * 然后看是否包含sort对象，如果包含则加入排序。</br>
	 * 最后会在hql中加入 order by xxx asc||desc
	 * @param hql  要加入排序信息的hql语句
	 * @return
	 */
	private String initSort(String hql)
	{
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();

		if (sort != null && !"".equals(sort.trim()))
		{
			
			hql += " order by " + sort;
			/*if (!"desc".equals(order))
				hql += " asc";
			else
				hql += " desc";
				*/
			//如果order不是desc，则加入asc。是就加入desc了。
			hql+=(!"desc".equals(order))?(" asc"):(" desc");
			
		}

//		System.out.println("order= " + order);
//		System.out.println("sort= " + sort);
		return hql;
	}
	
	 

	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query, Map<String, Object> alias)
	{

		if (alias != null)
		{
			Set<String> keys = alias.keySet();
			for (String key : keys)
			{
				Object val = alias.get(key);
				if (val instanceof Collection)
				{
					query.setParameterList(key, (Collection) val);
				}
				else
				{
					query.setParameter(key, val);
				}
			}
		}
	}

	private void setParameter(Query query, Object[] args)
	{
		if (args != null && args.length > 0)
		{
			int index = 0;
			for (Object arg : args)
			{
				query.setParameter(index++, arg);

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#list(java.lang.String, java.lang.Object[],
	 * java.util.Map)
	 */
	public List<T> list(String hql, Object[] args, Map<String, Object> alias)
	{
		// TODO Auto-generated method stub
		hql = initSort(hql);
		// String cq=getCountHql(hql,);
		// cq=initSort(cq);
		Query query = getSession().createQuery(hql);

		setAliasParameter(query, alias);

		setParameter(query, args);

		
		return query.list();
	}
	
	public List<T> list_firstResult(String hql, Object[]args, Integer num){
		hql=initSort(hql);
		Query query=getSession().createQuery(hql);
		query.setFirstResult(num);
		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#list(java.lang.String, java.util.Map)
	 */
	public List<T> listByAlias(String hql, Map<String, Object> alias)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#find(java.lang.String, java.lang.Object[])
	 */
	public Pager<T> find(String hql, Object[] args)
	{
		// TODO Auto-generated method stub
		return this.find(hql, args, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#find(java.lang.String, java.lang.Object)
	 */
	public Pager<T> find(String hql, Object arg)
	{
		// TODO Auto-generated method stub
		return this.find(hql, new Object[] { arg });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#find(java.lang.String)
	 */
	public Pager<T> find(String hql)
	{
		// TODO Auto-generated method stub
		return this.find(hql, null);
	}

	private String getCountHql(String hql, boolean isHql)
	{
		String e = hql.substring(hql.indexOf("from"));
		String c = "select count(*) " + e;
		if (isHql)
			c.replaceAll("fetch", "");
		return c;
	}
	
	/**
	 * 这里设置了pageSize和pageOffset，如果有值则使用已经值，没有则设置为offset=0,pageSize=15
	 * 最后将query设置firstResult和MaxResult.
	 * @param query
	 * @param pages
	 */
	private void setPagers(Query query, Pager pages)
	{
		Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if (pageOffset == null || pageOffset < 0)
			pageOffset = 0;
		if (pageSize == null || pageSize < 0)
			pageSize = 15;
		pages.setOffset(pageOffset);
		pages.setSize(pageSize);

		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#find(java.lang.String, java.lang.Object[],
	 * java.util.Map)
	 */
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias)
	{
		
		hql = initSort(hql);
		
		//count query string. select count(*) from...
		String count_query_string = getCountHql(hql, true);

		Query count_query = getSession().createQuery(count_query_string);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setAliasParameter(count_query, alias);
		setParameter(query, args);
		setParameter(count_query, args);
		Pager<T> pages = new Pager<T>();
		setPagers(query, pages);
		List<T> datas = query.list();
		pages.setDatas(datas);
		long total = (Long) count_query.uniqueResult();
		pages.setTotal(total);
		return pages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#find(java.lang.String, java.util.Map)
	 */
	public Pager<T> findByAlias(String hql, Map<String, Object> alias)
	{
		// TODO Auto-generated method stub
		return this.find(hql, null, alias);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#queryObject(java.lang.String,
	 * java.lang.Object[])
	 */
	public Object queryObject(String hql, Object[] args)
	{
		// TODO Auto-generated method stub
		return this.queryObject(hql, args, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#queryObject(java.lang.String,
	 * java.lang.Object)
	 */
	public Object queryObject(String hql, Object arg)
	{
		// TODO Auto-generated method stub
		return this.queryObject(hql, new Object[] { arg });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#queryObject(java.lang.String)
	 */
	public Object queryObject(String hql)
	{
		// TODO Auto-generated method stub
		return this.queryObject(hql, null);
	}

 
	public int updateByHql(String hql, Object[] args)
	{
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);

		setParameter(query, args);
		return query.executeUpdate();
	}

	
	public int updateByHql(String hql, Object arg)
	{
		// TODO Auto-generated method stub
		return this.updateByHql(hql, new Object[] { arg });
	}

	/**
	 * 孔浩写的是public void updateByHql(String hql). 但是我觉得这里返回值可以设置成int，</br> 
	 * 因为executeUpdate返回的是int类型，表示被修改的数量
	 * @param hql
	 * @return 返回被更新的row数量
	 */
	public int updateByHql(String hql)
	{
		// TODO Auto-generated method stub
		return this.updateByHql(hql, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#listBySql(java.lang.String,
	 * java.lang.Object[], java.lang.Class, boolean)
	 */
	public <N extends Object> List<N> listBySql(String sql, Object[] args,
			Class<?> clz, boolean hasEntity)
	{
		// TODO Auto-generated method stub
		return this.listBySql(sql, args, null, clz, hasEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#listBySql(java.lang.String,
	 * java.lang.Object, java.lang.Class, boolean)
	 */
	public <N extends Object> List<N> listBySql(String sql, Object arg,
			Class<?> clz, boolean hasEntity)
	{
		// TODO Auto-generated method stub
		return this.listBySql(sql, new Object[] { arg }, null, clz, hasEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#listBySql(java.lang.String,
	 * java.lang.Class, boolean)
	 */
	public <N extends Object> List<N> listBySql(String sql, Class<?> clz,
			boolean hasEntity)
	{
		// TODO Auto-generated method stub
		return this.listBySql(sql, null, clz, hasEntity);
	}

	/**
	 * 通过sql语句查询对象集合，返回包含对象的List集合。
	 * @param sql SQL语句
	 * @param args
	 * @param alias
	 * @param clz 要返回的对象类型， 也就是N.class
	 * @param hasEntity 
	 * @return
	 */
	public <N extends Object> List<N> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity)
	{
		// TODO Auto-generated method stub
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		System.out.println("sql=" + sql);

		if (hasEntity)
		{
			sq.addEntity(clz);
			System.out.println("sq=" + sq);
		}
		else
		{
			sq.addScalar("id", StandardBasicTypes.INTEGER)
					.addScalar("name", StandardBasicTypes.STRING)
					.addScalar("pid", StandardBasicTypes.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(clz));
		}

		return sq.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#listBySql(java.lang.String, java.util.Map,
	 * java.lang.Class, boolean)
	 */
	public <N extends Object> List<N> listByAliasSql(String sql,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#findBySql(java.lang.String,
	 * java.lang.Object[], java.lang.Class, boolean)
	 */
	public Pager<Object> findBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity)
	{
		// TODO Auto-generated method stub
		return this.findBySql(sql, args, null, clz, hasEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#findBySql(java.lang.String,
	 * java.lang.Object, java.lang.Class, boolean)
	 */
	public Pager<Object> findBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity)
	{
		// TODO Auto-generated method stub
		return this.findBySql(sql, new Object[] { arg }, clz, hasEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#findBySql(java.lang.String,
	 * java.lang.Class, boolean)
	 */
	public Pager<Object> findBySql(String sql, Class<?> clz, boolean hasEntity)
	{
		// TODO Auto-generated method stub
		return this.findBySql(sql, null, clz, hasEntity);
	}

	
	// public Pager<Object> findBySql(String sql, Object[] args,
	/**
	 * 凡是find的，返回值都是Pager对象。
	 * @param sql
	 * @param args
	 * @param alias
	 * @param clz
	 * @param hasEntity
	 * @return
	 */
	public <N extends Object> Pager<N> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity)
	{
		// TODO Auto-generated method stub
		String cq = getCountHql(sql, false);
		cq = initSort(cq);
		sql = initSort(sql);

		SQLQuery sq = getSession().createSQLQuery(sql);
		SQLQuery cquery = getSession().createSQLQuery(cq);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		setParameter(cquery, args);
		Pager<N> pages = new Pager<N>();
		setPagers(sq, pages);
		System.out.println(clz);
		if (hasEntity)
		{
			sq.addEntity(clz);
		}
		else
		{
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		System.out.println(sq.list());
		List<N> datas = sq.list();
		pages.setDatas(datas);
		long total = ((BigDecimal) cquery.uniqueResult()).longValue();
		pages.setTotal(total);

		return pages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lj.basic.dao.IBaseDao#findBySql(java.lang.String, java.util.Map,
	 * java.lang.Class, boolean)
	 */
	public Pager<Object> findByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity)
	{
		// TODO Auto-generated method stub
		return this.findBySql(sql, null, alias, clz, hasEntity);
	}

	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias)
	{
		// TODO Auto-generated method stub
		System.out.println("hql= " + hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);

		return query.uniqueResult();
	}

	public Object queryObjectByAlias(String hql, Map<String, Object> alias)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
