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

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
    DatabaseReference mfoodRef = mRootRef.child("Menu");


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

        viewHolder.itemName.setText(MessageFormat.format("Food Name: {0}", item.getItemName()));
        viewHolder.itemprice.setText(MessageFormat.format("Price:{0}", Double.toString(item.getPrice_double())));
//        viewHolder.quantity.setText(MessageFormat.format("Food Code:{0}", item.getDescription()));
//        viewHolder.description.setVisibility(View.INVISIBLE);
//        viewHolder.dateAdded.setVisibility(View.INVISIBLE);
        //viewHolder.description.setText(MessageFormat.format("Description: {0}", item.getDescription()));
        //viewHolder.dateAdded.setText("Date added: "+item.getDateItemadded());



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public  class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView itemName;
        public TextView itemprice;
        public TextView quantity;
        public TextView description;
        public TextView dateAdded;
        public int id;
        public Button editButton;
        public Button addButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context= ctx;

            itemName= itemView.findViewById(R.id.item_name);
            itemprice = itemView.findViewById(R.id.item_price);
//            quantity= itemView.findViewById(R.id.item_quantity);
//            description=itemView.findViewById(R.id.item_description);
//            dateAdded= itemView.findViewById(R.id.item_date);
//
//            editButton= itemView.findViewById(R.id.editButton);
            addButton=itemView.findViewById(R.id.addButton);

//            editButton.setOnClickListener(this);
            addButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            int position;

            position = getAdapterPosition();
            Item item = itemList.get(position);

            switch (v.getId()) {
//                case R.id.editButton:
//                    //edit item
//
//
//                    editItem(item);
//
//                    break;
                case R.id.addButton:
                    //delete item
//                    deleteItem(item.getId());
                    break;
            }

        }

//        private void deleteItem(final int id) {
//            builder= new AlertDialog.Builder(context);
//            inflater= LayoutInflater.from(context);
//            View view = inflater.inflate(R.layout.confirmation_pop,null);
//
//            Button noButton = view.findViewById(R.id.conf_no_button);
//            Button yesButton = view.findViewById(R.id.conf_yes_button);
//
//
//            builder.setView(view);
//            dialog= builder.create();
//            dialog.show();
//
//            noButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//
//                }
//            });
//            yesButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DataBaseHandler db = new DataBaseHandler(context);
//                    mfoodRef.child("Menu"+itemList.get(getAdapterPosition()).getDescription()).removeValue();
//
//                    db.deleteItem(id);
//                    itemList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                    dialog.dismiss();
//
//
//                }
//            });
//        }
//        private void editItem(final Item newItem) {
//            //Item item = itemList.get(getAdapterPosition());
//
//            DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
//            builder = new AlertDialog.Builder(context);
//            inflater= LayoutInflater.from(context);
//            View view = inflater.inflate(R.layout.popup,null);
//            final EditText foodItem;
//            final EditText itemQuantity;
//            final EditText price;
//            final EditText description;
//            Button saveButton;
//            TextView title;
//
//
//            foodItem = view.findViewById(R.id.foodItem);
//            description = view.findViewById(R.id.itemQuantity);
//            price = view.findViewById(R.id.price);
//            itemQuantity = view.findViewById(R.id.description);
//            saveButton = view.findViewById(R.id.saveButton);
//            title = view.findViewById(R.id.title);
//            itemQuantity.setVisibility(View.INVISIBLE);
//
//            saveButton.setText(R.string.update_text);;
//
//            builder.setView(view);
//            dialog=builder.create();
//            dialog.show();
//
//            title.setText(R.string.edit_item);
//            foodItem.setText(newItem.getItemName());
//            price.setText(String.valueOf(newItem.getPrice_double()));
//            description.setText(newItem.getDescription());
//
//
//            saveButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
//                    //update items
//                    newItem.setItemName(foodItem.getText().toString());
//                    newItem.setItemQuantity(1);
//                    newItem.setPrice_double(Double.parseDouble(price.getText().toString()));
//                    newItem.setDescription(description.getText().toString());
//
//                    if(!foodItem.getText().toString().isEmpty()
//                            && !price.getText().toString().isEmpty()
//                            && !description.getText().toString().isEmpty()){
//
//                        dataBaseHandler.updateItem(newItem);
//                        Menu updateMenu = new Menu(newItem.getItemName(),newItem.getPrice_double(),newItem.getDescription());
//                        mfoodRef.child("Menu"+newItem.getDescription()).setValue(updateMenu);
//                        notifyItemChanged(getAdapterPosition(), newItem); // so user dont need to refresh to see the after edit
//
//                    }else{
//                        Snackbar.make(v,"Fields empty",Snackbar.LENGTH_SHORT).show();
//                    }
//                    dialog.dismiss();
//                }
//            });
//        }




    }
}
