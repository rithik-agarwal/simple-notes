package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class text extends AppCompatActivity {
    int set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        EditText e = (EditText) findViewById(R.id.editText2);
        Intent intent = getIntent();
        int i = intent.getIntExtra("index", -1);
        if (i == -1) {

        } else {
             set=i;
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh-mm");
             MainActivity.arr.set(i,sdf.format(new Date())+" "+ set);
            e.setText(MainActivity.arr2.get(i));
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    MainActivity.arr2.set(set,String.valueOf(charSequence));
                    MainActivity.ad.notifyDataSetChanged();
                    SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                   try{sharedPreferences.edit().putString("set1",ObjectSerializer.serialize(MainActivity.arr)).apply();}
                   catch(Exception e)
                   {
                       e.printStackTrace();
                   }
                    try{sharedPreferences.edit().putString("set2",ObjectSerializer.serialize(MainActivity.arr2)).apply();}
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }



                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }
    }
}
