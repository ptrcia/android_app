package com.example.android_app;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScoreManager {

    private int clickValue=1;
    private int passiveValue=0;
    private int score=0;
    private String scoreText;
    private String clickValueText;
    private String passiveValueText;

    //region Instance
    private static ScoreManager instance;

    private ScoreManager() {
        PassiveTimer passiveTimer = new PassiveTimer(this);
        passiveTimer.start();
    }

    public static synchronized ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }
    //endregion

    public void ClickActive(){
        score = clickValue + score;
        setScore(score);
        Game.getInstance().UpdateScoreText();
    }
    public void ClickPassive(){
        score = passiveValue + score;
        setScore(score);
        if (Game.getInstance() != null) {
            Game.getInstance().UpdateScoreText();
        } else {
            Log.e("Clicker->", "Game instance is null");
        }
        //Game.getInstance().UpdateScoreText();
    }

    public void applyActiveUpgrade(Context context , int cost, int effect){
            score -= cost;
            clickValue += effect;
            Log.d("Clicker-> ", "Has comprado la mejora");
            Log.d("Clicker-> ", "Score->" + score);
            Log.d("Clicker-> ", "ClickValue->" + clickValue);
        Log.d("Clicker->", "Game instance: " + Game.getInstance());

        if (Game.getInstance() != null) {
            Game.getInstance().UpdateScoreText();
        } else {
            Log.e("Clicker->", "Game instance is null");
        }

        //Game.getInstance().UpdateScoreText();
            //Audio
            checkAudio(context);
    }
    public void applyPassiveUpgrade(Context context , int cost, int effect){
            score -= cost;
            passiveValue += effect;

            Log.d("Clicker-> ", "Has comprado la mejora");
            Log.d("Clicker-> ", "Score->" + score);
            Log.d("Clicker-> ", "PassiveValue->" + passiveValue);
            Game.getInstance().UpdateScoreText();
            //Audio
            checkAudio(context);
    }

    void checkAudio(Context context){
        //Hacer sonar
        if (!AudioManager.isMutedMusic()) {
            Intent playIntent = new Intent(context, AudioManager.class);
            playIntent.setAction("playSFX");
            playIntent.putExtra("resourceID", R.raw.purchase);
            context.startService(playIntent);
        }
    }

    //region Getter y Setters

    public int getPassiveValue(){
        return passiveValue;
    }
    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return score;
    }
    public String getClickValueText(){
        clickValueText = String.valueOf(clickValue);
        return clickValueText;
    }
    public String getPassiveValueText(){
        passiveValueText = String.valueOf(passiveValue);
        return passiveValueText;
    }
    public int getClickValue() {
        return clickValue;
    }

    public void setClickValue(int clickValue) {
        this.clickValue = clickValue;
    }

    public void setPassiveValue(int passiveValue) {
        this.passiveValue = passiveValue;
    }

    //endregion
}
