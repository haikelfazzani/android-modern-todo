package com.example.haike.mytodolist.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.activity.TodoForm;
import com.example.haike.mytodolist.activity.TodoPreviewActivity;
import com.example.haike.mytodolist.config.DbHandler;
import com.example.haike.mytodolist.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> implements Filterable {

    private Context context;

    private List<Todo> mDataset;
    private List<Todo> fullDataset;

    private DbHandler dbHandler;

    public TodoAdapter(Context context, List<Todo> myDataset) {
        this.context = context;
        mDataset = myDataset;
        fullDataset = new ArrayList<>(myDataset);
        dbHandler = new DbHandler(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.todo_layout_items, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        final Todo todo = this.mDataset.get(position);
        myViewHolder.title.setText(todo.getTitle());
        myViewHolder.desc.setText(todo.getDesc());
        myViewHolder.date.setText(todo.getDate());
        myViewHolder.time.setText(todo.getTime());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TodoPreviewActivity.class);
                intent.putExtra("todo-id", todo.getId()+"");
                intent.putExtra("todo-title", todo.getTitle());
                intent.putExtra("todo-desc", todo.getDesc());
                intent.putExtra("todo-date", todo.getDate());
                intent.putExtra("todo-time", todo.getTime());
                context.startActivity(intent);
            }
        });

        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Suppression");
                builder.setMessage("êtes-vous sûr de vouloir continuer ?");
                builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.openDB();
                        dbHandler.deleteTodo(todo.getId());
                        mDataset.remove(position);

                        dbHandler.closeDB();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,mDataset.size());
                    }
                })
                        .setNegativeButton("non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();                            }
                        }).create().show();
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
            List<Todo> filerList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filerList.addAll(fullDataset);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Todo todoItem : fullDataset) {
                    if(todoItem.getTitle().toLowerCase().contains(filterPattern)) {
                        filerList.add(todoItem);
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
            mDataset.addAll((List<Todo>)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, desc, date, time;
        private Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            title = itemView.findViewById(R.id.todo_title);
            desc = itemView.findViewById(R.id.todo_desc);
            date = itemView.findViewById(R.id.todo_date);
            time = itemView.findViewById(R.id.todo_time);
        }
    }
}
