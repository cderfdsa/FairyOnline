package com.fairyonline.xiaoye.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fairyonline.statics.AudioUtils;
import com.fairyonline.statics.ResponseJsonUtils;
import com.fairyonline.xiaoye.service.TulingService;


@Controller//�йص�̨������controller
@Repository
@RequestMapping("/tuling")
public class TulingController {
	@Resource
	private TulingService tuSer;
	
//	@PostMapping("/ask")
//	public void asdTuLing(HttpServletResponse response,
//			@RequestParam(value="askWav") String askWav) {
//		ResponseJsonUtils.json(response, tuSer.asdTuLing(askWav));
//	}
	private String url = "E:\\Program Files\\JavaEE\\eclipseWork\\FairyOnline\\";
	@PostMapping("/test")
	public void test(HttpServletResponse response
			/*@RequestParam(value="askWav") String askWav*/) {
		ResponseJsonUtils.json(response, tuSer.test("E:\\Program Files\\JavaEE\\eclipse\\result.mp3"));
	}
	
	@PostMapping("/test1")
    public void testPaly() throws Exception{
		System.out.println("get test1");
        AudioUtils utils  = AudioUtils.getInstance();
        System.out.println("new utils success");
        utils.playMP3(url+"mps\\result.mp3");
        System.out.println("out test1");
	}
	
	@PostMapping("/test12")
    public void testPaly2() throws Exception{
		System.out.println("get test1");
        AudioUtils utils  = AudioUtils.getInstance();
        System.out.println("new utils success");
        utils.playMP3(url+"pcms\\result1.pcm");
        System.out.println("out test1");
	}
	
	@PostMapping("/test2")
    public void testConvert() throws Exception{
		System.out.println("get test1");
        AudioUtils utils  = AudioUtils.getInstance();
        System.out.println("new utils success");
        utils.convertMP32Pcm(url+"mps\\result.mp3",url+"pcms\\result1.pcm");
        System.out.println("out test1");
	}
	
//	@PostMapping("/addprogram")//���һ����ĿProgram
//	public void addProgram(HttpServletResponse response,
//			@RequestParam(value="time") String time,//����ʱ��
//			@RequestParam(value="longOfTime",required=false, defaultValue="0:00") String longOfTime,//��Ŀʱ��
//			@RequestParam(value="name") String  name,//��Ŀ����
//			@RequestParam(value="img") String  img,//��Ŀ����
//			@RequestParam(value="advCat") int advCatid,//��Ӧ��̨�����id
//			@RequestParam(value="album") int albumId,//��Ӧר��
//			@RequestParam(value="FMName") String FMName,//��Ŀ��Դ���ļ����ƣ���236.mp4
//			@RequestParam(value="sortNumber",required=false, defaultValue="0") int sortNumber//������
//			) {
//		//��ȡ��Ӧר��
//		Album album = radioService.getAlbum(albumId);
//		//����һ����Ŀ����
//		Program pro = new Program(time,longOfTime,name,img,advCatid,album,FMName,sortNumber);
//		ResponseJsonUtils.json(response, radioService.addProgram(pro));
//	}
}
