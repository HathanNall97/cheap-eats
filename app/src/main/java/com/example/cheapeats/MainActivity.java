package com.example.cheapeats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity<toggle> extends AppCompatActivity{
    //creation declarations for test layout
    private Button btnAccountSettings;
    private Button browsePostsBtn;



    //Filter Stuff
    private Spinner spinFilter;
    private Button applyFilter, deleteFilter;


    public void onCreateFilt(Bundle savedInstanceStates)
    {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.activity_main);
        addItemsOnSpinner();
        addFilterOnButton();
        addListenerOnSpinnerItemSelection();
        //addListenerOnDeleteButton()
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        spinFilter = (Spinner) findViewById(R.id.FilterPosts);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFilter.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinFilter = (Spinner) findViewById(R.id.FilterPosts);
        //spinFilter.setOnItemClickListener(new CustomOnItemSelectedListener());
    }

    public void addFilterOnButton()
    {
        spinFilter = (Spinner) findViewById(R.id.FilterPosts);
        applyFilter = (Button) findViewById(R.id.applyFilterButton);

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinFilter.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
    
    /*public void addItemsOnSpinner(){

        spinFilter = (Spinner) findViewById(R.id.FilterPosts);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFilter.setAdapter(dataAdapter);
    }*/


    //post stuff
//    EditText edt_title, edt_content;
//    Button btn_post;
//    RecyclerView recyclerView;

//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;

    // Represents our database connection to FireStore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postsRef = db.collection("posts");
    private PostAdapter adapter;

//    private RecyclerView mListView;
//    private FirestoreRecyclerAdapter<PostModel, MyRecylerViewHolder> adapter;
//    FirestoreRecyclerOptions<PostModel> options;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpRecyclerView();

        // SOME VERY IMPORTANT VIEW BULLSHIT
//        View app_bar_view = findViewById(R.id.app_bar_id);
//        View app_content_veiw= app_bar_view.findViewById(R.id.inluded_content_main_id);
//        /*
//         * TEST CODE FOR POST CREATION
//         */
//        edt_content = (EditText) app_content_veiw.findViewById(R.id.edt_content);
//        edt_title = (EditText) app_content_veiw.findViewById(R.id.edt_title);
//        btn_post = (Button) app_content_veiw.findViewById(R.id.btn_post);
//        recyclerView = (RecyclerView)app_content_veiw.findViewById(R.id.my_fucking_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // SET ONCLICK LISTENER FOR TEST BUTTON
//        btn_post.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                postComment();
//            }
//        });

        /* tell our adapter to start listening, and refreshing on updates*/
//        adapter.startListening();
//        recyclerView.setAdapter(adapter);




        btnAccountSettings = (Button) findViewById(R.id.UserSettings);
        btnAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
            }
        });

        FloatingActionButton buttonAddPost = findViewById(R.id.btn_add_post);
        buttonAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewPostActivity.class));
            }
        });

//        browsePostsBtn = (Button) findViewById(R.id.button1);
//        browsePostsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, PostBrowsingActivity.class));
//            }
//        });
//        browsePostsBtn = (Button) findViewById(R.id.PostCreateButton);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setUpRecyclerView() {
        Query query = postsRef.orderBy("title", Query.Direction.ASCENDING);

        // put the query into the adapter
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<PostModel>()
            .setQuery(query, PostModel.class)
            .build();

        adapter = new PostAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //TODO: UPDATE TO CREATE A NEW EVENTMODEL WITH THE FULL FIELDS FILLED OUT
//    private void postComment() {
//        String title = edt_title.getText().toString();
//        String content = edt_content.getText().toString();
//
//        // Create the new post to display
//        PostModel newPost = new PostModel(title, content);
//
//        // Commits the new item to the database
//        db.collection("posts")
//                .add(newPost)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("I IS A TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("I IS A SAD TAG!!", "Error adding document", e);
//                    }
//                });
//        //Display the comment in the RecylclerViewHolder, TODO: Check if this is necessary
////        displayEventPosts();
////        adapter.notifyDataSetChanged();
//    }

//    private void displayEventPosts() {
//        //Get list of posts from firesore
//        Query query = db.collection("posts")
//                .orderBy("postTitle", Query.Direction.ASCENDING);
//
//        // specify the options that will be used to build the list of posts from firestore
//        options = new FirestoreRecyclerOptions.Builder<PostModel>()
//                .setQuery(query, PostModel.class)
//                .build();
//
//        // adapt the firestore data using our model
//        adapter = new FirestoreRecyclerAdapter<PostModel, MyRecylerViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull MyRecylerViewHolder holder, int i, @NonNull PostModel postModel) {
////                postViewHolder.setPostTitle(postModel.getTitle());
//                holder.txt_title.setText(postModel.getTitle());
//                holder.txt_content.setText(postModel.getDescription());
//            }
//
//            @NonNull
//            @Override
//            public MyRecylerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//                //!-------------------------------------------------------------------------------------------------------------- may have to change item_product to post
//                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false);
//                return new MyRecylerViewHolder(itemView);
//            }
//        };
//        /* tell our adapter to start listening, and refreshing on updates*/
//        adapter.startListening();
//        recyclerView.setAdapter(adapter);
//    }


}
