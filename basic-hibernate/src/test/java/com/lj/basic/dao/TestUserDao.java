package com.lj.basic.dao;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
 

import javax.inject.Inject;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ObjectNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

 

 
import com.lj.basic.model.User;
import com.lj.basic.util.MyLog4jLogger;
import com.lj.basic.test.util.AbstractDbUnitTestCase;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml") //从根目录找到beans.xml来初始化spring容器。
public class TestUserDao extends AbstractDbUnitTestCase {
	@Inject
	private SessionFactory sessionFactory;

	@Inject
	private IUserDao userDao;
	
	//Logger logger;

	@Before
	public void setUp() throws DataSetException, SQLException, IOException {
		//logger=Logger.getLogger(TestUserDao.class);
		//System.out.println("setUp process");
		MyLog4jLogger.debug("Setup process");

//		Session s = sessionFactory.openSession();
//		TransactionSynchronizationManager.bindResource(sessionFactory,
//				new SessionHolder(s));
//
//		this.backupOneTable("basic_t_user");
	}
	
	@Test
	public void testLoad02(){
		try
		{
			IDataSet ds=createDataSet("t_topic");
		}
		catch (DataSetException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
//	@Test
//	public void testAdd() throws DatabaseUnitException, SQLException{
//		IDataSet ds = createDataSet("t_user");
//		
//		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
//		User u=new User(1,"alleni");
//		userDao.add(u);
//		System.out.println(userDao.load(1).getUsername());
//	}
//
//	@Test
//	public void testLoad() throws DatabaseUnitException, SQLException {
//		System.out.println(1);
//		
//		IDataSet ds = createDataSet("t_user");
//		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
//		User u = userDao.load(11);
//		System.out.println(u);
//		System.out.println(u.getUsername());
//		Assert.assertNotNull(u);
//
//	}
//
//	@Test(expected = ObjectNotFoundException.class)
//	public void testDelete() throws DatabaseUnitException, SQLException {
//		IDataSet ds = createDataSet("t_user");
//		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
//		User u1=userDao.load(5);
//		System.out.println("u1="+u1);
//		userDao.delete(5);
//		User u = userDao.load(5);
//		System.out.println("u="+u);
//	//	System.out.println(u.getUsername());
//		 
//
//	}
//
//	@Test
//	public void testListByArgs() throws DatabaseUnitException, SQLException {
////		IDataSet ds = createDataSet("t_user");
////		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
////		SystemContext.setOrder("desc");
////		SystemContext.setSort("id");
////
////		List<User> expected = userDao.list("from User where id>? and id<?",
////				new Object[] { 1, 4 });
////		List<User> actual = Arrays.asList(new User(3, "alleni3"), new User(2,
////				"alleni2"));
////		EntitiesHelper.assertUsers(expected, actual);
//	}
//
//	@Test
//	public void testListByArgsAndAlias() throws DatabaseUnitException,
//			SQLException {
////		IDataSet ds = createDataSet("t_user");
////		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
////		SystemContext.setOrder("desc");
////		SystemContext.setSort("id");
////
////		Map<String, Object> alias = new HashMap<String, Object>();
////		alias.put("ids", Arrays.asList(1, 2, 3, 5, 6, 7, 8, 9));
////
////		List<User> expected = userDao.list(
////				"from User where id>? and id<? and id in(:ids)", new Object[] {
////						1, 4 }, alias);
////		List<User> actual = Arrays.asList(new User(3, "alleni3"), new User(2,
////				"alleni2"));
////		EntitiesHelper.assertUsers(expected, actual);
//	}
//
//	@Test
//	public void testFindByArgs() throws DatabaseUnitException, SQLException{
////		IDataSet ds=createDataSet("t_user");
////		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
////		SystemContext.setOrder("desc");
////		SystemContext.setSort("id");
////		SystemContext.setPageSize(3);
////		 
////		
////		Pager<User> expected=userDao.find(" from User where id>=? and id<=?",new Object[]{1,10});
////		List<User> actual=Arrays.asList(new User(10,"alleni10"),new User(9,"alleni9"),new User(8,"alleni8"));
////		System.out.println(expected.getDatas().size());
////		for(int i=0;i<expected.getDatas().size();i++){
////		System.out.println(expected.getDatas().get(i).getUsername());}
////		
////		assertTrue(expected.getTotal()==10);
////		assertNotNull(expected);
////		
////		List<User> expectedUser=expected.getDatas();
////		
////	 	EntitiesHelper.assertUsers(expectedUser, actual);
//	}
//	
//	
//	@Test
//	public void testFindByArgsAndAlias() throws DatabaseUnitException,
//			SQLException {
////		IDataSet ds = createDataSet("t_user");
////		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
////		 
////		 SystemContext.removeAll();
////		//SystemContext.setOrder("asc");
////		//SystemContext.setSort("id");
////		System.out.println("pageSize="+SystemContext.getPageSize());
////
////		Map<String, Object> alias = new HashMap<String, Object>();
////		alias.put("ids", Arrays.asList(1, 2, 4, 5, 7, 9));
////
////		Pager<User> expected = userDao.find(
////				"from User where id>? and id<? and id in(:ids)", new Object[] {
////						4, 10 }, alias);
////		List<User> actual = Arrays.asList(new User(5, "alleni5"), new User(7,
////				"alleni7"),new User(9,"alleni9"));
////		System.out.println(expected.getDatas().size());
////		List<User> expectedUser=expected.getDatas();
////		EntitiesHelper.assertUsers(expectedUser, actual);
////		assertTrue(expected.getOffset()==0);
////		assertTrue(expected.getTotal()==3);
////		assertTrue(expected.getSize()==15);
//		
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testFindBySql() throws DatabaseUnitException, SQLException{
//		IDataSet ds = createDataSet("t_user");
//		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
////		Pager<User> expected =  (Pager<User>) userDao.findBySql(
////				"select * from basic_t_user where id>? and id<? ", new Object[]{
////						4, 10 }, User.class, true );
////		System.out.println(expected.getSize());
//	}
//	
//	
//	@Test
//	public void testListBySql() throws DataSetException{
//		IDataSet ds=createDataSet("t_user");
//		
//		ITable table=ds.getTable("basic_t_user");
//		System.out.println(table.getValue(1, "username"));
//		
//	}

	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException,
			SQLException {
//		System.out.println("tearDown process");
//		SessionHolder sh = (SessionHolder) TransactionSynchronizationManager
//				.getResource(sessionFactory);
//		Session s = sh.getSession();
//		s.flush();
//		TransactionSynchronizationManager.unbindResource(sessionFactory);
//		this.resumeTable();
	}
}
