package com.example.senkugame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                            // Movimiento válido: intercambiar las tags y visibilidad
                            textView.setTag("visible");
                            textView.setBackgroundResource(R.drawable.corners_grid); // Cambia al recurso gráfico de la ficha
                            // Puedes modificar el texto si es necesario

                            selectedTextView.setTag("invisible");
                            selectedTextView.setBackgroundResource(R.drawable.void_cell); // Cambia al recurso gráfico de la casilla vacía
                            selectedTextView.setText(""); // Puedes modificar el texto si es necesario

                            selectedTextView = null; // Restablecer la selección
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
