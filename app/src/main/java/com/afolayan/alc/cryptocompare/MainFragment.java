package com.afolayan.alc.cryptocompare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afolayan.alc.cryptocompare.adapter.RecyclerAdapter;
import com.afolayan.alc.cryptocompare.model.Currency;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @Bind(R.id.currency_list)
    RecyclerView currencyRecyclerView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        refreshViews();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshViews();
            }
        });
        Currency currency = new Currency();
        List<Currency> mList = currency.getCurrencies(getActivity());

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(mList);
        currencyRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //currencyRecyclerView.setLayoutManager( linearLayoutManager );
        currencyRecyclerView.setLayoutManager( new GridLayoutManager(getActivity(), 2));

        currencyRecyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    private void refreshViews() {
        // get updated values
        // parse values
        // present updates

        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
