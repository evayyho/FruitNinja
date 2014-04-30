/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery & Michael Terry
 */
package com.example.a4;

import android.graphics.PointF;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.example.a4complete.R;

/*
 * Class the contains a list of fruit to display.
 * Follows MVC pattern, with methods to add observers,
 * and notify them when the fruit list changes.
 */
public class Model extends Observable {
    // List of fruit that we want to display
    private ArrayList<Fruit> shapes = new ArrayList<Fruit>();
    String currentTime = "00:00";
	private long startTime = 0L;
	private Handler customHandler = new Handler();
	private long timeInMilliseconds = 0L;
	private long timeSwapBuff = 0L;
	private long updatedTime = 0L;
	public int miss = 0;
	private int count = 0;
	private boolean pauseGame = true;
	private float velocityU = 0;
	private float velocityD = 0;
	private float accelerationU = -5;
	private float accelerationD = -5;
	private static int NumberArray[] = new int[400];


    // Constructor
    Model() {
        shapes.clear();
        initializeNumberArray();
        
    }
    
    private Runnable updateTimerThread = new Runnable() {
    	    public void run() {
    	        timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
    	        updatedTime = timeSwapBuff + timeInMilliseconds;
    	        int secs = (int) (updatedTime / 1000);
    	        int mins = secs / 60;
    	        secs = secs % 60;
    	        currentTime = "" + mins + ":" + String.format("%02d", secs);
    	        customHandler.postDelayed(this, 0);
    	    }
     };
     
     public void start() {

			startTime = SystemClock.uptimeMillis();
			customHandler.postDelayed(updateTimerThread, 0); 
     }
     
     public void pause() {

			timeSwapBuff += timeInMilliseconds;
			customHandler.removeCallbacks(updateTimerThread);
     }
     
     public void initialize() {
    	 miss = 0;
    	 count = 0;
    	 currentTime = "00:00";
     }
     
     public void setMiss(int missNum) {
    	 miss += missNum;
         setChanged();
    	 notifyObservers(this);
     }
     
     public int getMiss() {
    	 return miss;
     }
     
     public void setCount(int countNum) {
    	 count += countNum;
         setChanged();
    	 notifyObservers(this);
     }
     
     public int getCount() {
    	 return count;
     }


    // Model methods
    // You may need to add more methods here, depending on required functionality.
    // For instance, this sample makes to effort to discard fruit from the list.
    public void add(Fruit s) {
        shapes.add(s);
        setChanged();
        notifyObservers();
    }

    
    public void remove(Fruit s) {
        shapes.remove(s);
    }

    public ArrayList<Fruit> getShapes() {
        return (ArrayList<Fruit>) shapes.clone();
    }

    // MVC methods
    // Basic MVC methods to bind view and model together.
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    // a helper to make it easier to initialize all observers
    public void initObservers() {
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void deleteObserver(Observer observer) {
        super.deleteObserver(observer);
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
        setChanged();
        notifyObservers();
    }
    
    public String getCurrentTime() {
    	return currentTime;
    }
    
    public void Animation(Fruit s){
    	
    	PointF temp = s.getPoint();
    	if(!pauseGame) {
    		if(s.ReachTop() == false) {
    			
    			s.translate(0, velocityU);
    			//velocityU += 0.01*acceleration;
    			s.setVelocityU((float) (0.009*accelerationU));
    			velocityU = s.getVelocityU();
    			
    			if(temp.y < 100) {
    				s.setReachTop(true);
    			}
    			
    		} else {
    			s.translate(0, velocityD);
    			//velocityD -= 0.01*acceleration;  
    			s.setVelocityD((float)0.009*accelerationD);
    			velocityD = s.getVelocityD();
    		}
    	
        	if(temp.y > 580 && s.ReachTop()) {
        		if(!s.getIntersect()) {
            			setMiss(1);
        			if(getMiss() >= 5) {
        				
        				startOver();
        			}
        		}
        		remove(s);
        		if(ArraySize() < 2) {
        			GenerateFruit(GenerateNum());
        		}
        	}
        	
    		
    	}
    	
        setChanged();
        notifyObservers();
    	
    }
    
    public boolean getGameState() {
    	return pauseGame;
    }
    public void setGameState(boolean gameState) {
    	pauseGame = gameState;
    	setChanged();
    	notifyObservers();
    }
    
    public int ArraySize() {
    	return shapes.size();
    }
    
    public int GenerateNum() {
     	Random ran = new Random();
     	int x = ran.nextInt(2) + 1;
     	return x;
    }
    
    private void GenerateFruit(int i) {
   	 for (int j = 0; j < i; j ++) {
   		 
         Fruit f = new Fruit(new float[] {0, 10, 10, 0, 20, 0, 30, 10, 30, 20, 20, 30, 10, 30, 0, 20});
         f.setIntersect(false);
         f.setReachTop(false);
         f.translate(generateRandomNumber(), 580);
         f.setInitVelocity();
		 f.setFillColor(f.getRandomColor());
		 add(f);
		     
   	 }
    }
    
    public static int generateRandomNumber() {
    	Random ran = new Random();
    	int x = ran.nextInt(400) + 0;
    	return NumberArray[x];
    }
    
    public void initializeNumberArray() {
    	for (int i = 0; i < 400; i ++) {
    	    NumberArray[i] = i;
    	}
    }
    
    public void initializeFruit() {
    	
        Fruit f1 = new Fruit(new float[] {0, 10, 10, 0, 20, 0, 30, 10, 30, 20, 20, 30, 10, 30, 0, 20});
        //f1.translate(100, 100);
        f1.translate(100, 580);
        f1.setIntersect(false);
        f1.setReachTop(false);
        f1.setInitVelocity();
		f1.setFillColor(f1.getRandomColor());
        add(f1);
        
        Fruit f2 = new Fruit(new float[] {0, 10, 10, 0, 20, 0, 30, 10, 30, 20, 20, 30, 10, 30, 0, 20});
        f2.setIntersect(false);
        f2.setReachTop(false);
        f2.setInitVelocity();
        //f2.translate(200, 200);
        f2.translate(200, 580);
        f2.setFillColor(f2.getRandomColor());
        add(f2);
    }
    
    
    public void startOver() {
    	
    	//remove all the fruits on the array list
        for (Fruit s : getShapes()) {
        	remove(s);
        }
        
        // initialize the fruit position
    	initializeFruit();
    	
    	//reset the count, miss and currentTime(timer)
    	initialize();
    	miss = 0;
    	count = 0;
    	setMiss(0);
    	setCount(0);
    	pause();
    	startTime = 0L;
    	timeInMilliseconds = 0L;
    	timeSwapBuff = 0L;
    	updatedTime = 0L;
    	
    	pauseGame = true;
    	
    	
    }
}
