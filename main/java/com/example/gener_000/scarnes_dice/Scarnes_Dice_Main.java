package com.example.gener_000.scarnes_dice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import android.os.Handler;

public class Scarnes_Dice_Main extends AppCompatActivity {

    private int overallScore;
    private int turnScore;
    private int compOverallScore;
    private int compTurnScore;
    Button holdButton;
    Button rollButton;
    Button resetButton;
    TextView scoreStatusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scarnes__dice__main);
        scoreStatusText = (TextView) findViewById(R.id.score_status_text);
        rollButton = (Button) findViewById(R.id.roll_button);
        rollButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rollDice(1);
            }
        });

        resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                overallScore = 0;
                turnScore = 0;
                compOverallScore = 0;
                compTurnScore = 0;
                rollButton.setEnabled(true);
                holdButton.setEnabled(true);
                TextView scoreStatusText = (TextView) findViewById(R.id.score_status_text);
                scoreStatusText.setText("Your score:0 Computer score:0");
            }
        });

        holdButton = (Button) findViewById(R.id.hold_button);
        holdButton.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                overallScore += turnScore;
                turnScore = 0;
                computerTurn();
            }
        });
    }

    protected int rollDice(int player){
        int [] dieFaces = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
        Random rng = new Random();
        int randomNumber = rng.nextInt(6);
        Log.i("Error","Error");
        ImageView die = (ImageView) findViewById(R.id.imageView);
        die.setImageResource(dieFaces[randomNumber]);

        if (player == 1) {
            if (randomNumber == 0) {
                turnScore = 0;
                scoreStatusText.setText("Your score:" + overallScore + " Computer score:" + compOverallScore + " You rolled a 1!");
                computerTurn();

            } else {
                turnScore += (randomNumber + 1);
                scoreStatusText.setText("Your score:" + overallScore + " Computer score:" + compOverallScore + " your turn score:" + turnScore);

            }
        }
        if (player == 2){
            if (randomNumber == 0) {
                compTurnScore = 0;
                scoreStatusText.setText("Your score:" + overallScore + " Computer score:" + compOverallScore + " computer turn score:" + compTurnScore);

            } else {
                compTurnScore += (randomNumber + 1);
                scoreStatusText.setText("Your score:" + overallScore + " Computer score:" + compOverallScore + "Computer turn score:" + compTurnScore);
            }
        }

        if ( compOverallScore + compTurnScore >= 100 || overallScore + turnScore >= 100){
            overallScore = 0;
            turnScore = 0;
            compOverallScore = 0;
            compTurnScore = 0;

            if (player == 1){
                scoreStatusText.setText("You win!");
            }
            else{
                scoreStatusText.setText("You lost!");
                rollButton.setEnabled(true);
                holdButton.setEnabled(true);
            }
        }
        return randomNumber;
    }

    protected void computerTurn(){

        final Handler timer = new Handler();
        final Runnable timerRunnable = new Runnable(){
            @Override
            public void run(){

                rollButton.setEnabled(false);
                holdButton.setEnabled(false);
                int compRoll = rollDice(2);
                compRoll += 1;
                if(compRoll != 1 && compTurnScore < 20) {
                    scoreStatusText.setText("Your score:" + overallScore + " Computer score:" + compOverallScore + "Computer turn score:" + compTurnScore);
                    timer.postDelayed(this, 1000);
                }
                if (compTurnScore >= 20){
                    compOverallScore += compTurnScore;
                    scoreStatusText.setText("Your score:" + overallScore + " Computer score:" + compOverallScore);
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                }
                if (compRoll == 1){
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                    scoreStatusText.setText("Your score:" + overallScore + " Computer score:" + compOverallScore + " Computer rolled a 1!");
                }
            }
        };
        timerRunnable.run();
    }
}
