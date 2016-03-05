package edu.lclark.githubfragmentapplication.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.UserAsyncTask;
import edu.lclark.githubfragmentapplication.fragments.LoginFragment;
import edu.lclark.githubfragmentapplication.fragments.MainActivityFragment;
import edu.lclark.githubfragmentapplication.fragments.UserFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.FollowerSelectedListener, UserFragment.UserListener, UserAsyncTask.onLoginSuccessListener{

    @Bind(R.id.activity_main_framelayout)
    FrameLayout mFrameLayout;
    FloatingActionButton mFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().popBackStack(null,getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                mFab.setVisibility(View.GONE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_framelayout, LoginFragment.newInstance());
                transaction.commit();
            }
        });
        mFab.setVisibility(View.INVISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, new LoginFragment());
        transaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFollowerSelected(GithubUser follower) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, UserFragment.newInstance(follower));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onUserFollowerButtonClicked(GithubUser user) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, MainActivityFragment.newInstance(user));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onLoginSuccess(GithubUser githubUser){
        mFab.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, UserFragment.newInstance(githubUser));
        transaction.commit();
    }
}
