package com.fairyonline.xiaoye.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fairyonline.common.DemoException;
import com.fairyonline.statics.AsrMain;
import com.fairyonline.statics.HttpClientTool;
import com.fairyonline.statics.Result;
import com.fairyonline.statics.TtsMain;


@Service
@Repository
public class TulingService {
//	@Resource
	//eeeeee
	public HashMap<String, Object> say(String say,String name){
		//����ֵ
		Result result = new Result();
		String statusCode =result.getStatusCode();//״̬��
		String desc = result.getDesc();//״̬������
		//ִ�в���
		
		//�������֡�-��������Ƶ��
		String anWav=null;
		try {
			anWav = new TtsMain(say).run(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DemoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map message =new HashMap<String, Object>(2);
		message.put("anWav",anWav);
//		message.put("jsonObject",jsonObject);
//		System.out.println("askStr  is "+askStr);
//				message.put("jsonObject", jsonObject);
		//���뷵��ֵ
		result.getResult().put("message", message);
		result.setStatusCode(statusCode);
		result.setDesc(desc);
		return result.getRe();
	}
	public HashMap<String, Object> ask(String wav) {
		//����ֵ
		Result result = new Result();
		String statusCode =result.getStatusCode();//״̬��
		String desc = result.getDesc();//״̬������
		//ִ�в���
		//��ѯ����Ƶ��-����ѯ�����֡�
		System.out.println("wav is "+ wav);
		String askStr = null;
		JSONObject jsonObject =null;
		try {
			askStr = new AsrMain(wav).run();
			jsonObject = JSONArray.parseObject(askStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DemoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map message =new HashMap<String, Object>(2);
		message.put("askStr",askStr);
		message.put("jsonObject",jsonObject);
		System.out.println("askStr  is "+askStr);
//		message.put("jsonObject", jsonObject);
		//���뷵��ֵ
		result.getResult().put("message", message);
		result.setStatusCode(statusCode);
		result.setDesc(desc);
		return result.getRe();
	}
	
	public HashMap<String, Object> test(String askWar) {
		//����ֵ
		Result result = new Result();
		String statusCode =result.getStatusCode();//״̬��
		String desc = result.getDesc();//״̬������
		//ִ�в���
//		//��ѯ����Ƶ��-����ѯ�����֡�
//		String askStr = null;
//		JSONObject jsonObject =null;
//		try {
//			askStr = new AsrMain(askWar).run();
//			jsonObject = JSONArray.parseObject(askStr);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DemoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//��ѯ�����֡�-��ͼ��-���������֡�
//		String Url = "utl";
//		Map pr =new HashMap<String, Object>(1);
//		pr.put("askStr",askStr);
//		String jsoStr = HttpClientTool.doGet(Url,pr);//��ȡ����ֵjson String
//		JSONObject jsonObject = JSONArray.parseObject(jsoStr);//��  json String -�� json 
//		String anStr = jsonObject.getString("anStr");
		//�������֡�-��������Ƶ��
		String anWav=null;
//		try {
//			anWav = new TtsMain("��������1").run("anName1");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DemoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//���ؽ��

		Map message =new HashMap<String, Object>(1);
//		{{
//			put("askWar",askWar);
//			put("anStr",anStr);
//		}};
//		message.put("askStr",askStr);
		message.put("anWav",anWav);
//		message.put("jsonObject", jsonObject);
		//���뷵��ֵ
		result.getResult().put("message", message);
		result.setStatusCode(statusCode);
		result.setDesc(desc);
		return result.getRe();
	}
//	public HashMap<String, Object> asdTuLing(String askWar) {
//		//����ֵ
//		Result result = new Result();
//		String statusCode =result.getStatusCode();//״̬��
//		String desc = result.getDesc();//״̬������
//		//ִ�в���
//		//��ѯ����Ƶ��-����ѯ�����֡�
//		String askStr = null;
//		try {
//			askStr = new AsrMain(askWar).run();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DemoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//��ѯ�����֡�-��ͼ��-���������֡�
//		String Url = "utl";
//		Map pr =new HashMap<String, Object>(1);
//		pr.put("askStr",askStr);
//		String jsoStr = HttpClientTool.doGet(Url,pr);//��ȡ����ֵjson String
//		JSONObject jsonObject = JSONArray.parseObject(jsoStr);//��  json String -�� json 
//		String anStr = jsonObject.getString("anStr");
//		//�������֡�-��������Ƶ��
//		String anWav=null;
//		try {
//			anWav = new TtsMain(anStr).run();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DemoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//���ؽ��
//
//		Map message =new HashMap<String, Object>(4)
//		{{
//			put("askWar",askWar);
//			put("anStr",anStr);
//		}};
//		message.put("askStr",askStr);
//		message.put("anWav",anWav);
//		//���뷵��ֵ
//		result.getResult().put("message", message);
//		result.setStatusCode(statusCode);
//		result.setDesc(desc);
//		return result.getRe();
//	}
	
	//������ַ����̬����
	public static String getURLContent(String urlStr) {                 
        
        //�����url   
        URL url = null;        
          
        //������http����    
        HttpURLConnection httpConn = null;    
          
        //�����������  
        BufferedReader in = null;     
          
        //�������Ļ���  
        StringBuffer sb = new StringBuffer();   
          
        try{       
             url = new URL(urlStr);       
               
             in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8") );   
               
             String str = null;    
              
             //һ��һ�н��ж���  
             while((str = in.readLine()) != null) {      
                sb.append( str );       
             }       
        } catch (Exception ex) {     
                  
        } finally{      
             try{               
                  if(in!=null) {    
                   in.close(); //�ر���      
                  }       
            }catch(IOException ex) {        
              
            }       
        }       
        String result =sb.toString();       
        return result;      
    }    
}
