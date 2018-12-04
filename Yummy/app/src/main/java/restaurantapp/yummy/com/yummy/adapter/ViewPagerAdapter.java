package restaurantapp.yummy.com.yummy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import restaurantapp.yummy.com.yummy.fragments.AboutFragment;
import restaurantapp.yummy.com.yummy.fragments.GalleryFragment;
import restaurantapp.yummy.com.yummy.fragments.HomeFragment;

/**
 * Created by User on 3/20/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int no_of_tabs;


    public ViewPagerAdapter(FragmentManager fm, int no_of_tabs) {
        super(fm);
        this.no_of_tabs = no_of_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            case  1:

                AboutFragment af =new AboutFragment();
                return af;

            case 2:

                GalleryFragment gf=new GalleryFragment();
                return gf;

            default:
                return null;

         }
    }

    @Override
    public int getCount() {

        return no_of_tabs;

    }
}