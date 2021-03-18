package com.dadashow.instagramcloneparse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
       ArrayList<String> userNames;
       ArrayList<String> comments;
       ArrayList<Bitmap> images;
       ListView listView;
    PostCLass postCLass;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.feed_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.add_post_item){
            Intent intent=new Intent(getApplicationContext(),UploadActivity.class);
            startActivity(intent);
        }else  if (item.getItemId()==R.id.log_out_item){
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e!=null){
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }else {
                        Intent intent=new Intent(FeedActivity.this,signUpActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        userNames=new ArrayList<>();
        comments=new ArrayList<>();
        images=new ArrayList<>();
        listView=findViewById(R.id.listView);
         postCLass=new PostCLass(FeedActivity.this,userNames,comments,images);
        downloadData();
        listView.setAdapter(postCLass);

    }

    private void downloadData( ) {

        ParseQuery<ParseObject> query=ParseQuery.getQuery("Posts1");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }else {
                    if (objects.size()>0){
                        for (final ParseObject parseObject:objects){
                            ParseFile parseFile=(ParseFile) parseObject.get("image");
                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                        if (e==null&&data!=null){
                                            Bitmap bitmap=BitmapFactory.decodeByteArray(data,0,data.length);
                                            images.add(bitmap);
                                            comments.add(parseObject.getString("comment"));
                                            userNames.add(parseObject.getString("username"));
                                            postCLass.notifyDataSetChanged();

                                    }
                                }
                            });

                        }
                    }
                }
            }
        });
    }
}