package com.example.life.buildul;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.app.CartControl;
import com.example.life.buildul.app.VerticalRecycle;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Search extends AppCompatActivity {
    private EditText searchText;
    private ImageButton searchButton;
    private RecyclerView searchRecycle;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = (EditText) findViewById(R.id.text_search);
        searchButton = (ImageButton) findViewById(R.id.search_button);
        searchRecycle = (RecyclerView) findViewById(R.id.search_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchRecycle.setLayoutManager(layoutManager);

        String searchTet = searchText.getText().toString();
        /*final RecyclerView.Adapter adapter = newAdapter();

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });

        searchRecycle.setAdapter(adapter);*/

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 

                attachRecyclerViewAdapter();

            }
        });

        query =
                FirebaseDatabase.getInstance().getReference().child("VerticalRecycle").orderByChild("title").startAt(searchTet).endAt(searchTet + "\uf8ff");
    }

    private void attachRecyclerViewAdapter(){
        final RecyclerView.Adapter adapter = newAdapter();

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                searchRecycle.smoothScrollToPosition(adapter.getItemCount());
                //super.onItemRangeInserted(positionStart, itemCount);
            }
        });

        searchRecycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @NonNull
    protected FirebaseRecyclerAdapter<VerticalRecycle, Viewholder> newAdapter(){
        FirebaseRecyclerOptions<VerticalRecycle> options =
                new FirebaseRecyclerOptions.Builder<VerticalRecycle>()
                        .setQuery(query, VerticalRecycle.class)
                        .setLifecycleOwner(this)
                        .build();

        return new FirebaseRecyclerAdapter<VerticalRecycle, Search.Viewholder>(options){
            @NonNull
            @Override
            public Search.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                return new Search.Viewholder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_layout, parent, false));
            }


            @Override
            protected void onBindViewHolder(@NonNull Search.Viewholder viewholder, int position, @NonNull final VerticalRecycle model){
                viewholder.name.setText(model.getTitle());
                Picasso.with(Search.this).load(model.getImage()).fit().centerCrop().into(viewholder.imageView);
                viewholder.price.setText(model.getPrice());
                viewholder.description.setText(model.getProductdescription());
            }

            @Override
            public void onDataChanged(){
                //If there are no vertical feeds, show a view indicating error

            }

            @Override
            public void onError(DatabaseError e){
                Toast.makeText(Search.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }


        };
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, price, description;

        private Viewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title_search);
            imageView = (ImageView) itemView.findViewById(R.id.image_search);
            price = (TextView) itemView.findViewById(R.id.price_search);
            description = (TextView) itemView.findViewById(R.id.description_search);
        }

    }

}
