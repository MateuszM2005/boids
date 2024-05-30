package com.company;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Boid {

    private double xSpeed;
    double xSpeedPublic;
    private double ySpeed;
    double ySpeedPublic;
    private double xPos;
    double xPosPublic;
    private double yPos;
    double yPosPublic;
    Circle circle;

    public Boid(){
        xPos = Math.random()*Main.WIDHT;
        xPos = xPos%Main.WIDHT;
        yPos = Math.random()*Main.HEIGHT;
        yPos = yPos%Main.HEIGHT;
        xPosPublic = xPos;
        yPosPublic = yPos;
        xSpeed = (Math.random()-1.0/2)*Main.BASE_SPEED * 2;
        ySpeed = Math.random() < 1.0/2 ? Math.sqrt(Main.BASE_SPEED*Main.BASE_SPEED - xSpeed*xSpeed) : -Math.sqrt(Main.BASE_SPEED*Main.BASE_SPEED - xSpeed*xSpeed);
        circle = new Circle(xPos+50, yPos+50, 4, Color.BLACK);
    }

    public ArrayList<Boid> detectInRadius(){
        ArrayList<Boid> list = new ArrayList<>();
        for (int i = 0; i < Main.AMMOUNT;i++){
            if(Main.boids[i] != this){
                double xDiff = ((Math.abs(xPos - Main.boids[i].xPosPublic) > Main.WIDHT/2.0) ?  Main.WIDHT - Math.abs(xPos - Main.boids[i].xPosPublic) : Math.abs(xPos - Main.boids[i].xPosPublic));
                double yDiff = ((Math.abs(yPos - Main.boids[i].yPosPublic) > Main.HEIGHT/2.0) ?  Main.HEIGHT - Math.abs(yPos - Main.boids[i].yPosPublic) : Math.abs(yPos - Main.boids[i].yPosPublic));
                if(Math.sqrt(Math.pow(xDiff,2)+Math.pow(yDiff,2)) < Main.RADIUS){
                    list.add(Main.boids[i]);
                }
            }
        }
        return list;
    }

    public void move(){
        xSpeedPublic = xSpeed;
        ySpeedPublic = ySpeed;

        xPos = (xPos+xSpeed)%Main.WIDHT;
        if(xPos < 0)
            xPos = Main.WIDHT + xPos;
        xPosPublic = xPos;
        circle.setCenterX(xPos);

        yPos = (yPos+ySpeed)%Main.HEIGHT;
        if(yPos < 0)
            yPos = Main.HEIGHT + yPos;
        yPosPublic = yPos;
        circle.setCenterY(yPos);
    }

    public void algorithm(){

        ArrayList<Boid> boids = detectInRadius();
        int ammount = boids.size();
        ArrayList<Vector> vectors = new ArrayList<>();
        Vector own = new Vector(xSpeed,ySpeed,Main.INERTIA);
        vectors.add(own);

        for(Boid boid : boids){
            boolean xjump = (Math.abs(xPos - boid.xPosPublic) > Main.WIDHT/2.0);
            boolean yjump = (Math.abs(yPos - boid.yPosPublic) > Main.HEIGHT/2.0);

            double xDiff = (xjump ?  (xPos > boid.xPosPublic ? Main.WIDHT - xPos + boid.xPosPublic : boid.xPosPublic - Main.WIDHT - xPos) : (boid.xPosPublic - xPos));
            double yDiff = (yjump ?  (yPos > boid.yPosPublic ? Main.HEIGHT - yPos + boid.yPosPublic : boid.yPosPublic - Main.HEIGHT - yPos) : (boid.yPosPublic - yPos));
            double distance = Math.sqrt(yDiff*yDiff + xDiff * xDiff);

            double cohStrenght = Main.COHESION_STRENGHT;
            double cohRatio = 0;
            if(distance != 0)
                cohRatio = cohStrenght/distance;

            double sepStrenght = (1 - Math.sqrt(distance/Main.RADIUS)) * Main.SEPARATION_STRENGHT;
            double sepRatio = 0;
            if(distance != 0)
                sepRatio = -sepStrenght/distance;

            Vector aligmentVector = new Vector(boid.xSpeedPublic, boid.ySpeedPublic,Main.ALIGMENT/ammount);
            vectors.add(aligmentVector);
            Vector cohesionVector = new Vector((xDiff * cohRatio), (yDiff * cohRatio),Main.COHESION/ammount);
            vectors.add(cohesionVector);
            Vector separationVector = new Vector((xDiff * sepRatio),(yDiff * sepRatio),Main.SEPARATION/ammount);
            vectors.add(separationVector);
        }

        double[] result = Vector.combineVectors(vectors);
        double xS = result[0];
        double yS = result[1];
        double S = Math.sqrt(xS*xS+yS*yS);
        double ratio = Main.BASE_SPEED / S;
        xSpeed = xS * ratio;
        ySpeed = yS * ratio;
    }
}
