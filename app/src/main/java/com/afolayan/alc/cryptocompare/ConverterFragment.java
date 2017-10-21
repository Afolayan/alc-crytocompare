package com.afolayan.alc.cryptocompare;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConverterFragment extends Fragment {

    private static final String TAG = ConverterFragment.class.getSimpleName();

    public ConverterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_converter, container, false);

        Log.e(TAG, "onCreateView: id"+getActivity().getIntent().getStringExtra("CRYPTO_ID"));
        return view;
    }
}
