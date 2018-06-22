package com.fairyonline.course.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.fairyonline.admin.dao.AdminDaoImpl;
import com.fairyonline.admin.entity.Admin;
import com.fairyonline.course.entity.Cart;
import com.fairyonline.course.entity.Category;
import com.fairyonline.course.entity.Chapters;
import com.fairyonline.course.entity.Course;
import com.fairyonline.course.entity.Coursebk;
import com.fairyonline.course.entity.FollowCourse;
import com.fairyonline.course.entity.Orders;
import com.fairyonline.course.entity.OrdersList;
import com.fairyonline.course.entity.Video;
import com.fairyonline.user.entity.User;
import com.fairyonline.user.entity.UserLogin;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;



@Repository
public class CourseDaoImpl {
	@Resource
	private SessionFactory sessionFactory;
//	@Autowired
//	JdbcTemplate jdbcTemplate;
	@Resource
	private AdminDaoImpl admindao;
	/*save*//*����*/
	public void save(Course course) {
		Session session = sessionFactory.getCurrentSession();//��ȡsessio
		Transaction tra = session.beginTransaction();//��������
		session.save(course);
		System.out.println("save success!");
		session.flush();
		tra.commit();
		System.out.println("out dao");
	}
	
	/*��ѯ*/
	public List<Course> getList(){
		Query q=this.sessionFactory.getCurrentSession().createQuery("from Course");
		//List<Course> c=this.sessionFactory.getCurrentSession().createQuery("from Course").list();
		return q.list();
	}
	public Chapters selectBycId(int id){
		Session session = sessionFactory.openSession();
		System.out.println("****************chapter");
//		Query query = session.createQuery("select Chapters");
//		//.ID,Chapters.CID,Chapters.ChapterNum,Chapters.ChapterName from Chapters c1,Course c2 where c1.CID = c2.id
//		//List<Course> c=this.sessionFactory.getCurrentSession().createQuery("from Course").list();
//		System.out.println("****************chapter");
//		query.setParameter(0, id);
//		System.out.println("****************chapter");
//		Chapters chapter = (Chapters)query.uniqueResult();
		Chapters chapter = session.get(Chapters.class , id);
		return chapter;
	}
	
	public Course selectById(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		System.out.println("get dao success");
//		Query query = session.createQuery("from Course where id = ?");
//		query.setParameter(0, id);
//		Course course = (Course)query.uniqueResult();
		Course course = session.get(Course.class,id);
		session.close();
//		System.out.println("get dao success course name is : " + course.getCName());
		System.out.println("get dao2 success");
		return course; 
	}
	
	//�ۿ���Ƶ
	public Video videoview(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		System.out.println("get dao success");
//		Query query = session.createQuery("from Course where id = ?");
//		query.setParameter(0, id);
//		Course course = (Course)query.uniqueResult();
		Video video = session.get(Video.class,id);
		session.close();
//		System.out.println("get dao success course name is : " + course.getCName());
		System.out.println("get dao2 success");
		return video; 
	}
	//�ղؿγ�
	public Boolean collection(int id,int ID) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Course where ID=?");
		query.setParameter(0,ID);
		Course Course = (Course) query.uniqueResult();
		User user = session.get(User.class, new Integer(id));
		Set<FollowCourse> set = user.getFcSet();
		Iterator<FollowCourse> it = set.iterator();
//		while(it.hasNext()) {
//			FollowCourse c = it.next();
//			if(c.getFcid().equals(ID)) {
//			//	int count = c.getCount();
//				//c.setCount(count+1);
//				session.update(c);
//				tx.commit();
//				session.close();
//				return true;
//			}
//		}
		FollowCourse fc = new FollowCourse();
		fc.setFcid(Course);;;
		fc.setFuid(user);
		user.getFcSet().add(fc);
		session.save(fc);
		session.update(user);
		tx.commit();
		session.close();
		return true;
	}
	//ȡ���ղ�
	public void unCollection(int id,int uid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		System.out.println("get dao");
		
		User u = session.get(User.class, uid);
		FollowCourse fc= session.get(FollowCourse.class, id);
		u.getFcSet().remove(fc);
		session.update(u);
		session.update(fc);
	//	session.delete(fc);
		tx.commit();
		session.close();
	}

	//��ѯ�ղصĿγ�
	public List<FollowCourse> selectfc() {
		Query q=this.sessionFactory.getCurrentSession().createQuery("from FollowCourse");
		return q.list();
		
	}
	//���ﳵ
	public List<Cart> selectAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Cart");
		List<Cart> list = query.list();
		return list;
	}
	
	public Boolean addCart(int id, int ID) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Course where ID=?");
		query.setParameter(0,ID);
		Course Course = (Course) query.uniqueResult();
		
		User user = session.get(User.class, new Integer(id));
		Set<Cart> set = user.getCartSet();
		Iterator<Cart> it = set.iterator();
//		while(it.hasNext()) {
//			Cart c = it.next();
//			if(c.getCourseId().equals(ID)) {
//				int count = c.getCount();
//				c.setCount(count+1);
//				session.update(c);
//				tx.commit();
//				session.close();
//				return true;
//			}
//		}
		Cart c = new Cart();
		c.setCourseId(Course);;
		//c.setCount(1);
		c.setUserId(user);
		user.getCartSet().add(c);
		session.save(c);
		session.update(user);
		tx.commit();
		session.close();
		return true;
	}
	
	public List<Cart> selectListById(int[] c){
		Session session = sessionFactory.openSession();
		List<Cart> list = new ArrayList<Cart>();
		System.out.println("c is "+c[0]);
		for(int i :c) {
//		for(int i =0; i < c.length; i ++) {
//			Query query = session.createQuery("from Cart where cartId=?");
//			query.setParameter(0, Integer.parseInt(c[i]));
//			Cart cart = (Cart)query.uniqueResult();
			Cart cart  = session.get(Cart.class, i);
			list.add(cart);
		}
		return list;
	}
	
	public void deletCartByList(List<Cart> clist) {
		Session session = sessionFactory.getCurrentSession();//��ȡsessio
		Transaction tra = session.beginTransaction();//��������
		for(Cart c : clist) {
			session.delete(c);
		}
		session.flush();
		tra.commit();
		System.out.println("out dao");
	}
	
	public List<Cart> selectByUserId(int userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Cart where userId=?");
		query.setParameter(0, userId);
		List<Cart> list = query.list();
		return list;
	}

	public void deleteCart(int cartId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Cart cart = session.get(Cart.class,new Integer(cartId));
		session.delete(cart);
		tx.commit();
		session.close();
	}
	
	public User addCount(int id) {
		Session session = sessionFactory.getCurrentSession();
		Cart cart = session.get(Cart.class, new Integer(id));
	//	int count = cart.getCount();
	//	cart.setCount(count+1);
		session.update(cart);
		int userId = 1;
		Query query = session.createQuery("from User where id=?");
		query.setParameter(0, userId);
		User user = (User) query.uniqueResult();
		//User user = session.get(User.class, new Integer(shopping.getUser().getId()));
		//Query query = session.createQuery("select user from Shopping where id=?");
		//query.setParameter(0, id);
		//User user = shopping.getUser();
		return user;
	}
	
	public void misCount(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Cart cart = session.get(Cart.class, new Integer(id));
	//	int count = cart.getCount();
	//	cart.setCount(count-1);
		session.update(cart);
		tx.commit();
		session.close();
	}
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	//����
	/*seve*/
	public void save(Orders orders,Session session) {
//		Session session = sessionFactory.getCurrentSession();//��ȡsession
		Transaction tra = session.beginTransaction();//��������	
		session.save(orders);
		tra.commit();
	}
	/*get*/ //��Ҫgetô��
	public Orders getById(int id ) {
//		Session session = sessionFactory.openSession();
		Session session = sessionFactory.getCurrentSession();//��ȡsession
		Transaction tra = session.beginTransaction();//��������
		Orders orders = session.get(Orders.class, id);
		tra.commit();
//		session.close();
		return orders;
	}
	/* getAllList*/
	public List<Orders> getAllList(){
		Query q=this.sessionFactory.getCurrentSession().createQuery("from Orders");
		return q.list();
	}
	
	/* getListByUserinfo*/
	public List<Orders> getListByUser(User user){
		List<Orders> list = new ArrayList<Orders>();
		List<Orders> list1 = this.getAllList();
		for(Orders ord : list1) {
			if(ord.getUserId().getId()==user.getId()) {//equals
				list.add(ord);
			}
		}
		return list;
	}
	
	/*update*/
	public void updateByOrders(Orders orders) {
		Session session = sessionFactory.getCurrentSession();//��ȡsession
		Transaction tra = session.beginTransaction();//��������
		session.update(orders);
		tra.commit();
	}
	/*delet*/
	public void deleteById(int id) {
		Session session = sessionFactory.getCurrentSession();//��ȡsession
		Transaction tra = session.beginTransaction();//��������
		Orders orders = session.get(Orders.class, id);
		session.delete(orders);
		tra.commit();
	}
	/*save*/
	public void save(OrdersList ordersItem,Session session) {
		Transaction tra = session.beginTransaction();//��������
		session.save(ordersItem);
//		session.flush();
		tra.commit();
	}
	
	/*saveList*/
	public void saveList(List<OrdersList> list,Session session) {
//		Session session = sessionFactory.getCurrentSession();
		for(OrdersList t : list) 
			this.save(t,session);
	}
	
	/*get*/
	/*getAll*/
	public List<OrdersList> getAllList2(){
		Query q=this.sessionFactory.getCurrentSession().createQuery("from OrdersItem");
		return q.list();
	}
	/*getListByOrder*/
	public List<OrdersList> getListByOrder(Orders orders){
		Query q=this.sessionFactory.getCurrentSession().createQuery("from OrdersItem");
		//ִ����һЩɸѡ���� ��������ִ��HQL���
		return q.list();
	}
	//update
	/*delet*/
	public void deleteItem(OrdersList item) {
		Session session = sessionFactory.getCurrentSession();//��ȡsession
		Transaction tra = session.beginTransaction();//��������
		session.delete(item);
		tra.commit();
	}
	/* delete List*/
	public void deleteList(List<OrdersList> list) {
		for(OrdersList t : list) 
			this.deleteItem(t);
	}















	//�ύ����
	
	//��˿γ��б�
	public List<Coursebk> getcbkList(){
		Query q=this.sessionFactory.getCurrentSession().createQuery("from Coursebk");
		//List<Course> c=this.sessionFactory.getCurrentSession().createQuery("from Course").list();
		return q.list();
	}
	//���γ�����
	public Coursebk selectBycbkId(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		System.out.println("get dao success");
//		Query query = session.createQuery("from Course where id = ?");
//		query.setParameter(0, id);
//		Course course = (Course)query.uniqueResult();
		Coursebk coursebk = session.get(Coursebk.class,id);
		session.close();
//		System.out.println("get dao success course name is : " + course.getCName());
		System.out.println("get dao2 success");
		return coursebk; 
	}
	//�����б�
	public List<Category> getcList(){
		System.out.println("get dao");
		Query q=this.sessionFactory.getCurrentSession().createQuery("from Category");
		System.out.println("out dao");
		//List<Course> c=this.sessionFactory.getCurrentSession().createQuery("from Course").list();
		return q.list();
	}
	//�γ̷�������
	public Category classesDetail(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		System.out.println("get dao success");
		Category category = session.get(Category.class,id);
		session.close();
		System.out.println("get dao2 success");
		return category; 
	}
	//��ӷ���
	public boolean addcatgory(String categoryName,Date uptime,String adminId,String introduce){
		java.sql.Date uptime1=new java.sql.Date(uptime.getTime());
		Session session = sessionFactory.openSession();//.getCurrentSession();
		Transaction tra = session.beginTransaction();//��������
		Category c = new Category();
		c.setCategoryName(categoryName);
		c.setUptime(uptime1);
		Admin a = admindao.findByName(adminId);
		System.out.println("amin name"+ a.getUserName());
		c.setAdminId(a);
		c.setIntroduce(introduce);
		session.save(c);
		tra.commit();
		session.close();
		return true;
	}
	public void addCategory(Category category) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tra = session.beginTransaction();//��������
		session.save(category);
		System.out.println("save success");
		session.flush();
		tra.commit();
		System.out.println("out Dao");
	}
	
	//ɾ������
	public void deleteCategory(int categoryId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Category category = session.get(Category.class,new Integer(categoryId));
		session.delete(category);
		tx.commit();
		session.close();
	}
	
	
	
//	 public Course find(int id) {
//	 Session session = sessionFactory.openSession();
//	 Course course = session.get(Course.class,id);
//	 session.close(); 
//	 return course; 
//	 }
//public List<Course> getALL() {
//	 Query q=this.sessionFactory.getCurrentSession().createQuery("from Course");
//	//List<Course> c=this.sessionFactory.getCurrentSession().createQuery("from Course").list();
//	 return q.list();
//	 }
//	/*ģ����ѯ*/
//	public List<Course> getCourseByPartName(String courseName){
//		Query q = this.sessionFactory.getCurrentSession().createQuery("from Watch where name like ?");
//		q.setString(0, "%"+courseName+"%");
//		return q.list();
//	}
	
	
	
}
