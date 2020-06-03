package com.example.aplikasikelolapasiencovid_19.ui.tabview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AboutUsPagerAdapter extends FragmentStateAdapter {

    private static int TAB_COUNT = 5;

    public AboutUsPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new RijaFragment();
            case 1:
                return new RidwanFragment();
            case 2:
                return new IrvanFragment();
            case 3:
                return new DolaFragment();
            case 4:
                return new AgoyFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
}
