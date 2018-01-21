package com.example.jorge.mytestb2w;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jorge.mytestb2w.Utilite.Utilite;
import com.example.jorge.mytestb2w.adapter.AdapterCategory;
import com.example.jorge.mytestb2w.adapter.AdapterDepartment;
import com.example.jorge.mytestb2w.interfaceFolder.InterfaceMenu;
import com.example.jorge.mytestb2w.model.Children;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.jorge.mytestb2w.Utilite.Utilite.PUT_BUNDLE_CHILDREN;
import static com.example.jorge.mytestb2w.Utilite.Utilite.PUT_EXTRA_CHILDREN;


public class CategoryActivity extends AppCompatActivity implements AdapterCategory.AdapterCategoryOnClickHandler {

    AdapterCategory mAdapterCategory;

    @BindView(R.id.rv_category)
    RecyclerView mRecyclerView;

    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);

        Bundle extra = getIntent().getExtras();
        Bundle bundle = extra.getBundle(PUT_EXTRA_CHILDREN);
        Children children = (Children) bundle.getSerializable(PUT_BUNDLE_CHILDREN);
        initRecyclerView();

        mAdapterCategory = new AdapterCategory(children.getChildren());
        mRecyclerView.setAdapter(mAdapterCategory);


    }


    private void initRecyclerView() {
        /**
         * use RecyclerView for list the Category .
         */
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapterCategory = new AdapterCategory(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

    }


    @Override
    public void onClick(Children children) {
        Context context = this;
        Class destinationClass = ProductActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        String idCategory = getIdCategoryLink(children.getLink());

        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_CHILDREN_ID,idCategory);
        startActivity(intentToStartDetailActivity);
    }

    public String getIdCategoryLink(String link){

        if (!link.equals("")){
            int position = link.toString().lastIndexOf("/");
            if (position > 0)
                link = link.substring(position + 1, link.length());
        }else{
            link = "0";
        }

        return link;
    }


}
