package com.example.doraemoncomics.Adapters.Admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.doraemoncomics.Fragments.AccountFragment;
import com.example.doraemoncomics.Fragments.Admin.ComicManagementFragment;
import com.example.doraemoncomics.Fragments.Admin.UserManagementFragment;
import com.example.doraemoncomics.Fragments.User.HomeFragment;

public class ViewPagerAdminAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdminAdapter(@NonNull FragmentManager fm, int behavior) {
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
                return new UserManagementFragment();
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
