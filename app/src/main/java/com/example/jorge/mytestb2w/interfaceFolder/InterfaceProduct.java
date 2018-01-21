package com.example.jorge.mytestb2w.interfaceFolder;

import com.example.jorge.mytestb2w.model.ListWrapperProduct;
import com.example.jorge.mytestb2w.model.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jorge on 20/01/2018.
 * interface for get Products Americanas
 */

public interface InterfaceProduct {

   @GET("/mystique/search")
   Call<ListWrapperProduct<Product>> getProduct(@Query("offset") String offset,
                                                @Query("sortBy") String sortBy,
                                                @Query("source") String source,
                                                @Query(value = "filter", encoded = true) String filter );

}
