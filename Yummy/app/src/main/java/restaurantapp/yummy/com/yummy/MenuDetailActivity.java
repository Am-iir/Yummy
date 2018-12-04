package restaurantapp.yummy.com.yummy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import restaurantapp.yummy.com.yummy.pojo.MenuList;

public class MenuDetailActivity  extends AppCompatActivity{

    MenuList menuList ;
    ImageView mMenuimage;
    TextView mName,mPrice,mMaterialused,mAboutFood;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        mMenuimage = (ImageView) findViewById(R.id.activity_menu_detail_menuimage);
        mName = (TextView)findViewById(R.id.activity_menu_detail_foodname);
        mPrice = (TextView)findViewById(R.id.activity_menu_detail_foodprice);
        mMaterialused = (TextView)findViewById(R.id.activity_menu_detail_materialused);
        mAboutFood = (TextView)findViewById(R.id.activity_menu_detail_aboutfood);

        menuList =(MenuList) getIntent().getSerializableExtra("key");

        mName.setText("Name: "+menuList.getName());
        mPrice.setText("Price: Rs. "+menuList.getPrice());
        mMaterialused.setText("Material Used: "+menuList.getMaterials());
        mAboutFood.setText("Detail: "+menuList.getDetails());

        Glide.with(MenuDetailActivity.this).load(menuList.getImage()).into(mMenuimage);

    }
}
