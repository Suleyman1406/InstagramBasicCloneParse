package com.dadashow.instagramcloneparse;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class PostCLass extends ArrayAdapter<String> {
    private final    ArrayList<String> userNames;
    private final   ArrayList<String> comments;
    private final   ArrayList<Bitmap> images;
     private final Activity context;


    public PostCLass(Activity context, ArrayList<String> userNames, ArrayList<String> comments, ArrayList<Bitmap> images) {
        super(context, R.layout.custom_view_layout,userNames);
        this.comments=comments;
        this.userNames=userNames;
        this.images=images;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.custom_view_layout,null,true);
        TextView userNameText=view.findViewById(R.id.customViewUserNameText);
        TextView commentText=view.findViewById(R.id.customViewCommentText);
        ImageView imageView=view.findViewById(R.id.customViewImage);
        userNameText.setText(userNames.get(position));
        commentText.setText(comments.get(position));
        imageView.setImageBitmap(images.get(position));
        return view;
    }
}
