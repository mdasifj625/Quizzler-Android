package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare member variables here:
        Button mTrueBtn;
        Button mFalseBtn;
        TextView qTextView;
        TextView mScoreView;
        ProgressBar mProgressBar;
        int mQuestionId;
        int mIndex;
        int mScore;


    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    // TODO: Declare constants here

    final int INCREMENT_PROGRESS = (int) Math.ceil(100.0 / mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        }else{
            mScore = 0;
            mIndex = 0;
        }


        mTrueBtn = findViewById(R.id.true_button);
        mFalseBtn = findViewById(R.id.false_button);
        qTextView = findViewById(R.id.question_text_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mScoreView = findViewById(R.id.score);


        mScoreView.setText("Score: " + mScore + "/" + mQuestionBank.length);

        mQuestionId = mQuestionBank[mIndex].getQuestionId();



        qTextView.setText(mQuestionId);

        mTrueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });

        mFalseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateQuestion();
            }
        });



    }


    private void updateQuestion(){
        mIndex = (mIndex+1)%mQuestionBank.length;
        if (mIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over!").
                    setMessage("Your Score is:  "+ mScore).

                    setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).
                    setCancelable(false).

                    show();
        }
        mQuestionId = mQuestionBank[mIndex].getQuestionId();
        qTextView.setText(mQuestionId);
        mScoreView.setText("Score: " + mScore + "/" + mQuestionBank.length);
        mProgressBar.incrementProgressBy(INCREMENT_PROGRESS);
    }

    private void checkAnswer (boolean userSelection){
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();
        if (userSelection == correctAnswer){
            mScore++;
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey",mIndex);
    }

}
