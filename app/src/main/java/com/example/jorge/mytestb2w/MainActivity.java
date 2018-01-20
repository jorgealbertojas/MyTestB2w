package com.example.jorge.mytestb2w;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.example.jorge.mytestb2w.Utilite.Utilite;
import com.example.jorge.mytestb2w.interfaceFolder.InterfaceMenu;
import com.example.jorge.mytestb2w.model.ListWrapper;
import com.example.jorge.mytestb2w.model.Menu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private InterfaceMenu mInterfaceMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createStackOverflowAPI();
        mInterfaceMenu.getMenu().enqueue(menuCallback);



    }


    /**
     * Open connect with URL for get JSON  .
     */
    private void createStackOverflowAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilite.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceMenu = retrofit.create(InterfaceMenu.class);
    }



    /**
     * Call Get Data Information of the Menu and Children.
     */
    private Callback<ListWrapper> menuCallback = new Callback<ListWrapper>() {
        @Override
        public void onResponse(Call<ListWrapper> call, Response<ListWrapper> response) {
            try {
                if (response.isSuccessful()) {
                    List<Menu> data = new ArrayList<>();
                //    data.addAll(response.body().items);

                //    if (mRecyclerView.getAdapter() != null) {
                //        List<Menu> dataOld = mRepositoriesAdapter.getData();
               //         data.addAll(dataOld);
               //     }
                 //   mRepositoriesAdapter = new RepositoriesAdapter(data);
                //    mRecyclerView.setAdapter(mRepositoriesAdapter);

                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            } catch (NullPointerException e) {
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {
                        Context context = getApplicationContext();
                       // Toast toast = Toast.makeText(context, R.string.Error_Access_empty, Toast.LENGTH_SHORT);
                      //  toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<ListWrapper> call, Throwable t) {
            t.printStackTrace();
        }
    };

}
