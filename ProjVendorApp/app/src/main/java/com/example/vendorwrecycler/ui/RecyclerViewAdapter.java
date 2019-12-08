package com.example.vendorwrecycler.ui;

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

import com.example.vendorwrecycler.Menu;
import com.example.vendorwrecycler.data.DataBaseHandler;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendorwrecycler.R;
import com.example.vendorwrecycler.model.Item;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.MessageFormat;
import java.util.List;
/*

This class is responsible for populating the card view that contains each individual Menu item that is retrieved from Firebase.
It also allows for the Vendor to update or delete any Menu item. This change in data is also updated on Firebase as the Vendor
does on their app.

 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   // Finds the root of the connected firebase database
    DatabaseReference mfoodRef = mRootRef.child("Menu");                         // Specifies the child that is to be created/updated


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

    //Prepares the items from the itemList ArrayList to be displayed on the Card View, list_row.
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {

        Item item = itemList.get(position);//object item

        viewHolder.itemName.setText(MessageFormat.format("Food Name: {0}", item.getItemName()));
        viewHolder.itemprice.setText(MessageFormat.format("Price:{0}", Double.toString(item.getPrice_double())));
        viewHolder.quantity.setText(MessageFormat.format("Food Code:{0}", item.getDescription()));
        viewHolder.description.setVisibility(View.INVISIBLE);
        viewHolder.dateAdded.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    /*
    Dictates what each card on the Card View on the Recycler View looks like. Instantiates all the buttons and text boxes that are
    described on the list_row.xml file and binds to their data source. Also provides the necessary action that each text box or
    button needs to perform.
     */
    public  class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView itemName;
        public TextView itemprice;
        public TextView quantity;
        public TextView description;
        public TextView dateAdded;
        public int id;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context= ctx;

            // Referencing and Binding the textboxes and buttons that are on the card.
            itemName= itemView.findViewById(R.id.item_name);
            itemprice = itemView.findViewById(R.id.item_price);
            quantity= itemView.findViewById(R.id.item_quantity);
            description=itemView.findViewById(R.id.item_description);
            dateAdded= itemView.findViewById(R.id.item_date);

            editButton= itemView.findViewById(R.id.editButton);
            deleteButton=itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);


        }

        // Giving function to the buttons that are found on the Card view.
        // Namely the save and edit buttons. Each calling its respective functions to change the Meny data.
        @Override
        public void onClick(View v) {

            int position;

            position = getAdapterPosition();
            Item item = itemList.get(position);

            switch (v.getId()) {
                case R.id.editButton:
                    //edit item


                    editItem(item);

                    break;
                case R.id.deleteButton:
                    //delete item
                    deleteItem(item.getId());
                    break;
            }

        }

        /*
        Method for deleting a Menu item from local database and Firebase. Constructs a confirmation pop-up to ensure that
        Vendor would like to delete the Menu item
         */
        private void deleteItem(final int id) {
            builder= new AlertDialog.Builder(context);
            inflater= LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_pop,null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);


            builder.setView(view);
            dialog= builder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHandler db = new DataBaseHandler(context);

                    // Delete Menu from Firebase
                    mfoodRef.child("Menu"+itemList.get(getAdapterPosition()).getDescription()).removeValue();

                    // Delete Menu from local Database
                    db.deleteItem(id);

                    // Let the adapter know that an item has been removed.
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });
        }

        /*
        Method to allow for the existing Menu item to be updated. Creates a new pop-up dialog
        to retrieve the information that is to be updated.
         */
        private void editItem(final Item newItem) {
            //Item item = itemList.get(getAdapterPosition());

            DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
            builder = new AlertDialog.Builder(context);
            inflater= LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup,null);
            final EditText foodItem;
            final EditText itemQuantity;
            final EditText price;
            final EditText description;
            Button saveButton;
            TextView title;


            foodItem = view.findViewById(R.id.foodItem);
            description = view.findViewById(R.id.itemQuantity);
            price = view.findViewById(R.id.price);
            itemQuantity = view.findViewById(R.id.description);
            saveButton = view.findViewById(R.id.saveButton);
            title = view.findViewById(R.id.title);
            itemQuantity.setVisibility(View.INVISIBLE);

            saveButton.setText(R.string.update_text);;

            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            title.setText(R.string.edit_item);
            foodItem.setText(newItem.getItemName());
            price.setText(String.valueOf(newItem.getPrice_double()));
            description.setText(newItem.getDescription());


            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                    //update items
                    newItem.setItemName(foodItem.getText().toString());
                    newItem.setItemQuantity(1);
                    newItem.setPrice_double(Double.parseDouble(price.getText().toString()));
                    newItem.setDescription(description.getText().toString());

                    if(!foodItem.getText().toString().isEmpty()
                    && !price.getText().toString().isEmpty()
                    && !description.getText().toString().isEmpty()){

                        dataBaseHandler.updateItem(newItem);
                        Menu updateMenu = new Menu(newItem.getItemName(),newItem.getPrice_double(),newItem.getDescription());

                        // Updating the Menu item in Firebase
                        mfoodRef.child("Menu"+newItem.getDescription()).setValue(updateMenu);
                        notifyItemChanged(getAdapterPosition(), newItem); // so user dont need to refresh to see the after edit

                    }else{
                        Snackbar.make(v,"Fields empty",Snackbar.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
    }
    }
}

