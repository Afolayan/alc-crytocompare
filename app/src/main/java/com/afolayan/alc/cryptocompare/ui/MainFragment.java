package com.afolayan.alc.cryptocompare.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afolayan.alc.cryptocompare.R;
import com.afolayan.alc.cryptocompare.adapter.RecyclerAdapter;
import com.afolayan.alc.cryptocompare.model.CryptoCurrency;
import com.afolayan.alc.cryptocompare.model.CryptoList;
import com.afolayan.alc.cryptocompare.rest.APIController;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;


public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    @Bind(R.id.currency_recycler)
    RecyclerView currencyRecyclerView;

    @Bind(R.id.no_currency)
    TextView tvNoCurrency;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Realm realm;

    private RecyclerAdapter recyclerAdapter;
    private RealmResults<CryptoList> mList;
    private LinearLayoutManager linearLayoutManager;
    private RealmChangeListener<RealmResults<CryptoList>> changeListener;


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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshViews();
            }
        });

        mList = realm.where(CryptoList.class).findAll();

        linearLayoutManager =
                new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        changeListener = new RealmChangeListener<RealmResults<CryptoList>>() {
            @Override
            public void onChange(RealmResults<CryptoList> mList) {
                loadList(mList, linearLayoutManager);
            }
        };
        mList.addChangeListener(changeListener);

        currencyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        loadList(mList, linearLayoutManager);


        return view;
    }

    private void loadList(RealmResults<CryptoList> mList, LinearLayoutManager linearLayoutManager) {
        recyclerAdapter = new RecyclerAdapter(mList);
        currencyRecyclerView.setLayoutManager(linearLayoutManager);
        currencyRecyclerView.setAdapter(recyclerAdapter);

        if( mList.size() > 0 ) {
            tvNoCurrency.setVisibility(View.GONE);
        } else{
            tvNoCurrency.setVisibility(View.VISIBLE);
        }
    }

    private void refreshViews() {

        if( mList.size() == 0 ) {
            Toast.makeText(getActivity(), "Add a card before refreshing", Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        List<String> addedCurrencies = new ArrayList<>();
        List<String> addedCurrenciesIds = new ArrayList<>();

        for( CryptoList cryptoList: mList){
            RealmList<CryptoCurrency> currencies = cryptoList.getCryptoCurrencies();
            addedCurrencies.add( currencies.get(0).getToSymbol());
            addedCurrenciesIds.add( cryptoList.getID() );
        }

        if( addedCurrencies.size() != addedCurrenciesIds.size()) {
            Toast.makeText(getActivity(), "Something happened. Cannot update data", Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        APIController controller = new APIController();
        controller.start(addedCurrencies, addedCurrenciesIds, mSwipeRefreshLayout);

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


    }
}
