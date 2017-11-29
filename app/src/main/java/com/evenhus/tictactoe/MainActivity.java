package com.evenhus.tictactoe;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    //0 = yellow, 1 = red
    int activePlayer = 0;
    //2 means unplayed
    int[] gamestate = {2,2,2,2,2,2,2,2,2};
    boolean gameIsActive = true;

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gamestate[tappedCounter] == 2 && gameIsActive) {

            gamestate[tappedCounter] = activePlayer;

            if (activePlayer == 0) {
                counter.setTranslationY(-1000f);
                counter.setImageResource(R.drawable.yellow);
                counter.animate().translationYBy(1000f).rotation(720).setDuration(500);
                activePlayer = 1;
            } else {
                counter.setTranslationX(-1000f);
                counter.setImageResource(R.drawable.red);
                counter.animate().translationXBy(1000f).rotation(720).setDuration(500);
                activePlayer = 0;
            }

            for (int[] winningPosition : winningPositions) {
                if (gamestate[winningPosition[0]] == gamestate[winningPosition[1]] &&
                        gamestate[winningPosition[1]] == gamestate[winningPosition[2]] && gamestate[winningPosition[0]] != 2) {

                    gameIsActive = false;

                    String winner = "Red";

                    if (gamestate[winningPosition[0]] == 0) {
                        winner = "Yellow";
                    }

                    TextView winnerMessage = (TextView)findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");

                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                } else {

                    boolean gameIsOver = true;

                    for (int state : gamestate) {

                        if(state == 2){
                            gameIsOver = false;
                        }
                    }

                    if (gameIsOver) {
                        gameIsActive = false;
                        TextView draw = (TextView)findViewById(R.id.winnerMessage);
                        draw.setText("The game is a draw, play again?");
                        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        gameIsActive = true;
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;
        for (int i = 0; i < gamestate.length; i++) {
            gamestate[i] = 2;
        }

        GridLayout gridlayout = (GridLayout)findViewById(R.id.gridLayout);

        for(int i = 0; i < gridlayout.getChildCount(); i++) {
            ((ImageView) gridlayout.getChildAt(i)).setImageResource(0);
        }

    }
}
