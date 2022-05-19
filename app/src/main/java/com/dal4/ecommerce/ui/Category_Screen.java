package com.dal4.ecommerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.adapters.Adapter_ProductsHome;
import com.dal4.ecommerce.data_base.Remote.RetrofitClient;
import com.dal4.ecommerce.fragments.Frag_Home;
import com.dal4.ecommerce.models.Products_Model;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.utils.Communicator;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_Screen extends AppCompatActivity implements Communicator {
    //Views
    Button Btn_back, Btn_Cart, Btn_Prev, Btn_Next;
    TextView NumberOfItem_category, PageNumber;
    RecyclerView RV_Category;
    NotificationBadge notificationBadge;

    //Var
    List<Products_Model> ElectronicsProducts_List = new ArrayList<>();
    List<Products_Model> UserCartProducts = new ArrayList<>();
    private String CurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);

        initViews();

    }

    private void initViews() {
        Btn_back = findViewById(R.id.Btn_back_category);
        Btn_Cart = findViewById(R.id.Btn_Cart_category);
        Btn_Prev = findViewById(R.id.Btn_Prev_CS);
        Btn_Next = findViewById(R.id.Btn_Next_CS);

        PageNumber = findViewById(R.id.TV_PageNumber);

        NumberOfItem_category = findViewById(R.id.NumberOfItem_category);
        RV_Category = findViewById(R.id.RV_category);
        notificationBadge = findViewById(R.id.badge_CategoryScreen);

        GetProductsByPageNumber(CurrentPage);

        Btn_Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = (Integer.parseInt(CurrentPage)) - 1;
                GetProductsByPageNumber(String.valueOf(page));
            }
        });

        Btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = (Integer.parseInt(CurrentPage)) + 1;
                GetProductsByPageNumber(String.valueOf(page));
            }
        });

        GetListFromFrag();
        notificationBadge.setNumber(Frag_Container.UserCartProducts.size());

        RV_Category.setLayoutManager(new GridLayoutManager(this, 2));
        RV_Category.setAdapter(new AdapterElectronics(ElectronicsProducts_List));

        NumberOfItem_category.setText(ElectronicsProducts_List.size() + "");

        Btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Category_Screen.this, Frag_Container.class));
            }
        });

    }

    private void GetListFromFrag() {
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ElectronicsProducts_List = (ArrayList<Products_Model>) args.getSerializable("ArrayList");
    }

    public void GetProductsByPageNumber(String page) {
        Call<Products_Model> call = RetrofitClient.getInstance().GetProductByPage(page);
        call.enqueue(new Callback<Products_Model>() {
            @Override
            public void onResponse(Call<Products_Model> call, Response<Products_Model> response) {
                if (response.isSuccessful() && response.code() == 200) {
//                    progress_circular_FragHome.setVisibility(View.GONE);

                    Products_Model list = response.body();

                    if (list != null) {
                        int CurrentPage_ = list.getPage();
                        CurrentPage = String.valueOf(CurrentPage_);
                        List<Products_Model_Detail> products_model_detail = list.getProducts();

                        if (CurrentPage_ != 1)
                            Btn_Prev.setVisibility(View.VISIBLE);
                        else
                            Btn_Prev.setVisibility(View.GONE);

                        if (CurrentPage_ == list.getPages()) {
                            Btn_Next.setVisibility(View.GONE);
                        } else {
                            Btn_Next.setVisibility(View.VISIBLE);
                        }

                        PageNumber.setText(CurrentPage_ + "");

                        RV_Category.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                        RV_Category.setAdapter(new Adapter_ProductsHome(products_model_detail, Category_Screen.this, Category_Screen.this));
                        NumberOfItem_category.setText(products_model_detail.size() + "");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.message() + "", Toast.LENGTH_SHORT).show();
//                    progress_circular_FragHome.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Products_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
//                progress_circular_FragHome.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onClickAddProduct(List<Products_Model_Detail> products_modelList) {

    }

    public class AdapterElectronics extends RecyclerView.Adapter<VH_Electronics> {

        List<Products_Model> list;

        public AdapterElectronics(List<Products_Model> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public VH_Electronics onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_main_product, parent, false);
            return new VH_Electronics(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH_Electronics holder, int position) {
            Products_Model products_model = list.get(position);

//            String name = products_model.getName() + "";
//            String price = products_model.getPrice() + "";
//            String Desc = products_model.getBrand() + "";

//
//            holder.ProductName.setText(name);
//            holder.ProductDesc.setText(Desc);
//            holder.ProductPrice.setText(price + "$");
//            holder.ProductImage.setImageResource(products_model.getImage());

            holder.Btn_AddTOCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Frag_Container.UserCartProducts.contains(products_model)) {
                        Toast.makeText(getApplicationContext(), "This Item Already in your Cart", Toast.LENGTH_LONG).show();
                    } else {

                        notificationBadge.setNumber(Frag_Container.UserCartProducts.size());
                    }
                }
            });
            holder.Layout_item_main_product.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), ItemDetail.class);
                intent.putExtra("CurrentItem", products_model);
                startActivity(intent);

            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class VH_Electronics extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView ProductName, ProductPrice, ProductDesc;
        Button Btn_AddTOCart;
        CardView Layout_item_main_product;

        public VH_Electronics(@NonNull View itemView) {
            super(itemView);

            ProductImage = itemView.findViewById(R.id.ImageProduct_ItemMain);
            ProductName = itemView.findViewById(R.id.NameProduct_ItemMain);
            ProductPrice = itemView.findViewById(R.id.Price_ItemMain);
            ProductDesc = itemView.findViewById(R.id.DescProduct_ItemMain);
            Btn_AddTOCart = itemView.findViewById(R.id.Btn_ADDToCart_ItemMain);
            Layout_item_main_product = itemView.findViewById(R.id.Layout_item_main_product);

        }
    }


}