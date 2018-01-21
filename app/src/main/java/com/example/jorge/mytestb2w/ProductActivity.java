package com.example.jorge.mytestb2w;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.jorge.mytestb2w.Utilite.Common;
import com.example.jorge.mytestb2w.Utilite.EndlessRecyclerViewScrollListener;
import com.example.jorge.mytestb2w.Utilite.Utilite;
import com.example.jorge.mytestb2w.adapter.AdapterProduct;
import com.example.jorge.mytestb2w.interfaceFolder.InterfaceProduct;
import com.example.jorge.mytestb2w.model.ListWrapperProduct;
import com.example.jorge.mytestb2w.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.example.jorge.mytestb2w.Utilite.Utilite.PUT_EXTRA_CHILDREN_ID;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_FILTER_PART1;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_FILTER_PART2;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_SORT_BY;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_SOURCE;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_START;

public class ProductActivity extends AppCompatActivity implements AdapterProduct.AdapterProductOnClickHandler{

    AdapterProduct mAdapterProduct;
    private InterfaceProduct mInterfaceProduct;

    @BindView(R.id.rv_product)
    RecyclerView mRecyclerView;

    LinearLayoutManager mLinearLayoutManager;
    List<Product> mListProduct;
    String mId;
    private EndlessRecyclerViewScrollListener mScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ButterKnife.bind(this);

        // Start RecyclerView with Adapter
        initRecyclerView();

        // Call Api with Retrofit
        createStackOverflowAPI();

        Bundle extra = getIntent().getExtras();
        mId = extra.getString(PUT_EXTRA_CHILDREN_ID);

        // Configuration Interface
        mInterfaceProduct.getProduct(SUPPORT_URL_START , SUPPORT_URL_SORT_BY ,SUPPORT_URL_SOURCE , SUPPORT_URL_FILTER_PART1 + mId + SUPPORT_URL_FILTER_PART2 ).enqueue(productCallback);

    }

    /**
     * Call Get Information Repositories .
     */
    private Callback<ListWrapperProduct<Product>> productCallback = new Callback<ListWrapperProduct<Product>>() {
        @Override
        public void onResponse(Call<ListWrapperProduct<Product>> call, Response<ListWrapperProduct<Product>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Product> data = new ArrayList<>();
                    data.addAll(response.body().products);

                    if (mRecyclerView.getAdapter() != null) {
                        List<Product> dataOld = mAdapterProduct.getData();
                        data.addAll(dataOld);
                    }
                    mAdapterProduct = new AdapterProduct(data);
                    mRecyclerView.setAdapter(mAdapterProduct);

                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            } catch (NullPointerException e) {
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.Error_Access_empty, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<ListWrapperProduct<Product>> call, Throwable t) {
            t.printStackTrace();
        }
    };




    private void initRecyclerView() {
        /**
         * use RecyclerView for list the Category .
         */
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapterProduct = new AdapterProduct(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(Integer.toString(page * 24));
            }

        };
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    /**
     * Open connect with URL for get JSON  .
     */
    private void createStackOverflowAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilite.BASE_URL_PRODUCTOR)

                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceProduct = retrofit.create(InterfaceProduct.class);
    }


    @Override
    public void onClick(Product product) {

    }


    /**
     * This function call more product
     * When Retrofit need to get more data
    */
    public void loadNextDataFromApi(String page) {

        if (Common.isOnline(getSystemService(Context.CONNECTIVITY_SERVICE))) {
            createStackOverflowAPI();
            mInterfaceProduct.getProduct(page , SUPPORT_URL_SORT_BY ,SUPPORT_URL_SOURCE , SUPPORT_URL_FILTER_PART1 + mId + SUPPORT_URL_FILTER_PART2).enqueue(productCallback);
        } else {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.Error_Access, Toast.LENGTH_SHORT);
            toast.show();
        }



    }
}
