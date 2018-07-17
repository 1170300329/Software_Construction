/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	if(sideLength<=0) {
    	  return;
    	}
    	for(int i=1;i<=4;i++)
    	{
    		turtle.forward(sideLength);
    		turtle.turn(90);
    	}
      
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
    	if(sides<=2) {
      	  return -1;
      	}
    	double angle;
    	angle =((sides - 2) * 180.0) / sides*1.0;
    	return angle;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
       if(angle<60) {
          return -1;
       }
       int sides= (int) Math.round(360.0/(180.0-angle));
       return sides;
       
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
    	if(sides<=2||sideLength<=0) {
        	  return;
        }
    	for(int i=1;i<=sides;i++)
    	{
    		turtle.forward(sideLength);
    		turtle.turn(180-calculateRegularPolygonAngle(sides));
    	}
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {

		int deltaX = targetX - currentX;
		int deltaY = targetY - currentY;
		double theta=Math.toDegrees(Math.atan((double) deltaY / (double) deltaX));
		if (deltaX == 0 && deltaY > 0) {
			return currentHeading>0?(360 - currentHeading):0;
		} else if (deltaX == 0 && deltaY < 0) {
			return 180 - currentHeading;
		} else if (deltaX > 0 && deltaY >= 0) {
			if (90 - currentHeading - theta > 0)
				return (90 - currentHeading - theta);
			else
				return (360 + 90 - currentHeading - theta);
		} else if (deltaY < 0) {
			return (270 - currentHeading - theta);
		} else
			return (450 - currentHeading - theta);

	}
    

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
//        throw new RuntimeException("implement me!");
    	List<Double> ans=new ArrayList<Double>();
    	ans.add(calculateHeadingToPoint(0, xCoords.get(0), yCoords.get(0),xCoords.get(1), yCoords.get(1)));
    	for(int i=1;i<xCoords.size()-1;i++)
    	{
    		ans.add(calculateHeadingToPoint(ans.get(i-1), xCoords.get(i), yCoords.get(i),xCoords.get(i+1), yCoords.get(i+1)));
    	}
    	return ans;    	
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     * @throws InterruptedException 
     */
    public static void drawPersonalArt(Turtle turtle){
//    	turtle.color(PenColor.PINK);
//    	turtle.turn(90);
//    	for(int i=1;i<=5;i++)
//    	{
//    		turtle.forward(40);
//    		turtle.turn(144);
//    	}
      // throw new RuntimeException("implement me!");
    	
        for(int i=1;i<=200;i++)
        {
        	if(i%4==0)turtle.color(PenColor.BLACK);
        	if(i%4==1)turtle.color(PenColor.BLUE);
        	if(i%4==2)turtle.color(PenColor.CYAN);
        	if(i%4==3)turtle.color(PenColor.YELLOW);
        	turtle.forward(i);
        	turtle.turn(91);
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        drawPersonalArt(turtle);
        //drawSquare(turtle, 40);
        //drawRegularPolygon(turtle, 7, 40);
        turtle.draw();
    }

}
