package com.dal4.ecommerce.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.data_base.Local.DaoFun.AddProduct_Cart;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.ui.ItemDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Search extends RecyclerView.Adapter<VH_SearchItem> {

    private final List<Products_Model_Detail> list;
    private final Context context;

    public Adapter_Search(List<Products_Model_Detail> list, Context context ) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public VH_SearchItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new VH_SearchItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH_SearchItem holder, int position) {
        Products_Model_Detail CurrentProduct = list.get(position);

        String name = CurrentProduct.getName() + "";
        String price = CurrentProduct.getPrice() + "";
        String Desc = CurrentProduct.getBrand() + "";

        holder.ProductName.setText(name);
        holder.ProductDesc.setText(Desc);
        holder.ProductPrice.setText(price + "$");
        SetImageIntoPicasso(CurrentProduct.getImage(), holder.ProductImage, R.drawable.ic_error);



        holder.Layout_item_main_product.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemDetail.class);
            intent.putExtra("CurrentItem", CurrentProduct);
            intent.putExtra("userId", CurrentProduct.get_id());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //More Fun
    private void SetImageIntoPicasso(String ImageUrl, ImageView imageView, int ErrorImage) {
        String ImageURL = ImageUrl;
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

}

class VH_SearchItem extends RecyclerView.ViewHolder {
    ImageView ProductImage;
    TextView ProductName, ProductPrice, ProductDesc;
    Button Btn_AddTOCart;
    CardView Layout_item_main_product;

    public VH_SearchItem(@NonNull View itemView) {
        super(itemView);

        if (ProductImage == null)
            ProductImage = itemView.findViewById(R.id.ImageProduct_SearchScreen);

        if (ProductName == null)
            ProductName = itemView.findViewById(R.id.NameProduct_SearchScreen);

        if (ProductPrice == null)
            ProductPrice = itemView.findViewById(R.id.Price_SearchScreen);

        if (ProductDesc == null)
            ProductDesc = itemView.findViewById(R.id.DescProduct_SearchScreen);

        if (Layout_item_main_product == null)
            Layout_item_main_product = itemView.findViewById(R.id.Layout_item_search);

    }


}
