package com.example.HOT.nomichats.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kshitiz on 3/17/2018.
 * //---THIS CLASS FOR MANAGING FRAGMENTS---
 *
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter{

    public CustomFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ChatFragment chatFragment=new ChatFragment();
                return chatFragment;
            case 1:
                FriendFragment friendFragment=new FriendFragment();
                return friendFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "CHATS";
            case 1:
                return "FRIENDS";
            default:
                return null;
        }
    }

}
