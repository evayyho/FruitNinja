/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery
 */
package com.example.a4;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a4complete.R;

public class MainActivity extends Activity {
    private Model model;
    private TitleView titleView;
    private MainView mainView;
    public static Point displaySize;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setTitle("CS349 A4 Demo");

        // save display size
        Display display = getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        display.getSize(displaySize);
        

        // initialize model
        model = new Model();

        // set view
        setContentView(R.layout.main);

        model.initialize();
        final Button button = (Button) findViewById(R.id.button1);
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Log.e("Timer", model.currentTime);
            	if(button.getText().equals("start")) {
            		model.start();
            		model.setGameState(false);
            		button.setText("pause");
            	} else {
            		if(button.getText().equals("pause")) {
            			model.pause();
                		model.setGameState(true);
            			button.setText("resume");
            		}else if (button.getText().equals("resume")) {
            			model.start();
                		model.setGameState(false);
            			button.setText("pause");
            		}
            	}
            	

            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // create the views and add them to the main activity
        titleView = new TitleView(this.getApplicationContext(), model);
        ViewGroup v1 = (ViewGroup) findViewById(R.id.main_1);
        
        v1.addView(titleView);
        
        
        // the view for fruits
        mainView = new MainView(this.getApplicationContext(), model);
        ViewGroup v2 = (ViewGroup) findViewById(R.id.main_2);
        v2.addView(mainView);
       

        // notify all views
        model.initObservers();
    }
}
