package com.fairyonline.xiaoye.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fairyonline.statics.AudioUtils;
import com.fairyonline.statics.ResponseJsonUtils;
import com.fairyonline.xiaoye.service.TulingService;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


@Controller//有关电台的所以controller
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
		ResponseJsonUtils.json(response, tuSer.test(url+"mps\\result.mp3"));
	}
	@PostMapping("/asktest")
	public void ask(HttpServletResponse response,
			@RequestParam(value="wav") String askWav) {
		ResponseJsonUtils.json(response, tuSer.ask(url+"pcms\\"+askWav));
	}
	@PostMapping("/saytest")
	public String say(Model model,
			HttpServletResponse response,
			@RequestParam(value="say") String say,
			@RequestParam(value="name") String name
			) {
//		ResponseJsonUtils.json(response, );
		tuSer.say(say,name);
		model.addAttribute("say",say);
//		return this.playMp3(model,name);
		model.addAttribute("url",url+"mps\\"+name+".mp3");
		return "Xiaoye/xiaoye";
	}
	@PostMapping("/test1")//播放mp3
    public void testPaly() throws Exception{
		System.out.println("get test1");
        AudioUtils utils  = AudioUtils.getInstance();
        System.out.println("new utils success");
        utils.playMP3(url+"mps\\result.mp3");
        System.out.println("out test1");
	}
	
	@PostMapping("/test12")//播放pcm3
    public void testPaly2() throws Exception{
		System.out.println("get test1");
        AudioUtils utils  = AudioUtils.getInstance();
        System.out.println("new utils success");
        utils.playMP3(url+"pcms\\result1.pcm");
        System.out.println("out test1");
	}
	@PostMapping("/test13")//success  can jumo to say.
	public String playMp3(Model model,String name) {
		model.addAttribute("url",url+"mps\\"+name+".mp3");
		return "Xiaoye/xiaoye";
	}
	
	@PostMapping("/test2")//mp3 - > pcm
    public void testConvert() throws Exception{
		System.out.println("get test1");
        AudioUtils utils  = AudioUtils.getInstance();
        System.out.println("new utils success");
        utils.convertMP32Pcm(url+"\\mps\\anName1.mp3",url+"pcms\\result1.pcm");
        System.out.println("out test1");
	}
	
//	@PostMapping("/addprogram")//添加一个节目Program
//	public void addProgram(HttpServletResponse response,
//			@RequestParam(value="time") String time,//发布时间
//			@RequestParam(value="longOfTime",required=false, defaultValue="0:00") String longOfTime,//节目时常
//			@RequestParam(value="name") String  name,//节目名称
//			@RequestParam(value="img") String  img,//节目名称
//			@RequestParam(value="advCat") int advCatid,//对应电台分类的id
//			@RequestParam(value="album") int albumId,//对应专辑
//			@RequestParam(value="FMName") String FMName,//节目资源的文件名称，如236.mp4
//			@RequestParam(value="sortNumber",required=false, defaultValue="0") int sortNumber//排序编号
//			) {
//		//获取对应专辑
//		Album album = radioService.getAlbum(albumId);
//		//创建一个节目对象
//		Program pro = new Program(time,longOfTime,name,img,advCatid,album,FMName,sortNumber);
//		ResponseJsonUtils.json(response, radioService.addProgram(pro));
//	}
}
