package com.dal4.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.models.Products_Model;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<VH_Slider> {

    List<Products_Model_Detail> list;
    Context context;

    public SliderAdapter(List<Products_Model_Detail> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public VH_Slider onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slider, null);
        return new VH_Slider(inflate);
    }

    @Override
    public void onBindViewHolder(VH_Slider viewHolder, int position) {
        Products_Model_Detail products_model = list.get(position);

        SetImageIntoPicasso(products_model.getImage(), viewHolder.imageView, R.drawable.ic_error);
        viewHolder.Name.setText(products_model.getName() + "");
        viewHolder.Price.setText(products_model.getPrice() + "$");

    }

    @Override
    public int getCount() {
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

class VH_Slider extends SliderViewAdapter.ViewHolder {
    ImageView imageView;
    TextView Name, Price;


    public VH_Slider(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.Image_Item_Slider);
        Name = itemView.findViewById(R.id.ProductName_itemImageSlider);
        Price = itemView.findViewById(R.id.ProductPrice_itemImageSlider);

    }
}