package com.example.life.buildul;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.example.life.buildul.app.KeyStore;
import com.example.life.buildul.app.KeystoreReturn;
import com.example.life.buildul.app.ReturnCart;
import com.example.life.buildul.app.VerticalRecycle;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
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
    private Query query, keyref, removeQuery, removeSingleQuery;
    private Button PayCart;
    private int TotalPrice = 5;
    private ArrayList<CartControl> size;
    private String Counter;
    private TextView ErrorMessage;
    private Query queryNow;
    private ArrayList<CartControl> mItems;
    private ArrayList<String> mKeys;
    private ProgressDialog progressDialog;

    /*@NonNull
    private Query keyref = database.getReference().child("Carts/"+ UID + "/" + "keyString");
*/
    /*@NonNull
    private Query removeQuery = database.getReference().child("Carts/Cart/" + UID);
*/
    @NonNull
    protected String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            //Uid = mAuth.getCurrentUser().getUid();
            startActivity(new Intent(Cart.this, Login.class));
            finish();
        }else {
            final String WelcomMail = mAuth.getCurrentUser().getEmail();
        }
        setContentView(R.layout.activity_cart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //UID = mAuth.getCurrentUser().getUid();
        if (mAuth.getCurrentUser() != null){
            Uid = mAuth.getCurrentUser().getUid();
            String strUid = String.valueOf(Uid);
            query =
                    FirebaseDatabase.getInstance().getReference().child("Carts/Cart/"+strUid);
        }

        database = FirebaseDatabase.getInstance();
        reference =  database.getReference();
        this.keyref = database.getReference().child("Carts/"+ UID + "/" + "keyString");
        removeQuery = database.getReference().child("Carts/Cart/" + UID);



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int countday = (int)dataSnapshot.getChildrenCount();
                setCount(countday);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getCount = setCount(count);


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

        /*keyref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String value = (String) dataSnapshot.child(UID).child("totalprice").getValue();
                int count = (int)dataSnapshot.getChildrenCount();

                int total = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        //KeystoreReturn store = ds.getValue(KeystoreReturn.class);
                        String key = (String) dataSnapshot.child(UID).child("keyString").getValue();
                        String value = (String) dataSnapshot.child(UID+"/"+key ).child("totalprice").getValue();

                        if (value != null) {
                            //String priceTotal = store.getTotalprice().toString().trim();
                            int cost = Integer.parseInt(value);
                            total = +cost;
                        }

                    }
                    PayCart = (Button) findViewById(R.id.pay_cart);
                    String Counter = String.valueOf(total);


                    PayCart.setHint(Counter + "Pay All");
                    PayCart.setVisibility(View.VISIBLE);
                    PayCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


    }

    int setCount(int county){
        return county;
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
        FirebaseRecyclerOptions<CartControl> options =
                new FirebaseRecyclerOptions.Builder<CartControl>()
                        .setQuery(query, CartControl.class)
                        .setLifecycleOwner(this)
                        .build();

        return new FirebaseRecyclerAdapter<CartControl, Cart.Viewholder>(options){
            @NonNull
            @Override
            public Cart.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                return new Cart.Viewholder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cart_feeds, parent, false));
            }


            @Override
            protected void onBindViewHolder(@NonNull Cart.Viewholder viewholder, int position, @NonNull final CartControl model){
                viewholder.name.setText(model.getTitle());
                Picasso.with(Cart.this).load(model.getImageUrl()).fit().centerCrop().into(viewholder.imageView);
                viewholder.price.setText(model.getPrice());
                viewholder.description.setText(model.getDescription());
                viewholder.totalprice.setText(model.getTotalPrice());
                viewholder.quanty.setText(model.getQuantity());
                viewholder.Parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Cart.this, ShipmentAndPayment.class);
                        i.putExtra("Name", model.getTitle());
                        i.putExtra("total", model.getTotalPrice());
                        i.putExtra("mqty", model.getQuantity());
                        i.putExtra("Image", model.getImageUrl());
                        Cart.this.startActivity(i);
                    }
                });
                //Cart.this.TotalPrice = TotalPrice + Integer.parseInt(model.getTotalPrice());
                viewholder.deleteSingleCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Cart.this);
                        alert.setTitle("Remove this item from Cart List")
                                .setIcon(R.drawable.buildul)
                                .setMessage("Delete this item")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //String key = (String) database.getReference().child("Carts").child(Uid).push().getKey();
                                        Query remveQuery = database.getReference().child("Carts");
                                        remveQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String value = (String) dataSnapshot.child(Uid).child("keyString").getValue();
                                                if (value != null){
                                                    dataSnapshot.child("Cart").child(Uid).child(value).getRef().removeValue();
                                                }
                                                //dataSnapshot.getRef().removeValue();
                                                /*String value = (String) dataSnapshot.child(Uid).child("keyString").getValue();

                                                if (value != null){
                                                    //keyref.getRef().removeValue();

                                                }*/
                                                /*for (DataSnapshot remove: dataSnapshot.getChildren()){
                                                    String key = remove.getKey();

                                                    if (key != null){
                                                        dataSnapshot.child(key).getRef().removeValue();
                                                    }
                                                }*/
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.show();
                    }

                });
            }

            @Override
            public void onDataChanged(){
                //If there are no vertical feeds, show a view indicating error
                ErrorMessage = (TextView) findViewById(R.id.error);
                ErrorMessage.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onError(DatabaseError e){
                Toast.makeText(Cart.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        };
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, price, description, quanty, totalprice;
        RelativeLayout Parent;
        ImageButton deleteSingleCart;

        private Viewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            price = (TextView) itemView.findViewById(R.id.price);
            description = (TextView) itemView.findViewById(R.id.description);
            quanty = (TextView) itemView.findViewById(R.id.quantity);
            totalprice = (TextView) itemView.findViewById(R.id.total_price);
            Parent = (RelativeLayout) itemView.findViewById(R.id.parent);
            deleteSingleCart = (ImageButton) itemView.findViewById(R.id.delete_cart);
        }

    }

    private String addPrice(String total){

        return total;
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            AlertDialog.Builder alert = new AlertDialog.Builder(Cart.this);
            alert.setTitle("Do you want to Clear the entire cart")
                    .setIcon(R.drawable.buildul)
                    .setMessage("Clear Cart")
                    .setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Query remveQuery = database.getReference().child("Carts").child("Cart").child(Uid);
                            remveQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot remove: dataSnapshot.getChildren()){
                                        //String key = remove.getKey();

                                        remove.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(Cart.this, "Unable to complete", Toast.LENGTH_LONG).show();
                                }

                            });
                        }

                    });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();

            return true;
        }else if (id == R.id.home){
            startActivity(new Intent(this, MainActivity.class));
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
