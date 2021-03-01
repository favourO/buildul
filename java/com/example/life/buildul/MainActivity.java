package com.example.life.buildul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.adapters.MainAdapter;
import com.example.life.buildul.adapters.VerticalAdapter;
import com.example.life.buildul.app.HorizontalRecycle;
import com.example.life.buildul.app.User;
import com.example.life.buildul.app.VerticalRecycle;
import com.example.life.buildul.models.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FirebaseAuth.AuthStateListener {
    private RecyclerView mRecyclerview, vRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter, adapter;
    private ArrayList<String> mDataSet, mDataSet1;
    //private VerticalAdapter vAdapter;
    private Context mContext;
    private List<Model> models;
    private ArrayList<Integer> mDataSet2;
    private GridLayoutManager vLayoutManager;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase, vDatabase;
    private TextView displayName, displayName2, displayEmail;
    private FirebaseAuth mAuth;
    private String Name, Name2, eml;
    private String UID;
    private TextView SearchBar;
    @NonNull
    protected static final Query query =
            FirebaseDatabase.getInstance().getReference().child("VerticalRecycle").limitToLast(20);
    @NonNull
    protected static final Query query1 =
            FirebaseDatabase.getInstance().getReference().child("HorizontalRecycle").limitToLast(7);
    //private ArrayList<VerticalRecycle> recycleList;

    //private FirebaseRecyclerAdapter ;

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.aone, R.drawable.atwo, R.drawable.athree, R.drawable.afour, R.drawable.afive, R.drawable.asix, R.drawable.aseven, R.drawable.aeight};
    //int[] horizontalImages = {R.drawable.aone, R.drawable.atwo, R.drawable.athree, R.drawable.afour, R.drawable.afive, R.drawable.asix, R.drawable.aseven, R.drawable.aeight};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            UID = mAuth.getUid();
        }
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Users/" + UID);
        vDatabase = FirebaseDatabase.getInstance().getReference("Users/");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //search bar
        SearchBar = (TextView) findViewById(R.id.search_link);
        SearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Search.class);
                startActivity(i);
            }
        });

        //carousel started here
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        //Add carousel slider

        //Horizontal recycler view was populated here
        //populateHorizontal();
        //Horizontal recycler-view started here
        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        //mRecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerview.setLayoutManager(layoutManager);
        final RecyclerView.Adapter adapter1 = newAdapter1();

        adapter1.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });
        //mAdapter = new MainAdapter(mDataSet, mDataSet1, mDataSet2, mContext);
        mRecyclerview.setAdapter(adapter1);

        //Vertical recycler view populated here
        vRecyclerview = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager vLayoutManager = new GridLayoutManager(this, 2);
        vRecyclerview.setLayoutManager(vLayoutManager);
        final RecyclerView.Adapter adapter = newAdapter();

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });

        vRecyclerview.setAdapter(adapter);
        //vRecyclerview.setAdapter(vAdapter);
        //populateVertical();
        //fab here
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Cart.class);
                startActivity(i);
            }
        });

        //Drawer Navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Login and SignUp TextView
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headView = navigationView.getHeaderView(0);
        displayName = (TextView) headView.findViewById(R.id.textView);
        displayName.setText(getString(R.string.login_drawer));
        displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
        displayName2 = (TextView) headView.findViewById(R.id.textView1);
        displayName2.setText(getString(R.string.sign_up));
        displayName2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class
                ));
            }
        });
        displayEmail = (TextView) headView.findViewById(R.id.textView2);
        displayEmail.setText(getString(R.string.welcome));


        //Set Drawer Display name
        final ValueEventListener valueEventListener = mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mAuth.getCurrentUser() != null){
                    Name = dataSnapshot.child("firstname").getValue().toString();
                    Name2 = dataSnapshot.child("lastname").getValue().toString();
                    eml = dataSnapshot.child("email").getValue().toString();
                    View headView = navigationView.getHeaderView(0);
                    displayName = (TextView) headView.findViewById(R.id.textView);
                    displayName2 = (TextView) headView.findViewById(R.id.textView1);
                    displayEmail = (TextView) headView.findViewById(R.id.textView2);
                    displayName.setText(Name);
                    displayName.setTextColor(Color.BLUE);
                    displayName.setHighlightColor(Color.WHITE);
                    displayName2.setText("  "+Name2);
                    displayName2.setTextColor(Color.BLUE);
                    displayName2.setHighlightColor(Color.WHITE);
                    displayEmail.setText(eml);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        attachRecyclerViewAdapter();
        attachRecyclerViewAdapter1();

        FirebaseAuth.getInstance().addAuthStateListener(this);
    }
    @Override
    protected void onStop(){
        super.onStop();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth auth){
        attachRecyclerViewAdapter();
        attachRecyclerViewAdapter1();
    }

    @Override
    public void onResume(){
        super.onResume();

        attachRecyclerViewAdapter();
        attachRecyclerViewAdapter1();
    }

    private void attachRecyclerViewAdapter(){
        final RecyclerView.Adapter adapter = newAdapter();

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                vRecyclerview.smoothScrollToPosition(adapter.getItemCount());
                //super.onItemRangeInserted(positionStart, itemCount);
            }
        });

        vRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void attachRecyclerViewAdapter1(){
        final RecyclerView.Adapter adapter1 = newAdapter1();

        adapter1.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclerview.smoothScrollToPosition(adapter1.getItemCount());
                //super.onItemRangeInserted(positionStart, itemCount);
            }
        });

        mRecyclerview.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }

    @NonNull
    protected RecyclerView.Adapter newAdapter(){
        FirebaseRecyclerOptions<VerticalRecycle> options =
                new FirebaseRecyclerOptions.Builder<VerticalRecycle>()
                .setQuery(query, VerticalRecycle.class)
                .setLifecycleOwner(this)
                .build();

        return new FirebaseRecyclerAdapter<VerticalRecycle, Viewholder>(options){
            @NonNull
            @Override
            public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                return new Viewholder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vertical_feeds, parent, false));
            }


            @Override
            protected void onBindViewHolder(@NonNull Viewholder viewholder, int position, @NonNull final VerticalRecycle model){
                viewholder.name.setText(model.title);
                Picasso.with(MainActivity.this).load(model.image).fit().centerCrop().into(viewholder.imageView);
                viewholder.price.setText(model.price);
                viewholder.description.setText(model.description);
                viewholder.checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, Gallery.class);
                        i.putExtra("Title", model.title);
                        i.putExtra("Image", model.image.toString());
                        i.putExtra("Price", model.price);
                        i.putExtra("Description", model.description);
                        MainActivity.this.startActivity(i);
                    }
                });
            }

            @Override
            public void onDataChanged(){
                //If there are no vertical feeds, show a view indicating error

            }

            @Override
            public void onError(DatabaseError e){
                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        };
    }




    @NonNull
    protected RecyclerView.Adapter newAdapter1(){
        FirebaseRecyclerOptions<HorizontalRecycle> options =
                new FirebaseRecyclerOptions.Builder<HorizontalRecycle>()
                        .setQuery(query1, HorizontalRecycle.class)
                        .setLifecycleOwner(this)
                        .build();

        return new FirebaseRecyclerAdapter<HorizontalRecycle, Viewholder>(options){
            @NonNull
            @Override
            public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                return new Viewholder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.horizontal_feeds, parent, false));
            }


            @Override
            protected void onBindViewHolder(@NonNull Viewholder viewholder, int position, @NonNull final HorizontalRecycle model){
                viewholder.name.setText(model.title);
                Picasso.with(MainActivity.this).load(model.image).fit().centerCrop().into(viewholder.imageView);
                viewholder.price.setText(model.price);
                viewholder.description.setText(model.description);
                viewholder.checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, PlanGallery.class);
                        i.putExtra("Title", model.title);
                        i.putExtra("Image", model.image.toString());
                        i.putExtra("Price", model.price);
                        i.putExtra("Description", model.description);
                        MainActivity.this.startActivity(i);
                    }
                });
            }

            @Override
            public void onDataChanged(){
                //If there are no vertical feeds, show a view indicating error

            }

            @Override
            public void onError(DatabaseError e){
                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        };
    }




    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, price, description;
        Button checkout;

        private Viewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            price = (TextView) itemView.findViewById(R.id.price);
            description = (TextView) itemView.findViewById(R.id.description);
            checkout = (Button) itemView.findViewById(R.id.checkout);

        }

    }


    //horizontal arraylist
   /* private void  populateHorizontal(){
        mDataSet = new ArrayList<>();
        for (int i = 0; i <= 7; i++){
            mDataSet.add("Design " + i);
        }

        mDataSet2 = new ArrayList<>();
        for (int i = 0; i < horizontalImages.length; i++) {
            mDataSet2.add(horizontalImages[i]);
        }

        mDataSet1 = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            mDataSet1.add("N345,000");
        }
    }*/



    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (id == R.id.cart) {
            Intent i = new Intent(MainActivity.this, Cart.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, MyAccount.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(MainActivity.this, MyOrders.class);
            startActivity(intent);
        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(MainActivity.this, Cart.class);
            startActivity(intent);
        }/* else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(MainActivity.this, MyFavorites.class);
            startActivity(intent);
        } */else if (id == R.id.nav_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        } /*else if (id == R.id.nav_signup) {
            if (mAuth.getCurrentUser() == null){
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(MainActivity.this, "You are Already Logged in", Toast.LENGTH_LONG).show();
            }
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
