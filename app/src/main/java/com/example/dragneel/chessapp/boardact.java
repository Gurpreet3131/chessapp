package com.example.dragneel.chessapp;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class boardact extends AppCompatActivity implements View.OnClickListener{

    public static GridLayout glayout;
    public static FrameLayout innerframe[][] = new FrameLayout[8][8];
    int board[][] = new int[8][8];
    ImageView seliv[][] = new ImageView[8][8];
    ImageView backiv[][] = new ImageView[8][8];
    ImageView frontiv[][] = new ImageView[8][8];
    public static int white = 1, black = -1;
    public static int me = white, bot = black , empty = 0, select = 1;
    int turn = white, itemhold = 0, holdid=0;
    public static int bking = -1, bqueen = -2, brook = -3, bknight = -4, bbishop = -5, bpawn = -6;
    public static int wking = 1, wqueen = 2, wrook = 3, wknight = 4, wbishop = 5, wpawn = 6;



    int n = 8;
     // king =1, queen = 2, roook = 3, knight =4, bishop =5, pawn= 6.


    public void initboard(int board[][])
    {
        board[0][0] = brook; board[0][1] = bknight; board[0][2] = bbishop; board[0][3] = bqueen;
        board[0][4] = bking; board[0][5] = bbishop; board[0][6] = bknight; board[0][7] = brook;
        board[1][0] = bpawn; board[1][1] = bpawn; board[1][2] = bpawn; board[1][3] = bpawn;
        board[1][4] = bpawn; board[1][5] = bpawn; board[1][6] = bpawn; board[1][7] = bpawn;

        board[7][0] = wrook; board[7][1] = wknight; board[7][2] = wbishop; board[7][3] = wqueen;
        board[7][4] = wking; board[7][5] = wbishop; board[7][6] = wknight; board[7][7] = wrook;
        board[6][0] = wpawn; board[6][1] = wpawn; board[6][2] = wpawn; board[6][3] = wpawn;
        board[6][4] = wpawn; board[6][5] = wpawn; board[6][6] = wpawn; board[6][7] = wpawn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardact);

        Intent in = getIntent();
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                board[i][j] = 0;
            }
        }
        initboard(board);
        itemhold = 0; holdid= 0;

        //getting the screen size in pixel
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y; //
        int min = Math.min(width,height);

        glayout = (GridLayout) findViewById(R.id.gridlayoutid);
        //innerframe = (FrameLayout) findViewById(R.id.innerframe);

        glayout.setRowCount(n);
        glayout.setColumnCount(n);

        //System.out.println(width+ " " + height);

        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                innerframe[i][j] = new FrameLayout(this);
                backiv[i][j] = new ImageView(this);
                frontiv[i][j] = new ImageView(this);
                seliv[i][j] = new ImageView(this);
                backiv[i][j].setLayoutParams(new LinearLayout.LayoutParams(min/9, min/9));
                frontiv[i][j].setLayoutParams(new LinearLayout.LayoutParams(min/9, min/9));
                seliv[i][j].setLayoutParams(new LinearLayout.LayoutParams(min/9, min/9));
                if((i+j)%2 == 0) backiv[i][j].setBackgroundResource(R.drawable.bcell);
                else backiv[i][j].setBackgroundResource(R.drawable.wcell);
                frontiv[i][j].setId(n*i+j);
                seliv[i][j].setBackgroundResource(R.drawable.trans);
                setfrontiv(i,j);
                innerframe[i][j].addView(backiv[i][j]);
                innerframe[i][j].addView(seliv[i][j]);
                innerframe[i][j].addView(frontiv[i][j]);
                glayout.addView(innerframe[i][j]);
                frontiv[i][j].setOnClickListener(this);
            }
        }
    }
    // set image on front
    public void setfrontiv(int i, int j)
    {
        if(board[i][j] == 0) frontiv[i][j].setBackgroundResource(R.drawable.trans);
        else if(board[i][j] == 1) frontiv[i][j].setBackgroundResource(R.drawable.wking);
        else if(board[i][j] == 2) frontiv[i][j].setBackgroundResource(R.drawable.wqueen);
        else if(board[i][j] == 3) frontiv[i][j].setBackgroundResource(R.drawable.wrook);
        else if(board[i][j] == 4) frontiv[i][j].setBackgroundResource(R.drawable.wknight);
        else if(board[i][j] == 5) frontiv[i][j].setBackgroundResource(R.drawable.wbishop);
        else if(board[i][j] == 6) frontiv[i][j].setBackgroundResource(R.drawable.wpawn);

        else if(board[i][j] == -1) frontiv[i][j].setBackgroundResource(R.drawable.bking);
        else if(board[i][j] == -2) frontiv[i][j].setBackgroundResource(R.drawable.bqueen);
        else if(board[i][j] == -3) frontiv[i][j].setBackgroundResource(R.drawable.brook);
        else if(board[i][j] == -4) frontiv[i][j].setBackgroundResource(R.drawable.bknight);
        else if(board[i][j] == -5) frontiv[i][j].setBackgroundResource(R.drawable.bbishop);
        else if(board[i][j] == -6) frontiv[i][j].setBackgroundResource(R.drawable.bpawn);

    }

    @Override
    public void onClick(View v)
    {
        ImageView cur = (ImageView) v;
        int id = cur.getId();
        int curi = id/n, curj = id%n;
        if(turn == me)
        {

        }
        else // turn == bot
        {

        }

    }
}
