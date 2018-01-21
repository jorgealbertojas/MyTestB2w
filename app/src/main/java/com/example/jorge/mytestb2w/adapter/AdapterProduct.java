package com.example.jorge.mytestb2w.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.mytestb2w.R;
import com.example.jorge.mytestb2w.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jorge on 20/01/2018.
 */

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder>  {

    private final List<Product> data;

    private Context mContext;


    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private static AdapterProductOnClickHandler mClickHandler;
    /**
     * The interface that receives onClick messages.
     */
    public interface AdapterProductOnClickHandler {
        void onClick(Product product);
    }

    /** Constructs the class**/
    public  AdapterProduct(AdapterProductOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        data = null;
    }




    /** class view holder**/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
     //   @BindView(R.id.iv_avatar_url)
    //    ImageView mAvatarUrlImageView;
        @BindView(R.id.tv_name_product)
        TextView mNameTextView;
/*        @BindView(R.id.tv_description) TextView mDescriptionTextView;
        @BindView(R.id.tv_stargazers_count) TextView mStargazersCountTextView;
        @BindView(R.id.tv_forks_count) TextView mForksCountTextView;
        @BindView(R.id.tv_login) TextView mLoginTextView;
        @BindView(R.id.iv_forks) ImageView mForks;
        @BindView(R.id.iv_star) ImageView mStar;*/





        /** get field of the main for show recyclerView**/
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
            v.setOnClickListener(this);
        }

        /** configuration the Event onclick. Pass o Object Travel **/
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Product product = data.get(adapterPosition);
            mClickHandler.onClick(product);

        }
    }

    /** create lit de Adapter Travel**/
    public AdapterProduct(List<Product> data) {
        this.data = data;
    }

    /** Create information View holder**/
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_product, parent, false);
        mContext = parent.getContext();


        return new ViewHolder(v);
    }

    /** Create filed bind hold full **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        /** Paint dynamic forks image and Star image**/
        Resources res = mContext.getResources();
     //   final int newColor = res.getColor(R.color.colorYellow);
     //   holder.mForks.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
     //   holder.mStar.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);

        /** Create filed bind hold full **/

        Product product = ((Product) data.get(position));
        holder.mNameTextView.setText(Integer.toString(product.getId()));
  //      holder.mDescriptionTextView.setText(product.getDescription());
 //       holder.mStargazersCountTextView.setText(Integer.toString(product.getStargazers_count()));
 //       holder.mForksCountTextView.setText(Integer.toString(product.getForks_count()));
 //       holder.mLoginTextView.setText(product.getOwner().getLogin());
 //       holder.mDescriptionTextView.setText(product.getDescription());
  //      Picasso.with(mContext).load(product.getOwner().getAvatar_url()).into(holder.mAvatarUrlImageView);

    }

    /** Returns the total Adapter**/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Product> getData() {
        return data;
    }




}

