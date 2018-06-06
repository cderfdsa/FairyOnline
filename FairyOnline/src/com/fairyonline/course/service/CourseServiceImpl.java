package com.fairyonline.course.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fairyonline.course.dao.CourseDaoImpl;
import com.fairyonline.course.entity.Cart;
import com.fairyonline.course.entity.CartItem;
import com.fairyonline.course.entity.Category;
import com.fairyonline.course.entity.Chapters;
import com.fairyonline.course.entity.Course;
import com.fairyonline.course.entity.Coursebk;
import com.fairyonline.course.entity.FollowCourse;
import com.fairyonline.course.entity.Video;
import com.fairyonline.user.entity.User;

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
	//�ۿ���Ƶ
	public Video videoview(int id) {
		Video video = cdi.videoview(id);
		return video;
	}
	//�ղؿγ�
	public void collection(int id,int ID) {
		cdi.collection(id, ID);
	}
	//ȡ���ղ�
	public void unCollection(int id,int uid) {
		cdi.unCollection(id,uid);
	}
	//��ѯ�ղصĿγ�
	public List<FollowCourse> selectfc() {
		List<FollowCourse> list = cdi.selectfc();
		return list;
		
	}
	//���ﳵ
	public List<Cart> selectAll(){
		List<Cart> list = cdi.selectAll();
		return list;
	}
    
    public void addCart(int id,int ID ) {
			cdi.addCart(id, ID);
	}
    
    public List<Cart> selectById(String[] c){
			List<Cart> list = cdi.selectById(c);
			return list;
	}
    
    public List<Cart> selectByUserId(int userId){
		List<Cart> list = cdi.selectByUserId(userId);
		return list;
	}
    
    public void deleteCart(int cartId) {
			cdi.deleteCart(cartId);
	}
		
	public User addCount(int id) {
			User user = cdi.addCount(id);
			return user;
	}
		
	public void misCount(int id) {
			cdi.misCount(id);
	}
	
	public CourseDaoImpl getCartDao() {
			return cdi;
	}
	
	public void setCartDao(CourseDaoImpl cdi) {
			this.cdi = cdi;
	}
    
	//��˿γ��б�
	public List<Coursebk> getcbkList(){
		List<Coursebk> list = cdi.getcbkList();
		return list;	
	}
	//���γ�����
	public Coursebk selectBycbkId(int id) {
		Coursebk coursebk = cdi.selectBycbkId(id);
		return coursebk;
	}
	public List<Category> getcList(){
		List<Category> list = cdi.getcList();
		return list;	
	}
	
//	 public List<Course> getAllCourse() {
//		  
//		  return cdi.getALL();
//		 }
//	 public void buyCourse(int id, Cart cart) {
//		 Course course = cdi.find(id);
//		 cart.add(course);  
//	 }
//	 public void updateCart(Cart cart, String CName, String quantity) {
//	     CartItem item = cart.getMap().get(CName);
//	     item.setQuantity(Integer.parseInt(quantity));
//	 } 
//	 public void deleteCartItem(Cart cart, String CName) {
//		 cart.getMap().remove(CName);
//	 }
//	 public void clearCart(Cart cart) {
//	     cart.getMap().clear();
//	 }
	//ģ���γ̲�ѯ
//	public List<Course> getCourseByPartName(String courseName){
//		Course list = cdi.getCourseByPartName(courseName);
//		return list;
//	}
	
	

	

}
