package com.dal4.ecommerce.data_base.Local.DaoFun;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dal4.ecommerce.adapters.AdapterCart;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.utils.Constants;
import com.dal4.ecommerce.utils.Repository;

import java.util.List;

public class GetCartList extends AsyncTask<Void, Void, List<Products_Model_Detail>> {
    static List<Products_Model_Detail> ListProduct;
    Context context;
    private RecyclerView recyclerView;
    private TextView TotalPrice_Text, TotalPriceWithoutShipping;
    private Products_Model_Detail CurrentProduct;

    public GetCartList(Context context, RecyclerView recyclerView, TextView TotalPrice_Text, TextView TotalPriceWithoutShipping) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.TotalPriceWithoutShipping = TotalPriceWithoutShipping;
        this.TotalPrice_Text = TotalPrice_Text;
    }

    public GetCartList(Context context, Products_Model_Detail CurrentProduct) {
        this.context = context;
        this.CurrentProduct = CurrentProduct;
    }

    @Override
    protected List<Products_Model_Detail> doInBackground(Void... voids) {
        List<Products_Model_Detail> list;
        list = Repository.getCartList_dataBase(context).studentsDao().GetCartProduct();
        return list;
    }

    @Override
    protected void onPostExecute(List<Products_Model_Detail> list) {
        super.onPostExecute(list);
        ListProduct = list;
        Constants.saveCartSize(context, list.size());
        if (recyclerView != null) {
            recyclerView.setAdapter(new AdapterCart(list, context, TotalPrice_Text, TotalPriceWithoutShipping));
        } else {
            if (!list.contains(CurrentProduct)) {
                new AddProduct_Cart(context).execute(CurrentProduct);
                Toast.makeText(context, "This product has been added to your cart", Toast.LENGTH_SHORT).show();
            }
        }
    }


}