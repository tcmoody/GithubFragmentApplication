package edu.lclark.githubfragmentapplication.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.UserAsyncTask;

/**
 * Created by prog on 3/4/16.
 */
public class LoginFragment extends Fragment implements UserAsyncTask.onLoginFailureListener{

    @Bind(R.id.login_fragment_imageview)
    ImageView mImageView;
    @Bind(R.id.login_fragment_username_entry)
    EditText mEditText;
    @Bind(R.id.login_fragment_enter_button)
    Button mButton;

    UserAsyncTask mUserAsyncTask;
    public static final String url = "http://s.quickmeme.com/img/3e/3e15b4ca02814e783df7e212f444357dab4b4972f987ea323a5cdfc11722fbe5.jpg";

    public interface LoginListener{
        void onLoginButtonClicked(String username);
    }

    public static LoginFragment newInstance(){
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        rootView.setBackgroundColor(Color.LTGRAY);
        ButterKnife.bind(this, rootView);
        Picasso.with(getContext()).load(url).fit().into(mImageView);
        return rootView;
    }

    @Override
    public void onLoginFailure() {
        mButton.setEnabled(true);
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.login_fragment_enter_button)
    public void onLoginButtonClick(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = connectivityManager.getActiveNetworkInfo();
        if(net == null || !net.isConnected()){
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Logging in now", Toast.LENGTH_SHORT).show();
            mUserAsyncTask = new UserAsyncTask(this, (UserAsyncTask.onLoginSuccessListener) getActivity(), mEditText.getText().toString());
            mUserAsyncTask.execute(mEditText.getText().toString());
            mButton.setEnabled(false);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mUserAsyncTask!=null || !mUserAsyncTask.isCancelled()){
            mUserAsyncTask.cancel(true);
            mUserAsyncTask=null;
        }
    }
}
