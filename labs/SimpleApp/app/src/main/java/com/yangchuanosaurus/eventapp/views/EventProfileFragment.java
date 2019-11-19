package com.yangchuanosaurus.eventapp.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.yangchuanosaurus.eventapp.R;
import com.yangchuanosaurus.eventapp.contracts.EventProfileContract;
import com.yangchuanosaurus.eventapp.presenters.EventProfilePresenter;

import arch.anmobile.mvp.FragmentMvp;

/**
 * Created by Albert Zhao on 2019-11-14.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class EventProfileFragment extends Fragment implements EventProfileContract.View {

    private static final String TAG = EventProfileFragment.class.getSimpleName();

    private EventProfileContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = FragmentMvp.of(this).create(EventProfilePresenter.class);
        Log.d(TAG, "onCreate " + mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_event_profile, parent, false);
    }

    @Override
    public void renderEventProfile(String owner) {

    }

    @Override
    public void renderError(String error) {

    }
}
