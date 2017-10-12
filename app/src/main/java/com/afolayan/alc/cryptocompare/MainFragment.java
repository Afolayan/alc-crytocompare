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

import com.afolayan.alc.cryptocompare.adapter.RecyclerAdapter;
import com.afolayan.alc.cryptocompare.db.CurrencyRealmController;
import com.afolayan.alc.cryptocompare.model.CryptoCurrency;
import com.afolayan.alc.cryptocompare.model.CryptoList;
import com.afolayan.alc.cryptocompare.model.Currency;

import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    @Bind(R.id.currency_recycler)
    RecyclerView currencyRecyclerView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Realm realm;
    private OrderedRealmCollectionChangeListener<RealmResults<CryptoList>> changeListener;
    private RecyclerAdapter recyclerAdapter;
    private RealmResults<CryptoList> mList;


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        if (getArguments() != null) {
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mList.removeChangeListener(changeListener);
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

        mList = realm.where(CryptoList.class).findAll();
        changeListener = new OrderedRealmCollectionChangeListener<RealmResults<CryptoList>>() {
            @Override
            public void onChange(RealmResults<CryptoList> collection, OrderedCollectionChangeSet changeSet) {
                changeSet.getInsertions();
                recyclerAdapter = new RecyclerAdapter(collection);
            }
        };
        mList.addChangeListener(changeListener);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        recyclerAdapter = new RecyclerAdapter(mList);
        currencyRecyclerView.setItemAnimator(new DefaultItemAnimator());

        currencyRecyclerView.setLayoutManager( linearLayoutManager);

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

    @Override
    public void onResume() {
        super.onResume();
        RealmResults<CryptoList> mList = realm.where(CryptoList.class).findAll();
        recyclerAdapter = new RecyclerAdapter(mList);
    }
}
