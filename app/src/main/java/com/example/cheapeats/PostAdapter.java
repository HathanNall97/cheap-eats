package com.example.cheapeats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PostAdapter extends FirestoreRecyclerAdapter<PostModel, PostAdapter.PostHolder> {

    public PostAdapter(@NonNull FirestoreRecyclerOptions<PostModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostHolder postHolder, int i ,
                                    @NonNull PostModel postModel) {
        postHolder.txt_title.setText(postModel.getTitle());
        postHolder.txt_description.setText(postModel.getTitle());
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,
                                            parent, false);
        return new PostHolder(itemView);
    }

    class PostHolder extends RecyclerView.ViewHolder {
        //TODO: replace with actual event post details
        TextView txt_title, txt_description;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            //TODO: Replace with views for the actual post display
            txt_title = (TextView)itemView.findViewById(R.id.text_view_title);
            txt_description = (TextView)itemView.findViewById(R.id.text_view_description);
        }
    }
}
