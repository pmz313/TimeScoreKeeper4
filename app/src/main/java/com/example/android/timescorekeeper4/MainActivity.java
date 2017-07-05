package com.example.android.timescorekeeper4;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView countdownTimerText;
    EditText minutes;
    Button startTimer;
    Button resetTimer;
    CountDownTimer countDownTimer;
    int scoreRed = 0;
    int foulRed = 0;
    int faceRed = 0;
    int scoreWhite = 0;
    int foulWhite = 0;
    int faceWhite = 0;
    int msRemaining = 0;

    private final TextWatcher timeWatcher = new TextWatcher()
    {
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (countdownTimerText == null)
                return;

            SetTimer();
        }

        public void afterTextChanged(Editable s)
        {
            //if (countdownTimerText == null)
            //    return;

//            if (s.length() == 0)
//            {
//                countdownTimerText.setTex
//            }
//            else
//            {
//                textView.setText("You have entered : " + passwordEditText.getText());
//            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownTimerText = (TextView) findViewById(R.id.countdownText);
        minutes = (EditText) findViewById(R.id.enterMinutes);
        startTimer = (Button) findViewById(R.id.startTimer);
        resetTimer = (Button) findViewById(R.id.resetTimer);

        minutes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    SetTimer();
                }
            }
        });
        setListeners();

        minutes.addTextChangedListener(timeWatcher);
    }

    protected void SetTimer()
    {
        String getMinutes = minutes.getText().toString();//Get minutes from edittext
        //Check validation over edit text
        if (getMinutes.equals("") || getMinutes.length() <= 0)
        {
            long zero = 0;
            countdownTimerText.setText(String.format("%02d:%02d", zero, zero));
        }
        else
        {
            msRemaining = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds
            //Convert milliseconds into minute and seconds
            long minRemaining = TimeUnit.MILLISECONDS.toMinutes(msRemaining) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(msRemaining));
            long secRemaining = TimeUnit.MILLISECONDS.toSeconds(msRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(msRemaining));
            //long mSecRemaining = msRemaining % 60;
            String ms = String.format("%02d:%02d", minRemaining, secRemaining);
            countdownTimerText.setText(ms);//set text
        }
    }


    /**
     * Displays the given score for RED
     */
    public void displayScoreRed(int score) {
        TextView scoreView = (TextView) findViewById(R.id.red_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Decrease the score for RED by 1 point.
     */

    public void minusOneForRed(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        if (scoreRed == 0) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 point", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        //Alert when removing Point
        new AlertDialog.Builder(this)
                .setMessage("Remove 1 Point from Red?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        scoreRed = scoreRed - 1;
                        displayScoreRed(scoreRed);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    /**
     * Increase the score for RED by 1 points.
     * */

    public void addOneForRed(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        //Add 1 point on short press
        scoreRed = scoreRed + 1;
        displayScoreRed(scoreRed);

        if (scoreRed >= scoreWhite + 7) {
            // Vibrate for 1600 milliseconds
            v.vibrate(1600);
            //Alert when White wins by point spread
            new AlertDialog.Builder(this)
                    .setMessage("Red wins by 7 point spread!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
        }
    }

    /**
     * Displays the given fouls for RED
     */
    public void displayFoulsRed(int fouls) {
        TextView scoreView = (TextView) findViewById(R.id.fouls_red);
        scoreView.setText(String.valueOf(fouls));
    }

    /**
     * Minus 1 foul to RED.
     */

    public void minusOneFoulRed(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        if (foulRed == 0) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 foul", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        //Alert when removing Foul
        new AlertDialog.Builder(this)
                .setMessage("Remove 1 Foul from Red?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        foulRed = foulRed - 1;
                        displayFoulsRed(foulRed);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    /**
     * Add 1 foul to RED.
     */
    public void addOneFoulRed(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        foulRed = foulRed + 1;
        displayFoulsRed(foulRed);

        if (foulRed >= 3) {
            //Alert when Red makes 3 or more Fouls
            new AlertDialog.Builder(this)
                    .setMessage("Red has too many fouls. \nAward 1 point to White!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
        }
    }

    /**
     * Displays the given face contact for Red
     */
    public void displayFaceRed(int faceContact) {
        TextView scoreView = (TextView) findViewById(R.id.faceContact_red);
        scoreView.setText(String.valueOf(faceContact));
    }

    /**
     * Minus 1 face contact to RED.
     */

    public void minusOneFaceRed(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        if (faceRed == 0) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 face contact", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do

            return;
        }
        //Alert when removing Face Contact
        new AlertDialog.Builder(this)
                .setMessage("Remove 1 Face Contact from Red?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        faceRed = faceRed - 1;
                        displayFaceRed(faceRed);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    /**
     * Add 1 face contacted to RED.
     */
    public void addOneFaceRed(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        faceRed = faceRed + 1;
        displayFaceRed(faceRed);

        if (faceRed <= 2) {
            new AlertDialog.Builder(this)
                    .setMessage("Red has made face contact. \nAward 1 point to White!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
        }

        if (faceRed > 2) {
            // Vibrate for 1600 milliseconds
            v.vibrate(1600);
            //Alert when White wins by point spread
            new AlertDialog.Builder(this)
                    .setMessage("Red has scored 3 face contacts. \nRed is disqualified!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
        }

    }

    /**
     * Displays the given score for White
     */
    public void displayScoreWhite(int score) {
        TextView scoreView = (TextView) findViewById(R.id.white_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Decrease the score for White by 1 point.
     */

    public void minusOneForWhite(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);
        if (scoreWhite == 0) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 point", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        //Alert when removing Point
        new AlertDialog.Builder(this)
                .setMessage("Remove 1 Point from White?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        scoreWhite = scoreWhite - 1;
                        displayScoreWhite(scoreWhite);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    /**
     * Increase the score for White by 1 point.
     */
    public void addOneForWhite(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        scoreWhite = scoreWhite + 1;
        displayScoreWhite(scoreWhite);

        if (scoreWhite >= scoreRed + 7) {
            // Vibrate for 1600 milliseconds
            v.vibrate(1600);
            //Alert when White wins by point spread
            new AlertDialog.Builder(this)
                    .setMessage("White wins by 7 point spread!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
        }
    }

    /**
     * Displays the given fouls for White
     */
    public void displayFoulsWhite(int fouls) {
        TextView scoreView = (TextView) findViewById(R.id.fouls_white);
        scoreView.setText(String.valueOf(fouls));
    }

    /**
     * Minus 1 foul to White.
     */

    public void minusOneFoulWhite(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        if (foulWhite == 0) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 foul", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        //Alert when removing Foul
        new AlertDialog.Builder(this)
                .setMessage("Remove 1 Foul from White?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        foulWhite = foulWhite - 1;
                        displayFoulsWhite(foulWhite);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Add 1 foul to White.
     */
    public void addOneFoulWhite(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        foulWhite = foulWhite + 1;
        displayFoulsWhite(foulWhite);

        if (foulWhite >= 3) {
            //Alert when White makes 3 or more Fouls
            new AlertDialog.Builder(this)
                    .setMessage("White has too many fouls. \nAward 1 point to Red!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
        }
    }

    /**
     * Displays the given face contact for White
     */
    public void displayFaceWhite(int faceContact) {
        TextView scoreView = (TextView) findViewById(R.id.faceContact_white);
        scoreView.setText(String.valueOf(faceContact));
    }

    /**
     * Minus 1 face contact to White.
     */

    public void minusOneFaceWhite(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        if (faceWhite == 0) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 face contact", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        //Alert when removing Face Contact
        new AlertDialog.Builder(this)
                .setMessage("Remove 1 Face Contact from White?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        faceWhite = faceWhite - 1;
                        displayFaceWhite(faceWhite);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Add 1 face contacted to White.
     */
    public void addOneFaceWhite(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        faceWhite = faceWhite + 1;
        displayFaceWhite(faceWhite);

        if (faceWhite <= 2) {
            //Alert when White makes Face Contact
            new AlertDialog.Builder(this)
                    .setMessage("White has made face contact. \nAward 1 point to Red!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
        }

        if (faceWhite > 2) {
            // Vibrate for 1600 milliseconds
            v.vibrate(1600);
            //Alert when White is disqualified
            new AlertDialog.Builder(this)
                    .setMessage("White has scored 3 face contacts. \nWhite is disqualified!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
        }

    }


    /**
     * Resets both scores back to 0.
     */
    public void resetScores(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        //Alert when Resetting scores
        new AlertDialog.Builder(this)
                .setMessage("Reset all points? \nThis cannot be undone")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // continue with Reset
                        scoreRed = 0;
                        foulRed = 0;
                        faceRed = 0;
                        scoreWhite = 0;
                        foulWhite = 0;
                        faceWhite = 0;
                        displayScoreRed(scoreRed);
                        displayFoulsRed(foulRed);
                        displayFaceRed(faceRed);
                        displayScoreWhite(scoreWhite);
                        displayFoulsWhite(foulWhite);
                        displayFaceWhite(faceWhite);
                    }
                })
                //Cancels reset
                .setNegativeButton("No", null)
                .show();

    }

    /***Timer Code**/

    //Set Listeners over button
    private void setListeners() {
        startTimer.setOnClickListener(this);
        resetTimer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startTimer:
                //If CountDownTimer is null then start timer
                if (countDownTimer == null) {
                    String getMinutes = minutes.getText().toString();//Get minutes from edittext
                    //Check validation over edit text
                    if (!getMinutes.equals("") && getMinutes.length() > 0) {
                        startTimer(msRemaining);//start countdown
                        startTimer.setText(getString(R.string.stop_timer));//Change Text

                    } else
                        Toast.makeText(MainActivity.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edit text is empty
                } else {
                    //Else stop timer and change text
                    stopCountdown();
                    startTimer.setText(getString(R.string.start_timer));
                }
                break;
            case R.id.resetTimer:
                stopCountdown();//stop count down
                startTimer.setText(getString(R.string.start_timer));//Change text to Start Timer
                SetTimer();
                break;
        }


    }


    //Stop Countdown method
    private void stopCountdown() {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            minutes.setEnabled(true);
        }
    }

    //Start Countdown method
    private void startTimer(int noOfMinutes) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        countDownTimer = new CountDownTimer(noOfMinutes, 100) {
            public void onTick(long millisUntilFinished) {
                msRemaining = (int) millisUntilFinished;
                long minRemaining = TimeUnit.MILLISECONDS.toMinutes(msRemaining) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(msRemaining));
                long secRemaining = TimeUnit.MILLISECONDS.toSeconds(msRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(msRemaining));
                //long mSecRemaining = msRemaining % 60;
                //Convert milliseconds into minute and seconds
                String ms = String.format("%02d:%02d", minRemaining, secRemaining);
                countdownTimerText.setText(ms);//set text
                minutes.setEnabled(false);
            }

            public void onFinish() {
                // Get instance of Vibrator from current Context
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 1600 milliseconds
                v.vibrate(1600);

                countdownTimerText.setText("STOP!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                startTimer.setText(getString(R.string.start_timer));//Change button text
                minutes.setEnabled(true);

            }
        }.start();

    }

    /**
     * Activity Alert when Exiting
     **/
    @Override
    public void onBackPressed() {
        //Alert when Exiting App
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?" + "\nAll data will be lost")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
