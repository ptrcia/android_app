package com.example.android_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_app.RoomDB.GameViewModel;



public class Game extends AppCompatActivity {

    private GameViewModel gameViewModel;
    TextView textScore;
    ScoreManager scoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button buttonPassives = findViewById(R.id.buttonPassives);
        Button buttonActives = findViewById(R.id.buttonActives);
        ImageButton buttonClickScore = findViewById(R.id.buttonClickeableCat);
        textScore = findViewById(R.id.scoreText);
        scoreManager = ScoreManager.getInstance();

        //RoomDB
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        gameViewModel.getUserStats("User1").observe(this, userStats -> {
            if (userStats != null) {
                textScore.setText(String.valueOf(userStats.getTotalScore())); //gestionar los pùntos totales
            }
        });


        //region Botones
        buttonActives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFragment("Active");
            }
        });
        buttonPassives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFragment("Passive");
            }
        });
        buttonClickScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reference Script Score
                Log.d("Clicker-> ", "Se ha hecho click");
                scoreManager.ClickImage();
                UpdateScoreText();
            }
        });
        //endregion

        //Consulta de prueba para imprimir

    }
    private void OpenFragment(String upgradeType){
        Log.d("Clicker-> ", "Se ha hecho click en: " + upgradeType);
        //abrir

        Fragment fragment = UpgradeFragment.newInstance(upgradeType);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Log.d("Clicker->", "Transacción de fragmento en progreso");
        transaction.replace(R.id.container_layout, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    public void UpdateScoreText(){
        textScore.setText(scoreManager.getScore());
    }
}