package com.yangchuanosaurus.eventapp.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.yangchuanosaurus.eventapp.R;
import com.yangchuanosaurus.eventapp.contracts.EventOwnerContract;
import com.yangchuanosaurus.eventapp.presenters.EventOwnerPresenter;

import arch.anmobile.mvp.FragmentMvp;

/**
 * Created by Albert Zhao on 2019-11-14.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventOwnerFragment extends Fragment implements EventOwnerContract.View {

    private static final String TAG = EventOwnerFragment.class.getSimpleName();

    private EventOwnerContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPresenter = FragmentMvp.of(this).create(EventOwnerPresenter.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_event_owner, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void renderEventOwner(String owner) {

    }

    @Override
    public void renderError(String error) {

    }
}
