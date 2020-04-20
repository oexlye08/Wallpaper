package id.simpus.wallpaper.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import id.simpus.wallpaper.R;

public class Functions {
    public static void changeMainFragment (FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }

    public static void changeMainFragmentWithBack (FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }
}
