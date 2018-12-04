package restaurantapp.yummy.com.yummy.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import restaurantapp.yummy.com.yummy.R;

/**
 * Created by User on 3/19/2018.
 */

public class HomeFragment extends Fragment {

    TextView makecall;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view= inflater.inflate(R.layout.fragment_home, container, false);

        makecall= (TextView)view.findViewById(R.id.fragment_home_tv_contact);
        makecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                builder.setMessage("Sure you wanna call? ");
                builder.setCancelable(false);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String phonenumber;
                        phonenumber="0123456789";

                        Intent phoneIntent =new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phonenumber,null));
                        try{
                            startActivity(phoneIntent);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog =builder .create();
                alertDialog.setTitle("Phone call");
                alertDialog.show();
            }
        });

        return view;

    }
}
