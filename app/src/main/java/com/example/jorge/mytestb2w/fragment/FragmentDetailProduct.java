package com.example.jorge.mytestb2w.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.mytestb2w.R;
import com.example.jorge.mytestb2w.model.Product;
import com.squareup.picasso.Picasso;

/**
 * Created by jorge on 22/01/2018.
 * Fragment for use Detail Product SmartPhone and Tablet
 */

public class FragmentDetailProduct extends Fragment {


    // Variables to store a list of image resources and the index of the image that this fragment displays
    private Product mProduct;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public FragmentDetailProduct() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);

        TextView textViewId = (TextView) rootView.findViewById(R.id.tv_product_detail);
        TextView textViewPrice = (TextView) rootView.findViewById(R.id.tv_price_detail);
        TextView textViewName = (TextView) rootView.findViewById(R.id.tv_name_product_detail);
        TextView textViewInstallment = (TextView) rootView.findViewById(R.id.tv_installment_detail);
        ImageView imageViewProduct = (ImageView) rootView.findViewById(R.id.iv_image_product_detail);

        textViewId.setText(Integer.toString(mProduct.getId()));
        textViewPrice.setText(getContext().getResources().getString(R.string.money) + " " + mProduct.getPrice());
        textViewName.setText((mProduct.getName()));

        if (mProduct.getValue() != null){
            textViewInstallment.setText(Integer.toString(mProduct.getQuantity()) + " x " + mProduct.getValue());
        }else {
            textViewInstallment.setText("");
        }


        if (mProduct.getUrl_image_big() != null) {
            Picasso.with(getContext())
                    .load(mProduct.getUrl_image_big())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageViewProduct);
        }

        // Return the rootView*/
        return rootView;
    }

    public void setListIndex(Product product) {
        mProduct = product;
    }
}
