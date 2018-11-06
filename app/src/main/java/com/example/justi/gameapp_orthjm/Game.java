package com.example.justi.gameapp_orthjm;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.DragEvent;
import android.graphics.Point;

import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.justi.gameapp_orthjm.LogIn.email;
import static com.example.justi.gameapp_orthjm.LogIn.gameInfo;
import static com.example.justi.gameapp_orthjm.LogIn.userName;

public class Game extends AppCompatActivity {

    //player circle
    ImageView player;

    //Views for all of the spaces
    Button[][] board = new Button[6][4];

    //submit button
    Button btn1;

    //dragable arrows
    Button rightArrow;
    Button leftArrow;
    Button upArrow;
    Button downArrow;

    //draglistener
    EndDrgLsntr endDrgLsntr;
    StartDrgLsntr startDrgLsntr;
    ClearClick clearClick;

    //Board information
    int[][] gamelayout = new int[4][6];
    //Minumum placed arrows to complete the level
    int minBoxesToComplete;
    String levelName;

    private SoundPool soundpool;
    private int soundDropSuccess, soundDropFail;

    int[] distanceCalc = new int[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        soundpool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);


        soundDropSuccess = soundpool.load(this,R.raw.dropsuccess,1);
        soundDropFail = soundpool.load(this,R.raw.dropfail,1);

        /*
        Get the layout of the level to load
         */
        for (int i = 0; i < gamelayout.length; i++){
            for (int j = 0; j < gamelayout[i].length; j++){
                gamelayout[i][j] = getIntent().getExtras().getInt("levelLay" + i + j);
            }
        }

        levelName = getIntent().getExtras().getString("name");
        minBoxesToComplete = getIntent().getExtras().getInt("min");

        startDrgLsntr=new StartDrgLsntr();
        endDrgLsntr=new EndDrgLsntr();
        clearClick=new ClearClick();

        player = (ImageView) findViewById(R.id.imageCircle);
        btn1 = (Button) findViewById(R.id.button);

        rightArrow = (Button) findViewById(R.id.rightArrowDragable);
        leftArrow = (Button) findViewById(R.id.leftArrowDragable);
        upArrow = (Button) findViewById(R.id.upArrowDragable);
        downArrow = (Button) findViewById(R.id.downArrowDragable);

        rightArrow.setOnLongClickListener(startDrgLsntr);
        leftArrow.setOnLongClickListener(startDrgLsntr);
        upArrow.setOnLongClickListener(startDrgLsntr);
        downArrow.setOnLongClickListener(startDrgLsntr);


        board[0][0] = (Button) findViewById(R.id.c1r1);
        board[1][0] = (Button) findViewById(R.id.c2r1);
        board[2][0] = (Button) findViewById(R.id.c3r1);
        board[3][0] = (Button) findViewById(R.id.c4r1);
        board[4][0] = (Button) findViewById(R.id.c5r1);
        board[5][0] = (Button) findViewById(R.id.c6r1);
        board[0][1] = (Button) findViewById(R.id.c1r2);
        board[1][1] = (Button) findViewById(R.id.c2r2);
        board[2][1] = (Button) findViewById(R.id.c3r2);
        board[3][1] = (Button) findViewById(R.id.c4r2);
        board[4][1] = (Button) findViewById(R.id.c5r2);
        board[5][1] = (Button) findViewById(R.id.c6r2);
        board[0][2] = (Button) findViewById(R.id.c1r3);
        board[1][2] = (Button) findViewById(R.id.c2r3);
        board[2][2] = (Button) findViewById(R.id.c3r3);
        board[3][2] = (Button) findViewById(R.id.c4r3);
        board[4][2] = (Button) findViewById(R.id.c5r3);
        board[5][2] = (Button) findViewById(R.id.c6r3);
        board[0][3] = (Button) findViewById(R.id.c1r4);
        board[1][3] = (Button) findViewById(R.id.c2r4);
        board[2][3] = (Button) findViewById(R.id.c3r4);
        board[3][3] = (Button) findViewById(R.id.c4r4);
        board[4][3] = (Button) findViewById(R.id.c5r4);
        board[5][3] = (Button) findViewById(R.id.c6r4);

        /*
        This draws the board based on the int layout
        NOTE: I messed my 2d arrays here and needed to turn the integer board sideways to keep everything working
        I found this out later into testing, since my test level looked and worked the same when flipped
         */
        for (int j = 0; j < board.length; j++){
            for (int i = 0; i < board[j].length; i++){
                if (gamelayout[i][j] == 1){
                    board[j][i].setBackground(this.getResources().getDrawable(R.drawable.ximage));
                } else if(gamelayout[i][j] == 2){
                    board[j][i].setBackground(this.getResources().getDrawable(R.drawable.arrowrightblue));
                } else if(gamelayout[i][j] == 3){
                    board[j][i].setBackground(this.getResources().getDrawable(R.drawable.arrowleftblue));
                } else if(gamelayout[i][j] == 4){
                    board[j][i].setBackground(this.getResources().getDrawable(R.drawable.arrowupblue));
                } else if(gamelayout[i][j] == 5){
                    board[j][i].setBackground(this.getResources().getDrawable(R.drawable.arrowdownblue));
                } else if(gamelayout[i][j] == 6){
                    board[j][i].setBackground(this.getResources().getDrawable(R.drawable.finish));
                }

                board[j][i].setOnDragListener(endDrgLsntr);
                board[j][i].setOnLongClickListener(clearClick);
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundpool.release();
        soundpool = null;
    }

    /*
        Checks and animates the current layout
        Known bug, the game currently does too much on falure and will sometimes lag and skip frames, making the first animation look off sometimes
         */
    public void trySolution(View view){
        int delayCounter = 0;
        int xPos = 0;
        int yPos = 0;
        int[] cellPosition = new int[2];
        boolean animContinue = true;
        int counter = 0;
        board[0][0].getLocationOnScreen(distanceCalc);
        boolean gameWon = false;
        int[] Startlocation = new int[2];
        player.getLocationOnScreen(Startlocation);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(player,"X", distanceCalc[0]);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        delayCounter += 1000;


        /*
        Animates the circle based off the current board layout
         */
        while (animContinue){
            if (xPos <= 5 && xPos >=0 && yPos >= 0 && yPos <=3) {
                if (board[xPos][yPos].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowrightgreen).getConstantState() ||
                        board[xPos][yPos].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowrightblue).getConstantState()) {
                    xPos++;
                    if (xPos <= 5) {
                        board[xPos][yPos].getLocationOnScreen(cellPosition);
                        animate("X", cellPosition[0], delayCounter);
                        delayCounter += 1000;
                    }
                } else if (board[xPos][yPos].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowleftgreen).getConstantState() ||
                        board[xPos][yPos].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowleftblue).getConstantState()) {
                    xPos--;
                    if (xPos >= 0) {
                        board[xPos][yPos].getLocationOnScreen(cellPosition);
                        animate("X", cellPosition[0], delayCounter);
                        delayCounter += 1000;
                    }
                } else if (board[xPos][yPos].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowdowngreen).getConstantState() ||
                        board[xPos][yPos].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowdownblue).getConstantState()) {
                    yPos++;
                    if (yPos <= 3) {
                        board[xPos][yPos].getLocationOnScreen(cellPosition);
                        animate("Y", cellPosition[1] - getStatusBarHeight(), delayCounter);
                        delayCounter += 1000;
                    }
                } else if (board[xPos][yPos].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowupgreen).getConstantState() ||
                        board[xPos][yPos].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowupblue).getConstantState()) {
                    yPos--;
                    if (yPos >= 0) {
                        board[xPos][yPos].getLocationOnScreen(cellPosition);
                        animate("Y", cellPosition[1] - getStatusBarHeight(), delayCounter);
                        delayCounter += 1000;
                    }
                } else if (board[xPos][yPos].getBackground().getConstantState() == getResources().getDrawable(R.drawable.finish).getConstantState()) {
                    gameWon = true;
                    //Wait for other animations to finish, then start with game win
                    ObjectAnimator endAnimation = ObjectAnimator.ofFloat(player, "X", cellPosition[0]);
                    endAnimation.setDuration(500);
                    endAnimation.setStartDelay(delayCounter);
                    endAnimation.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            gameWin();
                        }
                    });
                    endAnimation.start();

                }
            }
            //Check infinite loops and reset starting position
            if(counter == 22 || gameWon == true){
                animContinue = false;
                if (gameWon == false) {
                    animate("Y", Startlocation[1] - getStatusBarHeight(), delayCounter);
                    animate("X", Startlocation[0], delayCounter);
                }
            }
            counter++;
        }


    }


    /*
    Handles what happens when the level is won
    Saves performance of the player
     */
    private void gameWin(){
        int usedCounter = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowrightgreen).getConstantState() ||
                        board[i][j].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowleftgreen).getConstantState() ||
                        board[i][j].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowupgreen).getConstantState() ||
                        board[i][j].getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowdowngreen).getConstantState()
                        ){
                    if(usedCounter - minBoxesToComplete < 10) {
                        usedCounter++;
                    }
                }
            }
        }
        int score = 100 + ((minBoxesToComplete-usedCounter) * 10);
        String[] resultData = {email, Integer.toString(score), userName, levelName};
        gameInfo.add(resultData);
        saveArrayList(gameInfo, "gameData");

        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder.setMessage("You Win, you scored " + score + "/100 points.").setCancelable(false)
                .setPositiveButton("Back to Level Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gameScreen= new Intent(Game.this, LevelSelect.class);
                        startActivity(gameScreen);
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

        btn1.setText("Wait finished");
    }

    /*
    Animates the circle based on the parameters
     */
    private void animate(String XorY, int location, int delay){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(player, XorY, location);
        objectAnimator.setStartDelay(delay);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    private class StartDrgLsntr implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View view){

            WithShadow withShadow=new WithShadow(view);
            ClipData data= ClipData.newPlainText("","");
            view.startDrag(data,withShadow,view,0);

            return false;
        }


    }

    /*
    Clears the box if a player placed arrow is in it
     */
    private class ClearClick implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View view){
            if (view.getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowrightgreen).getConstantState() ||
                    view.getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowleftgreen).getConstantState() ||
                    view.getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowupgreen).getConstantState() ||
                    view.getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowdowngreen).getConstantState())
            {
                view.setBackgroundResource(R.drawable.blanktile);
                soundpool.play(soundDropFail,1,1,0,0,1);
            }

            return false;
        }


    }

    private class EndDrgLsntr implements View.OnDragListener{

        @Override
        public boolean onDrag(View view, DragEvent event) {
            if(event.getAction()==DragEvent.ACTION_DROP){
                if (view.getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowrightgreen).getConstantState() ||
                        view.getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowleftgreen).getConstantState() ||
                        view.getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowupgreen).getConstantState() ||
                        view.getBackground().getConstantState() == getResources().getDrawable(R.drawable.arrowdowngreen).getConstantState() ||
                        view.getBackground().getConstantState() == getResources().getDrawable(R.drawable.blanktile).getConstantState()) {
                    view.setBackground(((Button) event.getLocalState()).getBackground());
                    soundpool.play(soundDropSuccess,1,1,0,0,1);
                }else{
                    soundpool.play(soundDropFail,1,1,0,0,1);
                }
            }
            return true;
        }
    }

    private class WithShadow extends View.DragShadowBuilder{
        public WithShadow(View v){
            super(v);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
        }

    }

    /*
    Gets the height of the status bar, this is used for animation purposes
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void saveArrayList(ArrayList<String[]> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public void exitGame(View view){
        Intent gameScreen= new Intent(Game.this, LevelSelect.class);
        startActivity(gameScreen);
        finish();
    }

}
