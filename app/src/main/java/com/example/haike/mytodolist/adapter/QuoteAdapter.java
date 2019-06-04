package com.example.haike.mytodolist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.activity.QuotePreviewActivity;
import com.example.haike.mytodolist.model.Quote;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyViewHolder> {

    private Context context;
    private List<Quote> mDataset;

    public QuoteAdapter(Context context, List<Quote> myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_quote_items, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        final Quote quote = this.mDataset.get(position);
        myViewHolder.txtQuote.setText(quote.getTextQuote());
        myViewHolder.author.setText(quote.getAuthor());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuotePreviewActivity.class);
                intent.putExtra("text-quote", quote.getTextQuote());
                intent.putExtra("author-quote", quote.getAuthor());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtQuote, author;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuote = itemView.findViewById(R.id.txtTextQuote);
            author = itemView.findViewById(R.id.txtAuthor);
        }
    }
}
