package com.example.justi.gameapp_orthjm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.justi.gameapp_orthjm.LogIn.email;
import static com.example.justi.gameapp_orthjm.LogIn.gameInfo;
import static com.example.justi.gameapp_orthjm.LogIn.userIsParent;

public class Results extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ArrayList<String> resultInfo = new ArrayList<String>();
        for (int i = 0; i < gameInfo.size(); i++){
            //check if user is parent, then display scores of everyone if they are, display scores of just the user if they aren't
            if(userIsParent) {
                resultInfo.add("User: " + gameInfo.get(i)[2] + " Level: " + gameInfo.get(i)[3] + " Score: " + gameInfo.get(i)[1]);
            }else{
                if (gameInfo.get(i)[0].equals(email)) {
                    resultInfo.add("Level: "+ gameInfo.get(i)[3] + " Score: " + gameInfo.get(i)[1]);
                }
            }
        }
        String[] resultString = resultInfo.toArray(new String[resultInfo.size()]);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.results_listview, resultString);
        ListView scoreListView = (ListView)findViewById(R.id.ScoreList);
        scoreListView.setAdapter(adapter);
    }
}
