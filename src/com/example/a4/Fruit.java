/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery
 */
package com.example.a4;


import java.util.Random;

import android.graphics.*;
import android.util.Log;

/**
 * Class that represents a Fruit. Can be split into two separate fruits.
 */
public class Fruit {
    private Path path = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Matrix transform = new Matrix();
    private boolean reachTop = false;
    private boolean isIntersect;
	private float velocityU = 0;
	private float velocityD = 0;
	int[] ColorArray = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED};


    /**
     * A fruit is represented as Path, typically populated 
     * by a series of points 
     */
    Fruit(float[] points) {
        init();
        this.path.reset();
        this.path.moveTo(points[0], points[1]);
        for (int i = 2; i < points.length; i += 2) {
            this.path.lineTo(points[i], points[i + 1]);
        }
        this.path.moveTo(points[0], points[1]);
    }

    Fruit(Region region) {
        init();
        this.path = region.getBoundaryPath();
    }

    Fruit(Path path) {
        init();
        this.path = path;
    }

    private void init() {
    	
        this.paint.setColor(Color.BLUE);
        this.paint.setStrokeWidth(5);
    }

    /**
     * The color used to paint the interior of the Fruit.
     */
    public int getFillColor() { return paint.getColor(); }
    public void setFillColor(int color) { paint.setColor(color); }

    /**
     * The width of the outline stroke used when painting.
     */
    public double getOutlineWidth() { return paint.getStrokeWidth(); }
    public void setOutlineWidth(float newWidth) { paint.setStrokeWidth(newWidth); }

    /**
     * Concatenates transforms to the Fruit's affine transform
     */
    public void rotate(float theta) { transform.postRotate(theta); }
    public void scale(float x, float y) { transform.postScale(x, y); }
    public void translate(float tx, float ty) { transform.postTranslate(tx, ty); }

    /**
     * Returns the Fruit's affine transform that is used when painting
     */
    public Matrix getTransform() { return transform; }
    
    public PointF getPoint() {
    	float[] values = new float[9];
    	this.getTransform().getValues(values);
    	PointF temp = new PointF();
    	temp.x = values[2];
    	temp.y = values[5];
    	
    	return temp;
    }

    /**
     * The path used to describe the fruit shape.
     */
    public Path getTransformedPath() {
        Path originalPath = new Path(path);
        Path transformedPath = new Path();
        originalPath.transform(transform, transformedPath);
        return transformedPath;
    }

    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Canvas canvas) {
    	canvas.drawPath(getTransformedPath(), paint);
    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(PointF p1, PointF p2) {
    	  Path line = new Path();
          line.moveTo(p1.x, p1.y);
          line.lineTo(p2.x, p2.y);
          line.lineTo(p1.x+1, p1.y+1);
          
          Region clip = new Region(0, 0, 480, 800);
          Region fruitRegion = new Region();
          Region lineRegion = new Region();
             
          fruitRegion.setPath(getTransformedPath(), clip);
          lineRegion.setPath(line, clip);
          return fruitRegion.op(lineRegion, Region.Op.INTERSECT);

    	
        //return false;
    }

    /**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(PointF p1) {
        Region region = new Region();
        boolean valid = region.setPath(getTransformedPath(), new Region());
        return valid && region.contains((int) p1.x, (int) p1.y);
    }

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */
    public Fruit[] split(PointF p1, PointF p2) {
    	Path topPath = null;
    	Path bottomPath = null;
    	// TODO BEGIN CS349
        // calculate angle between points
        // rotate and flatten points passed in
        // rotate region
        // define region masks and use to split region into top and bottom
        // TODO END CS349
        if (topPath != null && bottomPath != null) {
           return new Fruit[] { new Fruit(topPath), new Fruit(bottomPath) };
        }
        return new Fruit[0];
    }
    
    public boolean ReachTop() {
    	return reachTop;
    }
    public void setReachTop(boolean top) {
    	reachTop = top;
    }
    
    public boolean getIntersect() {
    	return isIntersect;
    }
    public void setIntersect(boolean intersect) {
    	isIntersect = intersect;
    }
    
    public void setInitVelocity() {
    	velocityU = 0;
    	velocityD = 0;
    }
    public void setVelocityD(float num) {
    	velocityD -= num;
    }
    public float getVelocityD() {
    	return velocityD;
    }
    public void setVelocityU(float num) {
    	velocityU += num;
    }
    public float getVelocityU() {
    	return velocityU;
    }
    
    public int getRandomColor() {
    	return ColorArray[generateRandomNumber()];
    }

    public int generateRandomNumber() {
    	Random ran = new Random();
    	int x = ran.nextInt(9) + 0;
    	return x;
    }
}
