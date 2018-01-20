package com.example.jorge.mytestb2w.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jorge.mytestb2w.R;
import com.example.jorge.mytestb2w.model.Children;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jorge on 19/01/2018.
 */

public class AdapterDepartment extends RecyclerView.Adapter<AdapterDepartment.ViewHolder> {

    private final List<Children> data;

    private Context mContext;


    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private static AdapterDepartmentOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface AdapterDepartmentOnClickHandler {
        void onClick(Children children);
    }

    /**
     * Constructs the class
     **/
    public AdapterDepartment(AdapterDepartmentOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        data = null;
    }


    /**
     * class view holder
     **/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_name)
        TextView mNameTextView;

        /**
         * get field of the main for show recyclerView
         **/
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
            v.setOnClickListener(this);
        }

        /**
         * configuration the Event onclick. Pass o Object Travel
         **/
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Children children = data.get(adapterPosition);
            mClickHandler.onClick(children);

        }
    }

    /**
     * create lit de Adapter Travel
     **/
    public AdapterDepartment(List<Children> data) {
        this.data = data;
    }

    /**
     * Create information View holder
     **/
    @Override
    public AdapterDepartment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_departament, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(v);
    }

    /**
     * Create filed bind hold full
     **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        /** Create filed bind hold full **/
        Children children = ((Children) data.get(position));
        holder.mNameTextView.setText(children.getTitle());
    }

    /**
     * Returns the total Adapter
     **/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Children> getData() {
        return data;
    }
}
