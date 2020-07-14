package com.melih.tabactionbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.HashMap;

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    private Button btnTEST;
    View view ;
    LinearLayout linearLayout;
    ImageView addButton;
    ArrayList<HashMap<String, String>> users;
    DatabaseHelper databaseHelper ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment3_layout,container,false);

         linearLayout = view.findViewById(R.id.parentLinear);
         addButton = view.findViewById(R.id.addpersonbutton);
         //deleteButton = view.findViewById(R.id.deletepersonbutton);

         databaseHelper = new DatabaseHelper(this.getContext());


        addButton.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {


                 createDialogBox("", "","",0);

             }
         });


        listUsers();

        return view;
    }


    public void createDialogBox(String usernameS, String surnameS, String dateofbirthS, final int userid){

        final AlertDialog.Builder builder = new AlertDialog.Builder(Tab3Fragment.this.getContext());
        final View mView = getLayoutInflater().inflate(R.layout.createuser,null);
        final EditText username = mView.findViewById(R.id.usernameEditText);
        final EditText surname = mView.findViewById(R.id.surnameEditText);
        final EditText dateOfBirth = mView.findViewById(R.id.dateofbirthEditText);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        Button signupButton = mView.findViewById(R.id.signup);

        if(userid!=0){
            username.setText(usernameS);
            surname.setText(surnameS);
            dateOfBirth.setText(dateofbirthS);
        }

            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!username.getText().toString().isEmpty() && !dateOfBirth.getText().toString().isEmpty()) {

                        if(userid==0) {
                            createUser(username.getText().toString(), surname.getText().toString(), dateOfBirth.getText().toString(), userid);
                            databaseHelper.addUser(username.getText().toString(), surname.getText().toString(), dateOfBirth.getText().toString());
                        }
                        else{
                        databaseHelper.editUser(username.getText().toString(), surname.getText().toString(), dateOfBirth.getText().toString(),userid);
                        }

                        Log.i("User Signup", "Kaydedildi");
                        Toast.makeText(Tab3Fragment.this.getContext(), "Kişi kaydedildi", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                        linearLayout.removeAllViews();
                        listUsers();


                    } else {
                        Toast.makeText(Tab3Fragment.this.getView().getContext(), "Lütfen bilgileri giriniz", Toast.LENGTH_LONG).show();
                    }
                }
            });

        dialog.show();

    }

    public void listUsers(){

        String usernamelist [],surnamelist [],dateofbirthlist [];
        int userID [];
        users = databaseHelper.activeUsers();

        if(users.size()==0){//kitap listesi boþsa

        }else {
            usernamelist = new String[users.size()];
            surnamelist = new String[users.size()];
            dateofbirthlist = new String[users.size()];
            userID = new int[users.size()];
            for (int i = 0; i < users.size(); i++) {
                usernamelist[i] = users.get(i).get("username");
                surnamelist[i] = users.get(i).get("surname");
                dateofbirthlist[i] = users.get(i).get("dateofbirth");
                userID[i] = Integer.parseInt(users.get(i).get("id"));

                createUser(usernamelist[i],surnamelist[i],dateofbirthlist[i],userID[i]);
            }
        }
    }

    public void createUser(final String username, final String surname, final String dateofbirth, final int userid){

        final float scale = getResources().getDisplayMetrics().density;
        int dpWidthInPx  = (int) (30 * scale);
        int dpHeightInPx = (int) (30 * scale);

        final LinearLayout parent = new LinearLayout(view.getContext());

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams2.setMargins(20,40,10,20);
        parent.setLayoutParams(layoutParams2);

        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setPadding(20,50,20,50);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            parent.setBackground(getResources().getDrawable(R.drawable.imageviewborder));
        }

        linearLayout.addView(parent);
        ImageView iv = new ImageView(view.getContext());
        iv.setImageDrawable(getResources().getDrawable(R.drawable.userpassive));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx, 1.0f);

        layoutParams.setMargins(5,10,5,10);
        iv.setLayoutParams(layoutParams);

        ImageView iv2 = new ImageView(view.getContext());
        iv2.setImageDrawable(getResources().getDrawable(R.drawable.editicon));

        ImageView iv3 = new ImageView(view.getContext());
        iv3.setImageDrawable(getResources().getDrawable(R.drawable.deleteicon2));

        iv2.setLayoutParams(layoutParams);
        iv3.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams text = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

        TextView tv1 = new TextView(view.getContext());
        tv1.setText(username);
        tv1.setTextColor(Color.WHITE);
        tv1.setLayoutParams(text);
        tv1.setTextSize(15f);
        tv1.setPadding(10,20,10,20);

        TextView tv2 = new TextView(view.getContext());
        tv2.setText(surname);
        tv2.setLayoutParams(text);
        tv2.setTextColor(Color.WHITE);
        tv2.setTextSize(15f);
        tv2.setPadding(10,20,10,20);

        TextView tv3 = new TextView(view.getContext());
        tv3.setText(dateofbirth);
        tv3.setLayoutParams(text);
        tv3.setTextSize(15f);
        tv3.setTextColor(Color.WHITE);
        tv3.setPadding(10,20,10,20);

        parent.addView(iv);
        parent.addView(tv1);
        parent.addView(tv2);
        parent.addView(tv3);
        parent.addView(iv2);
        parent.addView(iv3);


        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createDialogBox(username, surname, dateofbirth,userid);

            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseHelper.deleteUser(userid);
                linearLayout.removeView(parent);

            }
        });

    }

}