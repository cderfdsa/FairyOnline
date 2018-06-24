package com.fairyonline.statics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;


/**
 * ��������:2018��1��14��
 * ����ʱ��:����10:09:39
 * ������    :yellowcong
 * ���ܸ�Ҫ:MP3תPCM Java��ʽʵ��
 * http://ai.baidu.com/forum/topic/show/496972
 */
public class AudioUtils {
    private static AudioUtils audioUtils = null;
    private AudioUtils(){}

    //˫�жϣ������������
    public static AudioUtils getInstance(){
        if(audioUtils == null){
            synchronized (AudioUtils.class) {
                if(audioUtils == null){
                    audioUtils = new AudioUtils();
                }
            }
        }
        return audioUtils;
    }

    /**
     * MP3ת��PCM�ļ�����
     * 
     * @param mp3filepath ԭʼ�ļ�·��
     * @param pcmfilepath ת���ļ��ı���·��
     * @return 
     * @throws Exception
     */
    public boolean convertMP32Pcm(String mp3filepath, String pcmfilepath){
        try {
            //��ȡ�ļ�����Ƶ����pcm�ĸ�ʽ
            AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);
            //����Ƶת��Ϊ  pcm�ĸ�ʽ��������
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(pcmfilepath));
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ����MP3����
     * 
     * @param mp3filepath
     * @throws Exception
     */
    public void playMP3(String mp3filepath) throws Exception {
    	System.out.println("get playMP3");
        //��ȡ��ƵΪpcm�ĸ�ʽ
        AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);
        System.out.println("get playMP3 0 ");
        // ����
        if (audioInputStream == null){
            System.out.println("null audiostream");
            return;
        }
        System.out.println("get playMP3 1 ");
        //��ȡ��Ƶ�ĸ�ʽ
        AudioFormat targetFormat = audioInputStream.getFormat();
        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, targetFormat, AudioSystem.NOT_SPECIFIED);
        System.out.println("get playMP3 2");
        //����豸
        SourceDataLine line = null;
        try {
            line = (SourceDataLine) AudioSystem.getLine(dinfo);
            line.open(targetFormat);
            line.start();

            int len = -1;
//            byte[] buffer = new byte[8192];
            byte[] buffer = new byte[1024];
            //��ȡ��Ƶ�ļ�
            while ((len = audioInputStream.read(buffer)) > 0) {
                //�����Ƶ�ļ�
                line.write(buffer, 0, len);
            }

            // Block�ȴ���ʱ���ݱ����Ϊ��
            line.drain();

            //�رն�ȡ��
            audioInputStream.close();

            //ֹͣ����
            line.stop();
            line.close();
            System.out.println("get playMP3 3");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("audio problem " + ex);

        }
        System.out.println("get playMP3 4");
    }

    /**
     * ��������:2018��1��14��<br/>
     * ����ʱ��:����9:53:14<br/>
     * �����û�:yellowcong<br/>
     * ���ܸ�Ҫ:��ȡ�ļ�����Ƶ��
     * @param mp3filepath
     * @return
     */
    private AudioInputStream getPcmAudioInputStream(String mp3filepath) {
        File mp3 = new File(mp3filepath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;

            //��ȡ��Ƶ�ļ�����
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3);
            AudioFormat baseFormat = in.getFormat();

            //�趨�����ʽΪpcm��ʽ����Ƶ�ļ�
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

            //�������Ƶ
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }
}