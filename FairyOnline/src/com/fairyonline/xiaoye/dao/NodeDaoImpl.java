package com.fairyonline.xiaoye.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.fairyonline.xiaoye.entity.Node;
import com.fairyonline.xiaoye.entity.Side;

@Repository
public class NodeDaoImpl {
	@Resource
	private SessionFactory sessionFactory;
	
	/*save*/
	//save one
	public void save(Node node) {
		Session session = sessionFactory.getCurrentSession();//��ȡsessio
		Transaction tra = session.beginTransaction();//��������
		session.save(node);	
		node.show();
		session.flush();
		tra.commit();
	}
	//save list
	public void saveList(Node[] nodeList) {
		System.out.println("dao get");
		Session session = sessionFactory.getCurrentSession();//��ȡsessio
		Transaction tra = session.beginTransaction();//��������
		for(int i = 0; i<nodeList.length;i++) {//Node node : nodeList
			session.save(nodeList[i]);
			nodeList[i].show();
//			session.flush();
//			tra.commit();
		}
		session.flush();
		tra.commit();
		System.out.println("dao out");
	}
	/*get*/
//	get list
	public List<Node> getList(){
		Query q=this.sessionFactory.getCurrentSession().createQuery("from Node");
		return q.list();
	}
//	get by id 
	public Node getById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tra = session.beginTransaction();
		Node nod = session.get(Node.class, id);
		tra.commit();
		return nod;
	}
//  get by name
	public Node getByName(String name) {
		System.out.println("dao get");
		Session session = sessionFactory.getCurrentSession();
		Transaction tra = session.beginTransaction();
		Query query = session.createQuery("from Node as s where s.name like'%"+name+"%'");
		List list = query.list();
		Node node = (Node) list.get(0);
		tra.commit();
		System.out.println("dao out");
		return node;
	}
	/*update*/
//	update 
	public void update(Node nod) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tra = session.beginTransaction();
		session.update(nod);
		session.flush();
		tra.commit();
	}
	/*delete*/
//	delete
	public void delete(Node nod) {
		Session session = sessionFactory.getCurrentSession(); 
		Transaction tra = session.beginTransaction();
		session.delete(nod);
		session.flush();
		tra.commit();
	}
//	delete by id
	public void deleteById(int id) {
		Session session = sessionFactory.getCurrentSession(); 
		Transaction tra = session.beginTransaction();
		Node nod = session.get(Node.class, id);
		session.delete(nod);
		session.flush();
		tra.commit();
	}
}
