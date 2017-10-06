package com.afolayan.alc.cryptocompare.adapter;

import android.media.Image;
import android.support.v4.widget.ImageViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afolayan.alc.cryptocompare.R;
import com.afolayan.alc.cryptocompare.model.Currency;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Afolayan Oluwaseyi on 10/6/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Currency> mList;
    public static final String TAG = RecyclerAdapter.class.getSimpleName();
    public static final int CURRENCY_TYPE = 0;

    public RecyclerAdapter(List<Currency> list){
        this.mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.layout_one_currency, parent, false);
        return new CurrencyHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Currency currency = mList.get(position);
        if( currency != null ){
            ((CurrencyHolder) holder).tvCurrencyName.setText(currency.getName());
            ((CurrencyHolder) holder).tvCurrencyCode.setText(currency.getCode());

            ((CurrencyHolder) holder).imgRemoveCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("HERE", "Hey, I'm working");
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }
    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public class CurrencyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_currency_name) public TextView tvCurrencyName;
        @Bind(R.id.txt_currency_code) public TextView tvCurrencyCode;
        @Bind(R.id.btn_remove_card) public ImageView imgRemoveCard;
        CurrencyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
