package com.dal4.ecommerce.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.data_base.Local.DaoFun.AddProduct_Cart;
import com.dal4.ecommerce.data_base.Local.DaoFun.GetCartList;
import com.dal4.ecommerce.data_base.Remote.RetrofitClient;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.models.RateBodyModel;
import com.dal4.ecommerce.models.RateModel;
import com.dal4.ecommerce.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetail extends AppCompatActivity {
    private static final String TAG = "LongestNum";
    //Views
    private ImageView imageView;
    private TextView price, product_name, reviews_Number, product_Description;
    private Button Btn_AddToCart, Btn_back_ItemDetail, Btn_AddRateProduct;
    private RatingBar ratingBar;
    private Dialog dialog;
    //Var
    private List<String> Spinner1List = new ArrayList<>();
    private List<String> Spinner2List = new ArrayList<>();
    private String userID;
    private Products_Model_Detail CurrentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initViews();

    }


    private void initViews() {
        imageView = findViewById(R.id.Image_ItemDetail);
//        slider_detail_item = findViewById(R.id.slider_detail_item);
        price = findViewById(R.id.Price_ItemDetail);
        product_name = findViewById(R.id.ProductName_ItemDetail);
        reviews_Number = findViewById(R.id.ReviewsNumber_ItemDetail);

        Btn_AddToCart = findViewById(R.id.Btn_ADDToCart_ItemDetail);
        ratingBar = findViewById(R.id.RatingBar_ItemDetail);
        Btn_back_ItemDetail = findViewById(R.id.Btn_back_ItemDetail);
        Btn_AddRateProduct = findViewById(R.id.Btn_AddRate_ItemDetail);
        product_Description = findViewById(R.id.Description_ItemDetail);

        //Get UserProducts from FragHome
        Intent intent = getIntent();
        CurrentProduct = (Products_Model_Detail) intent.getSerializableExtra("CurrentItem");

        userID = intent.getStringExtra("userId");

        SetDataToViews(CurrentProduct);

        Btn_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetCartList(getApplicationContext(), CurrentProduct).execute();
            }
        });


        Btn_back_ItemDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Btn_AddRateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRateDialog();
            }
        });


    }

    public void OpenRateDialog() {
        dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_rate_product);

        RatingBar RateBar;
        Button Btn_Submit;
        EditText ET_Comment;

        ET_Comment = dialog.findViewById(R.id.ET_MessageReview);
        RateBar = dialog.findViewById(R.id.RatingBar_DialogRate);
        Btn_Submit = dialog.findViewById(R.id.Btn_SubmitRate_DialogRate);

        Btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RateProduct(ET_Comment.getText() + "", RateBar.getRating(), CurrentProduct.get_id());
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    public int Return_Pixels_By_LongestNumInArr(List<String> array) {
        int index = 0;
        int elementLength = array.get(0).length();
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i).length() > elementLength) {
                index = i;
                elementLength = array.get(i).length();
            }
        }
        int widthOfSpinner1 = (array.get(index).length() + 4) * 10;
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (widthOfSpinner1 * scale + 0.5f);

        return pixels;

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Save Statement In database
    }

    public void SetDataToViews(Products_Model_Detail products_model_detail) {
        String S_name = products_model_detail.getName() + "";
        String S_price = products_model_detail.getPrice() + "";
        String S_Dec = products_model_detail.getDescription() + "";

        String S_countRate = products_model_detail.getNumReviews() + "";

        ratingBar.setRating((float) products_model_detail.getRating());
        product_name.setText(S_name);
        price.setText(S_price + "$");
        reviews_Number.setText(S_countRate + " Reviews");
        product_Description.setText(S_Dec);
        SetImageIntoPicasso(products_model_detail.getImage(), imageView, R.drawable.ic_error);

    }

    //More Fun
    private void SetImageIntoPicasso(String ImageUrl, ImageView imageView, int ErrorImage) {
        String ImageURL = ImageUrl;
        if (!ImageUrl.isEmpty()) {
            Picasso.get()
                    .load(ImageURL)
                    .into(imageView, new com.squareup.picasso.Callback() {
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

    public void RateProduct(String message, Float rate, String ProductID) {
        Map<Object, Object> map = new HashMap<>();
        map.put("rating", rate);
        map.put("comment", message);

        Call<RateModel> call = RetrofitClient.getInstance().RateProductById(Constants.getUserToken(ItemDetail.this), map, ProductID);
        call.enqueue(new Callback<RateModel>() {
            @Override
            public void onResponse(Call<RateModel> call, Response<RateModel> response) {
                if (response.isSuccessful() && response.code() == 201) {
                    RateModel rateModel = response.body();
                    Toast.makeText(ItemDetail.this, rateModel.getMessage() + "", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(ItemDetail.this, response.message() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RateModel> call, Throwable t) {
                Toast.makeText(ItemDetail.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }
}