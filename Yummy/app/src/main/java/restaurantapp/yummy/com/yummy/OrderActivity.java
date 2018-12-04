package restaurantapp.yummy.com.yummy;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Calendar;
import java.util.HashMap;

import restaurantapp.yummy.com.yummy.Parser.JsonParser;
import restaurantapp.yummy.com.yummy.helper.Constants;

public class OrderActivity extends AppCompatActivity {

    Calendar calendar;
    EditText mQuantity,mTime,mDate,mDetail,mUserid,mUsername;
    Spinner mName;
    String name,quantity,time,date,detail,userid,username,selectedfood;
    Button mOrder;
    ProgressDialog mLoading;
    JsonParser  jsonParser = new JsonParser();
    JSONObject jsonObject;
    int status=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        calendar=Calendar.getInstance();

        mName=(Spinner)findViewById(R.id.activity_order_spinner_name);
        mQuantity = (EditText) findViewById(R.id.activity_order_quantity);
        mTime = (EditText) findViewById(R.id.activity_order_time);
        mDate = (EditText) findViewById(R.id.activity_order_date);
        mDetail = (EditText) findViewById(R.id.activity_order_detail);
        mUserid = (EditText) findViewById(R.id.activity_order_userid);
        mUsername = (EditText) findViewById(R.id.activity_order_username);
        mOrder = (Button) findViewById(R.id.activity_order_btn_order);


        ArrayAdapter<CharSequence> nameadapter =ArrayAdapter.createFromResource(OrderActivity.this,R.array.menu_list,android.R.layout.simple_list_item_1);
        nameadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mName.setAdapter(nameadapter);
        mName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                selectedfood=mName.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int year=calendar.get(Calendar.YEAR);
                 int month=calendar.get(Calendar.MONTH);
                 int day=calendar.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog =new DatePickerDialog(OrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        mDate.setText(day+ "/" + (month +1) + "/" + year);

                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });


        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour =calendar.get(Calendar.HOUR_OF_DAY);
                final int min =calendar.get(Calendar.MINUTE);

                final TimePickerDialog timePickerDialog = new TimePickerDialog(OrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int min) {
                        mTime.setText(hour + ":" + min);

                    }
                },hour,min, false  );
                timePickerDialog.show();

            }
        });



        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity = mQuantity.getText().toString();
                time=mTime.getText().toString();
                date=mDate.getText().toString();
                detail = mDetail.getText().toString();
                userid = mUserid.getText().toString();
                username = mUsername.getText().toString();

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(userid) && TextUtils.isEmpty(username)) {
                    Toast.makeText(OrderActivity.this, "Required fields", Toast.LENGTH_SHORT).show();
                } else {

                    new PerformOrder().execute();
                }
            }
        });

    }
        class PerformOrder extends AsyncTask<String ,String ,String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mLoading =new ProgressDialog(OrderActivity.this);
                mLoading.setMessage("On Progress");
                mLoading.setCancelable(false);
                mLoading.show();

            }


            @Override
            protected String doInBackground(String... strings) {
                HashMap<String,String> hashMap= new HashMap<>();
                hashMap.put("name",selectedfood);
                hashMap.put("quantity",quantity);
                hashMap.put("time",time);
                hashMap.put("date",date);
                hashMap.put("detail",detail);
                hashMap.put("userid",userid);
                hashMap.put("username",username);

                String url = Constants.BASE_URL + "api/orderfood";

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
                        Toast.makeText(OrderActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (status==1)
                {
                    Toast.makeText(OrderActivity.this, "Something is Wrong", Toast.LENGTH_SHORT).show();
                }

                else if (status==2)
                {
                    Toast.makeText(OrderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OrderActivity.this,MainActivity.class));
                }

                else if (status==3)
                {
                    Toast.makeText(OrderActivity.this, "Wrong data", Toast.LENGTH_SHORT).show();
                }
            }


        }
}
