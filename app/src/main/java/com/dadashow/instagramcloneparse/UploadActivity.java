package com.dadashow.instagramcloneparse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {
    ImageView imageView;
    EditText commentText;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        imageView=findViewById(R.id.selectedImage);
        commentText=findViewById(R.id.commentText);
    }
    public  void upload(View view){
        String  comment=commentText.getText().toString();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
        byte[]bytes=byteArrayOutputStream.toByteArray();
        ParseFile parseFile=new ParseFile("image.png",bytes);
        ParseObject parseObject=new ParseObject("Posts1");
        parseObject.put("comment",comment);
        parseObject.put("image",parseFile);
        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Post Uploaded",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
    public void chooseImage(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }else{
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         bitmap=null;
        if (requestCode==2){
            if (resultCode==RESULT_OK && data!=null){
                try {
                    if (Build.VERSION.SDK_INT>=28){
                        ImageDecoder.Source source=ImageDecoder.createSource(this.getContentResolver(),data.getData());

                        bitmap=ImageDecoder.decodeBitmap(source);

                    }else {
                        bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                imageView.setImageBitmap(bitmap);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==1){
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED ){
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}