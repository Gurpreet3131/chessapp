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
    int pawnmove[] = new int[64];
    int rookmove[] = new int[64];
    public static int white = 1, black = -1;
    public static int me = white, bot = black , empty = 0, select = 1;
    int turn = white, itemhold = 0, holdid=0, npassi = -1, npassj = -1, npassflag=0;
    int wkingmove = 0, bkingmove=0, bkingcheck=0, wkingcheck=0;
    public static int bking = -1, bqueen = -2, brook = -3, bknight = -4, bbishop = -5, bpawn = -6;
    public static int wking = 1, wqueen = 2, wrook = 3, wknight = 4, wbishop = 5, wpawn = 6;



    int n = 8;
     // king =1, queen = 2, roook = 3, knight =4, bishop =5, pawn= 6.


    public void initboard(int board[][])
    {
        //if(select == 1 || (select == 2 && me == white))
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
        /*else if(select == 2 && me == black)
        {
            board[0][0] = wrook; board[0][1] = wknight; board[0][2] = wbishop; board[0][3] = wking;
            board[0][4] = wqueen; board[0][5] = wbishop; board[0][6] = wknight; board[0][7] = wrook;
            board[1][0] = wpawn; board[1][1] = wpawn; board[1][2] = wpawn; board[1][3] = wpawn;
            board[1][4] = wpawn; board[1][5] = wpawn; board[1][6] = wpawn; board[1][7] = wpawn;

            board[7][0] = brook; board[7][1] = bknight; board[7][2] = bbishop; board[7][3] = bking;
            board[7][4] = bqueen; board[7][5] = bbishop; board[7][6] = bknight; board[7][7] = brook;
            board[6][0] = bpawn; board[6][1] = bpawn; board[6][2] = bpawn; board[6][3] = bpawn;
            board[6][4] = bpawn; board[6][5] = bpawn; board[6][6] = bpawn; board[6][7] = bpawn;
        }*/
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
        itemhold = 0; holdid= 0; turn = white; npassflag = 0;
        wkingmove = 0; bkingmove=0; // for castling
        bkingcheck = 0; wkingcheck = 0;

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
                if((i+j)%2 == 0) backiv[i][j].setBackgroundResource(R.drawable.wcell);
                else backiv[i][j].setBackgroundResource(R.drawable.bcell);
                frontiv[i][j].setId(n*i+j);
                seliv[i][j].setBackgroundResource(R.drawable.trans);
                pawnmove[n*i+j] = 0; rookmove[n*i+j] = 0;
                setfrontiv(i,j);
                innerframe[i][j].addView(backiv[i][j]);
                innerframe[i][j].addView(seliv[i][j]);
                innerframe[i][j].addView(frontiv[i][j]);
                //glayout.addView(innerframe[i][j]);


                frontiv[i][j].setOnClickListener(this);
            }
        }
        if(select == 2 && me == black)
        {
            for(int i=n-1; i>=0; i--)
            {
                for(int j=n-1; j>=0; j--)
                {
                    glayout.addView(innerframe[i][j]);
                }
            }
        }
        else
        {
            for(int i=0; i<n; i++)
            {
                for(int j=0; j<n; j++)
                {
                    glayout.addView(innerframe[i][j]);
                }
            }
        }
        // if the bot has the first turn, then perform its action here

    }
    // set image on front
    public void setfrontiv(int i, int j)
    {
        if(board[i][j] == 0) frontiv[i][j].setBackgroundResource(R.drawable.trans);
        else if(board[i][j] == 1) frontiv[i][j].setBackgroundResource(R.drawable.wking);
        else if(board[i][j] == 2) frontiv[i][j].setBackgroundResource(R.drawable.wqueen);
        else if(board[i][j] == 3)
        {
            frontiv[i][j].setBackgroundResource(R.drawable.wrook);
            rookmove[n*i+j] = 1;
        }
        else if(board[i][j] == 4) frontiv[i][j].setBackgroundResource(R.drawable.wknight);
        else if(board[i][j] == 5) frontiv[i][j].setBackgroundResource(R.drawable.wbishop);
        else if(board[i][j] == 6)
        {
            frontiv[i][j].setBackgroundResource(R.drawable.wpawn);
            pawnmove[n*i+j] = 1;
        }

        else if(board[i][j] == -1) frontiv[i][j].setBackgroundResource(R.drawable.bking);
        else if(board[i][j] == -2) frontiv[i][j].setBackgroundResource(R.drawable.bqueen);
        else if(board[i][j] == -3)
        {
            frontiv[i][j].setBackgroundResource(R.drawable.brook);
            rookmove[n*i+j] = -1;
        }
        else if(board[i][j] == -4) frontiv[i][j].setBackgroundResource(R.drawable.bknight);
        else if(board[i][j] == -5) frontiv[i][j].setBackgroundResource(R.drawable.bbishop);
        else if(board[i][j] == -6)
        {
            frontiv[i][j].setBackgroundResource(R.drawable.bpawn);
            pawnmove[n*i+j] = -1;
        }
    }

    int sign(int val)
    {
        if(val > 0) return 1;
        else if(val<0) return -1;
        else return 0;
    }

    void moveknight(int i1, int j1, int i2, int j2, int val) // val is the piece value
    {
        int disti = Math.abs(i1-i2), distj = Math.abs(j1-j2);
        if( Math.max(disti,distj) == 2 && Math.min(disti,distj) == 1)
        {
            // move is possible, make the move
            board[i1][j1] = 0; board[i2][j2] = wknight*turn;
            frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
            if(val<0) frontiv[i2][j2].setBackgroundResource(R.drawable.bknight);
            else frontiv[i2][j2].setBackgroundResource(R.drawable.wknight);
            seliv[i1][j1].setBackgroundResource(R.drawable.trans);
            seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
            holdid = n*i2+j2;
            turn*=-1; npassflag = 0;
        }
    }

    void movebishop(int i1, int j1, int i2, int j2, int val)
    {
        int difi = sign(i2-i1), difj = sign(j2-j1);
        if(Math.abs(difi) == Math.abs(difj))
        {
            int tempi = i1 + difi, tempj = j1 + difj;
            while( (tempi>=0 && tempi<n) && (tempj>=0 && tempj<n))
            {
                if(tempi == i2 && tempj == j2)
                {
                    // move the bishop
                    board[i1][j1] = 0; board[i2][j2] = wbishop*turn;
                    frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
                    if(val<0) frontiv[i2][j2].setBackgroundResource(R.drawable.bbishop);
                    else frontiv[i2][j2].setBackgroundResource(R.drawable.wbishop);
                    seliv[i1][j1].setBackgroundResource(R.drawable.trans);
                    seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
                    holdid = n*i2+j2;
                    turn*=-1; npassflag = 0;

                }
                else if(board[tempi][tempj] !=0) break;
                tempi += difi; tempj += difj;
            }
        }


    }

    void movequeen(int i1, int j1, int i2, int j2, int val)
    {
        int difi = sign(i2-i1), difj = sign(j2-j1);
        if( (Math.abs(difi) == Math.abs(difj)) || (Math.min(Math.abs(difi), Math.abs(difj)) == 0) )
        {
            int tempi = i1 + difi, tempj = j1 + difj;
            while( (tempi>=0 && tempi<n) && (tempj>=0 && tempj<n))
            {
                if(tempi == i2 && tempj == j2)
                {
                    // move the queen
                    board[i1][j1] = 0; board[i2][j2] = wqueen*turn;
                    frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
                    if(val<0) frontiv[i2][j2].setBackgroundResource(R.drawable.bqueen);
                    else frontiv[i2][j2].setBackgroundResource(R.drawable.wqueen);
                    seliv[i1][j1].setBackgroundResource(R.drawable.trans);
                    seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
                    holdid = n*i2+j2;
                    turn*=-1; npassflag = 0;

                }
                else if(board[tempi][tempj] !=0) break;
                tempi += difi; tempj += difj;
            }
        }
    }


    public void movepawn(int i1, int i2, int j1, int j2, int val)
    {
        int flag = 0;
        if(turn > 0) // white's move
        {
            if(i1 - i2 == 2 && j1==j2)
            {
                if(pawnmove[i1*n+j1] == turn && board[i1-1][j1] == 0 && board[i1-2][j1] == 0)
                {
                    flag = 3; npassflag = 1; npassi = i2; npassj = j2;
                }
                    // 2 step move is possible;
            }
            else if((i1-i2) == 1)         // moving one step
            {
                // straight move
                if(j1 == j2 && board[i2][j2] == 0) flag = 1;
                else if(j1-j2 == 1 & sign(board[i2][j2]) == sign(-turn)) flag = 1; // move upleft
                else if(j2-j1 == 1 & sign(board[i2][j2]) == sign(-turn)) flag = 1; // move upright
                else if(npassflag == 1)
                {
                    if(j1-j2 == 1 & board[i2][j2] == 0 & ( npassi == i1 && npassj == j1-1) ) flag = 2;
                    else if(j2-j1 == 1 & board[i2][j2] == 0 & ( npassi == i1 && npassj == j1+1) ) flag = 2;
                }
            }
        }
        else if(turn < 0) // black's move
        {
            if(i2 - i1 == 2 && j1==j2)
            {
                if(pawnmove[i1*n+j1] == turn && board[i1+1][j1] == 0 && board[i1+2][j1] == 0)
                {
                    flag = 3; npassflag = 1; npassi = i2; npassj = j2;
                }
                    // 2 step move is possible;
            }
            else if((i2-i1) == 1)         // moving one step
            {
                if(j1 == j2 && board[i2][j2] == 0) flag = 1; // straight move
                else if(j1-j2 == 1 & sign(board[i2][j2]) == sign(-turn)) flag = 1; // move downleft
                else if(j2-j1 == 1 & sign(board[i2][j2]) == sign(-turn)) flag = 1; // move downright
                else if(npassflag == 1)
                {
                    if(j1-j2 == 1 & board[i2][j2] == 0 & ( npassi == i1 && npassj == j1-1) ) flag = 2;
                    else if(j2-j1 == 1 & board[i2][j2] == 0 & ( npassi == i1 && npassj == j1+1) ) flag = 2;
                }
            }
        }

        if(flag == 1 || flag == 3)
        {
            board[i1][j1] = 0; board[i2][j2] = wpawn*turn;
            frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
            if(val<0) frontiv[i2][j2].setBackgroundResource(R.drawable.bpawn);
            else frontiv[i2][j2].setBackgroundResource(R.drawable.wpawn);
            seliv[i1][j1].setBackgroundResource(R.drawable.trans);
            seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
            holdid = n*i2+j2;
            turn*=-1; npassflag = 0;
            if(flag == 3) npassflag = 1; // double move
        }
        else if(flag == 2) // npassent rule move
        {
            board[i1][j1] = 0; board[i2][j2] = wpawn*turn; board[i1][j2] = 0;
            frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
            frontiv[i1][j2].setBackgroundResource(R.drawable.trans);
            if(val<0) frontiv[i2][j2].setBackgroundResource(R.drawable.bpawn);
            else frontiv[i2][j2].setBackgroundResource(R.drawable.wpawn);
            seliv[i1][j1].setBackgroundResource(R.drawable.trans);
            seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
            holdid = n*i2+j2;
            turn*=-1; npassflag = 0;
        }
    }

    public void moverook(int i1, int j1, int i2, int j2, int val)
    {
        int difi = sign(i2-i1), difj = sign(j2-j1);
        if( (Math.min(Math.abs(difi), Math.abs(difj)) == 0) )
        {
            int tempi = i1 + difi, tempj = j1 + difj;
            while( (tempi>=0 && tempi<n) && (tempj>=0 && tempj<n))
            {
                if(tempi == i2 && tempj == j2)
                {
                    // move the rook
                    board[i1][j1] = 0; board[i2][j2] = wrook*turn;
                    frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
                    if(val<0) frontiv[i2][j2].setBackgroundResource(R.drawable.brook);
                    else frontiv[i2][j2].setBackgroundResource(R.drawable.wrook);
                    seliv[i1][j1].setBackgroundResource(R.drawable.trans);
                    seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
                    holdid = n*i2+j2; rookmove[n*i1+j1] = 0;
                    turn*=-1; npassflag = 0;
                }
                else if(board[tempi][tempj] !=0) break;
                tempi += difi; tempj += difj;
            }
        }
    }

    public void moveking(int i1, int j1, int i2, int j2, int val)
    {
        int difi = Math.abs(i2-i1), difj = Math.abs(j2-j1);
        if(Math.max(difi,difj) == 1)
        {
            // move the king
            board[i1][j1] = 0; board[i2][j2] = wking*turn;
            frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
            if(val<0) frontiv[i2][j2].setBackgroundResource(R.drawable.bking);
            else frontiv[i2][j2].setBackgroundResource(R.drawable.wking);
            seliv[i1][j1].setBackgroundResource(R.drawable.trans);
            seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
            holdid = n*i2+j2;
            if(turn > 0) wkingmove = 1;
            else bkingmove = 1;
            turn*=-1; npassflag = 0;

        }
        else if(turn > 0 && wkingmove == 0)
        {
            if(i2 == i1 && j1 - j2 == 2) //left castling
            {
                 if(board[i1][j1-1] == 0 && board[i1][j1-3] == 0 && board[i2][j2] == 0
                         && board[i2][j2-2] == wrook*turn && rookmove[i2*n+(j2-2)] == 1*turn)
                 {
                     // perform left castling
                     board[i1][j1] = 0; board[i2][j2] = wking*turn;
                     board[i2][j2+1] = wrook*turn; board[i2][j2-2] = 0;

                     frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
                     if(val<0) frontiv[i2][j2].setBackgroundResource(R.drawable.bking);
                     else frontiv[i2][j2].setBackgroundResource(R.drawable.wking);
                     frontiv[i2][j2+1].setBackgroundResource(R.drawable.wrook);
                     frontiv[i2][j2-2].setBackgroundResource(R.drawable.trans);

                     seliv[i1][j1].setBackgroundResource(R.drawable.trans);
                     seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
                     holdid = n*i2+j2; rookmove[n*i2+(j2-2)] = 0;
                     if(turn > 0) wkingmove = 1;
                     else bkingmove = 1;
                     turn*=-1; npassflag = 0;
                 }
            }
            else if(i2 == i1 && j2-j1 == 2) // right castling
            {
                if(board[i1][j1+1] == 0 && board[i2][j2] == 0
                        && board[i2][j2+1] == wrook*turn && rookmove[i2*n+(j2+1)] == 1*turn)
                {
                    // perform left castling
                    board[i1][j1] = 0; board[i2][j2] = wking*turn;
                    board[i2][j2-1] = wrook*turn; board[i2][j2+1] = 0;

                    frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
                    if(val<0) frontiv[i2][j2].setBackgroundResource(R.drawable.bking);
                    else frontiv[i2][j2].setBackgroundResource(R.drawable.wking);
                    frontiv[i2][j2-1].setBackgroundResource(R.drawable.wrook);
                    frontiv[i2][j2+1].setBackgroundResource(R.drawable.trans);

                    seliv[i1][j1].setBackgroundResource(R.drawable.trans);
                    seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
                    holdid = n*i2+j2; rookmove[n*i2+(j2+1)] = 0;
                    if(turn > 0) wkingmove = 1;
                    else bkingmove = 1;
                    turn*=-1; npassflag = 0;
                }
            }
        }
        else if(turn < 0 && bkingmove == 0)
        {
            if (i2 == i1 && j1 - j2 == 2) //left castling
            {
                if (board[i1][j1 - 1] == 0 && board[i1][j1 - 3] == 0 && board[i2][j2] == 0
                        && board[i2][j2 - 2] == wrook * turn && rookmove[i2 * n + (j2 - 2)] == 1*turn) {
                    // perform left castling
                    board[i1][j1] = 0; board[i2][j2] = wking * turn;
                    board[i2][j2 + 1] = wrook * turn; board[i2][j2 - 2] = 0;

                    frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
                    if (val < 0) frontiv[i2][j2].setBackgroundResource(R.drawable.bking);
                    else frontiv[i2][j2].setBackgroundResource(R.drawable.wking);
                    frontiv[i2][j2 + 1].setBackgroundResource(R.drawable.brook);
                    frontiv[i2][j2 - 2].setBackgroundResource(R.drawable.trans);

                    seliv[i1][j1].setBackgroundResource(R.drawable.trans);
                    seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
                    holdid = n * i2 + j2; rookmove[n * i2 + (j2 - 2)] = 0;
                    if (turn > 0) wkingmove = 1;
                    else bkingmove = 1;
                    turn *= -1; npassflag = 0;
                }
            }
            else if (i2 == i1 && j2 - j1 == 2) // right castling
            {
                if (board[i1][j1 + 1] == 0 && board[i2][j2] == 0
                        && board[i2][j2 + 1] == wrook * turn && rookmove[i2 * n + (j2 + 1)] == 1*turn) {
                    // perform left castling
                    board[i1][j1] = 0; board[i2][j2] = wking * turn;
                    board[i2][j2 - 1] = wrook * turn; board[i2][j2 + 1] = 0;

                    frontiv[i1][j1].setBackgroundResource(R.drawable.trans);
                    if (val < 0) frontiv[i2][j2].setBackgroundResource(R.drawable.bking);
                    else frontiv[i2][j2].setBackgroundResource(R.drawable.wking);
                    frontiv[i2][j2 - 1].setBackgroundResource(R.drawable.brook);
                    frontiv[i2][j2 + 1].setBackgroundResource(R.drawable.trans);

                    seliv[i1][j1].setBackgroundResource(R.drawable.trans);
                    seliv[i2][j2].setBackgroundResource(R.drawable.downsel);
                    holdid = n * i2 + j2; rookmove[n * i2 + (j2 + 1)] = 0;
                    if (turn > 0) wkingmove = 1;
                    else bkingmove = 1;
                    turn *= -1; npassflag = 0;
                }
            }
        }
    }

    void playmove(int i1, int j1, int i2, int j2)
    {
        int val = board[i1][j1];
        if(val == wknight*turn) moveknight(i1,j1,i2,j2,val);
        else if(val == wbishop*turn) movebishop(i1,j1,i2,j2,val);
        else if(val == wqueen*turn) movequeen(i1,j1,i2,j2,val);
        else if(val == wpawn*turn) movepawn(i1,i2,j1,j2,val);
        else if(val == wrook*turn) moverook(i1,j1,i2,j2,val);
        else if(val == wking*turn) moveking(i1,j1,i2,j2,val);
        // remember to make npassflag zero in all moves
    }

    public int checkdir(int turn, int b[][], int tempi, int tempj, int inci, int incj)
    {
        int checkresult = 0; int ss = sign(-turn);
        tempi += inci; tempj += incj;
        while((tempi>=0 && tempi<n) && (tempj>=0 && tempj<n))
        {
            if( sign(b[tempi][tempj]) == sign(turn)) break;
            else if(b[tempi][tempj] == 0) { tempi+= inci; tempj+=incj; }
            else if(sign(b[tempi][tempj]) == sign(-turn))
            {
                if(b[tempi][tempj] == wqueen*ss) {checkresult = 1; break; }
                if(b[tempi][tempj] == wbishop*ss)
                {
                    checkresult = 1; break;
                }
                if(b[tempi][tempj] == 1) {checkresult = 1; break; }
                if(b[tempi][tempj] == wqueen*ss) {checkresult = 1; break; }
                if(b[tempi][tempj] == wqueen*ss) {checkresult = 1; break; }
                if(b[tempi][tempj] == wqueen*ss) {checkresult = 1; break; }

            }
        }

        return checkresult;
    }

    public int findcheck(int turn, int b[][])
    {
        int found = 0;
        int bki = 0, bkj = 0, wki = 0, wkj = 0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(b[i][j] == wking) { wki = i; wkj = j; }
                else if(b[i][j] == bking) { bki = i; bkj = j; }
            }
        }
        // check for white
        if(turn > 0)
        {
            int inci = -1, incj = -1;
            // upleft
            checkdir(turn, b, wki, wkj, inci, incj);

        }
        else if(turn < 0)
        {

        }
        return found;
    }


    @Override
    public void onClick(View v)
    {
        ImageView cur = (ImageView) v;
        int id = cur.getId();
        int curi = id/n, curj = id%n;
        if(turn == me)
        {
            if(itemhold == 0)
            {
                if( sign(board[curi][curj]) == sign(turn))
                {
                    itemhold = 1; holdid = id;
                    seliv[curi][curj].setBackgroundResource(R.drawable.downsel);
                }
            }
            else
            {
                int holdi = holdid/n, holdj = holdid%n;
                if(sign(board[curi][curj]) == sign(turn))
                {
                    itemhold = 1;
                    holdid = id;
                    seliv[holdi][holdj].setBackgroundResource(R.drawable.trans);
                    seliv[curi][curj].setBackgroundResource(R.drawable.downsel);
                }
                else
                {
                    // make the move if possible
                    playmove(holdi, holdj,curi,curj);
                    // find if the king is under check
                    int ischeck = findcheck(turn, board);
                }
            }
        }
        else // turn == bot
        {

            if(itemhold == 0)
            {
                if( sign(board[curi][curj]) == sign(turn))
                {
                    itemhold = 1; holdid = id;
                    seliv[curi][curj].setBackgroundResource(R.drawable.downsel);
                }
            }
            else
            {
                int holdi = holdid/n, holdj = holdid%n;
                if(sign(board[curi][curj]) == sign(turn))
                {
                    itemhold = 1;
                    holdid = id;
                    seliv[holdi][holdj].setBackgroundResource(R.drawable.trans);
                    seliv[curi][curj].setBackgroundResource(R.drawable.downsel);
                }
                else
                {
                    // make the move if possible
                    playmove(holdi, holdj,curi,curj);
                    int ischeck = findcheck(turn, board);
                }
            }
        }
    }
}
