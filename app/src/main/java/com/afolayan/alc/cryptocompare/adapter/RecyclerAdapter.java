package com.afolayan.alc.cryptocompare.adapter;

import android.content.Context;
import android.media.Image;
import android.support.design.widget.Snackbar;
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
import com.afolayan.alc.cryptocompare.model.CryptoCurrency;
import com.afolayan.alc.cryptocompare.model.CryptoList;
import com.afolayan.alc.cryptocompare.model.Currency;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Afolayan Oluwaseyi on 10/6/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CryptoList> mList;
    public static final String TAG = RecyclerAdapter.class.getSimpleName();
    public static final int CURRENCY_TYPE = 0;
    View view;
    Context context;

    public RecyclerAdapter(List<CryptoList> list){
        this.mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.layout_one_currency, parent, false);
        this.view = view;
        return new CurrencyHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        CryptoList cryptoList = mList.get(position);
        RealmList<CryptoCurrency> currencies = cryptoList.getCryptoCurrencies();

        final String ID = cryptoList.getID();
        ((CurrencyHolder) holder).tvCurrencyName.setText("SEYI");
        ((CurrencyHolder) holder).tvCurrencyCode.setText("AFOLAYAN");

        ((CurrencyHolder) holder).imgRemoveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCryptoCurrency(ID);
            }
        });
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
        @Bind(R.id.txt_crypo_name_1) public TextView tvCurrencyCode;
        @Bind(R.id.btn_remove_card) public ImageView imgRemoveCard;
        CurrencyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void deleteCryptoCurrency(final String Id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.where(CryptoList.class).equalTo("ID", Id)
                            .findFirst()
                            .deleteFromRealm();
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Snackbar.make(view, "Successfully removed card", Snackbar.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                  @Override
                  public void onError(Throwable error) {
                      Snackbar.make(view, "Cannot removed card. Please try again", Snackbar.LENGTH_SHORT).show();
                  }
              }
        );
    }



}
