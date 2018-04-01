package com.app.sorathiya.blooddonors.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.sorathiya.blooddonors.R;
import com.app.sorathiya.blooddonors.activity.SearchActivity;
import com.app.sorathiya.blooddonors.utils.CommonUtils;
import com.app.sorathiya.blooddonors.utils.FragmentUtils;

public class HomeFragment extends Fragment {

    private ImageView imgDonateBlood;
    private TextInputEditText mEdtBloodGroup;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CommonUtils.closeKeyboard(getActivity());
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeView(view);
        setListeners();
    }

    private void setListeners() {
        mEdtBloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Blood group");

                String[] animals = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
                builder.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("blood_group", "A+");

                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("blood_group", "A-");

                                startActivity(intent);
                                break;
                            case 2:
                                intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("blood_group", "B+");

                                startActivity(intent);
                                break;
                            case 3:
                                intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("blood_group", "B-");

                                startActivity(intent);
                                break;
                            case 4:
                                intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("blood_group", "AB+");

                                startActivity(intent);
                                break;
                            case 5:
                                intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("blood_group", "AB-");

                                startActivity(intent);
                                break;
                            case 6:
                                intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("blood_group", "O+");

                                startActivity(intent);
                                break;
                            case 7:
                                intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("blood_group", "O-");

                                startActivity(intent);
                                break;
                        }
                    }
                });

                builder.show();
            }
        });
    }

    private void initializeView(View view) {
        mEdtBloodGroup = (TextInputEditText) view.findViewById(R.id.frg_home_edt_blood_group);
    }
}
