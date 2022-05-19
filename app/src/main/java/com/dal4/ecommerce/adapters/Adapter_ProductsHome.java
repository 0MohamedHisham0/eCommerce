package com.dal4.ecommerce.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.data_base.Local.DaoFun.AddProduct_Cart;
import com.dal4.ecommerce.fragments.Frag_Home;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.ui.ItemDetail;
import com.dal4.ecommerce.utils.Communicator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_ProductsHome extends RecyclerView.Adapter<VH_ProductItem> {

    private final List<Products_Model_Detail> list;
    private final Context context;
    private final List<Products_Model_Detail> CartList = new ArrayList<>();
    private final Communicator mCallback;

    public Adapter_ProductsHome(List<Products_Model_Detail> list, Context context, Communicator mCallback) {
        this.list = list;
        this.context = context;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public VH_ProductItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_product, parent, false);
        return new VH_ProductItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH_ProductItem holder, int position) {
        Products_Model_Detail CurrentProduct = list.get(position);

        String name = CurrentProduct.getName() + "";
        String price = CurrentProduct.getPrice() + "";
        String Desc = CurrentProduct.getBrand() + "";

        holder.ProductName.setText(name);
        holder.ProductDesc.setText(Desc);
        holder.ProductPrice.setText(price + "$");
        SetImageIntoPicasso(CurrentProduct.getImage(), holder.ProductImage, R.drawable.ic_error);

        holder.Btn_AddTOCart.setOnClickListener(v -> {
            if (mCallback instanceof Frag_Home) {
                if (!CartList.contains(CurrentProduct)) {
                    CartList.add(CurrentProduct);
                    mCallback.onClickAddProduct(CartList);
                    new AddProduct_Cart( context).execute(CurrentProduct);
                } else {
                    Toast.makeText(context, "This product is already in your cart", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!CartList.contains(CurrentProduct)) {
                    CartList.add(CurrentProduct);
                    new AddProduct_Cart( context).execute(CurrentProduct);

                } else {
                    Toast.makeText(context, "This product is already in your cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.Layout_item_main_product.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemDetail.class);
            intent.putExtra("CurrentItem", CurrentProduct);
            intent.putExtra("userId", CurrentProduct.get_id());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
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

}

class VH_ProductItem extends RecyclerView.ViewHolder {
    ImageView ProductImage;
    TextView ProductName, ProductPrice, ProductDesc;
    Button Btn_AddTOCart;
    CardView Layout_item_main_product;

    public VH_ProductItem(@NonNull View itemView) {
        super(itemView);

        if (ProductImage == null)
            ProductImage = itemView.findViewById(R.id.ImageProduct_ItemMain);

        if (ProductName == null)
            ProductName = itemView.findViewById(R.id.NameProduct_ItemMain);

        if (ProductPrice == null)
            ProductPrice = itemView.findViewById(R.id.Price_ItemMain);

        if (ProductDesc == null)
            ProductDesc = itemView.findViewById(R.id.DescProduct_ItemMain);

        if (Btn_AddTOCart == null)
            Btn_AddTOCart = itemView.findViewById(R.id.Btn_ADDToCart_ItemMain);

        if (Layout_item_main_product == null)
            Layout_item_main_product = itemView.findViewById(R.id.Layout_item_main_product);

    }
}