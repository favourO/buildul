package com.example.life.buildul.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.Gallery;
import com.example.life.buildul.R;
import com.example.life.buildul.models.Model;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {
    private List<Model> mDataSet;
    private Context mcontext;

    public VerticalAdapter(Context mContext, List<Model> mDataSet){
        this.mDataSet = mDataSet;
        this.mcontext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vertical_feeds, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, price;
        Button checkout;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            price = (TextView) itemView.findViewById(R.id.price);
            checkout = (Button) itemView.findViewById(R.id.checkout);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Model model = mDataSet.get(position);

        viewHolder.name.setText(model.getTitle());
        Picasso.with(mcontext).load(model.getImage()).fit().centerCrop().into(viewHolder.imageView);
        viewHolder.price.setText(model.getPrice());
        viewHolder.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext, Gallery.class);
                i.putExtra("Title", model.getTitle());
                i.putExtra("Image", model.getImage());
                i.putExtra("Price", model.getPrice());
                mcontext.startActivity(i);
            }
        });
    }


    private void showPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(mcontext, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.cart_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuOnclickListener());
        popupMenu.show();
    }

    class MyMenuOnclickListener implements PopupMenu.OnMenuItemClickListener{
        private MyMenuOnclickListener(){

        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem){
            switch (menuItem.getItemId()){
                case R.id.action_add_to_cart:
                    Toast.makeText(mcontext, "Add to cart", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_buy_now:
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}
