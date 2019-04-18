package com.example.test_php2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test_php2.R;
import com.example.test_php2.sql.DatabaseHelper;
import com.example.test_php2.sql.DatabaseHelper2;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class show_pic_arm extends AppCompatActivity {
    private final AppCompatActivity activity = show_pic_arm.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic_arm);
//        ImageView logoImageView = findViewById(R.id.imageView2);
//        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/pic_arm.jpg", null);
//        logoImageView.setImageBitmap(bitmap);
        re_pic();

        Button cancle = findViewById(R.id.cn);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(show_pic_arm.this, com.example.test_php2.arm.class);
                startActivity(intent);
            }
        });
        Button next = findViewById(R.id.button4);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resizeImage();
                up_pic();
            }
        });

    }

    public void resizeImage(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy", Locale.KOREA);
        Date now = new Date();

        //final File file = new File(Environment.getExternalStorageDirectory()+"/"+count_st+".jpg");
        //String path = (Environment.getExternalStorageDirectory()+"/"+count_st+".jpg");
        String Newpath = (Environment.getExternalStorageDirectory()+"/"+"arm_"+formatter.format(now)+".jpg");

        Bitmap photo = BitmapFactory.decodeFile(Newpath);
        photo = Bitmap.createScaledBitmap(photo,480,640,false);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 80,bytes);

        File f = new File(Newpath);
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            //File file = new File(path);
            //file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void up_pic(){
        Toast.makeText(getBaseContext(), "อัพโหลดรูป", Toast.LENGTH_LONG).show();
        //String path = Environment.getExternalStorageDirectory() + "/pic_arm.jpg";
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy", Locale.KOREA);
        Date now = new Date();
        String path = (Environment.getExternalStorageDirectory()+"/"+"arm_"+formatter.format(now)+".jpg");

        Ion.with(this)
                .load("http://a40e90a2.ngrok.io/pro-android/arm.php")
                .setMultipartFile("upload_file", new File(path))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        process();

                    }
                });
    }

    public void re_pic(){
        OutputStream out = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy", Locale.KOREA);
        Date now = new Date();
        File file = new File(Environment.getExternalStorageDirectory()+"/"+"arm_"+formatter.format(now)+".jpg");
        ImageView image = (ImageView)findViewById(R.id.imageView2);
        Matrix matrix = new Matrix();
        matrix.postRotate(270);
        Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/"+"arm_"+formatter.format(now)+".jpg");
        Bitmap rotated = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);


        try {
            out = new FileOutputStream(file);
            rotated.compress(Bitmap.CompressFormat.JPEG, 100, out);
            image.setImageBitmap(rotated);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    DatabaseHelper2 db2 = new DatabaseHelper2(activity);
    DatabaseHelper db1 = new DatabaseHelper(activity);

    public void process(){
        Ion.with(this)
                .load("http://a40e90a2.ngrok.io/pro-android/arm/test.php")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        double dist = Double.parseDouble(result);

                        if(test(dist)){

                            Intent intent = new Intent(show_pic_arm.this,Risk_armm.class);
                            startActivity(intent);
                        }else{
                            db2.updateDistArm(dist,db1.getName());
                            Intent intent2 = new Intent(show_pic_arm.this,No_riskarmm.class);
                            startActivity(intent2);
                        }

                    }
                });




    }
    public boolean test(double dist){
        if(db2.checkArm(db1.getName())){
            if(dist > db2.maxArm(db1.getName())){
                return true;
            }else {
                return false;
            }
        }else {
            if(dist > db2.firstArm(db1.getName())){
                return true;
            }else{
                return false;
            }
        }
    }

}


