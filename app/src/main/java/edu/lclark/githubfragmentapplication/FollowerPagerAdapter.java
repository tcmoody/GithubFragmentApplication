package edu.lclark.githubfragmentapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import edu.lclark.githubfragmentapplication.fragments.UserFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by prog on 3/10/16.
 */
public class FollowerPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<GithubUser> mUsers;
    public FollowerPagerAdapter(FragmentManager fm, ArrayList<GithubUser> mUsers) {
        super(fm);
        this.mUsers=mUsers;
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Fragment getItem(int position) {
        return UserFragment.newInstance(mUsers.get(position));
    }

}
