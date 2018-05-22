package com.fairyonline.user.dao;

import java.util.List;



import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fairyonline.teacher.entity.Teacher;
import com.fairyonline.user.entity.FollowUser;
import com.fairyonline.user.entity.User;
import com.fairyonline.user.entity.UserLogin;



@Repository
public class UserDaoImpl {
	@Resource
	private SessionFactory sessionFactory;
	
	public List<User> findAll(){
		Query q = this.sessionFactory.getCurrentSession().createQuery("from User");
		return q.list();
	}
	
	public List<UserLogin> allUserLogin(){
		Query query = this.sessionFactory.getCurrentSession().createQuery("from UserLogin");
		List<UserLogin> list = query.list();
		return list;
	}
	
	public void addUserLogin(UserLogin userLogin) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tra = session.beginTransaction();//��������
		System.out.println("user name : " +userLogin.getUserName()+"  user password :  "+userLogin.getPassWord() );
		session.save(userLogin);
		System.out.println("user name : " +userLogin.getUserName()+"  user password :  "+userLogin.getPassWord() );
		session.flush();
		tra.commit();
		System.out.println("out Dao");
	}
	
	public boolean addUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(user);
        return true;
	}
	
	public UserLogin login(String userName,String passWord) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from UserLogin where userName=? and passWord=?");
		query.setParameter(0,userName);
		query.setParameter(1,passWord);
		UserLogin userLogin = (UserLogin)query.uniqueResult();
		if(userLogin!=null) {
			return userLogin;
		}else {
			System.out.println("userLogin Dao����");
			return null;
		}
	}
	
	public UserLogin findUserLogin(String userName) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from UserLogin where userName=?");
		query.setParameter(0,userName);
		UserLogin userLogin = (UserLogin)query.uniqueResult();
		return userLogin;
	}
	
	public User findUserById(int id) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from User where id=?");
		query.setParameter(0,id);
		User user = (User)query.uniqueResult();
		return user;
	} 
	/*public User findUserById(int id) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from FollowUser where uid=? and status='�ѹ�ע'");
		query.setParameter(0,id);
		User user = (User)query.uniqueResult();
		return user;
	}*/
	public void updateUser(User user) {//����user
		Session session = sessionFactory.getCurrentSession();
		Transaction tra = session.beginTransaction();//��������
		session.update(user);
		session.flush();
		tra.commit();
	}
	
	public int addFollowUserStatus(User user) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tra = session.beginTransaction();//��������
		Query query = session.createQuery("update FollowUser set status='��ע'");
		int i = query.executeUpdate();
		tra.commit();
		return i;
	}
	
	public int updateFollowUserStatus(User user1,int id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tra = session.beginTransaction();//��������
		Query query = session.createQuery("update FollowUser set status='�ѹ�ע'where fid=?");
		query.setParameter(0,id);
		int i = query.executeUpdate();
		tra.commit();
		return i;
	}
	public UserLogin findUser(String userName) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from UserLogin where userName=?");
		query.setParameter(0,userName);
		UserLogin user = (UserLogin)query.uniqueResult();
		return user;
	}

	public List<UserLogin> getUserByPartName(String userName){
		Query query = this.sessionFactory.getCurrentSession().createQuery("from UserLogin where userName like ?");
		query.setString(0,"%"+userName+"%");
		return query.list();
	}
	
	public Teacher findTeacher(String Name) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from Teacher where Name=?");
		query.setParameter(0,Name);
		Teacher teacher = (Teacher)query.uniqueResult();
		return teacher;
	}
}
