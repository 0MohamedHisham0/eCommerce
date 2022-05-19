package com.dal4.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.adapters.Adapter_ProductsHome;
import com.dal4.ecommerce.data_base.Remote.RetrofitClient;
import com.dal4.ecommerce.models.Products_Model;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.ui.Category_Screen;
import com.dal4.ecommerce.ui.Frag_Container;
import com.dal4.ecommerce.utils.Communicator;
import com.dal4.ecommerce.utils.Connector;
import com.dal4.ecommerce.utils.Constants;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dal4.ecommerce.adapters.SliderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_Home extends Fragment implements Communicator {

    private static final String TAG = "Token";
    //Views
    View view;
    @BindView(R.id.slider)
    SliderView slider;
    @BindView(R.id.ShowAll_Electronics)
    TextView ShowAll_Products;
    @BindView(R.id.RV_Electronics)
    ObservableRecyclerView RV_Products;
    @BindView(R.id.progress_circular_FragHome)
    SpinKitView progress_circular_FragHome;
    @BindView(R.id.MainLayout_FragHome)
    LinearLayout linearLayout;

    //Var
    public static List<Products_Model_Detail> CartList = new ArrayList<>();
    private Connector mCallback_FragContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        Log.i(TAG, "initViews: " + Constants.getUserData(getActivity(), "UserToken"));
        ShowAll_Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Category_Screen.class);
                Bundle args = new Bundle();
                args.putSerializable("ArrayList", (Serializable) CartList);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });

        slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        slider.setScrollTimeInSec(3);

    }

    public void GetAllProducts() {
        Call<Products_Model> call = RetrofitClient.getInstance().GetAll();
        call.enqueue(new Callback<Products_Model>() {
            @Override
            public void onResponse(Call<Products_Model> call, Response<Products_Model> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    progress_circular_FragHome.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);

                    Products_Model list = response.body();
                    if (list != null) {
                        List<Products_Model_Detail> products_model_detail = list.getProducts();

                        RV_Products.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        RV_Products.setAdapter(new Adapter_ProductsHome(products_model_detail, getContext(), Frag_Home.this::onClickAddProduct));
                    }
                } else {
                    Toast.makeText(getContext(), response.message() + "", Toast.LENGTH_SHORT).show();
                    progress_circular_FragHome.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Products_Model> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                progress_circular_FragHome.setVisibility(View.GONE);

            }
        });
    }

    public void GetTopProducts() {
        Call<List<Products_Model_Detail>> products_modelCall = RetrofitClient.getInstance().GetTopProducts();
        products_modelCall.enqueue(new Callback<List<Products_Model_Detail>>() {
            @Override
            public void onResponse(Call<List<Products_Model_Detail>> call, Response<List<Products_Model_Detail>> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    Log.i(TAG, "Top: " + response.body().get(1).getPrice());
                    slider.setSliderAdapter(new SliderAdapter(response.body(), getContext()));

                } else {
                    Toast.makeText(getContext(), response.message() + "", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Top: " + response.message() + "");

                }
            }

            @Override
            public void onFailure(Call<List<Products_Model_Detail>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Top: " + t.getMessage());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        slider.setAutoCycle(true);
        slider.startAutoCycle();

        if (Constants.checkInternetConnection(getContext())) {
            GetAllProducts();
            GetTopProducts();
        } else {
            progress_circular_FragHome.setVisibility(View.GONE);
            Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No Internet", Snackbar.LENGTH_INDEFINITE);
            snackBar.show();
            snackBar.setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

    }

    @Override
    public void onPause() {
        super.onPause();
//        Communicator_MainActivity.SendListProductToActivity(UserCartProducts);
    }

    @Override
    public void onClickAddProduct(List<Products_Model_Detail> products_model_detail) {
        CartList = products_model_detail;
        mCallback_FragContainer = (Frag_Container) getActivity();
        mCallback_FragContainer.GetListProduct(products_model_detail);
    }

}

