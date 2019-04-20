package com.example.test_php2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test_php2.model.User;
import com.example.test_php2.sql.DatabaseHelper;
import com.example.test_php2.sql.DatabaseHelper2;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class first_sound extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = first_sound.this;
    static int visitCount = 0;
    static int visitNext = 0;

    public static final int RECORD_AUDIO = 0;
    private MediaRecorder myAudioRecorder;
    private String output = null;
    private Button start, stop, play,next;
    private boolean permissionToRecordAccepted = false;
    private boolean permissionToWriteAccepted = false;
    private String [] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_sound);

        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
        start = (Button)findViewById(R.id.button1);
        stop = (Button) findViewById(R.id.button2);
        play = (Button)findViewById(R.id.button3);
        next = (Button)findViewById(R.id.button4);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        stop.setEnabled(false);
        play.setEnabled(false);
        next.setEnabled(false);

    }
    @Override public void onClick(View v){
        switch (v.getId()){
            case R.id.button1:
                start(v);
                break;
            case R.id.button2:
                stop(v);
                break;
            case R.id.button3:
                try {
                    play(v);
                }
                catch (IOException e){
                    Log.i("IOException", "Error in play");
                    break;
                }
                break;
            case R.id.button4:
                up_sound(v);
                break;
            default:
                break;
        }
    }
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 200:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                permissionToWriteAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) first_sound.super.finish();
        if (!permissionToWriteAccepted ) first_sound.super.finish();
    }

    public void start(View view){


        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy", Locale.KOREA);
            Date now = new Date();
            visitCount++;
            String count = Integer.toString(visitCount);
            //final File file = new File(Environment.getExternalStorageDirectory()+"/"+"arm_"+formatter.format(now)+".jpg");
            output = Environment.getExternalStorageDirectory()+"/"+count+".wav";
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setAudioChannels(1);
            myAudioRecorder.setAudioSamplingRate(44100);
            myAudioRecorder.setAudioEncodingBitRate(192000);
            myAudioRecorder.setOutputFile(output);
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        start.setEnabled(false);

        stop.setEnabled(true);
        play.setEnabled(false);
        next.setEnabled(false);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();
    }
    public void stop(View view){
//        visitCount++;
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;

        stop.setEnabled(false);
        play.setEnabled(true);
        start.setEnabled(false);
        Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_SHORT).show();
    }
    public void play(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {

        MediaPlayer m = new MediaPlayer();

        try {
            m.setDataSource(output);
            m.prepare();
            m.start();

            Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // make something
        }

        start.setEnabled(true);
        next.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_SHORT).show();
    }
    public void up_sound(View view){
        renameSend();
        String toast_count = Integer.toString(visitNext);
        String toast_count2 = "กำลังอัพโหลดไฟล์เสียงรอบที่"+toast_count+"กรุณารอสักครู่";
        Toast.makeText(getBaseContext(), toast_count2, Toast.LENGTH_LONG).show();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy", Locale.KOREA);
        Date now = new Date();
        String count = Integer.toString(visitNext);
        String path = Environment.getExternalStorageDirectory()+"/"+count+".wav";
        String url = db1.getNg()+"/pro-android/sound.php";
        Ion.with(this)
                .load(url)
                .setMultipartFile("upload_file", new File(path))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        String toast_count = Integer.toString(visitNext);
                        String toast_count2 = "ส่งไฟล์เสียงรอบที่"+toast_count+"สำเร็จ";
                        Toast.makeText(getBaseContext(), toast_count2, Toast.LENGTH_LONG).show();
                        if(visitNext > 4){
                            visitNext = 0;
                            Toast.makeText(getBaseContext(), "รอสักครู่ระบบกำลังประมวลผล", Toast.LENGTH_LONG).show();
                            renameone();
                            process();
                        }

                    }
                });
    }


    DatabaseHelper2 db2 = new DatabaseHelper2(activity);
    DatabaseHelper db1 = new DatabaseHelper(activity);

    public void process() {
        String url = db1.getNg()+"/pro-android/sound/first_test.php";
        Ion.with(this)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        double sum = Double.parseDouble(result);
//                        String sum_st = Double.toString(sum);
//                        String name = user.getName();
                        db2.updateDistRc(sum, db1.getName());
                        Intent intent = new Intent(first_sound.this, Main2Activity.class);
                        startActivity(intent);


                    }
                });


    }

    public void renameSend(){
        visitNext++;
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy", Locale.KOREA);
        Date now = new Date();
        String count_st = Integer.toString(visitCount);
        String name = count_st+".wav";
        File sr = Environment.getExternalStorageDirectory();
        String count = Integer.toString(visitNext);
        File from = new File(sr,name);
        File to = new File(sr,count+".wav");
        from.renameTo(to);
    }

    public void renameone(){
        String name = "1.wav";
        File sr = Environment.getExternalStorageDirectory();
        String count = Integer.toString(visitNext);
        File from = new File(sr,name);
        File to = new File(sr,db1.getName()+".wav");
        from.renameTo(to);
    }
}