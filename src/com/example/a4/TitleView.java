/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery & Michael Terry
 */
package com.example.a4;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.a4complete.R;

import java.util.Observable;
import java.util.Observer;

/*
 * View to display the Title, and Score
 * Score currently just increments every time we get an update
 * from the model (i.e. a new fruit is added).
 */
public class TitleView extends TextView implements Observer {
	public int count;
    public int miss;
    
    
    // Constructor requires model reference
    public TitleView(Context context, Model model) {
        super(context);

        // set width, height of this view
        this.setHeight(235);
        this.setWidth(MainActivity.displaySize.x);

        model.addObserver(this);
    }
    


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        String count_string = getResources().getString(R.string.app_count) + " " + count;
        String miss_string = getResources().getString(R.string.app_miss) + " " + miss;
        
        setBackgroundColor(Color.TRANSPARENT);
        setTextColor(Color.MAGENTA);
        setTextSize(15);
        setText(Html.fromHtml(count_string + "<br>" + miss_string + "<br>"));
    }

    // Update from model
    // ONLY useful for testing that the view notifications work
    @Override
    public void update(Observable observable, Object data) {
    	
        // TODO BEGIN CS349
        // do something more meaningful here
        // TODO END CS349
    	Model model = (Model) observable;
    	if(!model.getGameState()) { 
    		count = model.getCount();
    		miss = model.getMiss();
    	}
    	
        invalidate();
        
    }  
}
