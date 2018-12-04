package restaurantapp.yummy.com.yummy.helper;

import android.app.Application;
import android.content.SharedPreferences;

public class GlobalState extends Application {

    SharedPreferences isLoggedIn;
    SharedPreferences.Editor isLoggedInEditor;

    SharedPreferences loggedUserInfo;
    SharedPreferences.Editor loggedUserInfoEditor;

    public static GlobalState singleton;

    public static final String PREFS_IS_LOGGED_IN = "is_logged_in"; // to check if user is logged in
    public static final String PREFS_VALID_USER_INFO = "valid_user_info"; // to store the user information like name and phone number


    @Override
    public void onCreate() {
        super.onCreate();


        isLoggedIn = this.getSharedPreferences(PREFS_IS_LOGGED_IN, 0);
        isLoggedInEditor = isLoggedIn.edit();

        loggedUserInfo = this.getSharedPreferences(PREFS_VALID_USER_INFO, 0);
        loggedUserInfoEditor = loggedUserInfo.edit();

        singleton = this;
    }

    /**
     * @return MySIngleton instance
     */

    public GlobalState getInstance() {
        return singleton;
    }

    public String getPrefsIsLoggedIn(){
        return isLoggedIn.getString(PREFS_IS_LOGGED_IN,"");
    }

    public void setPrefsIsLoggedIn(String loggedStatus, int resultCode){
        if (resultCode == 1) {
            isLoggedInEditor.putString(PREFS_IS_LOGGED_IN, loggedStatus).commit();
        } else {
            isLoggedInEditor.remove(PREFS_IS_LOGGED_IN).commit();
        }
    }

    public String getPREFS_Logged_USER_INFO(){
        return loggedUserInfo.getString(PREFS_VALID_USER_INFO,"");
    }

    public void setPrefsloggedUserInfo(String validUser, int resultCode){
        if (resultCode == 1) {
            loggedUserInfoEditor.putString(PREFS_VALID_USER_INFO, validUser).commit();
        } else {
            loggedUserInfoEditor.remove(PREFS_VALID_USER_INFO).commit();
        }
    }
}



