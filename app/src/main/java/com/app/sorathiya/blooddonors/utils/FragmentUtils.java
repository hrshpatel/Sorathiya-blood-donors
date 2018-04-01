package com.app.sorathiya.blooddonors.utils;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtils {

    public static void replaceFragment(FragmentManager fragmentManager
            , Fragment fragment, @IdRes int containerId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commit();
    }

}
