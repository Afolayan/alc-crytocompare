package com.afolayan.alc.cryptocompare.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.afolayan.alc.cryptocompare.R;
import com.afolayan.alc.cryptocompare.model.Currency;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Afolayan Oluwaseyi on 10/6/17.
 */

public class NewCurrencyAdapter extends ArrayAdapter<Currency> {

    private List<Currency> mList;
    public static final String TAG = NewCurrencyAdapter.class.getSimpleName();
    private Context context;
    private int layoutResource;
    OnItemSelected onItemSelectedListener;

    public NewCurrencyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Currency> list) {
        super(context, resource, list);
        this.mList = list;
        this.context = context;
        this.layoutResource = resource;
    }


    public OnItemSelected getOnItemSelectedListener() {
        return onItemSelectedListener;
    }

    public void setOnItemSelectedListener(OnItemSelected onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public int getCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @NonNull @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Currency currency = mList.get(position);
        TextView tvCurrencyName, tvCurrencyCode, tvCurrencySymbol;
        if( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater)
                    parent.getContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layoutResource, parent, false);
        }
        tvCurrencyName = convertView.findViewById(R.id.txt_currency_name);
        tvCurrencyCode = convertView.findViewById(R.id.txt_currency_code);
        tvCurrencySymbol = convertView.findViewById(R.id.txt_country_symbol);

        tvCurrencyName.setText(currency.getName());
        tvCurrencyCode.setText(currency.getCode());
        tvCurrencySymbol.setText(currency.getSymbol());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelectedListener.onItemSelected(currency);
            }
        });

        return convertView;

    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return mList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public interface OnItemSelected {
        void onItemSelected(Currency currency);
    }


}
