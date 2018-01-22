package com.example.jorge.mytestb2w.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.mytestb2w.R;
import com.example.jorge.mytestb2w.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jorge.mytestb2w.Utilite.Utilite.SUPPORT_STEP;

/**
 * Created by jorge on 20/01/2018.
 */

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private static AdapterProductOnClickHandler mClickHandler;
    private final List<Product> data;
    private Context mContext;

    /**
     * Constructs the class
     **/
    public AdapterProduct(AdapterProductOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        data = null;
    }

    /**
     * create lit de Adapter
     **/
    public AdapterProduct(List<Product> data) {
        this.data = data;
    }

    /**
     * Create information View holder
     **/
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_product, parent, false);
        mContext = parent.getContext();


        return new ViewHolder(v);
    }

    /**
     * Create filed bind hold full
     **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        /** Create filed bind hold full **/

        Product product = ((Product) data.get(position));
        holder.mNameTextView.setText((product.getName()));
        holder.mPriceTextView.setText(mContext.getResources().getString(R.string.money) + " " + product.getPrice());

        if (product.getValue() != null) {
            holder.mInstallmentTextView.setText(Integer.toString(product.getQuantity()) + " x " + product.getValue());
        } else {
            holder.mInstallmentTextView.setText("");
        }


        if (!product.getUrl_image_small().toString().equals("0")) {
            Picasso.with(mContext)
                    .load(product.getUrl_image_small())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.mImageProductImageView);
        }

    }

    /**
     * Returns the total Adapter
     **/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Product> getData() {
        return data;
    }

    /**
     * This function update the RecyclerView.
     * The Product with your Detail URL image and all Information value
     */
    public void updateData(int id, String urlImageSmall, String urlImageBig, String price, int quantity, String value) {
        int i = data.size() - SUPPORT_STEP;
        while (i < data.size()) {
            if (id == data.get(i).getId()) {
                data.get(i).setUrl_image_small(urlImageSmall);
                data.get(i).setUrl_image_big(urlImageBig);
                data.get(i).setPrice(price);
                data.get(i).setQuantity(quantity);
                data.get(i).setValue(value);
            }
            i++;
        }
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface AdapterProductOnClickHandler {
        void onClick(Product product);
    }

    /**
     * class view holder
     **/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_image_product)
        ImageView mImageProductImageView;
        @BindView(R.id.tv_name_product)
        TextView mNameTextView;
        @BindView(R.id.tv_price)
        TextView mPriceTextView;
        @BindView(R.id.tv_installment)
        TextView mInstallmentTextView;

        /**
         * get field of the main for show recyclerView
         **/
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
            v.setOnClickListener(this);
        }

        /**
         * configuration the Event onclick. Pass o Object
         **/
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Product product = data.get(adapterPosition);
            mClickHandler.onClick(product);

        }
    }


}

