package com.fairyonline.xiaoye.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Side")
public class Side {
	/*��Ҫ����*///5
	private int id ;
	//���������ڵ� ˳���Ⱥ� ��û����������
	private int nodeOneId;//�����ӵĵ�һ���ڵ�
	private int nodeTwoId;//�����ӵĵڶ����ڵ�
	//���������ڵ� ˳���Ⱥ���ǰ�������ڵ����Ӧ
	private int oneNextSideId;//��һ���ڵ����һ���ߣ�����Ǵ˽ڵ�����һ���ߣ���Ϊ�ջ�Ϊ0
	private int twoNextSideId;//�ڶ����ڵ����һ���ߣ�����Ǵ˽ڵ�����һ���ߣ���Ϊ�ջ�Ϊ0
	/*һ������*///6
	private String verNumber;//�汾�� 	version number
	private String noName;//�ڵ�һname NodeOneName
	private int noRelDegree;//�ڵ�һ ��ض�Related degree; degree of association
	private String ntName;//�ڵ��name NodeTwoName
	private int ntRelDegree;//�ڵ�� ��ض�Related degree; degree of association
	private String content;//��ϸ����
	
	/*���캯��*/
	//ȫ����
	public Side(int id, int nodeOneId, int nodeTwoId, int oneNextSideId, int twoNextSideId, String verNumber,
			String noName, int noRelDegree, String ntName, int ntRelDegree, String content) {
		this.id = id;
		this.nodeOneId = nodeOneId;
		this.nodeTwoId = nodeTwoId;
		this.oneNextSideId = oneNextSideId;
		this.twoNextSideId = twoNextSideId;
		this.verNumber = verNumber;
		this.noName = noName;
		this.noRelDegree = noRelDegree;
		this.ntName = ntName;
		this.ntRelDegree = ntRelDegree;
		this.content = content;
	}
	//��id ȫ����
	public Side(int nodeOneId, int nodeTwoId, int oneNextSideId, int twoNextSideId, String verNumber,
			String noName, int noRelDegree, String ntName, int ntRelDegree, String content) {
		this.nodeOneId = nodeOneId;
		this.nodeTwoId = nodeTwoId;
		this.oneNextSideId = oneNextSideId;
		this.twoNextSideId = twoNextSideId;
		this.verNumber = verNumber;
		this.noName = noName;
		this.noRelDegree = noRelDegree;
		this.ntName = ntName;
		this.ntRelDegree = ntRelDegree;
		this.content = content;
	}
	//һ�㹹��
	public Side(  String verNumber,	String noName, int noRelDegree, String ntName, int ntRelDegree, String content) {
		this.oneNextSideId = 0;
		this.twoNextSideId = 0;
		this.verNumber = verNumber;
		this.noName = noName;
		this.noRelDegree = noRelDegree;
		this.ntName = ntName;
		this.ntRelDegree = ntRelDegree;
		this.content = content;
	}
	//���Թ���
	public Side(String test) {
		this.nodeOneId = 0;
		this.nodeTwoId = 0;
		this.oneNextSideId = 0;
		this.twoNextSideId = 0;
		this.verNumber = "1.0.0";
		this.noName = "web";
		this.noRelDegree = 5;
		this.ntName = "css";
		this.ntRelDegree = 5;
		this.content = "һ��ʮ����ϸ�Ľ��ͣ�������Ȼ Ҳ��֪���ǲ��������ϸ����֮����ϸ�Ͷ��ˡ�some English words!To test";
	}
	//Ĭ�Ϲ���
	public Side() {}
	/*����ķ���*/
	//����node  name ����set nextside
	public void setNextSidId(Node node,Side nexSid) {
		if(this.noName.equals(node.getName())) 
			this.setOneNextSideId(nexSid.getId());
		else 
			this.setTwoNextSideId(nexSid.getId());
	}
	//չʾside
	public void show() {
		System.out.println(
				this.id+"\t"+
				this.nodeOneId+"\t"+
				this.nodeTwoId+"\t"+
				this.noName+"\t"+
				this.ntName+"\t"+
				this.oneNextSideId+"\t"+
				this.twoNextSideId+"\t"+
				this.verNumber+"\t"+
				this.noName+"\t"+
				this.noRelDegree+"\t"+
				this.ntName+"\t"+
				this.ntRelDegree+"\t"+
				this.content
				);
	}
	/*set&get*/
	@Id//����id
	@GeneratedValue(generator="my_gen")
    @GenericGenerator(name="my_gen", strategy="increment")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNodeOneId() {
		return nodeOneId;
	}
	public void setNodeOneId(int nodeOneId) {
		this.nodeOneId = nodeOneId;
	}
	public int getNodeTwoId() {
		return nodeTwoId;
	}
	public void setNodeTwoId(int nodeTwoId) {
		this.nodeTwoId = nodeTwoId;
	}
	public int getOneNextSideId() {
		return oneNextSideId;
	}
	public void setOneNextSideId(int oneNextSideId) {
		this.oneNextSideId = oneNextSideId;
	}
	public int getTwoNextSideId() {
		return twoNextSideId;
	}
	public void setTwoNextSideId(int twoNextSideId) {
		this.twoNextSideId = twoNextSideId;
	}
	public String getVerNumber() {
		return verNumber;
	}
	public void setVerNumber(String verNumber) {
		this.verNumber = verNumber;
	}
	public String getNoName() {
		return noName;
	}
	public void setNoName(String noName) {
		this.noName = noName;
	}
	public int getNoRelDegree() {
		return noRelDegree;
	}
	public void setNoRelDegree(int noRelDegree) {
		this.noRelDegree = noRelDegree;
	}
	public String getNtName() {
		return ntName;
	}
	public void setNtName(String ntName) {
		this.ntName = ntName;
	}
	public int getNtRelDegree() {
		return ntRelDegree;
	}
	public void setNtRelDegree(int ntRelDegree) {
		this.ntRelDegree = ntRelDegree;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
