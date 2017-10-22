package com.afolayan.alc.cryptocompare;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afolayan.alc.cryptocompare.model.CryptoCurrency;
import com.afolayan.alc.cryptocompare.model.CryptoList;
import com.afolayan.alc.cryptocompare.model.Currency;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConverterFragment extends Fragment {

    private static final String TAG = ConverterFragment.class.getSimpleName();

    @Bind(R.id.toggle_switch)ToggleSwitch toggleSwitch;

    @Bind(R.id.swap_converter)ImageView imgSwapper;

    @Bind(R.id.et_currency_from) EditText etCurrencyFrom;
    @Bind(R.id.et_currency_to) EditText etCurrencyTo;

    @Bind(R.id.tv_currency_name) TextView tvCurrencyName;
//    @Bind(R.id.tv_crypto_type) TextView tvCryptoType;
    @Bind(R.id.tv_last_market) TextView tvLastMarket;
    @Bind(R.id.tv_last_update) TextView tvLastUpdate;
    @Bind(R.id.tv_icon_1) TextView tvIcon1;
    @Bind(R.id.tv_icon_2) TextView tvIcon2;
    CryptoCurrency cryptoCurrency;
    CryptoList thisCrypto;
    private Realm realm;
    RealmList<CryptoCurrency> currencies;
    double conversionIndex;

    public ConverterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_converter, container, false);

        String cryptoId = getActivity().getIntent().getStringExtra("CRYPTO_ID");

        ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();

        ArrayList<String> labels = new ArrayList<>();
        labels.add("ETH");
        labels.add("BTC");
        toggleSwitch.setLabels( labels );
        thisCrypto = realm.where(CryptoList.class)
                .equalTo("ID", cryptoId).findFirst();

        currencies = thisCrypto.getCryptoCurrencies();

        switchCrypto( toggleSwitch.getCheckedTogglePosition() );

        toggleSwitch.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                switchCrypto(position);
            }
        });

        imgSwapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapCurrencies();
            }
        });

        etCurrencyFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if( s.length() == 0) return;
                double value = Double.parseDouble(s.toString().trim());
                String ss = String.valueOf(value * conversionIndex);
                etCurrencyTo.setText(ss);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void swapCurrencies() {
        String icon1 = tvIcon1.getText().toString().trim();
        String icon2 = tvIcon2.getText().toString().trim();

        tvIcon1.setText( icon2 );
        tvIcon2.setText( icon1 );

        conversionIndex = 1 / conversionIndex ;
    }

    private void switchCrypto(int position) {
        String currencyName, cryptoType,
                lastUpdate, lastMarket,
                fromSymbol, toSymbol;

        switch (position){
            case 0:
                cryptoCurrency = currencies.first();
                break;
            case 1:
                cryptoCurrency = currencies.last();
                break;
        }

        currencyName = cryptoCurrency.getToSymbol();
        currencyName = Currency.getCurrencyWithName(getActivity(), currencyName)
                .getName();
        //cryptoType = cryptoCurrency.getType();

        long lastUpdated = cryptoCurrency.getLastUpdate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, YYYY", Locale.UK);
        lastUpdate = dateFormat.format( new Date(lastUpdated) );

        lastMarket = cryptoCurrency.getLastmarket();
        fromSymbol = cryptoCurrency.getFromSymbolIcon();
        toSymbol = cryptoCurrency.getToSymbolIcon();

        tvCurrencyName.setText(currencyName);
        //tvCryptoType.setText( cryptoType );
        tvLastMarket.setText(String.format("%s %s", getString(R.string.market), lastMarket));
        tvLastUpdate.setText(String.format("%s %s", getString(R.string.last_updated), lastUpdate));
        tvIcon1.setText( fromSymbol );
        tvIcon2.setText( toSymbol );
        conversionIndex = Double.parseDouble(cryptoCurrency.getPrice().trim());

    }
}
