package com.fairyonline.course.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.annotations.GenericGenerator;

import com.fairyonline.user.entity.User;

@Entity
@Table(name="orderlist")
public class OrdersList {
	
	private int id ;//��ˮ��
	private Course course;//��Ӧ���� (noe to noe)
	private Orders order;
//	private int number = 0;//������������
//	private int price;//�����Ӧ��������ܼ�
	
	public OrdersList() {}

	public OrdersList(int id, Course course,Orders order) {
		this.id = id;
		this.course = course;
		this.order= order;
	}
//	public OrdersItem(Book book, int number) {//price �Զ�����
//		this.book = book;
//		this.number = number;
//		this.price = this.book.getPrice()*this.number;
//	}
	
	@Id
	@GeneratedValue(generator="id")
	@GenericGenerator(name="id",strategy="increment")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@OneToOne
	@JoinColumn(name="course")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
    @ManyToOne
    @JoinColumn(name="order")
	public Orders getOrder() {
		return order;
    }
	

	public void setOrder(Orders order) {
		this.order = order;
	}
    

}
