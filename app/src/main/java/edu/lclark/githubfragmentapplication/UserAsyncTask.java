package edu.lclark.githubfragmentapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by prog on 3/3/16.
 */
public class UserAsyncTask extends AsyncTask<String, Integer, GithubUser> {

    public static final String TAG = UserAsyncTask.class.getSimpleName();
    public onLoginFailureListener mOnLoginFailureListener;
    public onLoginSuccessListener mOnLoginSuccess;


    public interface onLoginFailureListener{
        void onLoginFailure();
    }

    public interface onLoginSuccessListener{
        void onLoginSuccess(GithubUser githubUser);
    }

    public UserAsyncTask(onLoginFailureListener mOnLoginFailureListener, onLoginSuccessListener mOnLoginSuccess, String username){
        this.mOnLoginFailureListener = mOnLoginFailureListener;
        this.mOnLoginSuccess = mOnLoginSuccess;
    }

    @Override
    protected GithubUser doInBackground(String... strings) {
        StringBuilder responseBuilder = new StringBuilder();
        GithubUser githubUser = null;
        if (strings.length == 0) {
            return null;
        }

        String userId = strings[0];

        try {
            URL url = new URL("https://api.github.com/users/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            if (isCancelled()) {
                return null;
            }
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);

                if (isCancelled()) {
                    return null;
                }
            }

            githubUser = new Gson().fromJson(responseBuilder.toString(), GithubUser.class);

            if (isCancelled()) {
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return githubUser;
    }

    @Override
    protected void onPostExecute(GithubUser githubUser){
        super.onPostExecute(githubUser);
        if (githubUser==null){
            mOnLoginFailureListener.onLoginFailure();
            Log.d(TAG, "onPostExecute: login failed");
        }else{
            mOnLoginSuccess.onLoginSuccess(githubUser);
            Log.d(TAG, "onPostExecute: login successful");
        }
    }
}
