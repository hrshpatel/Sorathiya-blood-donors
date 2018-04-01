package com.app.sorathiya.blooddonors.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.sorathiya.blooddonors.R;
import com.app.sorathiya.blooddonors.utils.CommonUtils;

public class AboutUsFragment extends Fragment {

    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CommonUtils.closeKeyboard(getActivity());
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

}
