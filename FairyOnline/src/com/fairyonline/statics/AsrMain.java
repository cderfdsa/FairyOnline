package com.fairyonline.statics;


import org.json.JSONObject;

import com.fairyonline.common.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsrMain {

//    public static void main(String[] args) throws IOException, DemoException {
//        AsrMain demo = new AsrMain("url");
//        // ��д������Ϣ
//        String result = demo.run();
//        System.out.println("ʶ�����������ǣ�");
//        System.out.println(result);
//    }
    public AsrMain(String filename) {
    	this.filename = filename;
    }
    public AsrMain() {};
    
    //  ��д��ҳ�������appkey �� $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
    private final String appKey = "8kFTjef6WSNXGKHkzUDC2FG2";

    // ��д��ҳ�������APP SECRET �� $secretKey="94dc99566550d87f8fa8ece112xxxxx"
    private final String secretKey = "a2b4e2509f6ea6d5eaad6c006234815f";

    // ��Ҫʶ����ļ�//corpus���Ͽ�
    private /*final*/ String filename = "corpus/16k_test.pcm";

    // �ļ���ʽ
    private final String format = "pcm";
    
    //  1537 ��ʾʶ����ͨ����ʹ�����뷨ģ�͡�1536��ʾʶ����ͨ����ʹ������ģ�͡� �������ֲμ��ĵ�
    //  Ӣ��   $dev_pid = 1737;
    private final int dev_pid = 1537;

    private String cuid = "1234567JAVA";
 
 // �����ʹ̶�ֵ
    private final int rate = 16000;
	
    public boolean methodRaw = false; // Ĭ����json��ʽ�ϴ���Ƶ�ļ�

    private final String url = "http://vop.baidu.com/server_api"; // ���Ը�Ϊhttps

    public String run() throws IOException, DemoException {
        TokenHolder holder = new TokenHolder(appKey, secretKey, TokenHolder.TTS_SCOPE);
        holder.resfresh();
        String token = holder.getToken();
        String result = null;
        if (methodRaw) {
            result = runRawPostMethod(token);
        } else {
            result = runJsonPostMethod(token);
        }
        return result;
    }

    private String runRawPostMethod(String token) throws IOException, DemoException {
        String url2 = url + "?cuid=" + ConnUtil.urlEncode(cuid) + "&dev_pid=" + dev_pid + "&token=" + token;
        //System.out.println(url2);
        byte[] content = getFileContent(filename);
        HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "audio/" + format + "; rate=" + rate);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(content);
        conn.getOutputStream().close();
        String result = ConnUtil.getResponseString(conn);
        return result;
    }

    public String runJsonPostMethod(String token) throws DemoException, IOException {

        byte[] content = getFileContent(filename);
        String speech = base64Encode(content);

        JSONObject params = new JSONObject();
        params.put("dev-pid", dev_pid);
        params.put("format", format);
        params.put("rate", rate);
        params.put("token", token);
        params.put("cuid", cuid);
        params.put("channel", "1");
        params.put("len", content.length);
        params.put("speech", speech);

        // System.out.println(params.toString());
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);
        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        String result = ConnUtil.getResponseString(conn);
        return result;
    }

    private byte[] getFileContent(String filename) throws DemoException, IOException {
        File file = new File(filename);
        if (!file.canRead()) {
            System.err.println("�ļ������ڻ��߲��ɶ�: " + file.getAbsolutePath());
            throw new DemoException("file cannot read: " + file.getAbsolutePath());
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            return ConnUtil.getInputStreamContent(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String base64Encode(byte[] content) {
        /**
         Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  �Ƽ�����
         String str = encoder.encodeToString(content);
         **/

        char[] chars = Base64Util.encode(content); // 1.7 �����£����Ƽ��������и�����ؿ�
        String str = new String(chars);

        return str;
    }

}
