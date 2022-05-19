package com.dal4.ecommerce.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dal4.ecommerce.R;
import com.dal4.ecommerce.data_base.Local.DaoFun.GetCartList;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.ui.Frag_Container;

import java.util.ArrayList;
import java.util.List;

public class Frag_Cart extends Fragment {
    //Views
    private View view;
    private Button Btn_PROCEED_FragCart;
    private RecyclerView RV_Cart;
    private TextView TotalPriceWithoutShipping, Shipping, TotalPrice_Text;

    //Var
    public static List<Products_Model_Detail> UserCartList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UserCartList = Frag_Container.getUserCartProducts();
        view = inflater.inflate(R.layout.frag_cart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private void initViews() {
        Btn_PROCEED_FragCart = view.findViewById(R.id.Btn_PROCEED_FragCart);
        RV_Cart = view.findViewById(R.id.RV_Cart_CartFrag);
        TotalPriceWithoutShipping = view.findViewById(R.id.TotalPriceWithoutShiping_CartFrag);
        Shipping = view.findViewById(R.id.ShippingPrice_CartFrag);
        TotalPrice_Text = view.findViewById(R.id.TotalPrice_CartFrag);

        new GetCartList(getContext(), RV_Cart, TotalPrice_Text, TotalPriceWithoutShipping).execute();

        Btn_PROCEED_FragCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RV_Cart.getAdapter().getItemCount() == 0) {
                    Toast.makeText(getContext(), "You didn't added items yet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Your order is successfully Checkout", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
