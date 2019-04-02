package com.example.test_php2;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test_php2.R;
import com.example.test_php2.sql.DatabaseHelper2;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class record extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = record.this;

    public static final int RECORD_AUDIO = 0;
    private MediaRecorder myAudioRecorder;
    private String output = null;
    private Button start, stop, play,next;
    private boolean permissionToRecordAccepted = false;
    private boolean permissionToWriteAccepted = false;
    private String [] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
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
        if (!permissionToRecordAccepted ) record.super.finish();
        if (!permissionToWriteAccepted ) record.super.finish();
    }

    public void start(View view){


        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy", Locale.KOREA);
            Date now = new Date();
            //final File file = new File(Environment.getExternalStorageDirectory()+"/"+"arm_"+formatter.format(now)+".jpg");
            output = Environment.getExternalStorageDirectory()+"/"+"record_"+formatter.format(now)+".wav";
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
        next.setEnabled(false);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();
    }
    public void stop(View view){

        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;

        stop.setEnabled(false);
        play.setEnabled(true);
        start.setEnabled(true);
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

        next.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_SHORT).show();
    }
    public void up_sound(View view){
        Toast.makeText(getBaseContext(), "อัพโหลดไฟล์เสียง", Toast.LENGTH_LONG).show();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy", Locale.KOREA);
        Date now = new Date();
        String path = Environment.getExternalStorageDirectory()+"/"+"record_"+formatter.format(now)+".wav";;
        Ion.with(this)
                .load("http://2ce3a670.ngrok.io/pro-android/sound.php")
                .setMultipartFile("upload_file", new File(path))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        process();
                    }
                });
    }


    DatabaseHelper2 db = new DatabaseHelper2(activity);

    public void process(){
        Ion.with(this)
                .load("http://2ce3a670.ngrok.io/pro-android/sound/test.php")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        double dist = Double.parseDouble(result);


                        if(test(dist)){
                            db.updateDistRc(dist,"yuriyuripps");
                            Intent intent = new Intent(record.this,Risk_record.class);
                            startActivity(intent);
                            //Toast.makeText(getBaseContext(), "Risk!!!", Toast.LENGTH_LONG).show();
                        }else {
                            Intent intent2 = new Intent(record.this,Norisk_record.class);
                            startActivity(intent2);
                            //Toast.makeText(getBaseContext(), "Same!!!", Toast.LENGTH_LONG).show();
                        }

//                        db.updateDistRc(dist,"yuriyuripps");
                    }
                });




    }

    public boolean test(double dist){
        if(db.checkRc("yuriyuripps")){
            if(dist > db.avgSound("yuriyuripps")){
                return true;
            }else {
                return false;
            }
        }else {
            if(dist > 130){
                return true;
            }else{
                return false;
            }
        }
    }



//    public void process(){
//        Ion.with(this)
//                .load("http://38d5be06.ngrok.io/pro-android/upload/sound/test.php")
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String result) {
//                        //int dist = Integer.parseInt(result);
//                        /*if(dist >= 130) {
//                            //Toast.makeText(getBaseContext(), "Different", Toast.LENGTH_LONG).show();
//                            if(dist >= 135){
//                                Toast.makeText(getBaseContext(), "Different", Toast.LENGTH_LONG).show();
//                            }else {
//                                Toast.makeText(getBaseContext(), "Same", Toast.LENGTH_LONG).show();
//                            }
//                        }else {
//                            Toast.makeText(getBaseContext(), "Same", Toast.LENGTH_LONG).show();
//
//                        }*/
//                        Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
//
//                    }
//                });
//    }
}

