package restaurantapp.yummy.com.yummy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import restaurantapp.yummy.com.yummy.R;
import restaurantapp.yummy.com.yummy.pojo.MenuList;

/**
 * Created by User on 3/21/2018.
 */

public class MenuListAdapter extends ArrayAdapter {

    ArrayList<MenuList> menuLists;
    Context context;
    TextView mName,mPrice;
    ImageView mImage;

    public MenuListAdapter(Context context,ArrayList<MenuList> menuLists) {
        super(context,0,menuLists);
        this.menuLists = menuLists;
        this.context = context;
    }

    //ctrl o to override

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MenuList menuList = (MenuList) getItem(position);

        if (convertView ==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_single_menuitem,parent,false);
        }

        mName=(TextView)convertView.findViewById(R.id.adapter_single_menuitem_tv_name);
        mPrice=(TextView)convertView.findViewById(R.id.adapter_single_menuitem_tv_price);
        mImage=(ImageView)convertView.findViewById(R.id.adapter_single_menuitem_iv);

        mName.setText(menuList.getName());
        mPrice.setText("Rs." + menuList.getPrice());

        Glide.with(getContext()).load(menuList.getImage()).into(mImage);
        return convertView;

    }
}
