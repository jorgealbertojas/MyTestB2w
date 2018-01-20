package com.example.jorge.mytestb2w.interfaceFolder;

import com.example.jorge.mytestb2w.model.ListWrapper;
import com.example.jorge.mytestb2w.model.Menu;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jorge on 19/01/2018.
 */

public interface InterfaceMenu {
        @GET("publications/desktop/feather?segment=RC%3DT0%2CMU%3DIN%2CMU%3DTC%2CMU%3DET")
        Call<ListWrapper> getMenu() ;


}
