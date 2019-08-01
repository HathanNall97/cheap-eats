package com.example.cheapeats;

import android.app.Notification;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import javax.annotation.Nullable;


public class MainActivity<toggle> extends AppCompatActivity {
    //creation declarations for test layout
    private Button btnAccountSettings, btnClearAll;


    // Represents our database connection to FireStore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postsRef = db.collection("posts");
    private PostAdapter adapter;
    private RecyclerView searchedView;
    //private DatabaseReference dbPosts;


//    private RecyclerView mListView;
//    private FirestoreRecyclerAdapter<PostModel, MyRecylerViewHolder> adapter;
//    FirestoreRecyclerOptions<PostModel> options;



    private Spinner spinnerFilter, sortBySpinner;
    private Button btnFilterOn, btnFilterOff, sortByBtn;
    private SearchView searchV;




    //--------------------------   FILTER STUFF START ----------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecyclerView();

        spinnerFilter = (Spinner) findViewById(R.id.FilterPosts);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filters, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFilter.setAdapter(adapter);

        //SpinnerActivity sp = new SpinnerActivity();

        btnFilterOn = (Button) findViewById(R.id.applyFilterButton);
        btnFilterOff = (Button) findViewById(R.id.deleteFilterBtn);

        btnFilterOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filter = spinnerFilter.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "Applying Filter for " + filter, Toast.LENGTH_SHORT).show();
                filterView(filter);
            }
        });

        btnFilterOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filter = spinnerFilter.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "Deleting Filter for " + filter, Toast.LENGTH_SHORT).show();
                resetOrignalView();
            }
        });


        //-----------------------------   FILTER STUFF DONE ----------------------------------//



        //-----------------------------   SEARCHING STUFF START ----------------------------------//

        searchV = (SearchView) findViewById(R.id.searcher);
        searchV.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                CharSequence searchSequence = searchV.getQuery();
                String searcher = searchSequence.toString();
                Toast.makeText(getApplicationContext(), "Searching Posts for " + searcher, Toast.LENGTH_SHORT).show();

                searchView(searcher);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });


        //-----------------------------   SEARCHING STUFF FINISH ----------------------------------//

        //-----------------------------   CLEAR ALL STUFFINS ----------------------------------//
        btnClearAll = (Button) findViewById(R.id.clearbutton);
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetOrignalView();
            }
        });
        //-----------------------------   CLEAR ALL STUFFINS DONE ----------------------------------//



        // ----------------------------  Sort By Stuff ----------------------------------- //

        sortBySpinner = (Spinner) findViewById(R.id.SortBy);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.sortbys, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortBySpinner.setAdapter(adapter1);

        sortByBtn = (Button) findViewById(R.id.sortByButton);
        sortByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sortBy = sortBySpinner.getSelectedItem().toString();

                Toast.makeText(getApplicationContext(), "Sorting List by " + sortBy, Toast.LENGTH_SHORT).show();

                if(sortBy.equalsIgnoreCase("title"))
                {
                        setTitleView();
                }
                else if(sortBy.equalsIgnoreCase("description"))
                {
                    //RETURN DESCRIPTION LIST
                    setDescriptionView();
                }
                else if(sortBy.equalsIgnoreCase("clout"))
                {
                    resetOrignalView();
                }
                else if(sortBy.equalsIgnoreCase("amount of tags"))
                {
                    //RETURN Tag LIST
                }
                else if(sortBy.equalsIgnoreCase("host"))
                {
                    //RETURN Host LIST
                    setHostView();
                }

            }
        });









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




        setUpRecyclerView();
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    // ---------------------- filter view function -------------------- //
    
    private void filterView(String theFilter)
    {
        // first, tell the adapter to stop listening so we can update it
        adapter.stopListening();
        
        Query query = postsRef.whereArrayContains("tags", theFilter);


        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<PostModel>()
                .setQuery(query, PostModel.class)
                .build();

        adapter = new PostAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // after our changes, tell the adapter to start listening
        adapter.startListening();


        // after our changes, tell the adapter to start listening
    }


    //------------------ Searching Function ----------------------- //

    private void searchView(String searchString)
    {
        adapter.stopListening();

        Query q = postsRef.whereEqualTo("title", searchString);

        // put the query into the adapter
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<PostModel>()
                .setQuery(q, PostModel.class)
                .build();

        adapter = new PostAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.startListening();
    }

    private void resetOrignalView()
    {

        adapter.stopListening();

        //Query query = postsRef.orderBy("title", Query.Direction.ASCENDING);
        Query query = postsRef.orderBy("cloutValue", Query.Direction.DESCENDING);

        // put the query into the adapter
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<PostModel>()
                .setQuery(query, PostModel.class)
                .build();

        adapter = new PostAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.startListening();
    }

    private void setTitleView()
    {
        adapter.stopListening();

        //Query query = postsRef.orderBy("title", Query.Direction.ASCENDING);
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

        adapter.startListening();

    }

    private void setDescriptionView()
    {
        adapter.stopListening();

        //Query query = postsRef.orderBy("title", Query.Direction.ASCENDING);
        Query query = postsRef.orderBy("description", Query.Direction.ASCENDING);

        // put the query into the adapter
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<PostModel>()
                .setQuery(query, PostModel.class)
                .build();

        adapter = new PostAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.startListening();

    }

    private void setHostView()
    {
        adapter.stopListening();

        //Query query = postsRef.orderBy("title", Query.Direction.ASCENDING);
        Query query = postsRef.orderBy("host", Query.Direction.ASCENDING);

        // put the query into the adapter
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<PostModel>()
                .setQuery(query, PostModel.class)
                .build();

        adapter = new PostAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.startListening();

    }

    private void setViewbyTags()
    {
        adapter.stopListening();

        //Query query = postsRef.orderBy("title", Query.Direction.ASCENDING);
        Query query = postsRef.orderBy("tags", Query.Direction.DESCENDING);

        // put the query into the adapter
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<PostModel>()
                .setQuery(query, PostModel.class)
                .build();

        adapter = new PostAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.startListening();


    }


    private void setUpRecyclerView() {
        //Query query = postsRef.orderBy("title", Query.Direction.ASCENDING);
        Query query = postsRef.orderBy("cloutValue", Query.Direction.DESCENDING);

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
