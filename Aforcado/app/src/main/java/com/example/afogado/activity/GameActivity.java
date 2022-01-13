package com.example.afogado.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afogado.R;
import com.example.afogado.adapter.LetterAdapter;
import com.example.afogado.database.DataBaseHelper;
import com.example.afogado.databinding.ActivityGameBinding;
import com.example.afogado.model.Word;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private ArrayList<Word> words;
    private Word currentWord;
    private Random random;
    private EditText[] letters;
    ActivityGameBinding binding;
    private LetterAdapter adapter;
    private int numCorr, numErr;
    private int numChars, points = 0, sum = 6, maxScore = 0;
    boolean endGame = false;
    private final int SEG = 600000;
    private final int PLAY = 0;
    private final int WIN = 1;
    private final int FAIL = 2;


    CountDown countDown;
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        random = new Random();
        words = DataBaseHelper.getInstance(this).loadAllWords();
        countDown = new CountDown(SEG,1000);

        maxScore = getMaxScore();

        playGame();
    }


    @SuppressLint("SetTextI18n")
    private void playGame(){
        playMusic(PLAY);
        if(maxScore != 0){
            binding.tvMaxScore.setText(getString(R.string.max_score) + "\n" + maxScore + " pts");
        }
        binding.tvPoints.setText(points + " pts");

        if(!words.isEmpty()) {
            Word newWord = words.get(random.nextInt(words.size()));
            countDown.start();

        while (newWord.equals(currentWord)){
            newWord = words.get(random.nextInt(words.size()));
        }
        currentWord = newWord;
        letters = new EditText[currentWord.getName().length()];

        binding.linearWords.removeAllViews();
        for(int i = 0; i < currentWord.getName().length(); i++){
            letters[i] = new EditText(this);
            letters[i].setInputType(InputType.TYPE_NULL);
            letters[i].setText("" + currentWord.getName().charAt(i));
            letters[i].setTextSize(24);
            letters[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            letters[i].setGravity(Gravity.CENTER);
            letters[i].setTextColor(Color.WHITE);
            binding.linearWords.addView(letters[i]);
        }
        adapter = new LetterAdapter(this);
        binding.gridLetters.setAdapter(adapter);
        binding.gridLetters.setBackgroundColor(Color.DKGRAY);
        numCorr = 0;
        numErr = 0;
        endGame = false;
        numChars = currentWord.getName().length();

        for(int i = 0; i < 6; i++){
            binding.ivAfogado.setImageResource(R.drawable.zero);
        }
    }
}

    /*
    *  0 = play
    *  1 = win
    *  2 = fail
    * */
    private void playMusic(int music) {
        if(mPlayer != null){
            mPlayer.release();
        }
       switch (music) {
           case PLAY:
               mPlayer = MediaPlayer.create(this, R.raw.aforcadopiano);
               mPlayer.seekTo(0);
               mPlayer.start();
               break;
           case WIN:
               mPlayer = MediaPlayer.create(this, R.raw.win);
               mPlayer.seekTo(0);
               mPlayer.setLooping(true);
               mPlayer.start();
               break;
           case FAIL:
               mPlayer = MediaPlayer.create(this, R.raw.fail);
               mPlayer.seekTo(0);
               mPlayer.setLooping(true);
               mPlayer.start();
               break;
           default:
               mPlayer.release();
       }


    }


    public void onClickLetter(View view){
        String clickletter = ((TextView) view).getText().toString();
        char letterChar = clickletter.charAt(0);

        view.setEnabled(false);
        boolean correct = false;

        for(int i = 0; i < currentWord.getName().length(); i++){
            if(currentWord.getName().charAt(i) == letterChar){
                correct = true;
                numCorr++;
                letters[i].setTextColor(Color.BLACK);
            }
        }

        if(correct){
            if(numCorr == numChars){
                disableButtons();
                endGame = true;
                countDown.onFinish();

                playMusic(WIN);

                LayoutInflater inflater = LayoutInflater.from(this);
                View subView = inflater.inflate(R.layout.image_win, null);
                final TextView tittle = (TextView)subView.findViewById(R.id.tv_titleWin);
                final TextView description = (TextView)subView.findViewById(R.id.tv_descriptionWin);
                final ImageView subImageView = (ImageView)subView.findViewById(R.id.dialog_imageView);
                final Button acept = (Button)subView.findViewById(R.id.bt_acept);


                try {
                    tittle.setText(currentWord.getName());
                    description.setText(currentWord.getDescription());
                    int id = getResources().getIdentifier(currentWord.getName().toLowerCase(), "drawable", getPackageName());
                    Drawable drawable = getResources().getDrawable(id);
                    subImageView.setImageDrawable(drawable);
                }catch (NullPointerException e){
                    Log.e("IMG", e.getMessage());
                    subImageView.setVisibility(View.INVISIBLE);
                }
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(subView);
                builder.setCancelable(false);
                builder.setTitle(R.string.win);
                dialog = builder.create();
                dialog.show();

                acept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        points += sum;
                        maxScore = points;
                        GameActivity.this.playGame();
                    }
                });
            }
        }else{
            numErr++;
            switch (numErr){
                case 0:
                    binding.ivAfogado.setImageResource(R.drawable.zero);
                    sum = 6;
                    break;
                case 1:
                    binding.ivAfogado.setImageResource(R.drawable.one);
                    sum = 5;
                    break;
                case 2:
                    binding.ivAfogado.setImageResource(R.drawable.two);
                    sum = 4;
                    break;
                case 3:
                    binding.ivAfogado.setImageResource(R.drawable.three);
                    sum = 3;
                    break;
                case 4:
                    binding.ivAfogado.setImageResource(R.drawable.four);
                    sum = 2;
                    break;
                case 5:
                    binding.ivAfogado.setImageResource(R.drawable.five);
                    sum = 1;
                    break;
                case 6:
                    binding.ivAfogado.setImageResource(R.drawable.six);
                    sum = 0;
                    disableButtons();
                    endGame = true;
                    countDown.onFinish();

                    playMusic(FAIL);

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setTitle(R.string.lose);
                    builder.setPositiveButton(R.string.playAgain, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            GameActivity.this.playGame();
                        }
                    });
                    builder.create().show();
                    break;
            }
        }
    }

    public void disableButtons(){
        binding.tvTimer.setTextColor(Color.WHITE);
        for(int i = 0; i < binding.gridLetters.getChildCount(); i++){
            binding.gridLetters.getChildAt(i).setEnabled(false);
        }
    }

    public void end(){
        countDown.cancel();
        disableButtons();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.endTime);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.playAgain, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GameActivity.this.playGame();
            }
        });
        builder.create().show();
    }


    public void help(){
        if(points >= 3){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.pista);
            builder.setIcon(android.R.drawable.ic_menu_info_details);
            builder.setMessage(currentWord.getDescription());
            builder.create().show();
            points -= 3;
            binding.tvPoints.setText(points + "pts");
        }else {
            Toast.makeText(this, R.string.no_points, Toast.LENGTH_LONG).show();
        }

    }

    public void showTime(long mil){
        String clockSeg = String.format("%02d",(mil % 60000 / 1000));
        if(Integer.parseInt(clockSeg) <= 3){
            binding.tvTimer.setTextColor(Color.RED);
        }
        if(Integer.parseInt(clockSeg) == 0){
            countDown.onFinish();
        }
        binding.tvTimer.setText("00:" + clockSeg);
    }

    public void onClickHelp(View view) {
        help();
    }


    public class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if(endGame){
                this.cancel();
            }else {
                end();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            showTime(millisUntilFinished);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(getMaxScore() < points){
            saveScore(points);
        }

        if(mPlayer != null){
            mPlayer.release();
        }
    }

    private void saveScore(int maxScore){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_high_score_key), maxScore);
        editor.commit();
    }

    private int getMaxScore(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), 0);
        return highScore;
    }
}
