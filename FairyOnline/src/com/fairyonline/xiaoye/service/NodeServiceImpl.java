package com.fairyonline.xiaoye.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.fairyonline.xiaoye.dao.NodeDaoImpl;
import com.fairyonline.xiaoye.entity.Node;

@Service
@Repository
public class NodeServiceImpl {
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private NodeDaoImpl nodeDaoImpl;
	/*���巽��*/
	
	public Node GetOneNode(int id) {
		Session session = sessionFactory.openSession();
		return nodeDaoImpl.getById(id);
	}
	
	public void AddOneNode(Node node) {
		System.out.println("service get");
		nodeDaoImpl.save(node);
		System.out.println("service out");
	}
	
	//��ȡ�ļ����ݡ��������󡢴������ݿ�
	public void AddNodeByFile(String fileUrl) {
		System.out.println("service get");
		Session session = sessionFactory.openSession();
		//��ȡ������ļ�����	����һ���ļ����ݶ���String2ά����
		String[][] strArr = this.GetStrArr(fileUrl);
		//����ȡ��������  ת��Ϊ��������
		Node[] nodeArr = this.GetNodSrr(strArr);
		//���÷���  �������ݿ�
		nodeDaoImpl.saveList(nodeArr);
		session.close();
		System.out.println("service out");
	}
	/*�ӷ���*/
	//����ȡ���������� ת��Ϊ��������
	public Node[] GetNodSrr(String[][] strArr) {
		System.out.println("GetNodSrr get");
		Node[] nodArr = new Node[strArr.length-1];
		for(int i=0;i<(strArr.length-1);i++) {
			nodArr[i] = new Node(
					strArr[i+1][0],//�汾��
					Integer.parseInt(strArr[i+1][1]),//���ͱ��  .toCharArray()[0]
					strArr[i+1][2],//����
					strArr[i+1][3],//ǰ��֪ʶ
					strArr[i+1][4],//���֪ʶ
					strArr[i+1][5],//һ�����֪ʶ
					strArr[i+1][6],///��ϸ����
					2//�ȶ�
					);
			System.out.println(nodArr[i].getCatNumber());
		}
		System.out.println("GetNodSrr out");
		return nodArr;
	}
	//��ȡ������ļ�����	����һ���ļ����ݶ���2ά����
	public String[][] GetStrArr(String fileUrl) {
		System.out.println("GetStrArr get");
		String encoding = "UTF-8"; 
		String sign;//sign �ָ���   �������ƺ�׺  ȷ���ָ�������
		if(fileUrl.substring(fileUrl.lastIndexOf(".")).equals(".csv"))
			sign=",";
		else
			sign="\t";
		File file = new File(fileUrl);
		Long filelength = file.length();  //��ȡ�ļ��ܳ���
		byte[] filecontent = new byte[filelength.intValue()];// �����ļ����ȵ��ֽ����� 
		try {  
            FileInputStream in = new FileInputStream(file);  //������һ���µ�ʲô����
            in.read(filecontent);  //��ȡ���� ���ո��´������ֽ�����(�ֽ����� ����ǳ��������: [B@325b51b)
            in.close();  //�رն�ȡ
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		String str;
		try {
			str = new String(filecontent, encoding);
			String[] strs = str.split("\r\n");
			String[][] strArr = new String[strs.length][];
			for(int i=0 ;i<strs.length;i++) {
	             strArr[i] = strs[i].split(sign);
            }
			System.out.println("GetStrArr out");
			return strArr;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("GetStrArr out");
			return null;
		} 
	}
	
	
	/*���Է���---------------------------*/
	//ת��
	public void RedAndSaveFile(String url1,String url2) {//��ȡ�ļ�  MultipartFile  file
        String encoding = "UTF-8";  
        //
        File file = new File(url1); 
   
        //��
        Long filelength = file.length();  //��ȡ�ļ��ܳ���
        byte[] filecontent = new byte[filelength.intValue()];// �����ļ����ȵ��ֽ����� 
        try {  
            FileInputStream in = new FileInputStream(file);  //������һ���µ�ʲô����
            in.read(filecontent);  //��ȡ���� ���ո��´������ֽ�����
            in.close();  //�رն�ȡ
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        //��
        try {
        	//����һ�������������Ŀ¼��û�д��ļ�  �����д���
			FileOutputStream file2 = new FileOutputStream(url2,true);
			try {
				file2.write(filecontent);//д��
				file2.close();//�ر���
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	
}
