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


@Controller
@Repository
@RequestMapping("/file")
public class FileController {

	@Resource
	private SideServiceImpl sideServiceImpl;
	@Resource
	private NodeServiceImpl nodeServiceImpl;
	
	
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
	public String Test(@RequestParam int id) {
		System.out.println("controller get");
		Node nod = nodeServiceImpl.GetOneNode(id);
		nod.show();
		System.out.println("controller out");
		return "Xiaoye/filetest";
	}

	
	/*������ӣ�������û��̫�����⡣���Ƕ��ļ���Ҫ��ܿ��̡����������ġ�������csv�ļ���������uft-8��ʽ*/
	@RequestMapping("/test")
	public String Test(@RequestParam(value="file") String  furl) {
		System.out.println("furl : "+furl+"  controller get");
		nodeServiceImpl.AddNodeByFile(furl);
		System.out.println("controller out");
		return "Xiaoye/filetest";
	}
}
