package com.example.justi.gameapp_orthjm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
    }


    /*
    LEVEL LAYOUT INFO
    This is the level that will be loaded when the game activity starts
    0 = Blank Tile
    1 = X Tile
    2 = Blue Right Tile
    3 = Blue Left Tile
    4 = Blue Up Tile
    5 = Blue Down Tile
    6 = End Level Tile
     */
    public void goTo1_1(View view){
        int minSquares = 2;
        int[][] levelLay = new int[][]{
                {0,0,1,1,1,1},
                {0,6,1,1,1,1},
                {1,1,1,1,1,1},
                {1,1,1,1,1,1}
        };
        String name = "1-1";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo1_2(View view){
        int minSquares = 3;
        int[][] levelLay = new int[][]{
                {0,0,2,0,6,1},
                {1,1,1,1,1,1},
                {1,1,1,1,1,1},
                {1,1,1,1,1,1}
        };
        String name = "1-2";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo1_3(View view){
        int minSquares = 6;
        int[][] levelLay = new int[][]{
                {0,1,0,0,6,1},
                {0,0,0,1,1,1},
                {1,1,1,1,1,1},
                {1,1,1,1,1,1}
        };
        String name = "1-3";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo2_1(View view){
        int minSquares = 4;
        int[][] levelLay = new int[][]{
                {0,2,0,0,5,1},
                {0,2,0,0,0,1},
                {1,1,1,1,6,1},
                {1,1,1,1,1,1}
        };
        String name = "2-1";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo2_2(View view){
        int minSquares = 6;
        int[][] levelLay = new int[][]{
                {0,3,0,2,0,1},
                {0,2,0,3,0,1},
                {1,1,1,1,6,1},
                {1,1,1,1,1,1}
        };
        String name = "2-2";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo2_3(View view){
        int minSquares = 4;
        int[][] levelLay = new int[][]{
                {0,5,2,0,0,1},
                {0,2,4,0,0,1},
                {0,0,0,0,6,1},
                {1,1,1,1,1,1}
        };
        String name = "2-3";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo3_1(View view){
        int minSquares = 3;
        int[][] levelLay = new int[][]{
                {0,2,0,5,0,1},
                {0,0,5,0,0,1},
                {0,2,0,2,6,1},
                {1,1,1,1,1,1}
        };
        String name = "3-1";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo3_2(View view){
        int minSquares = 3;
        int[][] levelLay = new int[][]{
                {0,2,5,2,0,1},
                {0,2,5,4,5,1},
                {0,2,0,4,6,1},
                {1,1,1,1,1,1}
        };
        String name = "3-2";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo3_3(View view){
        int minSquares = 4;
        int[][] levelLay = new int[][]{
                {0,2,0,2,0,0},
                {2,0,2,0,2,0},
                {0,2,0,2,0,0},
                {2,0,2,0,2,6}
        };
        String name = "3-3";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo4_1(View view){
        int minSquares = 8;
        int[][] levelLay = new int[][]{
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,6}
        };
        String name = "4-1";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo4_2(View view){
        int minSquares = 3;
        int[][] levelLay = new int[][]{
                {0,2,2,5,0,0},
                {0,2,4,2,4,0},
                {0,2,2,5,1,5},
                {0,2,4,2,2,6}
        };
        String name = "4-2";
        loadLevel(levelLay, minSquares, name);
    }
    public void goTo4_3(View view){
        int minSquares = 3;
        int[][] levelLay = new int[][]{
                {0,2,5,2,0,5},
                {0,5,0,5,3,3},
                {0,2,4,5,0,5},
                {2,2,0,2,4,6}
        };
        String name = "4-3";
        loadLevel(levelLay, minSquares, name);
    }
    public void loadLevel(int[][] levelLayout, int minToComplete, String levelName){
        Intent gameScreen= new Intent(LevelSelect.this, Game.class);
        Bundle mBundle = new Bundle();
        for (int i = 0; i < levelLayout.length; i++){
            for (int j = 0; j < levelLayout[i].length; j++){
                mBundle.putInt("levelLay" + i + j, levelLayout[i][j]);
            }
        }
        mBundle.putInt("min", minToComplete);
        mBundle.putString("name", levelName);
        gameScreen.putExtras(mBundle);
        startActivity(gameScreen);
        finish();
    }

    public void returnToMenu(View view){
        Intent mainMenu= new Intent(LevelSelect.this, MainMenu.class);
        startActivity(mainMenu);
        finish();
    }
}
