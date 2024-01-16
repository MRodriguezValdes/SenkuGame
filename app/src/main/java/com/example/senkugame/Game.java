package com.example.senkugame;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.TextView;

public class Game {
    int[][] senkuBoard;
    private TextView selectedTextView;

    private int selectedRow;
    private int selectedCol;
    private int targetRow;
    private int targetCol;

    private int middleRow;
    private int middleCol;


    GridLayout gridLayout;

    public Game(Context context) {
        this.senkuBoard = new int[7][7];
        this.gridLayout = ((Activity) context).findViewById(R.id.gridLayout);
        this.fillTheBoard();
    }

    private void fillTheBoard() {
        int numRows = gridLayout.getRowCount();
        int numColumns = gridLayout.getColumnCount();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                int index = row * numColumns + col;
                TextView textView = (TextView) gridLayout.getChildAt(index);

                setTextViewClickListener(textView, gridLayout);

                if (textView.getTag() != null && textView.getTag().equals("invisible")) {
                    senkuBoard[row][col] = -1; // Celdas invisibles
                } else if (textView.getTag() != null && textView.getTag().equals("visible")) {
                    senkuBoard[row][col] = 1; // Celdas visibles
                } else {
                    senkuBoard[row][col] = 0; // Celdas vacías
                }
            }
        }
    }

    private void setTextViewClickListener(TextView textView, GridLayout gridLayout) {
        textView.setOnClickListener(view -> {
            Log.d("SenkuGame", "TextView Clicked");
            if (selectedTextView != null) {
                if (textView.getTag() != null && textView.getTag().equals("void")) {
                    if (isValidMove(textView, gridLayout)) {
                        performMoveAnimation(textView);
                    } else {
                        selectedTextView = null;
                        Log.d("SenkuGame", "Invalid Move");
                    }
                }
            } else {
                if (textView.getTag() != null && textView.getTag().equals("visible")) {
                    selectedTextView = textView;
                }
            }
        });
    }

    private void performMoveAnimation(TextView targetTextView) {
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(500);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Método generado automáticamente, no se utiliza
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                targetTextView.setBackgroundResource(R.drawable.corners_grid);
                targetTextView.setTag("visible");
                selectedTextView.setBackgroundResource(R.drawable.void_cell);
                selectedTextView.setTag("void");
                selectedTextView.setText("");
                selectedTextView.setVisibility(View.VISIBLE);
                selectedTextView = null;

                updateBoard();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Método generado automáticamente, no se utiliza
            }
        });


        selectedTextView.startAnimation(fadeOut);
    }

    private boolean isValidMove(TextView targetTextView, GridLayout gridLayout) {
        // Obtén las posiciones (fila y columna) de la vista seleccionada
        selectedRow = getRowIndex(gridLayout, selectedTextView);
        selectedCol = getColumnIndex(gridLayout, selectedTextView);

        // Obtén las posiciones (fila y columna) de la vista objetivo
        targetRow = getRowIndex(gridLayout, targetTextView);
        targetCol = getColumnIndex(gridLayout, targetTextView);

        // Verifica si el movimiento es horizontal o vertical
        boolean isHorizontalMove = selectedRow == targetRow && Math.abs(selectedCol - targetCol) == 2;
        boolean isVerticalMove = selectedCol == targetCol && Math.abs(selectedRow - targetRow) == 2;

        // Verifica si la celda intermedia está ocupada
        middleRow = (selectedRow + targetRow) / 2;
        middleCol = (selectedCol + targetCol) / 2;

        boolean isMiddleCellOccupied = senkuBoard[middleRow][middleCol] == 1;

        // Verifica si el movimiento es válido
        return (isHorizontalMove || isVerticalMove) && isMiddleCellOccupied;
    }

    private int getRowIndex(GridLayout gridLayout, TextView textView) {
        // Obtén el índice de fila de la vista en el GridLayout
        int indexOfChild = gridLayout.indexOfChild(textView);
        int numRows = gridLayout.getRowCount();
        return indexOfChild / numRows;
    }

    private int getColumnIndex(GridLayout gridLayout, TextView textView) {
        // Obtén el índice de columna de la vista en el GridLayout
        int indexOfChild = gridLayout.indexOfChild(textView);
        int numColumns = gridLayout.getColumnCount();
        return indexOfChild % numColumns;
    }
    private void updateBoard(){
        senkuBoard [selectedRow][selectedCol] = 0;
        senkuBoard [targetRow][targetCol]=1;
        senkuBoard [middleRow][middleCol]=0;
        TextView middleTextView = getTextViewAtPosition(gridLayout, middleRow, middleCol);
        if (middleTextView != null) {
            middleTextView.setBackgroundResource(R.drawable.void_cell);
            middleTextView.setTag("void");
            middleTextView.setText("");
            middleTextView.setVisibility(View.VISIBLE);
        }
    }
    private TextView getTextViewAtPosition(GridLayout gridLayout, int row, int col) {
        int index = row * gridLayout.getColumnCount() + col;
        if (index >= 0 && index < gridLayout.getChildCount()) {
            View view = gridLayout.getChildAt(index);
            if (view instanceof TextView) {
                return (TextView) view;
            }
        }
        return null;
    }

}
