package com.example.life.buildul;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.app.Order;
import com.example.life.buildul.app.ReturnCart;
import com.example.life.buildul.app.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class ShipmentAndPayment extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText cardNumber, expiry_month, expiry_year, cvc, contactName, state, Mobile, street, country, city, zipCode;
    private TextView showError;
    private String cardnumber, expiryDM, expiryDY, CVC, cntName, State, mobile, Street, Country, City, Zip, SummaryName, SummaryQuantity, SummaryPriceTotal, SummaryImage;
    private int  mnth, yr, price;
    private TextView NameTitle, Qunatity, TotalPrice;
    private Button pay;
    private Card card;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String UID, Email, paymentReference;
    private ProgressDialog progressDialog;
    private CheckBox confirmCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //getting the current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UID = user.getUid();
            Email = user.getEmail();
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("Orders");

        }
        setContentView(R.layout.activity_shipment_and_payment);
        PaystackSdk.initialize(getApplicationContext());




        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Shipment starts here
            //Getting shipment details from previous users
            NameTitle = (TextView) findViewById(R.id.sumary_name_value);
            Qunatity = (TextView) findViewById(R.id.sumary_description_value);
            TotalPrice = (TextView) findViewById(R.id.sumary_totalprice_value);
            getShipmentIntent();


            //Getting shipment details from activity
            contactName = (EditText) findViewById(R.id.contact_name);
            Mobile = (EditText) findViewById(R.id.contact_number);
            street = (EditText) findViewById(R.id.street_number);
            country = (EditText) findViewById(R.id.country);
            city = (EditText) findViewById(R.id.City);
            zipCode = (EditText) findViewById(R.id.zip);
            state = (EditText) findViewById(R.id.State);
            confirmCheck = (CheckBox) findViewById(R.id.check_correct);
            processShipmentDetails();



        //Payment starts here
        //cardProcessing();
        cardNumber = (EditText) findViewById(R.id.card_number);
        cardNumber.addTextChangedListener(new FourDigitCardFormatWatcher());
        expiry_month = (EditText) findViewById(R.id.expiry_month);
        expiry_year = (EditText) findViewById(R.id.expiry_year);
        cvc = (EditText) findViewById(R.id.CVC);

        pay = (Button) findViewById(R.id.pay_now);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cntName = contactName.getText().toString().trim();
                mobile = Mobile.getText().toString().trim();
                Country = country.getText().toString().trim();
                City = city.getText().toString().trim();
                State = state.getText().toString().trim();
                Street = street.getText().toString().trim();
                Zip = zipCode.getText().toString().trim();

                if (TextUtils.isEmpty(cntName)){
                    contactName.setError("Enter Your Contact Name");
                    contactName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mobile)){
                    Mobile.setError("Enter Your Mobile number");
                    Mobile.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Street)){
                    street.setError("Enter Your Street");
                    street.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Country)){
                    country.setError("Enter Your Country");
                    country.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(City)){
                    city.setError("Enter Your City");
                    city.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(State)){
                    state.setError("Enter Your State");
                    state.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Zip)){
                    zipCode.setError("Enter Zip Code");
                    zipCode.requestFocus();
                }
                if (!confirmCheck.isChecked()){
                    confirmCheck.setError("Confirm your details");
                    confirmCheck.requestFocus();
                }
                

                cardnumber = cardNumber.getText().toString();
                expiryDM = expiry_month.getText().toString();
                expiryDY = expiry_year.getText().toString();
                CVC = cvc.getText().toString();

                showError = (TextView) findViewById(R.id.show_error);

                if (expiryDM.length() > 0 && expiryDY.length() > 0){
                    mnth = Integer.parseInt(expiryDM);
                    yr = Integer.parseInt(expiryDY);
                }


                card = new Card(cardnumber, mnth, yr, CVC);
                if (card.isValid()){
                    performCharge();
                }else{
                    showError.setText(getString(R.string.invalid_card));
                    Toast.makeText(ShipmentAndPayment.this, "Invalid Card details", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void cardProcessing() {

    }

    private void processShipmentDetails() {


    }

    private void getShipmentIntent() {
        if (getIntent().hasExtra("total") && getIntent().hasExtra("mqty") && getIntent().hasExtra("Name") && getIntent().hasExtra("Image")){
            this.SummaryName = getIntent().getStringExtra("Name");
            this.SummaryPriceTotal = getIntent().getStringExtra("total");
            this.SummaryQuantity = getIntent().getStringExtra("mqty");
            this.SummaryImage = getIntent().getStringExtra("Image");

            NameTitle.setText(SummaryName);
            Qunatity.setText(SummaryQuantity);
            TotalPrice.setText(SummaryPriceTotal);

            this.price = Integer.parseInt(SummaryPriceTotal);
        }
    }

    private void performCharge(){
        //create a Charge object
        Charge charge = new Charge();

        //set card to charge
        charge.setCard(card);

        //set the currency to charge
        //charge.setCurrency("Naira");

        //set the email to charge
        charge.setEmail(Email);

        //get date for an order
        Date date = new Date();
        Date  newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        final String stringdate = dt.format(newDate);

        //set amount to charge
        charge.setAmount(price);

        PaystackSdk.chargeCard(this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                //This is called only after transaction is deemed successful
                //Retrieve the transaction, and send its reference to your server for verification
                String paymentReference = transaction.getReference();

                Toast.makeText(ShipmentAndPayment.this, "Transaction Successful! payment reference", Toast.LENGTH_SHORT).show();

                processShipmentDetails();
                AddDatabase(UID, cntName, mobile, Email, Street, Country, City, Zip, State, SummaryName, SummaryQuantity, SummaryPriceTotal, SummaryImage, paymentReference);
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                //This is called only before requesting OTP
                //Save reference so you may send to server. If error occurs with OTP, you should still verify on server
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //handle error here
                Toast.makeText(ShipmentAndPayment.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public static class FourDigitCardFormatWatcher implements TextWatcher {
        private static final char space = '-';
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0 && (s.length() % 5) == 0){
                final char c = s.charAt(s.length() - 1);
                if (space == c){
                    s.delete(s.length() - 1, s.length());
                }
            }
            if (s.length() > 0 && (s.length() % 5) == 0){
                char c = s.charAt(s.length() - 1);
                if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3){
                    s.insert(s.length() - 1, String.valueOf(space));
                }
            }

        }
    }



    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    //String Uid, String contactName, String mobile, String Email, String street, String country, String city, String zip, String State, String date, String payStackTransactionDetails
    private void AddDatabase(String Uid, String contactName, String mobile, String Email, String street, String country, String city, String zip, String State, String name, String quantity, String priceTotal, String imageUrl, String payStackTransactionDetails) {
        progressDialog.dismiss();
        String key = databaseReference.child("Orders").push().getKey();
        Order control = new Order(Uid, contactName, mobile, Email, street, country, city, zip, State, name, quantity, priceTotal, imageUrl, payStackTransactionDetails);

        Map<String, Object> getOrder = control.toMap();

        Map<String, Object> childOrder = new HashMap<>();
        //childCart.put("/Carts/"+ key, getCart);
        childOrder.put("Order/" + UID + "/" + key, getOrder);
        databaseReference.updateChildren(childOrder);
        progressDialog.dismiss();

        Toast.makeText(ShipmentAndPayment.this, "Order Created", Toast.LENGTH_LONG).show();
        startActivity(new Intent(ShipmentAndPayment.this, MyOrders.class));

    }
}
