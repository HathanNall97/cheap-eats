package com.example.cheapeats;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecylcerViewHolder used in Post browsing to display the list of posts returned from firebase
 * * TODO: REPLACE txt_title, txt_content to fetch and display data
 */
public class MyRecylerViewHolder extends RecyclerView.ViewHolder {
    //TODO: replace with actual event post details
    TextView txt_title, txt_content;

    public MyRecylerViewHolder(@NonNull View itemView) {
        super(itemView);
        //TODO: Replace with views for the actual post display
        txt_content = (TextView)itemView.findViewById(R.id.txt_content);
        txt_title = (TextView)itemView.findViewById(R.id.txt_title);
    }
}
