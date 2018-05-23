package com.fairyonline.xiaoye.controller;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fairyonline.xiaoye.service.NodeServiceImpl;
import com.fairyonline.xiaoye.service.SideServiceImpl;
import com.fairyonline.xiaoye.entity.Node;
import com.fairyonline.xiaoye.entity.Side;


@Controller
@Repository
@RequestMapping("/file")
public class FileController {

	@Resource
	private SideServiceImpl sideServiceImpl;
	@Resource
	private NodeServiceImpl nodeServiceImpl;
	
	@RequestMapping("/test")
	public String Test(@RequestParam(value="file") String  furl) {
		System.out.println("furl : "+furl+"  controller get");
		sideServiceImpl.AddSideByFile(furl);
		
		
//		Side sid = new Side("test");
//		sideServiceImpl.addSide(sid);
//		String[][] strs = sideServiceImpl.GetStrArr(furl); 
//		System.out.println("strs.length : "+strs.length+"\t");
		
//		for(String[] str : strs) {
////			System.out.println("i : "+i);
//			System.out.println(" ");
//			for(String st : str) {
//				System.out.print(st+"\t");
//			}
//		}
//		
//		Side[] sids = sideServiceImpl.getSidStr(strs);
//		System.out.println("sids.length : "+sids.length+"\t");
//		for(int i = 0 ; i <sids.length;i++) {
//			System.out.println(i+"\t");
//			sids[i].show();
//			
//		}
		System.out.println("controller out");
		return "Xiaoye/filetest";
	}
	
	@RequestMapping("/getNodeByName")
	public String getNodeByName(@RequestParam String name) {
		System.out.println("controller get");
		Node nod = nodeServiceImpl.getNodeByName(name);
		nod.show();
		System.out.println("controller out");
		return "Xiaoye/filetest";
	}
	
	@RequestMapping("/addone")
	public String AddOne() {
		System.out.println("controller get");
		Node nod = new Node("test");
		nod.setContent("dddddddddddddddddddddd");
		nodeServiceImpl.AddOneNode(nod);
		Node nod2 = new Node("test");
		nod2.setContent("dd");
		nod2.setName("����");
		nodeServiceImpl.AddOneNode(nod2);
		System.out.println("controller out");
		return "Xiaoye/filetest";
	}
	
	@RequestMapping("/getbyid")
	public String getById(@RequestParam int id) {
		System.out.println("controller get");
		Node nod = nodeServiceImpl.GetOneNode(id);
		nod.show();
		System.out.println("controller out");
		return "Xiaoye/filetest";
	}

	
	
	
/*����controller����*/
	/*ͨ����ȡ�ļ��������ĵ���node�����ļ���Ҫ��ܿ��̡����������ġ�������csv�ļ���������uft-8��ʽ*/
	@RequestMapping("/addNodesByFile")
	public String addNodesByFile(@RequestParam(value="file") String  furl) {
		nodeServiceImpl.AddNodeByFile(furl);
		return "Xiaoye/addData";
	}
	/*ͨ����ȡ�ļ��������ĵ���side��������node���޸ģ������ļ���Ҫ��ܿ��̡����������ġ�������csv�ļ���������uft-8��ʽ*/
	@RequestMapping("/addSidesByFile")
	public String addSidesByFile(@RequestParam(value="file") String  furl) {
		sideServiceImpl.AddSideByFile(furl);
		return "Xiaoye/addData";
	}
}
