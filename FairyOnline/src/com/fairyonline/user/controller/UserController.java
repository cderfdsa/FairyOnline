package com.fairyonline.user.controller;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fairyonline.user.entity.User;
import com.fairyonline.user.entity.UserLogin;
import com.fairyonline.user.service.UserServiceImpl;


@Controller
@RequestMapping("user")
public class UserController {
	/*
	    private static List<UserLogin> userLoginList;
		
		public UserController() {
			super();
			userLoginList = new ArrayList<UserLogin>();
		}
	*/
		
		@Resource
		private UserServiceImpl userServiceImpl;
		
		@RequestMapping("/userList1")
		public String list(Model model) {
			List<User> list = this.userServiceImpl.listAll();
			model.addAttribute("list",list);
			return "user/userList1"; 
		}
		
		@RequestMapping("/regist1")
		public String regist(String userName)throws IOException{
			List<UserLogin> list = this.userServiceImpl.allUserLogin();
			System.out.println(userName);
			boolean flag = true;
			if(userName != null) {
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getUserName().equals(userName)) {
					flag=false;
					break;
				}
			}
			}else {
				flag=false;
			}
			System.out.println(flag);
			ObjectMapper x = new ObjectMapper();
			String isExist = x.writeValueAsString(flag);
			return isExist;
		}
		
		@RequestMapping("/regist")
		public String userRegist(UserLogin UserLogin,Model model){
			boolean result = this.userServiceImpl.addUserLogin(UserLogin);
			//boolean result1 = this.userServiceImpl.addUser(User);
			if(result) {
				model.addAttribute("userRegist",UserLogin);
				//model.addAttribute("userRegist1",User);
				return "user/login";
			}else {
				model.addAttribute("errormsg","ע��ʧ��");
				return "user/regist";
			}
			
		}   
		
		@RequestMapping("/login")
		public String userLogin(Model model,HttpServletRequest request,HttpServletResponse response)throws IOException{
			UserLogin userLogin2 = new UserLogin();
			String userName2;
			HttpSession session = request.getSession();
			if(session.getAttribute("userLogin")!=null) {
				session.removeAttribute("userLogin");
			}
			if(session.getAttribute("userLogin")==null) {
				String userName = request.getParameter("UserName");
				String passWord = request.getParameter("PassWord");
				UserLogin userLogin = this.userServiceImpl.login(userName,passWord);
				userLogin2 = userLogin;
				userName2 = userName;
			}else {
				String userName=(String)session.getAttribute("userLogin");
				UserLogin userLogin = this.userServiceImpl.findUserLogin(userName);
				userLogin2 = userLogin;
				userName2 = userName;
			}
			if(userLogin2!=null) {
				session.setAttribute("userLogin",userName2);
				model.addAttribute("admin",userName2);
				System.out.println("loginִ�гɹ�");
				return "user/index";
			}else {
				model.addAttribute("errormsg","�û������������");
				return "user/login";
			}
			
		}
		
		@RequestMapping(value="/updateitem",method= {RequestMethod.POST,RequestMethod.GET})
		public String updateItems(User items, MultipartFile picture) throws Exception {
			 // �����ϴ��ĵ���ͼƬ    
		    String originalFileName = picture.getOriginalFilename();// ԭʼ����
		    // �ϴ�ͼƬ
		    if (picture != null && originalFileName != null && originalFileName.length() > 0) {
		    	 String pic_path = "E:\\temp\\images\\";
		    	 String newFileName = UUID.randomUUID()
		                 + originalFileName.substring(originalFileName
		                         .lastIndexOf("."));     
		         File newFile = new File(pic_path + newFileName);//��ͼƬ
		         picture.transferTo(newFile);// ���ڴ��е�����д�����
		         items.setImg(newFileName);// ����ͼƬ����д��itemsCustom��
		    }else {
		    	//����û�û��ѡ��ͼƬ���ϴ��ˣ�����ԭ����ͼƬ
		        User temp = this.userServiceImpl.findUserById(items.getID());
		        items.setImg(temp.getImg());
		    }
		    this.userServiceImpl.updateUser(items);     
		    return "user/index";
		    
		}
       
		


}
