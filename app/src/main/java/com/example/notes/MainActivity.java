package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity{
    static ArrayList<String> arr = new ArrayList<>();
    static ArrayAdapter<String> ad;
    static ArrayList<String> arr2 = new ArrayList<>();
    ListView l;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add:
                arr.add("Additional note");
                arr2.add("Please write here!!");
                ad.notifyDataSetChanged();
                Log.i("msg", "clicked");


            default:
                return false;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l = (ListView) findViewById(R.id.listview);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        try {
            arr = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("set1", ObjectSerializer.serialize(new ArrayList<>())));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            arr2 = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("set2", ObjectSerializer.serialize(new ArrayList<>())));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (arr == null && arr2 == null) {
            arr.add("example node");
            arr2.add("Please write here!");
        }
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);

        l.setAdapter(ad);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), text.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });
        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int set=i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("are you sure")
                        .setMessage("you want to delete this note")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arr.remove(set);
                                arr2.remove(set);
                                ad.notifyDataSetChanged();
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
                        })
                        .setNegativeButton("no",null).show();
                return true;
            }
        });






    }
}
