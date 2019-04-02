package com.example.test_php2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Matrix;

import com.example.test_php2.model.User;
import com.example.test_php2.sql.DatabaseHelper2;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.luseen.simplepermission.permissions.PermissionActivity;

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
import java.util.List;
import java.util.Locale;

import com.example.test_php2.sql.DatabaseHelper;
public class show_pic_smile extends PermissionActivity {
    private final AppCompatActivity activity = show_pic_smile.this;
    private boolean mIsUploading = false;
    private DatabaseHelper dbuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_smile);

        dbuser = new DatabaseHelper(this);
//        db = new SQLiteDatabaseHandler(this);
//        ImageView logoImageView = findViewById(R.id.imageView2);
//        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/pic_smile.jpg", null);
//        logoImageView.setImageBitmap(bitmap);
        re_pic();

        Button cancle = findViewById(R.id.cn);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(show_pic_smile.this, com.example.test_php2.smile.class);
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm", Locale.KOREA);
        Date now = new Date();

        //final File file = new File(Environment.getExternalStorageDirectory()+"/"+count_st+".jpg");
        //String path = (Environment.getExternalStorageDirectory()+"/"+count_st+".jpg");
        String Newpath = (Environment.getExternalStorageDirectory()+"/"+"smile_"+formatter.format(now)+".jpg");

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
        //String path = Environment.getExternalStorageDirectory() + "/pic_smile.jpg";
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy", Locale.KOREA);
        Date now = new Date();
        String path = (Environment.getExternalStorageDirectory()+"/"+"smile_"+formatter.format(now)+".jpg");
        Ion.with(this)
                .load("http://0342578a.ngrok.io/pro-android/smile.php")
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
        File file = new File(Environment.getExternalStorageDirectory()+"/"+"smile_"+formatter.format(now)+".jpg");
        ImageView image = (ImageView)findViewById(R.id.imageView2);
        Matrix matrix = new Matrix();
        matrix.postRotate(270);
        Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/"+"smile_"+formatter.format(now)+".jpg");
        Bitmap rotated = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);


        try {
            out = new FileOutputStream(file);
            rotated.compress(Bitmap.CompressFormat.JPEG, 100, out);
            image.setImageBitmap(rotated);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    DatabaseHelper2 db = new DatabaseHelper2(activity);

    public void process(){
        Ion.with(this)
                .load("http://0342578a.ngrok.io/pro-android/smile/test.php")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        double dist = Double.parseDouble(result);
                        //Toast.makeText(getBaseContext(), "Risk!!!", Toast.LENGTH_LONG).show();
                        if(test(dist)){
                            db.updateDistSm(dist,"yuriyuripps");
                            Intent intent = new Intent(show_pic_smile.this,Risk_smile.class);
                            startActivity(intent);
                            //Toast.makeText(getBaseContext(), "Risk!!!", Toast.LENGTH_LONG).show();
                        }else {
                            Intent intent2 = new Intent(show_pic_smile.this,Norisk_smile.class);
                            startActivity(intent2);
                            //Toast.makeText(getBaseContext(), "Same!!!", Toast.LENGTH_LONG).show();
                        }

                        //Toast.makeText(getBaseContext(), db.check("yuriyuripps"), Toast.LENGTH_LONG).show();


                    }
                });




    }

//        public void process(){
//        final User user1 = new User();
//        Ion.with(this)
//                .load("http://4ad8f768.ngrok.io/pro-android/smile/test.php")
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String result) {
//                        Double dist = Double.parseDouble(result);
//
//                        if(test(dist)){
//                            db.updateDistSm(dist,"yuriyuripps");
//                            Intent intent = new Intent(show_pic_smile.this,Risk_smile.class);
//                            startActivity(intent);
//                            //db.updateDistRc(dist,"yuriyuripps");
//                            //Toast.makeText(getBaseContext(), "Risk!!!", Toast.LENGTH_LONG).show();
//                        }else {
//                            Intent intent2 = new Intent(show_pic_smile.this,Norisk_smile.class);
//                            startActivity(intent2);
//                            //Toast.makeText(getBaseContext(), "Same!!!", Toast.LENGTH_LONG).show();
//                        }
//
//
//
//
//
//
//
//                    }
//                });
//    }

    public boolean test(double sm){
        if(db.checkSm("yuriyuripps")){
            if(sm > db.avgSmile("yuriyuripps")){
                return true;
            }else {
                return false;
            }
        }else {
            if(sm > 130){
                return true;
            }else{
                return false;
            }
        }
    }



}

