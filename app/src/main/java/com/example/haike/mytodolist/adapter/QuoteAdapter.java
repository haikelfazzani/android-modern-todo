package com.example.haike.mytodolist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.activity.QuotePreviewActivity;
import com.example.haike.mytodolist.model.Quote;

import java.util.ArrayList;
import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyViewHolder> implements Filterable {

    private Context context;

    private List<Quote> mDataset;
    private List<Quote> fullDataset;

    public QuoteAdapter(Context context, List<Quote> myDataset) {
        this.context = context;
        mDataset = myDataset;
        fullDataset = new ArrayList<>(myDataset);
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

    @Override
    public Filter getFilter() {
        return myFilterList;
    }

    private Filter myFilterList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Quote> filerList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filerList.addAll(fullDataset);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Quote quoteItem : fullDataset) {
                    if(quoteItem.getAuthor().toLowerCase().contains(filterPattern)) {
                        filerList.add(quoteItem);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filerList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataset.clear();
            mDataset.addAll((List<Quote>)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtQuote, author;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuote = itemView.findViewById(R.id.txtTextQuote);
            author = itemView.findViewById(R.id.txtAuthor);
        }
    }
}
