package com.fairyonline.course.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.fairyonline.course.dao.CourseDaoImpl;
import com.fairyonline.course.entity.Cart;
import com.fairyonline.course.entity.CartItem;
import com.fairyonline.course.entity.Chapters;
import com.fairyonline.course.entity.Course;

@Service
public class CourseServiceImpl {
	
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private CourseDaoImpl cdi;
	
	/*��Ӷ���*/
	public void Add(Course course) {
		Session session = sessionFactory.openSession();
		this.cdi.save(course);
		session.close();//session�򿪺� �ǵùر�
	}
	
	//�γ̲�ѯ
	public List<Course> getList(){
		List<Course> list = cdi.getList();
		return list;	
	}
	public Chapters selectBycId(int cid) {
		Session session = sessionFactory.openSession();
		Chapters chapter = cdi.selectBycId(cid);
		session.close();
		return chapter;
	}
	
	//��ID��ѯ�γ�
	public Course selectById(int id) {
		Course course = cdi.selectById(id);
		return course;
	}
	
	//���ﳵ
	 public List<Course> getAllCourse() {
		  
		  return cdi.getALL();
		 }
	 public void buyCourse(int id, Cart cart) {
		 Course course = cdi.find(id);
		 cart.add(course);  
	 }
	 public void updateCart(Cart cart, String CName, String quantity) {
	     CartItem item = cart.getMap().get(CName);
	     item.setQuantity(Integer.parseInt(quantity));
	 } 
	 public void deleteCartItem(Cart cart, String CName) {
		 cart.getMap().remove(CName);
	 }
	 public void clearCart(Cart cart) {
	     cart.getMap().clear();
	 }
	//ģ���γ̲�ѯ
//	public List<Course> getCourseByPartName(String courseName){
//		Course list = cdi.getCourseByPartName(courseName);
//		return list;
//	}
	
	

	

}
