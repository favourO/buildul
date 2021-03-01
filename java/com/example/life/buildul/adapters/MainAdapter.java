package com.example.life.buildul.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<String> mDataSet;
    private ArrayList<String> mDataSet1;
    private ArrayList<Integer> mDataSet2;
    private Context mContext;

    public MainAdapter(ArrayList<String> mDataSet, ArrayList<String> mDataSet1, ArrayList<Integer> mDataSet2, Context mContext){
        this.mDataSet = mDataSet;
        this.mDataSet1 = mDataSet1;
        this.mDataSet2 = mDataSet2;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_feeds, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.category.setText(mDataSet.get(i));
        viewHolder.image.setImageResource(mDataSet2.get(i));
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mDataSet.get(i), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.price.setText(mDataSet1.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView category, price;
        public CircleImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.category);
            image = (CircleImageView) itemView.findViewById(R.id.image);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }
}
