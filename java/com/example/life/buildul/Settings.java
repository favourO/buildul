package com.example.life.buildul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Settings extends AppCompatActivity {
    private ListView listSettings;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listSettings = (ListView) findViewById(R.id.list_settings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.settings)
        );

        listSettings.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listSettings.setAdapter(adapter);

        listSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent one = new Intent(Settings.this, Language.class);
                        startActivity(one);
                        break;
                    case 1:
                        Intent two = new Intent(Settings.this, AboutUs.class);
                        startActivity(two);
                        break;
                    case 2:
                        Intent three = new Intent(Settings.this, Settings.class);
                        startActivity(three);
                        break;
                    case 3:
                        Intent four = new Intent(Settings.this, Help.class);
                        startActivity(four);
                        break;
                    case 4:
                        Intent five = new Intent(Settings.this, PrivacyPolicy.class);
                        startActivity(five);
                        break;
                        default:


                }
            }
        });

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
}
