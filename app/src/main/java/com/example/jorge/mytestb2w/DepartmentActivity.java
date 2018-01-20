package com.example.jorge.mytestb2w;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.jorge.mytestb2w.Utilite.EndlessRecyclerViewScrollListener;
import com.example.jorge.mytestb2w.Utilite.Utilite;
import com.example.jorge.mytestb2w.adapter.AdapterDepartment;
import com.example.jorge.mytestb2w.interfaceFolder.InterfaceMenu;
import com.example.jorge.mytestb2w.model.Children;
import com.example.jorge.mytestb2w.model.ListWrapper;
import com.example.jorge.mytestb2w.model.Menu;
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

import static com.example.jorge.mytestb2w.Utilite.Utilite.PUT_BUNDLE_CHILDREN;

// Thia activity show information Departament
public class DepartmentActivity extends AppCompatActivity implements AdapterDepartment.AdapterDepartmentOnClickHandler {

    AdapterDepartment mAdapterDepartment;
    private InterfaceMenu mInterfaceMenu;

    @BindView(R.id.rv_department)
    RecyclerView mRecyclerView;

    LinearLayoutManager mLinearLayoutManager;

    List<Children> mListChildren;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        ButterKnife.bind(this);

        // Start RecyclerView with Adapter
        initRecyclerView();

        // Call Api with Retrofit
        createStackOverflowAPI();

        // Configuration Interface
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
                    Menu data = new Menu();
                    data = (response.body().menu);

                    mAdapterDepartment = new AdapterDepartment(data.getComponent().getChildren().get(0).getChildren());
                    mRecyclerView.setAdapter(mAdapterDepartment);

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
        public void onFailure(Call<ListWrapper> call, Throwable t) {
            t.printStackTrace();
        }
    };

    private void initRecyclerView() {
        /**
         * use RecyclerView for list the Department .
         */
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapterDepartment = new AdapterDepartment(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

    }

    @Override
    public void onClick(Children children) {
        Context context = this;
        Class destinationClass = CategoryActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        Children allChildren = new Children();
        mListChildren = new ArrayList<Children>();
        allChildren = getAllCategory(children);

        Bundle bundle = new Bundle();

        bundle.putSerializable(PUT_BUNDLE_CHILDREN, allChildren);
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_CHILDREN,bundle);

        startActivity(intentToStartDetailActivity);
    }


    /** This Function Recursive have responsible  Call RecursiveCategory
     * With the Show all categories
     **/
    private Children getAllCategory(Children children){
        Children allChildren = new Children();
        for (int i = 0; i < children.getChildren().size() ; i++){
            mListChildren.addAll(children.getChildren().get(i).getChildren());
            RecursiveCategory(children.getChildren().get(i));
        }

        allChildren.setChildren(mListChildren);
     return allChildren;

    }


    /** This Function Recursive have responsible  get All Children Categories
     * GetAllCategory Call this function
    **/
    private Children RecursiveCategory(Children children){
        if (children.getChildren() != null){

            return getAllCategory(children);
        }
        return children;
    }




}
