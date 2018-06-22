package com.fairyonline.course.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.fairyonline.course.entity.Orders;
import com.fairyonline.course.entity.OrdersList;

@Repository
public class OrdersDao {
	@Resource
	private SessionFactory sessionFactory;
	
	public void saveOrd(Orders ord) {
		Session session = sessionFactory.getCurrentSession();//��ȡsessio
		Transaction tra = session.beginTransaction();//��������
		session.save(ord);
		session.flush();
		tra.commit();
		System.out.println("out dao");
	}
	public void saveOrdList(List<OrdersList> ordlist) {
		Session session = sessionFactory.getCurrentSession();//��ȡsessio
		Transaction tra = session.beginTransaction();//��������
		for(OrdersList or : ordlist) {
			session.save(or);
		}
		session.flush();
		tra.commit();
		System.out.println("out dao");
	}
//	public void save(Course course) {
//		Session session = sessionFactory.getCurrentSession();//��ȡsessio
//		Transaction tra = session.beginTransaction();//��������
//		session.save(course);
//		System.out.println("save success!");
//		session.flush();
//		tra.commit();
//		System.out.println("out dao");
//	}
//	
}
