package com.example.jorge.mytestb2w;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jorge.mytestb2w.fragment.FragmentDetailProduct;
import com.example.jorge.mytestb2w.model.Product;

import java.util.ArrayList;

import static com.example.jorge.mytestb2w.Utilite.Utilite.PUT_BUNDLE_PRODUCT;
import static com.example.jorge.mytestb2w.Utilite.Utilite.PUT_EXTRA_PRODUCT;

public class DetailProductActivity extends AppCompatActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        Bundle bundle = getIntent().getBundleExtra(PUT_EXTRA_PRODUCT);

        product = (Product) bundle.getSerializable(PUT_BUNDLE_PRODUCT);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {

            ShowFragmentDetail(product);

        }



    }


    // Show fragment with detail the product
    private void ShowFragmentDetail(Product product) {
        FragmentDetailProduct part1Fragment = new FragmentDetailProduct();
        FragmentManager fragmentManager = getSupportFragmentManager();
        part1Fragment.setListIndex(product);
        fragmentManager.beginTransaction()
                .replace(R.id.part1_container, part1Fragment)
                .commit();
    }
}
