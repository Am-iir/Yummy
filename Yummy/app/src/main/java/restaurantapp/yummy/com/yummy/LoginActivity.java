package restaurantapp.yummy.com.yummy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import restaurantapp.yummy.com.yummy.Parser.JsonParser;
import restaurantapp.yummy.com.yummy.helper.Constants;
import restaurantapp.yummy.com.yummy.helper.GlobalState;
import restaurantapp.yummy.com.yummy.pojo.LoginCredentials;
import restaurantapp.yummy.com.yummy.sqlite.DBHandler;

/**
 * Created by User on 3/14/2018.
 */

public class LoginActivity extends AppCompatActivity {

    int status=0;

    JsonParser  jsonParser =new JsonParser(); //alt enter to include librabry file
    JSONObject jsonObject;
    EditText mUname,mPwd;
    Button mLogin;
    String username,password,id;
    ProgressDialog mLoading;
    TextView mNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUname=(EditText)findViewById(R.id.activity_login_et_uname);
        mPwd=(EditText)findViewById(R.id.activity_login_et_pwd);
        mLogin=(Button)findViewById(R.id.activity_login_btn_login);
        mNew=(TextView)findViewById(R.id.activity_login_tv_new);


        mNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(myintent);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username=mUname.getText().toString();
                password=mPwd.getText().toString();

                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    new  PerformLogin().execute(); //calling class
                }


            }
        });


    }
        //creating the class within the class and extending as asyntask


    class  PerformLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading = new ProgressDialog(LoginActivity.this);
            mLoading.setMessage("Loading in progress .. ");
            mLoading.setCancelable(false); //loading hudai garda cancel garna huna ki nai
            mLoading.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> hashMap = new HashMap<>(); //hashmap ma key ra valure dubai string deko
            hashMap.put("email", username);//post man ma vako key
            hashMap.put("password", password);

            String url = Constants.BASE_URL + "api/login";

            jsonObject = jsonParser.performPostCI(url, hashMap);
            mLoading.dismiss(); // loading sakako

            if (jsonObject == null) {
                status = 1; // null vaye failure
            } else {
                try {
                    if (jsonObject.getString("status").equals("success")) {

                        status = 2; //sucess ko case

                        // id = jsonObject.getString("id");

                       LoginCredentials credentials=new LoginCredentials(username, password);

                       GlobalState state = GlobalState.singleton;
                        state.setPrefsIsLoggedIn(Constants.STATE_TRUE,1);
                        state.setPrefsloggedUserInfo(new Gson().toJson(credentials),1);

                    } else {
                        status = 3; //intenet  navayera failure ko case
                    }
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (status==1)
            {
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            else if (status==2)
            {

                Toast.makeText(LoginActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, DBHandler.class));


            }

            else if (status ==3)
            {
                Toast.makeText(LoginActivity.this, "Wrong data", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
