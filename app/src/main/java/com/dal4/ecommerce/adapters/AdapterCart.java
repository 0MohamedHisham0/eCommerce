package com.dal4.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.data_base.Local.DaoFun.DeleteProduct_Cart;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<VH_ProductCart> {
    List<Products_Model_Detail> list;
    Context context;
    TextView textView, textView2;

    public AdapterCart(List<Products_Model_Detail> list, Context context, TextView textView, TextView textView2) {
        this.list = list;
        this.context = context;
        this.textView = textView;
        this.textView2 = textView2;
    }

    @NonNull
    @Override
    public VH_ProductCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new VH_ProductCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH_ProductCart holder, @SuppressLint("RecyclerView") int position) {
        Products_Model_Detail Products_Model = list.get(position);
        new CalculateTotalPrice().execute(list);

        holder.ProductName.setText(Products_Model.getName());
        holder.BrandName.setText(Products_Model.getBrand());
        holder.Price.setText(Products_Model.getPrice() + " $");

        SetImageIntoPicasso(Products_Model.getImage(), holder.image, R.drawable.ic_error);
        //ClickListener

        holder.AddQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int CurrentQty = Integer.parseInt(holder.Qty.getText().toString());
                int s = CurrentQty + 1;
                Products_Model.setQty(s);
                new CalculateTotalPrice().execute(list);
                holder.Qty.setText(s + "");

            }
        });

        holder.RemoveQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int CurrentQty = Integer.parseInt(holder.Qty.getText().toString());
                if (CurrentQty > 1) {
                    int s = CurrentQty - 1;
                    Products_Model.setQty(s);
                    new CalculateTotalPrice().execute(list);
                    holder.Qty.setText(s + "");

                }
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteProduct_Cart(context).execute(Products_Model);
                new CalculateTotalPrice().execute(list);
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        });
    }

    @Override
    public int getItemCount() {

        return
                list.size();
    }

    //More Fun
    private void SetImageIntoPicasso(String ImageUrl, ImageView imageView, int ErrorImage) {
        String ImageURL =  ImageUrl;
        if (!ImageUrl.isEmpty()) {
            Picasso.get()
                    .load(ImageURL)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            imageView.setImageResource(R.drawable.ic_error);
                        }
                    });
        } else {
            imageView.setImageResource(ErrorImage);
        }
    }

    public class CalculateTotalPrice extends AsyncTask<List<Products_Model_Detail>, Void, Integer> {

        @Override
        protected Integer doInBackground(List<Products_Model_Detail>... lists) {

            int listSize = lists[0].size();
            int TotalPrice = 0;
            for (int i = 0; i < listSize; i++) {
                if (list.get(i).getQty() != 0) {
                    TotalPrice = TotalPrice + (lists[0].get(i).getPrice() * lists[0].get(i).getQty());

                }else {
                    TotalPrice = TotalPrice + (lists[0].get(i).getPrice() );

                }
            }
            return TotalPrice;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            textView.setText(integer + "$");
            textView2.setText(integer + "$");
        }

    }

}

class VH_ProductCart extends RecyclerView.ViewHolder {
    ImageView image;
    TextView ProductName, BrandName, Size, Price, Qty;
    Button Delete, AddQty, RemoveQty;

    public VH_ProductCart(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.Image_CartItem);
        if (ProductName == null)
            ProductName = itemView.findViewById(R.id.ProductName_ItemCart);
        if (BrandName == null)
            BrandName = itemView.findViewById(R.id.Brand_ItemCart);
        if (Size == null)
            Size = itemView.findViewById(R.id.Size_ItemCart);
        if (Price == null)
            Price = itemView.findViewById(R.id.Price_ItemCart);
        if (Qty == null)
            Qty = itemView.findViewById(R.id.TextQty);
        if (Delete == null)
            Delete = itemView.findViewById(R.id.Delete_ItemCart);
        if (AddQty == null)
            AddQty = itemView.findViewById(R.id.AddQty_CartItem);
        if (RemoveQty == null)
            RemoveQty = itemView.findViewById(R.id.MinQty_ItemCart);
    }
}
