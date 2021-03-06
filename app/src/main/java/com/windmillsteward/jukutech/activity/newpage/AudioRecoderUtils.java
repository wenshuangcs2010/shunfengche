package com.windmillsteward.jukutech.activity.newpage;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.windmillsteward.jukutech.utils.DateUtil;

/**
 * Created by MarioStudio on 2016/5/12.
 */

public class AudioRecoderUtils {

    private String filePath;
    private String FolderPath;
    private MediaRecorder mMediaRecorder;
    private final String TAG = "MediaRecord";
    public static final int MAX_LENGTH = 1000 * 60 ;// 最大录音时长1000*60*10;

    private OnAudioStatusUpdateListener audioStatusUpdateListener;

    public AudioRecoderUtils(){
        //默认保存路径为/sdcard/record/下
       this.filePath =  Environment.getExternalStorageDirectory()+"/record/";
        File path = new File(filePath);
        if(!path.exists()) {
            path.mkdirs();
        }
        this.FolderPath = filePath;
//        this.filePath = "/dev/null";
    }

    public AudioRecoderUtils(String filePath) {

        File path = new File(filePath);
        if(!path.exists())
            path.mkdirs();

        this.FolderPath = filePath;
    }

    public AudioRecoderUtils(File file) {
        this.filePath = file.getAbsolutePath();
    }

    private long startTime;
    private long endTime;

    /**
     * 开始录音 使用amr格式
     *      录音文件
     * @return
     */
    public void startRecord() {
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            filePath = FolderPath + DateUtil.getCurrentDate("yyyyMMddHHmm") + ".amr" ;
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            /* ③准备 */
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.setMaxDuration(MAX_LENGTH);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            // AudioRecord audioRecord.
            /* 获取开始时间* */
            startTime = System.currentTimeMillis();
            updateMicStatus();
            Log.i("ACTION_START", "startTime" + startTime);
        } catch (IllegalStateException e) {
            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    /**
     * 停止录音
     */
    public long stopRecord() {
        if (mMediaRecorder == null)
            return 0L;
        endTime = System.currentTimeMillis();
        Log.i("ACTION_END", "endTime" + endTime);
        try {
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            mMediaRecorder.setOnInfoListener(null);
            mMediaRecorder.stop();

        } catch (IllegalStateException e) {
            // TODO: handle exception
            Log.i("Exception", Log.getStackTraceString(e));
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            filePath = "";
        }catch (RuntimeException e) {
            // TODO: handle exception
            Log.i("Exception", Log.getStackTraceString(e));
        }catch (Exception e) {
            // TODO: handle exception
            Log.i("Exception", Log.getStackTraceString(e));
        }
        audioStatusUpdateListener.onStop(filePath,(endTime - startTime));
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;

        Log.i("ACTION_LENGTH", "Time" + (endTime - startTime));
        return endTime - startTime;
    }

    /**
     * 取消录音
     */
    public void cancelRecord(){

        try {
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            mMediaRecorder.setOnInfoListener(null);
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;

        }catch (RuntimeException e){
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        File file = new File(filePath);
        if (file.exists())
            file.delete();

        filePath = "";

    }


    private final Handler mHandler = new Handler();

    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    /**
     * 更新话筒状态
     */
    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间

    public void setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
    }

    private void updateMicStatus() {
        if (mMediaRecorder != null) {
            double ratio = (double)mMediaRecorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
                if(null != audioStatusUpdateListener) {
                    audioStatusUpdateListener.onUpdate(db);
                }
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    public interface OnAudioStatusUpdateListener {
        /**
         * 录音中
         * @param db
         */
        void onUpdate(double db);
        /* 停止录音
         * @param filePath 保存路径
         */
        void onStop(String filePath,long length);
    }
}
