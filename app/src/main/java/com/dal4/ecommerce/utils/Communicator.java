package com.dal4.ecommerce.utils;

import com.dal4.ecommerce.models.Products_Model;
import com.dal4.ecommerce.models.Products_Model_Detail;

import java.util.List;

public interface Communicator {

    public void onClickAddProduct(List<Products_Model_Detail> products_modelList);

}
