package com.fairyonline.statics;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fairyonline.common.*;

public class TtsMain {

//    public static void main(String[] args) throws IOException, DemoException {
//        (new TtsMain()).run();
//    }

	public TtsMain(String text) {
		this.text = text;
	};
	
    //  ��д��ҳ�������appkey �� $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
    private final String appKey = "8kFTjef6WSNXGKHkzUDC2FG2";

    // ��д��ҳ�������APP SECRET �� $secretKey="94dc99566550d87f8fa8ece112xxxxx"
    private final String secretKey = "a2b4e2509f6ea6d5eaad6c006234815f";


    // text ������Ϊ"��ӭʹ�ðٶ������ϳ�"��urlencode,utf-8 ����
    // ���԰ٶ�����"urlencode"
    private /*final*/ String text = "��ӭʹ�ðٶ�����";

    // ������ѡ��, 0Ϊ��ͨŮ����1Ϊ��ͨ������3Ϊ��кϳ�-����ң��4Ϊ��кϳ�-��ѾѾ��Ĭ��Ϊ��ͨŮ��
    private final int per = 4;
    // ���٣�ȡֵ0-9��Ĭ��Ϊ5������
    private final int spd = 5;
    // ������ȡֵ0-9��Ĭ��Ϊ5�����
    private final int pit = 5;
    // ������ȡֵ0-9��Ĭ��Ϊ5������
    private final int vol = 5;

    public final String url = "http://tsn.baidu.com/text2audio"; // ����ʹ��https

    private String cuid = "1234567JAVA";

    private String savUrl =  "E:\\Program Files\\JavaEE\\eclipseWork\\FairyOnline\\mps\\";
    
    public String run(String name) throws IOException, DemoException {
        TokenHolder holder = new TokenHolder(appKey, secretKey, TokenHolder.ASR_SCOPE);
        holder.resfresh();
        String token = holder.getToken();

        String url2 = url + "?tex=" + ConnUtil.urlEncode(text);
        url2 += "&per=" + per;
        url2 += "&spd=" + spd;
        url2 += "&pit=" + pit;
        url2 += "&vol=" + vol;
        url2 += "&cuid=" + cuid;
        url2 += "&tok=" + token;
        url2 += "&lan=zh&ctp=1";
         System.out.println(url2); // ��������ϴ�url��������Ͽ��Բ���
        HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
        conn.setConnectTimeout(5000);
        String contentType = conn.getContentType();
        if (contentType.contains("mp3")) {
            byte[] bytes = ConnUtil.getResponseBytes(conn);
            File file = new File(savUrl+name+".mp3"); // ��mp3�ļ����ɲ���
            // System.out.println( file.getAbsolutePath());
            FileOutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
            System.out.println("mp3 file write to " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } else {
            System.err.println("ERROR: content-type= " + contentType);
            String res  = ConnUtil.getResponseString(conn);
            System.err.println(res);
        }
        return null;
    }
}
