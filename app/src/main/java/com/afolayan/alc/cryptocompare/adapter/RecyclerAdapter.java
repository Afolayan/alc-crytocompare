package com.afolayan.alc.cryptocompare.adapter;

import android.content.Context;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ImageViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.CardView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Afolayan Oluwaseyi on 10/6/17.
 */

public class RecyclerAdapter extends RealmRecyclerViewAdapter<CryptoList> {

    private List<CryptoList> mList;
    public static final String TAG = RecyclerAdapter.class.getSimpleName();

    View view;
    Context context;
    private OnCurrencyClicked onCurrencyClicked;

    public OnCurrencyClicked getOnCurrencyClicked() {
        return onCurrencyClicked;
    }

    public void setOnCurrencyClicked(OnCurrencyClicked onCurrencyClicked) {
        this.onCurrencyClicked = onCurrencyClicked;
    }

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final CryptoList cryptoList = mList.get(position);
        RealmList<CryptoCurrency> currencies = cryptoList.getCryptoCurrencies();

        final String ID = cryptoList.getID();

        //RealmList at position 0 == ETH
        //RealmList at position 1 == BTC
        CryptoCurrency eth = currencies.first();
        CryptoCurrency btc = currencies.last();

        String currencyName = eth.getToSymbolIcon()+" - "
                +Currency.getCurrencyWithName(context, currencies.get(0)
                .getToSymbol())
                .getName();
        ((CurrencyHolder) holder).tvCurrencyName.setText(currencyName);

        String cryptoName1 = eth.getFromSymbol() +"("+eth.getFromSymbolIcon()+")";
        ((CurrencyHolder) holder).tvCryptoName1.setText(cryptoName1);

        String cryptoPrice1 = eth.getPrice();
        ((CurrencyHolder) holder).tvCryptoPrice1.setText(cryptoPrice1);

        String cryptoMarket1 = eth.getLastmarket();
        ((CurrencyHolder) holder).tvCryptoMarket1.setText(cryptoMarket1);

        String cryptoName2 = btc.getFromSymbol() +"("+btc.getFromSymbolIcon()+")";
        ((CurrencyHolder) holder).tvCryptoName2.setText(cryptoName2);

        String cryptoPrice2 = btc.getPrice();
        ((CurrencyHolder) holder).tvCryptoPrice2.setText(cryptoPrice2);

        String cryptoMarket2 = btc.getLastmarket();
        ((CurrencyHolder) holder).tvCryptoMarket2.setText(cryptoMarket2);

        long lastUpdated = btc.getLastUpdate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, YYYY", Locale.UK);
        String dateToDisplay = dateFormat.format( new Date(lastUpdated) );

        ((CurrencyHolder) holder).tvLastUpdated.setText(dateToDisplay);

        ((CurrencyHolder) holder).layoutCardView.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnCurrencyClicked().onCurrencyItemClicked(cryptoList);
            }
        });

        ((CurrencyHolder) holder).imgRemoveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCryptoCurrency(ID, position);
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
        @Bind(R.id.single_currency_card)
        CardView layoutCardView;

        @Bind(R.id.txt_currency_name)
        TextView tvCurrencyName;

        @Bind(R.id.txt_crypo_name_1)
        TextView tvCryptoName1;

        @Bind(R.id.txt_price_1)
        TextView tvCryptoPrice1;

        @Bind(R.id.txt_market_1)
        TextView tvCryptoMarket1;

        @Bind(R.id.txt_crypo_name_2)
        TextView tvCryptoName2;

        @Bind(R.id.txt_price_2)
        TextView tvCryptoPrice2;

        @Bind(R.id.txt_market_2)
        TextView tvCryptoMarket2;

        @Bind(R.id.txt_last_updated)
        TextView tvLastUpdated;

        @Bind(R.id.btn_remove_card)
        ImageView imgRemoveCard;

        CurrencyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void deleteCryptoCurrency(final String Id, final int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    CryptoList cryptoList = realm.where(CryptoList.class).equalTo("ID", Id)
                            .findFirst();
                    if( cryptoList != null);
                            cryptoList.deleteFromRealm();
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



    public interface OnCurrencyClicked{
        void onCurrencyItemClicked(CryptoList cryptoList);
    }

}
