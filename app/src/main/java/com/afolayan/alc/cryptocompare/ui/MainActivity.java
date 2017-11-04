package com.afolayan.alc.cryptocompare.ui;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.afolayan.alc.cryptocompare.R;
import com.afolayan.alc.cryptocompare.adapter.NewCurrencyAdapter;
import com.afolayan.alc.cryptocompare.model.CryptoCurrency;
import com.afolayan.alc.cryptocompare.model.CryptoList;
import com.afolayan.alc.cryptocompare.model.Currency;
import com.afolayan.alc.cryptocompare.rest.APIController;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener{


    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.add_currency_fab)
    FloatingActionButton addNewCurrencyFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        OnClickListener fabClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                doAddCard();
            }
        };
        addNewCurrencyFab.setOnClickListener(fabClickListener);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ){

        }
        return super.onOptionsItemSelected(item);
    }

    private void doAddCard() {
        final List<Currency> mList = Currency.getCurrencies(this);

        final NewCurrencyAdapter currencyAdapter =
                new NewCurrencyAdapter(this, R.layout.layout_currency_list_item, mList);

        View dialogView = getLayoutInflater().inflate(R.layout.layout_alert_list, null);
        final ListView listView = (ListView) dialogView.findViewById(R.id.currency_list);
        listView.setAdapter(currencyAdapter);




        AlertDialog.Builder addCurrencyAlert = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("Choose a currency")
                .setView(dialogView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alertDialog = addCurrencyAlert.create();

        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        alertDialog.show();

        currencyAdapter.setOnItemSelectedListener(new NewCurrencyAdapter.OnItemSelected() {
            @Override
            public void onItemSelected(Currency currency) {
                workWithSelected(currency);
                alertDialog.dismiss();
            }
        });
    }

    private void workWithSelected(Currency currency) {
        View dialogView = getLayoutInflater().inflate(R.layout.layout_progress_dialog, null);

        if(isCurrencyAdded(currency.getCode())){
            Snackbar.make(addNewCurrencyFab,
                    "This currency has already been added. You can refresh to get updates.",
                    Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this)
                .setView(dialogView);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
        APIController controller = new APIController();
        controller.start(currency.getCode(), alertDialog);
    }

    private boolean isCurrencyAdded(String code) {
        RealmResults<CryptoList> mList = Realm.getDefaultInstance()
                .where(CryptoList.class).findAll();
        for( CryptoList cryptoList: mList){
            RealmList<CryptoCurrency> currencies = cryptoList.getCryptoCurrencies();
            if( code.equalsIgnoreCase( currencies.get(0).getToSymbol()) ){
                return true;
            }
        }
        return false;
    }


}
