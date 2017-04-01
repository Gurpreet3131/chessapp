package com.example.dragneel.chessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button playbutton, botplaybutton, settingbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playbutton = (Button) findViewById(R.id.playbutton);
        playbutton.setOnClickListener(this);

        botplaybutton = (Button) findViewById(R.id.botplaybutton);
        botplaybutton.setOnClickListener(this);

        settingbutton = (Button) findViewById(R.id.settingbutton);
        settingbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.playbutton)
        {
            Intent in = new Intent(this, boardact.class);
            startActivity(in);
        }
        else if(view.getId() == R.id.botplaybutton)
        {
            Intent in = new Intent(this, boardact.class);
            startActivity(in);
        }
        else if(view.getId() == R.id.settingbutton)
        {

        }
    }
}
