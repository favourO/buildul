package com.example.life.buildul;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.app.ReturnCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PlanGallery extends AppCompatActivity {
    private static final String TAG = "Gallery";
    private Toolbar toolbar;
    private FloatingActionButton fabBuy, fabCart;
    private String Name, Image, Description, Quantity, Price;
    private int quantityNum;
    private int productPrice;
    private int totalGoods;
    private EditText quantity;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String UID, Email;
    private ProgressDialog progressDialog;
    private int count = 1;
    private Button getPlan, buildPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_gallery);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Carts");
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            UID = mAuth.getCurrentUser().getUid();
        }
        //String uid = "Cart"+UID+count;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getIncomingIntent();

        /*getPlan = (Button) findViewById(R.id.get);
        getPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlanGallery.this, GetPlan.class);
                startActivity(i);
            }
        });*/

        buildPlan = (Button) findViewById(R.id.build);
        buildPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlanGallery.this, BuildPlan.class);
                startActivity(i);
            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: Checking for incoming intents...");

        if (getIntent().hasExtra("Title") && getIntent().hasExtra("Image") && getIntent().hasExtra("Price") && getIntent().hasExtra("Description")){
            Log.d(TAG, "getIncomingIntent: found intent extras...");
            Name = getIntent().getStringExtra("Title");
            Image = getIntent().getStringExtra("Image");
            String price3 = getIntent().getStringExtra("Price");
            Price = price3.substring(1);
            Description = getIntent().getStringExtra("Description");

            TextView name = findViewById(R.id.product_namey);
            name.setText(Name);

            ImageView imageView = findViewById(R.id.product_image);
            //imageView.setImageBitmap(image);
            Picasso.with(this)
                    .load(Image)
                    .fit()
                    .centerCrop()
                    .into(imageView);
            TextView price = findViewById(R.id.product_price);
            price.setText(Price);

            TextView description = findViewById(R.id.product_description);
            description.setText(Description);
        }
    }

    private void AddToCart(){
            totalGoods = quantityNum * productPrice;
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Check Your goods again and Continue")
                    .setIcon(R.drawable.buildul)
                    .setMessage("Product Name: "+ Name + "\n" + "Description: " + Description + "\n" + "Quantity: " + quantityNum + "\n" + "Total Price: " + totalGoods + "\n")
                    .setPositiveButton("Add to Cart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth = FirebaseAuth.getInstance();
                            if (mAuth.getCurrentUser() != null){
                                UID = mAuth.getCurrentUser().getUid();

                                String mPrice = String.valueOf(Price);
                                String mqty = String.valueOf(quantityNum);
                                String total = String.valueOf(totalGoods);
                                AddDatabase(UID, Description, mPrice, mqty, Name, total, Image);

                            }else{
                                Toast.makeText(PlanGallery.this, "Login to continue", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(PlanGallery.this, Login.class);
                                startActivity(i);
                            }
                        }
                        //int count = 1;

                        private void AddDatabase(String userId, String description, String price, String quantity, String title, String totalPrice, String imageUrl) {
                            progressDialog.dismiss();
                            String key = databaseReference.child("Carts").push().getKey();
                            ReturnCart control = new ReturnCart(userId, description, price, quantity, title, totalPrice, imageUrl);

                            Map<String, Object> getCart = control.toMap();

                            Map<String, Object> childCart = new HashMap<>();
                            //childCart.put("/Carts/"+ key, getCart);
                            childCart.put("Cart/" + UID + "/" + key, getCart);
                            databaseReference.updateChildren(childCart);
                            progressDialog.dismiss();
                            Toast.makeText(PlanGallery.this, "Item added to Cart", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(PlanGallery.this, Cart.class));

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

    private void MakePayment() {
        quantity = (EditText) findViewById(R.id.qty);
        Quantity = quantity.getText().toString();
        if (TextUtils.isEmpty(Quantity)) {
            quantity.setError("Please Enter Number");
        } else {
            this.quantityNum = Integer.parseInt(Quantity);
            totalGoods = quantityNum * productPrice;
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Check Your goods again and Continue")
                    .setIcon(R.drawable.buildul)
                    .setMessage("Product Name: " + Name + "\n" + "Description: " + Description + "\n" + "Quantity: " + quantityNum + "\n" + "Total Price: " + totalGoods + "\n")
                    .setPositiveButton("Make Your Payment", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth = FirebaseAuth.getInstance();
                            if (mAuth.getCurrentUser() != null) {
                                UID = mAuth.getCurrentUser().getUid();
                                Date currentDate = Calendar.getInstance().getTime();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                                String date = dateFormat.format(currentDate);
                                String mPrice = String.valueOf(Price);
                                String mqty = String.valueOf(quantityNum);
                                String total = String.valueOf(totalGoods);
                                //AddDatabase(UID, date, Description, mPrice, mqty, Name, total, Image);
                                Intent i = new Intent(PlanGallery.this, ShipmentAndPayment.class);
                                i.putExtra("total", total);
                                i.putExtra("mqty", mqty);
                                i.putExtra("Name", Name);
                                startActivity(i);

                            } else {
                                Toast.makeText(PlanGallery.this, "Login to continue", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(PlanGallery.this, Login.class);
                                startActivity(i);
                            }
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
    }
}
