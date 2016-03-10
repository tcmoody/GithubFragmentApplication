package edu.lclark.githubfragmentapplication.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.lclark.githubfragmentapplication.FollowerPagerAdapter;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.fragments.UserFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;

public class FollowersActivity extends AppCompatActivity implements UserFragment.UserListener{

    @Bind(R.id.followers_tabs)
    TabLayout mTabLayout;
    @Bind(R.id.followers_view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        ButterKnife.bind(this);

        ArrayList<GithubUser> followers = getIntent().getParcelableArrayListExtra(MainActivity.ARG_USER);

        FragmentPagerAdapter adapter = new FollowerPagerAdapter(getSupportFragmentManager(), followers);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(mTabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onUserFollowerButtonClicked(GithubUser user) {
        Toast.makeText(FollowersActivity.this, "We did it!", Toast.LENGTH_SHORT).show();
    }
}
