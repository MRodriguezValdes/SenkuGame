package com.example.senkugame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView selectedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof TextView) {
                TextView textView = (TextView) child;
                textView.setOnClickListener(view -> {
                    if (selectedTextView != null) {
                        if (textView.getTag() != null && textView.getTag().equals("invisible")) {
                            AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                            fadeOut.setDuration(500);

                            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    // Método generado automáticamente, no se utiliza
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    textView.setBackgroundResource(R.drawable.corners_grid); // Cambia al recurso gráfico de la ficha
                                    textView.setTag("visible"); // Actualiza la etiqueta de la casilla vacía
                                    selectedTextView.setBackgroundResource(R.drawable.void_cell); // Cambia al recurso gráfico de la casilla vacía
                                    selectedTextView.setTag("invisible"); // Actualiza la etiqueta de la ficha
                                    selectedTextView.setText(""); // Puedes modificar el texto si es necesario
                                    selectedTextView.setVisibility(View.VISIBLE); // Vuelve a hacer visible el TextView de la ficha movida
                                    selectedTextView = null; // Restablecer la selección
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                    // Método generado automáticamente, no se utiliza
                                }
                            });

                            selectedTextView.startAnimation(fadeOut);
                        }
                    } else {
                        if (textView.getTag() != null && textView.getTag().equals("visible")) {
                            selectedTextView = textView;
                        }
                    }
                });
            }
        }
    }
}
