package com.fairyonline.course.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fairyonline.course.dao.CourseDaoImpl;
import com.fairyonline.course.dao.OrdersDao;
import com.fairyonline.course.entity.Cart;
import com.fairyonline.course.entity.CartItem;
import com.fairyonline.course.entity.Category;
import com.fairyonline.course.entity.Chapters;
import com.fairyonline.course.entity.Course;
import com.fairyonline.course.entity.Coursebk;
import com.fairyonline.course.entity.FollowCourse;
import com.fairyonline.course.entity.Orders;
import com.fairyonline.course.entity.OrdersList;
import com.fairyonline.course.entity.Video;
import com.fairyonline.user.dao.UserDaoImpl;
import com.fairyonline.user.entity.User;
import com.fairyonline.user.entity.UserLogin;

@Service
public class CourseServiceImpl {
	
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private CourseDaoImpl cdi;
	@Resource
	private UserDaoImpl usDao;
	@Resource
	private OrdersDao orDao;
	
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
    
    public List<Cart> selectListById(int[] c){
			List<Cart> list = cdi.selectListById(c);
			return list;
	}
    public void deletCatByList(int[] cids,int uid) {
    	cdi.deletCartByList(cids,uid);
    	System.out.println("deletCatByList success ");
    }
    public void  produceOrders(int[] cids,int uid) {
    	Session session =  sessionFactory.openSession();
    	//���� orders  orderlist
//    	Orders ord = new Orders(usDao.findUserById(uid),new Date());
    	Orders ord = orDao.getOrdById(1);
    	System.out.println("get order id is " + ord.getID());
    	List<OrdersList> orList = this.crlTorl(cids, ord);
    	System.out.println("new all success ");
    	//���� orders orderlist
//    	orDao.saveOrd(ord);
//    	orDao.saveOrdList(orList);
    	System.out.println("save all success ");
    	//ɾ�����ﳵ�� ����
    	this.deletCatByList(cids,uid);
    	session.close();
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
	
	//����
	/*save*/
	public void save(Orders orders,HttpSession sessionh) {
		Session session = sessionFactory.openSession();
		//���֮ǰ�ж���  ����ɾ��
		Orders sinOrders = (Orders)sessionh.getAttribute("orders");
		if(sinOrders!=null) { //sinOrder�����Ϊ��  ˵��֮ǰ�Ѿ��浽�����ݿ�����
			sessionh.removeAttribute("orders");
			this.cdi.updateByOrders(sinOrders);
		}
		//�ȴ��Ӷ���
		this.cdi.saveList(orders.getItem(),session);
		//�ٴ涩��
		this.cdi.save(orders,session);
		sessionh.setAttribute("orders", orders);//�滻��֮ǰ�Ķ���
		session.close();
	}
//	/*HavePay*/  //ɾ�����ﳵ�� �Ѿ����������
//	public void havePay(HttpSession session) {
//		User user = (User) session.getAttribute("user");
//		Orders orders = (Orders)session.getAttribute("orders");
//		Set<Cart> cartList = user.getCartSet();
//		List<OrdersList> ItemList = orders.getItem();
//		for(int i = 0 ; i<ItemList.size();i++) {
//			for(int j = 0 ; j<cartList.size();j++) {
//				if(cartList.iterator().next().getCartId()== ItemList.get(i).getCourse().getID()) {
//					cartList.remove(j);
//					j--;
//				}
//			}
//		}
//		user.setCartSet(cartList);
//	}
	
	//ͨ��catlist -> orderlist list
	public List<OrdersList> crlTorl(int[] cids,Orders ord){
		List<OrdersList> orList = new ArrayList(cids.length);
		//��ȡcart list
		List<Cart> cartList =  cdi.selectListById(cids);
		for(Cart c : cartList) {
			System.out.println("c.getCourseId() name is  "+c.getCourseId().getCName());
			OrdersList orl = new OrdersList(c.getCourseId(),ord);
			orList.add(orl);
		}
		return orList;
	}
	
//	/*�������� ���һ����*/
//	public void addOne(int bookId,int i ,HttpSession sessionh) {
//		Session session = sessionFactory.openSession();
//		User u = (User)sessionh.getAttribute("user");
//		List<Book> bookList = u.getCart();
//		bookList.add(this.bookDaoImpl.getById(bookId));
//		u.setCart(bookList);
//		List<OrdersItem> ItemList = (List<OrdersItem>)sessionh.getAttribute("ItemList");
//		ItemList.get(i).setNumber(ItemList.get(i).getNumber()+1);
//		session.close();
//	}
//	/*�������� ����һ����*/
//	public void cutOne(int bookId,int i ,HttpSession sessionh) {
//		Session session = sessionFactory.openSession();
//		UserInfo u = (UserInfo)sessionh.getAttribute("userInfo");//��ȡUserInfo
//		List<Book> bookList = u.getCart();//��ȡCart
//		Book rbook = this.bookDaoImpl.getById(bookId);
//		for(Book book: bookList) {//ѭ���Ƴ� Ҫ���ٵ��Ǳ���
//			if(book.getId()==rbook.getId()) {
//				System.out.println("remove book "+book.getName());
//				bookList.remove(book);//ò�ƿ�������д
//				break;
//			}
//		}
//		u.setCart(bookList);//��Cart ����userInfo��
////		sessionh.setAttribute("userInfo", u);//����userInfo�� session �е�����
//		List<OrdersItem> ItemList = (List<OrdersItem>)sessionh.getAttribute("ItemList");//��ȡsession �е� ItemList 
//		if(ItemList.get(i).getNumber()>1)//���������ĸ���
//			ItemList.get(i).setNumber(ItemList.get(i).getNumber()-1);
//		else
//			ItemList.remove(i);
//		session.close();
//	}
	
	
	/*get*/
	
	/*getAllList*/
	public void getAllLisst(HttpServletRequest request) {
		Session session = sessionFactory.openSession();
		request.setAttribute("ordersList",this.cdi.getAllList());
		session.close();
	}
	/*getListByUseInfo*/
	public void getListByUserInfo(User user,HttpServletRequest request){
		Session session = sessionFactory.openSession();
		request.setAttribute("ordersList",this.cdi.getListByUser(user));
		session.close();
	}
	/*getById*/
	public void getById(int id ,HttpServletRequest request ) {
		Session session = sessionFactory.openSession();
		request.setAttribute("orders",this.cdi.getById(id));
		session.close();
	}
	/*update*/
	public void updateByOrders(Orders orders) {
		Session session = sessionFactory.openSession();
		this.cdi.updateByOrders(orders);
		session.close();
	}
	
//	//��Ӷ���
//	public void save(Orders orders) {//��֪���Ӷ����Ǳ��Ƿ�������
//		//�ȴ��Ӷ���
//		this.ordersItemDaoImpl.saveList(orders.getItem());
//		//�ٴ涩��
//		this.ordersDaoImp.save(orders);
//	}
	//��ȡ���� list
//	public List<Orders> getListByUserInfo(UserInfo userInfo){
//		
//	}
	
//	//getById
//	public Orders getById(int id ) {
//		return this.ordersDaoImp.getById(id);
//	}
	
//	/*
//	 * getAllList
//	 * */
//	public List<Orders> getAllList(){
//		return this.ordersDaoImp.getAllList();
//	}
	
//	/*
//	 * getListByUserinfo
//	 * */
//	public List<Orders> getListByUserInfo(UserInfo userInfo){
//		return this.ordersDaoImp.getListByUserInfo(userInfo);
//	}
	/*
	 * upDateByOrders
	 * */
//	public void updateByOrders(Orders orders) {
//		this.ordersDaoImp.updateByOrders(orders);
//	}
	
	/*public void updateOrders(Orders orders) {
		
		// uuid orderUuid  status  10 
		
		
		// get order information 
		
		// get user information 
		
		// update the user money 
		
		// update the order status 
		
		
		//add wait send 
		
		// update 
	}*/
	//�ύ����
	
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
	//�γ̷���
	public List<Category> getcList(){
		Session session = sessionFactory.openSession();

		System.out.println("get ser");
		List<Category> list = cdi.getcList();
		session.close();
		System.out.println("out ser");
		return list;	
	}
	//�γ̷�������
	public Category classesDetail(int id) {
		Category category = cdi.classesDetail(id);
		return category;
	}
	//��ӷ���
	public boolean addcategory(String categoryName,Date uptime,String adminId,String introduce){
	//	Session session = sessionFactory.openSession();
		boolean c = cdi.addcatgory( categoryName, uptime, adminId, introduce);
	//	session.close();
		return c;
	}
	public void addCategory(Category category) {
		Session session = sessionFactory.openSession();
		this.cdi.addCategory(category);
		session.close();
	}
	//ɾ������
	public void deleteCategory(int categoryId) {
		cdi.deleteCategory(categoryId);
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
