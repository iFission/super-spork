//package com.example.vendorwrecycler.ui;
//
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.text.Layout;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TableRow;
//import android.widget.TextView;
//import com.example.vendorwrecycler.data.DataBaseHandler;
//
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.vendorwrecycler.R;
//import com.example.vendorwrecycler.model.Item;
//import com.google.android.material.snackbar.Snackbar;
//
//import java.text.MessageFormat;
//import java.util.List;
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
//
//    private Context context;
//    private List<Item> itemList;
//    private AlertDialog.Builder builder;
//    private AlertDialog dialog;
//    private LayoutInflater inflater;
//
//
//    public RecyclerViewAdapter(Context context, List<Item> itemList) {
//        this.context = context;
//        this.itemList = itemList;
//    }
//    @NonNull
//    @Override
//    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.list_row, viewGroup, false);
//
//
//        return new ViewHolder(view, context);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
//
//        Item item = itemList.get(position);//object item
//
//        viewHolder.itemName.setText(MessageFormat.format("Item: {0}", item.getItemName()));
//        viewHolder.itemprice.setText(MessageFormat.format("Price:{0}", Integer.toString(item.getPrice())));
//        viewHolder.quantity.setText(MessageFormat.format("Quantity:{0}", Integer.toString(item.getItemQuantity())));
//        viewHolder.description.setText(MessageFormat.format("Description: {0}", item.getDescription()));
//        viewHolder.dateAdded.setText("Date added: "+item.getDateItemadded());
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList.size();
//    }
//
//    public  class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public TextView itemName;
//        public TextView itemprice;
//        public TextView quantity;
//        public TextView description;
//        public TextView dateAdded;
//        public int id;
//        public Button editButton;
//        public Button deleteButton;
//
//        public ViewHolder(@NonNull View itemView, Context ctx) {
//            super(itemView);
//            context= ctx;
//
//            itemName= itemView.findViewById(R.id.item_name);
//            itemprice = itemView.findViewById(R.id.item_price);
//            quantity= itemView.findViewById(R.id.item_quantity);
//            description=itemView.findViewById(R.id.item_description);
//            dateAdded= itemView.findViewById(R.id.item_date);
//
//            editButton= itemView.findViewById(R.id.editButton);
//            deleteButton=itemView.findViewById(R.id.deleteButton);
//
//            editButton.setOnClickListener(this);
//            deleteButton.setOnClickListener(this);
//
//
//        }
//
//        @Override
//        public void onClick(View v) {
//
//            int position;
//
//            position = getAdapterPosition();
//            Item item = itemList.get(position);
//
//            switch (v.getId()) {
//                case R.id.editButton:
//                    //edit item
//
//
//                    editItem(item);
//
//                    break;
//                case R.id.deleteButton:
//                    //delete item
//                    deleteItem(item.getId());
//                    break;
//            }
//
//        }
//
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
//                    db.deleteItem(id);
//                    itemList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                    dialog.dismiss();
//
//
//                }
//            });
//
//
//
//
//
//        }
//        private void editItem(final Item newItem) {
//            //Item item = itemList.get(getAdapterPosition());
//
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
//            itemQuantity = view.findViewById(R.id.itemQuantity);
//            price = view.findViewById(R.id.price);
//            description = view.findViewById(R.id.description);
//            saveButton = view.findViewById(R.id.saveButton);
//            title = view.findViewById(R.id.title);
//
//            saveButton.setText(R.string.update_text);;
//
//            builder.setView(view);
//            dialog=builder.create();
//            dialog.show();
//
//            title.setText(R.string.edit_item);
//            foodItem.setText(newItem.getItemName());
//            price.setText(String.valueOf(newItem.getPrice()));
//            itemQuantity.setText(String.valueOf(newItem.getItemQuantity()));
//            description.setText(newItem.getDescription());
//
//
//
//            saveButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
//                    //update items
//                    newItem.setItemName(foodItem.getText().toString());
//                    newItem.setItemQuantity(Integer.parseInt(itemQuantity.getText().toString()));
//                    newItem.setPrice(Integer.parseInt(price.getText().toString()));
//                    newItem.setDescription(description.getText().toString());
//
//                    if(!foodItem.getText().toString().isEmpty()
//                            && !price.getText().toString().isEmpty()
//                            && !description.getText().toString().isEmpty()
//                            && !itemQuantity.getText().toString().isEmpty()){
//                        dataBaseHandler.updateItem(newItem);
//                        notifyItemChanged(getAdapterPosition(),newItem); // so user dont need to refresh to see the after edit
//                    }else{
//                        Snackbar.make(v,"Fields empty",Snackbar.LENGTH_SHORT).show();
//                    }
//                    dialog.dismiss();
//
//
//                }
//            });
//        }
//
//
//
//
//    }
//}
//
//
