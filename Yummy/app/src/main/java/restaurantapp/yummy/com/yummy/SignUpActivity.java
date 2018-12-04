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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import restaurantapp.yummy.com.yummy.Parser.JsonParser;
import restaurantapp.yummy.com.yummy.helper.Constants;

/**
 * Created by User on 3/18/2018.
 */

public class SignUpActivity extends AppCompatActivity {

    EditText mName,mEmail,mPassword,mAddress,mPhone;
    Button mSignup;
    String email,password,phone,address,name;
    ProgressDialog mLoading;
    JSONObject jsonObject;
    JsonParser jsonParser = new JsonParser();
    int status=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mName=(EditText)findViewById(R.id.activity_signup_et_name);
        mEmail=(EditText)findViewById(R.id.activity_signup_et_email);
        mPassword=(EditText)findViewById(R.id.activity_signup_et_password);
        mAddress=(EditText)findViewById(R.id.activity_signup_et_address);
        mPhone=(EditText)findViewById(R.id.activity_signup_et_phone);
        mSignup=(Button)findViewById(R.id.activity_signup_btn_signup);

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=mEmail.getText().toString();
                password=mPassword.getText().toString();
                name=mName.getText().toString();
                address=mAddress.getText().toString();
                phone=mPhone.getText().toString();

                if (TextUtils.isEmpty(email) ||  TextUtils.isEmpty(password) ||  TextUtils.isEmpty(name))
                {
                    Toast.makeText(SignUpActivity.this, "Required fields", Toast.LENGTH_SHORT).show();
                }

                else {

                    new PerformSignup().execute();

                }
            }
        });
    }

    class  PerformSignup extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading =new ProgressDialog(SignUpActivity.this);
            mLoading.setMessage("On Progress");
            mLoading.setCancelable(false);
            mLoading.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (status==1)
            {
                Toast.makeText(SignUpActivity.this, "Something is Wrong", Toast.LENGTH_SHORT).show();
            }

            else if (status==2)
            {
                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }

            else if (status==3)
            {
                Toast.makeText(SignUpActivity.this, "Wrong data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap  =new HashMap<>();
            hashMap.put("email",email);
            hashMap.put("password",password);
            hashMap.put("address",address);
            hashMap.put("phone",phone);
            hashMap.put("name",name);

            String url = Constants.BASE_URL + "api/register";

            jsonObject =jsonParser.performPostCI(url,hashMap);
            mLoading.dismiss();

            if (jsonObject==null)
            {
                status=1;
            }

            else
            {
                try{
                    if (jsonObject.getString("status").equals("success"))
                    {
                        status=2;
                    }

                    else{
                        status=3;
                    }
                }

                catch (JSONException e)
                {
                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            return null;
        }
    }
}
