package com.example.haike.mytodolist.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.activity.TodoForm;
import com.example.haike.mytodolist.config.DbHandler;
import com.example.haike.mytodolist.model.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    private Context context;
    private List<Todo> mDataset;
    private DbHandler dbHandler;

    public TodoAdapter(Context context, List<Todo> myDataset) {
        this.context = context;
        mDataset = myDataset;
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
