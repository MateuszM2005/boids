package com.company;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    final static double SEPARATION = 20;
    final static double COHESION = 10;
    final static double ALIGMENT = 0.5;
    final static double INERTIA = 10;
    final static double BASE_SPEED = 5;

    final static double COHESION_STRENGHT = BASE_SPEED;
    final static double SEPARATION_STRENGHT = 10 * BASE_SPEED;

    final static int RADIUS = 500;
    final static int AMMOUNT = 100;


    final static int WIDHT = 1600;
    final static int HEIGHT = 900;
    static Boid[] boids = new Boid[AMMOUNT];


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root,WIDHT,HEIGHT);

        for(int i = 0; i < AMMOUNT; i++){
            boids[i] = new Boid();

            root.getChildren().add(boids[i].circle);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),event -> {
            for(int i = 0; i < AMMOUNT;i++){
                boids[i].algorithm();
            }
            for(int i = 0; i < AMMOUNT;i++){
                boids[i].move();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
