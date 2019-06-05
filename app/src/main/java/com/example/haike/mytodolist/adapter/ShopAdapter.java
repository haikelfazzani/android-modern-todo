package com.example.haike.mytodolist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.model.Shop;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {

    private Context context;
    private List<Shop> mDataset;

    public ShopAdapter(Context context, List<Shop> myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ShopAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_shop_items, viewGroup, false);
        return new ShopAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.MyViewHolder myViewHolder, final int position) {

        final Shop shop = this.mDataset.get(position);

        myViewHolder.txtShop.setText(shop.getText());
        myViewHolder.priceShop.setText(shop.getPrice()+"$");
        myViewHolder.descShop.setText(shop.getDesc());

        Picasso.get().load(shop.getImage()).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtShop, priceShop, descShop;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtShop = itemView.findViewById(R.id.txtShop);
            priceShop = itemView.findViewById(R.id.priceShop);
            descShop = itemView.findViewById(R.id.descShop);
            imageView = itemView.findViewById(R.id.shopImg);
        }
    }
}

