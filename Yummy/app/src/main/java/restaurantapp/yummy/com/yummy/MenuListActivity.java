package restaurantapp.yummy.com.yummy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import restaurantapp.yummy.com.yummy.Parser.JsonParser;
import restaurantapp.yummy.com.yummy.adapter.MenuListAdapter;
import restaurantapp.yummy.com.yummy.helper.Constants;
import restaurantapp.yummy.com.yummy.pojo.MenuList;

/**
 * Created by User on 3/22/2018.
 */

public class MenuListActivity extends AppCompatActivity {

    int status=1;

    MenuListAdapter menuListAdapter;
    JsonParser jsonParser = new JsonParser();
    JSONObject jsonObject;
    ArrayList<MenuList> arrayofmenulist=new ArrayList<>();
    MenuList menuList;

    ListView mMenulistview;

    ProgressDialog progressDialog;

    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulist);
        mMenulistview=(ListView)findViewById(R.id.activity_menulist_foodlist);

        new Showmenulist().execute();
    }

    class  Showmenulist extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog =new ProgressDialog(MenuListActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap <String,String> hashMap =new HashMap<>();
            hashMap.put("id","1");
            String url= Constants.BASE_URL +"api/showmenulist";

            jsonObject=jsonParser.performPostCI(url,hashMap);

            if (jsonObject==null)
            {
                status=1;
            }

            else{
                try
                {
                    if (jsonObject.getString("status").equals("success")) {
                        status = 2;

                        Log.e("Test_Tag", "success");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject menuObject = jsonArray.getJSONObject(i);

                            String name, price, details, image, materials;

                            name = menuObject.getString("name");
                            price = menuObject.getString("price");
                            details = menuObject.getString("details");
                            image = menuObject.getString("image");
                            materials = menuObject.getString("materials");

                            menuList = new MenuList(name, price, details, image, materials);

                            arrayofmenulist.add(menuList);
                        }
                    }
                    else
                    {
                        status=3;
                    }
                }

                catch (JSONException e)
                {
                    Log.e("JsonParsing_Exception", ""+e.getLocalizedMessage());

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();

            if (status==2){

                menuListAdapter =new MenuListAdapter(MenuListActivity.this,arrayofmenulist);
                mMenulistview.setAdapter(menuListAdapter);
                mMenulistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        menuList=(MenuList)adapterView.getItemAtPosition(position);
                        Intent intent =new Intent(MenuListActivity.this,MenuDetailActivity.class);
                        intent.putExtra("key",menuList);
                        startActivity(intent);
                    }
                });

            }
        }

    }
}
