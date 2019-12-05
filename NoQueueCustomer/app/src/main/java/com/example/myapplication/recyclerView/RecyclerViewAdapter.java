package com.example.myapplication.recyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Menu;
import com.example.myapplication.recyclerView.DataBaseHandler;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.recyclerView.Item;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

//    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
//    DatabaseReference mfoodRef = mRootRef.child("Menu");


    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {

        Item item = itemList.get(position);//object item

        viewHolder.itemName.setText(MessageFormat.format("{0}", item.getItemName()));
        viewHolder.itemprice.setText(MessageFormat.format("$ {0}", Double.toString(item.getPrice_double())));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public  class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView itemName;
        public TextView itemprice;

        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context= ctx;

            itemName= itemView.findViewById(R.id.item_name);
            itemprice = itemView.findViewById(R.id.item_price);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
