package com.example.jorge.mytestb2w;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jorge.mytestb2w.Utilite.Common;
import com.example.jorge.mytestb2w.Utilite.EndlessRecyclerViewScrollListener;
import com.example.jorge.mytestb2w.Utilite.Utilite;
import com.example.jorge.mytestb2w.adapter.AdapterProduct;
import com.example.jorge.mytestb2w.fragment.FragmentDetailProduct;
import com.example.jorge.mytestb2w.interfaceFolder.InterfaceDetailProduct;
import com.example.jorge.mytestb2w.interfaceFolder.InterfaceProduct;
import com.example.jorge.mytestb2w.model.ListWrapperProduct;
import com.example.jorge.mytestb2w.model.Product;
import com.example.jorge.mytestb2w.model.ProductDetail.ProductDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.jorge.mytestb2w.Utilite.Utilite.KEY_ADAPTER_STATE_PRODUCT;
import static com.example.jorge.mytestb2w.Utilite.Utilite.KEY_RECYCLER_STATE_PRODUCT;
import static com.example.jorge.mytestb2w.Utilite.Utilite.NUMBER_OF_COUMNS;
import static com.example.jorge.mytestb2w.Utilite.Utilite.PUT_BUNDLE_PRODUCT;
import static com.example.jorge.mytestb2w.Utilite.Utilite.PUT_EXTRA_CHILDREN_ID;
import static com.example.jorge.mytestb2w.Utilite.Utilite.PUT_EXTRA_PRODUCT;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_FILTER_PART1;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_FILTER_PART2;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_SORT_BY;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_SOURCE;
import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_URL_START;

public class ProductActivity extends AppCompatActivity implements AdapterProduct.AdapterProductOnClickHandler {

    private static Bundle mBundleRecyclerViewState;
    AdapterProduct mAdapterProduct;
    @BindView(R.id.rv_product)
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    GridLayoutManager mGridLayoutManager;
    String mId;
    List<Product> mListProduct;
    private InterfaceProduct mInterfaceProduct;
    private InterfaceDetailProduct mInterfaceDetailProduct;
    private boolean mTwoPane;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private ProgressBar mLoadingIndicator;
    private Parcelable mListState;
    /**
     * Call Get Information Repositories .
     */
    private Callback<ProductDetail> productDetailCallback = new Callback<ProductDetail>() {
        @Override
        public void onResponse(Call<ProductDetail> call, final Response<ProductDetail> response) {
            try {
                if (response.isSuccessful()) {
                    ProductDetail data = new ProductDetail();
                    data = (response.body());

                    if (data.getProduct().getResult().getImages() != null) {

                        updateDataProductDetail(data);

                    }

                    mLoadingIndicator.setVisibility(View.INVISIBLE);

                    if (mTwoPane) {
                        // In two-pane mode, add initial BodyPartFragments to the screen
                        FragmentDetailProduct part1Fragment = new FragmentDetailProduct();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        part1Fragment.setListIndex(mAdapterProduct.getData().get(0));
                        fragmentManager.beginTransaction()
                                .replace(R.id.part1_container, part1Fragment)
                                .commit();

                    }

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
        public void onFailure(Call<ProductDetail> call, Throwable t) {
            t.printStackTrace();
        }
    };
    /**
     * Call Get Information Product .
     */
    private Callback<ListWrapperProduct<Product>> productCallback = new Callback<ListWrapperProduct<Product>>() {
        @Override
        public void onResponse(Call<ListWrapperProduct<Product>> call, final Response<ListWrapperProduct<Product>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Product> data = new ArrayList<>();
                    data.addAll(response.body().products);

                    if (mRecyclerView.getAdapter() == null) {
                        mAdapterProduct = new AdapterProduct(data);
                        mRecyclerView.setAdapter(mAdapterProduct);
                    } else {
                        mAdapterProduct.getData().addAll(data);
                        mAdapterProduct.notifyDataSetChanged();
                    }

                    for (int i = 0; i < data.size(); i++) {
                        mInterfaceDetailProduct.getProductDetail(Integer.toString(data.get(i).getId()))
                                .enqueue(productDetailCallback);
                    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        ButterKnife.bind(this);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_product);


        Resources res = getResources();


        mTwoPane = res.getBoolean(R.bool.adjust_view_bounds);

        if (savedInstanceState == null) {

            // Start RecyclerView with Adapter
            initRecyclerView();


            mBundleRecyclerViewState = new Bundle();
            Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE_PRODUCT, listState);


            /* Once all of our views are setup, we can load the weather data. */
            if (Common.isOnline(this)) {

                // Call Api with Retrofit
                createProductAPI();

                Bundle extra = getIntent().getExtras();
                mId = extra.getString(PUT_EXTRA_CHILDREN_ID);

                // Configuration Interface
                mInterfaceProduct.getProduct(SUPPORT_URL_START, SUPPORT_URL_SORT_BY, SUPPORT_URL_SOURCE, SUPPORT_URL_FILTER_PART1 + mId + SUPPORT_URL_FILTER_PART2).enqueue(productCallback);

                createProductDetailAPI();

            } else {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, R.string.Error_Access, Toast.LENGTH_SHORT);
                toast.show();
            }

        } else {
            /**
             * I ued this for get State of the Recycler for don't have without the need get API again
             */
            initRecyclerView();
            mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE_PRODUCT);
            mListProduct = (ArrayList<Product>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE_PRODUCT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);
            mAdapterProduct = new AdapterProduct(mListProduct);
            mRecyclerView.setAdapter(mAdapterProduct);

        }


    }

    private void updateDataProductDetail(ProductDetail data) {

        int id = (data.getProduct().getResult().getId());

        String price = "0";
        int quantity = 0;
        String value = "0";
        if (data.getInstallment().getResult().size() > 0) {
            price = Float.toString(data.getInstallment().getResult().get(0).getTotal());
            quantity = (data.getInstallment().getResult().get(0).getQuantity());
            value = Float.toString(data.getInstallment().getResult().get(0).getValue());
        }

        String urlImageSmall = "";
        String urlImageBig = "";

        if (data.getProduct().getResult().getImages().get(0).getSmall() != null) {
            urlImageSmall = data.getProduct().getResult().getImages().get(0).getSmall();
        } else if (data.getProduct().getResult().getImages().get(0).getMedium() != null) {
            urlImageSmall = data.getProduct().getResult().getImages().get(0).getMedium();
        } else if (data.getProduct().getResult().getImages().get(0).getBig() != null) {
            urlImageSmall = data.getProduct().getResult().getImages().get(0).getBig();
        } else if (data.getProduct().getResult().getImages().get(0).getLarge() != null) {
            urlImageSmall = data.getProduct().getResult().getImages().get(0).getLarge();
        } else if (data.getProduct().getResult().getImages().get(0).getExtraLarge() != null) {
            urlImageSmall = data.getProduct().getResult().getImages().get(0).getExtraLarge();
        }


        if (data.getProduct().getResult().getImages().get(0).getBig() != null) {
            urlImageBig = data.getProduct().getResult().getImages().get(0).getBig();
        } else if (data.getProduct().getResult().getImages().get(0).getLarge() != null) {
            urlImageBig = data.getProduct().getResult().getImages().get(0).getLarge();
        } else if (data.getProduct().getResult().getImages().get(0).getExtraLarge() != null) {
            urlImageBig = data.getProduct().getResult().getImages().get(0).getExtraLarge();
        }
        if (data.getProduct().getResult().getImages().get(0).getSmall() != null) {
            urlImageBig = data.getProduct().getResult().getImages().get(0).getSmall();
        } else if (data.getProduct().getResult().getImages().get(0).getMedium() != null) {
            urlImageBig = data.getProduct().getResult().getImages().get(0).getMedium();
        }


        mAdapterProduct.updateData(id, urlImageSmall, urlImageBig, price, quantity, value);
    }


    /**
     * use RecyclerView for list the Category .
     */
    private void initRecyclerView() {

        mRecyclerView.setHasFixedSize(true);
        mAdapterProduct = new AdapterProduct(this);


        if (mTwoPane) {
            mLinearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    loadNextDataFromApi(Integer.toString(page * 24));
                }
            };
            mRecyclerView.addOnScrollListener(mScrollListener);
        } else {
            mGridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COUMNS);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mScrollListener = new EndlessRecyclerViewScrollListener(mGridLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    loadNextDataFromApi(Integer.toString(page * 24));
                }

            };
            mRecyclerView.addOnScrollListener(mScrollListener);

        }


    }

    /**
     * Open connect with URL for get JSON  .
     */
    private void createProductAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilite.BASE_URL_PRODUCT)

                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceProduct = retrofit.create(InterfaceProduct.class);
    }


    /**
     * Open connect with URL for get JSON  .
     */
    private void createProductDetailAPI() {

        mLoadingIndicator.setVisibility(View.VISIBLE);

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilite.BASE_URL_DETAIL)

                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceDetailProduct = retrofit.create(InterfaceDetailProduct.class);
    }

    /**
     * This function call more product
     * When Retrofit need to get more data
     */
    public void loadNextDataFromApi(String page) {

        if (Common.isOnline(this)) {
            createProductAPI();
            mInterfaceProduct.getProduct(page, SUPPORT_URL_SORT_BY, SUPPORT_URL_SOURCE, SUPPORT_URL_FILTER_PART1 + mId + SUPPORT_URL_FILTER_PART2).enqueue(productCallback);
        } else {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.Error_Access, Toast.LENGTH_SHORT);
            toast.show();
        }

    }


    @Override
    public void onClick(Product product) {

        if (mTwoPane) {
            FragmentDetailProduct part1Fragment = new FragmentDetailProduct();
            FragmentManager fragmentManager = getSupportFragmentManager();
            part1Fragment.setListIndex(product);
            fragmentManager.beginTransaction()
                    .replace(R.id.part1_container, part1Fragment)
                    .commit();
        } else {

            Class destinationClass = DetailProductActivity.class;
            Intent intentToStartDetailActivity = new Intent(this, destinationClass);

            Bundle bundle = new Bundle();
            bundle.putSerializable(PUT_BUNDLE_PRODUCT, product);

            intentToStartDetailActivity.putExtra(PUT_EXTRA_PRODUCT, bundle);
            startActivity(intentToStartDetailActivity);
        }

    }

    /**
     * I ued this function for Life cycle activity for get State of the Recycler for don't have without the need get API again
     */
    @Override
    protected void onPause() {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mListProduct = (ArrayList<Product>) mAdapterProduct.getData();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE_PRODUCT, mListState);
        mBundleRecyclerViewState.putSerializable(KEY_ADAPTER_STATE_PRODUCT, (Serializable) mListProduct);
    }


    /**
     * I ued this function for Life cycle activity for get State of the Recycler for don't have without the need get API again
     */
    @Override
    protected void onResume() {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE_PRODUCT);
            mListProduct = (ArrayList<Product>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE_PRODUCT);

        }
    }


}
