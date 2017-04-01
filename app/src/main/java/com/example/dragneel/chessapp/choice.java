package com.example.dragneel.chessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class choice extends AppCompatActivity implements View.OnClickListener {

    Button whitebutton, blackbutton;
    public boardact bobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        whitebutton = (Button) findViewById(R.id.whitebutton);
        blackbutton = (Button) findViewById(R.id.blackbutton);
        whitebutton.setOnClickListener(this);
        blackbutton.setOnClickListener(this);
        Intent in = getIntent();
        bobj = new boardact();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.whitebutton)
        {
            bobj.me = bobj.white; bobj.bot = bobj.black;
            Intent in = new Intent(this, boardact.class);
            startActivity(in);
        }
        else if(v.getId() == R.id.blackbutton)
        {
            bobj.me = bobj.black; bobj.bot = bobj.white;
            Intent in = new Intent(this,boardact.class);
            startActivity(in);
        }
    }
}
