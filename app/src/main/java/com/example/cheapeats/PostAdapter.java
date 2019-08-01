package com.example.cheapeats;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static androidx.core.content.ContextCompat.startActivity;

public class PostAdapter extends FirestoreRecyclerAdapter<PostModel, PostAdapter.PostHolder> {
//    Context currentContext;
    public PostAdapter(@NonNull FirestoreRecyclerOptions<PostModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final PostHolder postHolder, int position ,
                                    @NonNull PostModel postModel) {
        final String theId = getSnapshots().getSnapshot(position).getId();
        postHolder.txt_title.setText(postModel.getTitle());
        postHolder.txt_description.setText(postModel.getDescription());
        postHolder.key_holder.setText(theId);


        postHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(postHolder.parentContext, ViewPostActivity.class);
                intent.putExtra("post_key", theId);
                postHolder.parentContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,
                                            parent, false);
        return new PostHolder(itemView, parent.getContext());
    }

    class PostHolder extends RecyclerView.ViewHolder {
        //TODO: replace with actual event post details
        TextView txt_title, txt_description, key_holder;
        ImageButton viewBtn;
        Context parentContext;

        public PostHolder(@NonNull View itemView, Context parentContext) {
            super(itemView);
            //TODO: Replace with views for the actual post display
            txt_title = (TextView)itemView.findViewById(R.id.text_view_title);
            txt_description = (TextView)itemView.findViewById(R.id.text_view_description);
            key_holder = (TextView)itemView.findViewById(R.id.post_key_holder);
            viewBtn = (ImageButton) itemView.findViewById(R.id.btn_view_post);
            this.parentContext = parentContext;
        }
    }
}
