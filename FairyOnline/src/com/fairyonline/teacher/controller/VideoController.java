package com.fairyonline.teacher.controller;
import java.awt.Image; 
import java.awt.image.BufferedImage; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.bytedeco.javacpp.opencv_core.IplImage; 
import org.bytedeco.javacv.FFmpegFrameGrabber; 
import org.bytedeco.javacv.Frame;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fairyonline.teacher.entity.Video;
import com.fairyonline.teacher.service.VideoService;
@Controller  
@RequestMapping("/video")  
public class VideoController {
	@Resource
	private SessionFactory sessionFactory;
	private VideoService videoService;
	 @RequestMapping("/add")  
	    public String oneFileUpload(
	    		@RequestParam("CName")String CName,
				@RequestParam("ChapterName")String ChapterName,
	            @RequestParam("file") MultipartFile files[],  
	            HttpServletRequest request, ModelMap model) throws org.bytedeco.javacv.FrameGrabber.Exception, IOException {  
		 boolean flag = videoService.submit(CName,ChapterName);
		
	     List<Video> list = new ArrayList<Video>();	
		 List<String> list1 = new ArrayList<String>();
		    // �����Ŀ��·��  
		    ServletContext sc = request.getSession().getServletContext();  
		    // �ϴ�λ��  
		    String path = sc.getRealPath("/img") + "/"; // �趨�ļ������Ŀ¼  
		    File f = new File(path);  
		    if (!f.exists())  
		        f.mkdirs();  
		  
		    for (int i = 0; i < files.length; i++) {  
		        // ���ԭʼ�ļ���  
		        String fileName = files[i].getOriginalFilename();  
		        System.out.println("ԭʼ�ļ���:" + fileName);  
		        // ���ļ���  
		        String newFileName = UUID.randomUUID() + fileName;  
		        if (!files[i].isEmpty()) {  
		            try {  
		                FileOutputStream fos = new FileOutputStream(path  
		                        + newFileName);  
		                InputStream in = files[i].getInputStream();  
		                int b = 0;  
		                while ((b = in.read()) != -1) {  
		                    fos.write(b);  
		                }  
		                fos.close();  
		                in.close();  
		            } catch (Exception e) {  
		                e.printStackTrace();  
		            }  
		        }
		        long start = System.currentTimeMillis();
			    File targetFile = new File(path + newFileName);
			    FFmpegFrameGrabber ff = (FFmpegFrameGrabber)files[i]; 
			    ff.start();
			    int lenght = ff.getLengthInFrames();
			    int j = 0;
			    Frame f1 = null;
			    while (j < lenght) {
			        // ����?5֡���������ȫ�ڵ�ͼƬ�����Լ������
			        f1 = ff.grabFrame();
			        if ((j > 5) && (f1.image != null)) {
			            break;
			        }
			        j++;
			    }
			    IplImage img = f1.image;
			    int owidth = img.width();
			    int oheight = img.height();
			    // �Խ�ȡ��֡���еȱ�������
			    int width = 800;
			    int height = 400;
			    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			    bi.getGraphics().drawImage(f1.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
			            0, 0, null);
			    ImageIO.write(bi, "jpg", targetFile);
			    //ff.flush();
			    ff.stop();
			    list1.add(path + newFileName);
			    System.out.println(System.currentTimeMillis() - start);
		        System.out.println("�ϴ�ͼƬ��:" + path + newFileName);  
		        Video video=(Video)files[i];
		        video.setName(fileName);
		        video.setURL(path + newFileName);
				Session s=this.sessionFactory.openSession();
				Transaction t=s.beginTransaction();
				s.save(video);
				t.commit();
				s.close();
		        list.add(video);  
		    }  
		 // �����ļ���ַ������JSPҳ�����  
		    model.addAttribute("videoList", list);
		    model.addAttribute("jpgList", list1);  
		    return "videoUpload";   
	    }
	 @RequestMapping(value = "/drop", method = RequestMethod.POST)  
	 public String drop(Model model,
				HttpServletRequest request,
				@RequestParam("ID")String ID){ 
			Query c=this.sessionFactory.getCurrentSession().createQuery("from Video where id=?");
			c.setParameter(0,ID);
			Video video = (Video)c.uniqueResult();
			String URL=video.getURL();
	        File targetFile = new File(URL);  
	        if (targetFile.exists()) {  
	            targetFile.delete();
	        }
	        Session s=this.sessionFactory.openSession();
			Transaction t=s.beginTransaction();
			Query u=this.sessionFactory.getCurrentSession().createQuery("delete from Video where id=?");
			u.setParameter(0,ID);
			int ret = u.executeUpdate();
			t.commit();
			s.close();
	        return "videoUpload";  
	    }  
}
