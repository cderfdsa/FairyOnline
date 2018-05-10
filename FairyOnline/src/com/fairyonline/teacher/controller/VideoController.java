package com.fairyonline.teacher.controller;
import java.awt.Image; 
import java.awt.image.BufferedImage; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.bytedeco.javacpp.opencv_core.IplImage; 
import org.bytedeco.javacv.FFmpegFrameGrabber; 
import org.bytedeco.javacv.Frame;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
@Controller  
@RequestMapping("/file")  
public class VideoController {
	 @RequestMapping("/onefile")  
	    public String oneFileUpload(  
	            @RequestParam("file") MultipartFile file,  
	            HttpServletRequest request, ModelMap model) throws org.bytedeco.javacv.FrameGrabber.Exception, IOException {  
	  
	        // ԭʼ�ļ���  
	        String fileName = file.getOriginalFilename();  
	        System.out.println("ԭʼ�ļ���:" + fileName);  
	        // ���ļ���  
	        String newFileName = UUID.randomUUID() + fileName;  
	  
	        // �����Ŀ��·��  
	        ServletContext sc = request.getSession().getServletContext();  
	        // �ϴ�λ��  
	        String path = sc.getRealPath("/img") + "/"; // �趨�ļ������Ŀ¼  
	  
	        File f1 = new File(path);  
	        if (!f1.exists())  
	            f1.mkdirs();  
	        if (!file.isEmpty()) {  
	            try {  
	                FileOutputStream fos = new FileOutputStream(path + newFileName);  
	                InputStream in = file.getInputStream();  
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
		    FFmpegFrameGrabber ff = (FFmpegFrameGrabber)file; 
		    ff.start();
		    int lenght = ff.getLengthInFrames();
		    int i = 0;
		    Frame f = null;
		    while (i < lenght) {
		        // ����?5֡���������ȫ�ڵ�ͼƬ�����Լ������
		        f = ff.grabFrame();
		        if ((i > 5) && (f.image != null)) {
		            break;
		        }
		        i++;
		    }
		    IplImage img = f.image;
		    int owidth = img.width();
		    int oheight = img.height();
		    // �Խ�ȡ��֡���еȱ�������
		    int width = 800;
		    int height = 400;
		    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		    bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
		            0, 0, null);
		    ImageIO.write(bi, "jpg", targetFile);
		    //ff.flush();
		    ff.stop();
		    System.out.println(System.currentTimeMillis() - start);
	        System.out.println("�ϴ�ͼƬ��:" + path + newFileName);  
	        // �����ļ���ַ������JSPҳ�����  
	        model.addAttribute("fileUrl", path + newFileName);  
	        return "fileUpload";  
	    }  
}
