package com.fairyonline.common;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * token�Ļ�ȡ��
 * ��apiKey��secretKey��ȡtoken��ע����Ч�ڱ�����expiresAt
 */
public class TokenHolder {


    public static final String ASR_SCOPE = "audio_voice_assistant_get";

    public static final String TTS_SCOPE = "audio_tts_post";

    /**
     * url , Token��url��http���Ը�Ϊhttps
     */
    private static final String url = "http://openapi.baidu.com/oauth/2.0/token";

    /**
     * asr��Ȩ�� scope ��  "audio_voice_assistant_get"
     * tts ��Ȩ�� scope �� "audio_tts_post"
     */
    private String scope;

    /**
     * ��ҳ����������ʶ��Ӧ�û�ȡ��apiKey
     */
    private String apiKey;

    /**
     * ��ҳ����������ʶ��Ӧ�û�ȡ��secretKey
     */
    private String secretKey;

    /**
     * ������ʽӿڻ�ȡ��token
     */
    private String token;

    /**
     * ��ǰ��ʱ���������
     */
    private long expiresAt;

    /**
     * @param apiKey    ��ҳ����������ʶ��Ӧ�û�ȡ��apiKey
     * @param secretKey ��ҳ����������ʶ��Ӧ�û�ȡ��secretKey
     */
    public TokenHolder(String apiKey, String secretKey, String scope) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.scope = scope;
    }


    /**
     * ��ȡtoken��refresh �����������Ч
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * ��ȡ����ʱ�䣬refresh �����������Ч
     *
     * @return
     */
    public long getExpiresAt() {
        return expiresAt;
    }




    /**
     * ��ȡtoken
     *
     * @return
     * @throws IOException   http�������
     * @throws DemoException http�ӿڷ��ز��� 200, access_tokenδ��ȡ
     */
    public void resfresh() throws IOException, DemoException {
        String getTokenURL = url + "?grant_type=client_credentials"
                + "&client_id=" + ConnUtil.urlEncode(apiKey) + "&client_secret=" + ConnUtil.urlEncode(secretKey);

        // ��ӡ��url�����ŵ�������ڿ��Ը���
        System.out.println("token url:" + getTokenURL);

        URL url = new URL(getTokenURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        String result = ConnUtil.getResponseString(conn);
        System.out.println("Token result json:" + result);
        parseJson(result);
    }

    /**
     * @param result token�ӿڻ�õ�result
     * @throws DemoException
     */
    private void parseJson(String result) throws DemoException {
        JSONObject json = new JSONObject(result);
        if (!json.has("access_token")) {
            // ����û��access_token�ֶ�
            throw new DemoException("access_token not obtained, " + result);
        }
        if (!json.has("scope")) {
            // ����û��scope�ֶ�
            throw new DemoException("scopenot obtained, " + result);
        }
        if (!json.getString("scope").contains(scope)) {
            throw new DemoException("scope not exist, " + scope + "," + result);
        }
        token = json.getString("access_token");
        expiresAt = System.currentTimeMillis() + json.getLong("expires_in") * 1000;
    }
}
