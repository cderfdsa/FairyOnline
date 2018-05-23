package com.fairyonline.xiaoye.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
 


public class FileseController {
	

	//��ȡ����Ƭ
	@RequestMapping("/raSliceFile")
	public String RedAndSliceFile() {//Slice ��Ƭ
	        String encoding = "UTF-8";  
	        //
	        File file = new File("F:\\Documents\\File\\coqytest3.txt"); 
	        //���Զ�ȡ.xls
//	        File file = new File("F:\\Documents\\File\\coqytest3.xls"); 
	        
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
	        try {  
	        	//��encoding(utf-8)�ĸ�ʽ����filecontent������ �浽 str��
	             String str = new String(filecontent, encoding); 
	             String[] strs = str.split("\r\n");
	             String[][] ses = new String[strs.length][];
	             for(int i=0 ;i<strs.length;i++) {
		             ses[i] = strs[i].split("\t");
	             }
	             
	             //print
	             for(int i =0;i<ses.length;i++) {
	            	 for(int b =0;b<ses[i].length;b++) 
		            	 System.out.println(ses[i][b]+"----\\t");
	            	 System.out.println("-------------------");
	             }
	        } catch (UnsupportedEncodingException e) {  
	            System.err.println("The OS does not support " + encoding);  
	            e.printStackTrace();  
	            return null;  
	        }  
		return "Xiaoye/Test";
	}
	//��ȡ��ת�浽��һ�����ļ�
	@RequestMapping("/rasFile")
	public String RedAndSaveFile() {//��ȡ�ļ�  MultipartFile  file
	        String encoding = "UTF-8";  
	        //
	        File file = new File("F:\\Documents\\File\\coqytest3.txt"); 
//	        File file2 = new File("F:\\Documents\\File\\coqytest2.txt"); //�����ڴ˴������ļ�
	        
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
				FileOutputStream file2 = new FileOutputStream("F:\\Documents\\File\\coqytest3.xls",true);
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
		return "Xiaoye/Test";
	}
	
	//��ȡ�ļ�����
	@RequestMapping("/redFile")
	public String RedFile() {//��ȡ�ļ�  MultipartFile  file
	        String encoding = "UTF-8";  
	        //
//	        File file = new File("F:\\Documents\\File\\coqytest.txt"); 
	        //���Զ�ȡ.xls
	        File file = new File("F:\\Documents\\File\\coqytest3.xls"); 
	        
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
	        try {  
	        	//��encoding(utf-8)�ĸ�ʽ����filecontent������ �浽 str��
	             String str = new String(filecontent, encoding);  
	             System.out.println(str);
	        } catch (UnsupportedEncodingException e) {  
	            System.err.println("The OS does not support " + encoding);  
	            e.printStackTrace();  
	            return null;  
	        }  
	       
		return "Xiaoye/Test";
	}
	
	//���ļ���д������
	@RequestMapping("/writeFile")
	public String WriteFile(@RequestParam String textarea) {
		System.out.println("get ReadFile controller");
		//���������� 
		FileOutputStream file = null; 
		try {
			//���������� ��ѡ��׷�ӡ���ʽ   д������
//			file = new FileOutputStream("F:\\Documents\\File\\coqytest3.txt",true);
			//�ɴ���.xls�ļ�
			file = new FileOutputStream("F:\\Documents\\File\\coqytest3.xls",true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//����ת�����ֽ�
//		byte[] b1 = textarea.getBytes();
		byte[] b2 = "\r\n".getBytes(); 
		byte[] b3 = "\r".getBytes();
		byte[] b4 = "line".getBytes();
		byte[] b5 = "\t".getBytes();
		try {
			//����д��
			file.write(b4);//1
			file.write(b2);
			file.write(b4);//2
			file.write(b2);
			file.write(b4);//3
			file.write(b5);
			file.write(b4);//4
			file.write(b5);
			file.write(b4);//5
			file.write(b5);
			file.write(b4);//6
			file.write(b2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//�ر���
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Xiaoye/Test";
	}	
	
//	ԭ����
//	@RequestMapping("/writeFile")
//	public String WriteFile(@RequestParam String textarea) {
//		System.out.println("get ReadFile controller");
//		System.out.println("textarea  ��"+textarea);
//		//���������� 
//		FileOutputStream file = null; 
//		try {
//			//���������� ��ѡ��׷�ӡ���ʽ   д������
//			file = new FileOutputStream("F:\\Documents\\File\\coqytest.txt",true);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		//����ת�����ֽ�
//		byte[] b1 = textarea.getBytes();
//		byte[] b2 = "\r\n".getBytes(); 
//		try {
//			//����д��
//			file.write(b1);
//			file.write(b2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			//�ر���
//			file.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "Xiaoye/Test";
//	}
	//��ȡ�ļ�������
	@RequestMapping("/saveFile")
	public String SaveFile(MultipartFile  file) {
		File newFile = new File("F:\\Documents\\File\\"+ "coqytest.txt");//�����ļ� 
        try {
			file.transferTo(newFile);//��ҳ�洫����ļ������浽�ոմ������ļ�
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ���ڴ��е�����д�����
		return "Xiaoye/Test";
	}
	

}
