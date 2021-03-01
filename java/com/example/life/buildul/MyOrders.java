package com.example.life.buildul;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.app.CartControl;
import com.example.life.buildul.app.OrderReturn;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyOrders extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String UID, Email;
    private int getCount;
    private int count;
    private RecyclerView cartRecycler;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Query query, removeQuery, removeSingleQuery;
    private TextView ErrorMessage;
    private Query queryNow;

    @NonNull
    protected String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            startActivity(new Intent(MyOrders.this, Login.class));
            finish();
        }else {
            final String WelcomMail = mAuth.getCurrentUser().getEmail();
        }
        setContentView(R.layout.activity_my_orders);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //UID = mAuth.getCurrentUser().getUid();
        //Uid = mAuth.getCurrentUser().getUid();
        String strUid = String.valueOf(Uid);

        database = FirebaseDatabase.getInstance();
        reference =  database.getReference();
        //this.removeQuery = database.getReference().child("Carts/Cart/" + UID).orderByKey();

        mAuth = FirebaseAuth.getInstance();
        //getting the current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /*if (user != null) {
            email.setText((CharSequence) user.getEmail());
        }*/

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int countday = (int)dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        query =
                FirebaseDatabase.getInstance().getReference().child("Orders/Order/"+UID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cartRecycler = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        cartRecycler.setLayoutManager(layoutManager);

        final RecyclerView.Adapter adapter = newAdapter();

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });

        cartRecycler.setAdapter(adapter);
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    @Override
    public void onStart(){
        super.onStart();
        attachRecyclerViewAdapter();
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
    }

    @Override
    public void onResume(){
        super.onResume();

        attachRecyclerViewAdapter();
    }

    private void attachRecyclerViewAdapter(){
        final RecyclerView.Adapter adapter = newAdapter();

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                cartRecycler.smoothScrollToPosition(adapter.getItemCount());
                //super.onItemRangeInserted(positionStart, itemCount);
            }
        });

        cartRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @NonNull
    protected RecyclerView.Adapter newAdapter(){
        FirebaseRecyclerOptions<OrderReturn> options =
                new FirebaseRecyclerOptions.Builder<OrderReturn>()
                        .setQuery(query, OrderReturn.class)
                        .setLifecycleOwner(this)
                        .build();

        return new FirebaseRecyclerAdapter<OrderReturn, MyOrders.Viewholder>(options){
            @NonNull
            @Override
            public MyOrders.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                return new MyOrders.Viewholder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_feeds, parent, false));
            }


            @Override
            protected void onBindViewHolder(@NonNull MyOrders.Viewholder viewholder, int position, @NonNull final OrderReturn model){
                viewholder.name.setText(model.getName());
                Picasso.with(MyOrders.this).load(model.getImageUrl()).fit().centerCrop().into(viewholder.imageView);
                viewholder.totalprice.setText(model.getTotalPrice());
                viewholder.quanty.setText(model.getQuantity());

            }

            @Override
            public void onDataChanged(){
                //If there are no vertical feeds, show a view indicating error
                ErrorMessage = (TextView) findViewById(R.id.error);
                ErrorMessage.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onError(DatabaseError e){
                Toast.makeText(MyOrders.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        };
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, quanty, totalprice;

        private Viewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            quanty = (TextView) itemView.findViewById(R.id.quantity);
            totalprice = (TextView) itemView.findViewById(R.id.total_price);
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            removeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot remove: dataSnapshot.getChildren()){
                        remove.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MyOrders.this, "Unable to complete", Toast.LENGTH_LONG).show();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
