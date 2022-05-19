package com.dal4.ecommerce.utils;

import com.dal4.ecommerce.models.Products_Model_Detail;

import java.util.List;

public interface Connector {
    public void GetListProduct(List<Products_Model_Detail> products_model_details);

}
