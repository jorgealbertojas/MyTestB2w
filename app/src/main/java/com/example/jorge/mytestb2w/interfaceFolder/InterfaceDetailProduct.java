package com.example.jorge.mytestb2w.interfaceFolder;


import com.example.jorge.mytestb2w.model.ProductDetail.ProductDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jorge on 21/01/2018.
 * interface for get Detail Products Americanas
 */

public interface InterfaceDetailProduct {
    @GET("catalogo/product-without-promotion/4")
    Call<ProductDetail>
    getProductDetail(@Query("id") String id);
}
