package org.wind.media;

import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Looper;

public class VoiceManager {
	private MediaRecorder recorder = null;
	private VoiceThread thread = null;
	//主线程的handler
	private Handler mainHandler=null;
	//录音默认时常为30秒
    private int timesize=30;
	private VoiceManager(String filepath,Handler handler) {
		recorder = new MediaRecorder();
		// 设置录音源
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// 3gp格式
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		// 设置录音的文件路径
		recorder.setOutputFile(filepath);
		recorder.setVideoEncoder(MediaRecorder.AudioEncoder.AMR_WB);
		thread=new VoiceThread();
//	    Looper looper=Looper.myLooper();
//	    looper.prepare();
//	    handler=new Handler(looper);
	}

	public boolean startRecord() {

		

		return true;
	}
    class VoiceThread extends Thread{
    	int i=0;
    	@Override
    	public void run() {
    		try {
				recorder.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
//    		for(int ){
//    			
//    		}
    	}
    }
}
