package com.example.doraemoncomics.Adapters.User;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doraemoncomics.Fragments.AccountFragment;
import com.example.doraemoncomics.Fragments.Admin.ComicManagementFragment;
import com.example.doraemoncomics.Fragments.User.FavoriteFragment;
import com.example.doraemoncomics.Fragments.User.HomeFragment;
import com.example.doraemoncomics.Fragments.User.ListFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ComicManagementFragment();
            case 2:
                return new FavoriteFragment();
            case 3:
                return new AccountFragment();
            default:
                return new HomeFragment();

        }
        }

    @Override
    public int getCount() {
        return 4;
    }
}
