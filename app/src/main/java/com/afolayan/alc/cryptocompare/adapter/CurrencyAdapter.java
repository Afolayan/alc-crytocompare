package com.afolayan.alc.cryptocompare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afolayan.alc.cryptocompare.R;
import com.afolayan.alc.cryptocompare.model.CryptoCurrency;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Afolayan Oluwaseyi on 10/11/17.
 */

public class CurrencyAdapter extends RealmRecyclerViewAdapter<CryptoCurrency>{

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public CurrencyAdapter(Context context){
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_currency, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
