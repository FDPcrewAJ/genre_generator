package com.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
//import java.lang.classfile.Label;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class MainController implements Initializable{

    // Key and Genre generation Controls
    @FXML
    private Button genreButton;

    @FXML
    private Label genreLabel;

    @FXML
    private Button keyButton;

    @FXML
    private Label keyLabel;

    private String[] keys = {"C Maj/A Min",
                            "G Maj/E Min",
                            "D Maj/B Min",
                            "A Maj/F Sharp Min",
                            "E Maj/C Sharp Min",
                            "B Maj/G Sharp Min",
                            "F Sharp Maj/D Sharp Min",
                            "C Sharp Maj/A Sharp Min",
                            "F Maj/D Min",
                            "B Flat Maj/G Min",
                            "E Flat Maj/C Min",
                            "A Flat Maj/F Min",
                            "D Flat Maj/B Flat Min",
                            "G Flat Maj/E Flat Min",
                            "C Flat Maj/A Flat Min"};
    
    private Random rand = new Random();

    private Boolean genreGen = false;

    // Time Selection lists
    @FXML
    private ListView<Integer> rList;

    @FXML
    private ListView<Integer> sList;

    // Research Timer Control
    @FXML
    private Button rStartButton;

    @FXML
    private Button rPauseButton;

    @FXML
    private Button rStopButton;

    @FXML
    private Label rTimerLabel;

    // Song Timer Control
    @FXML
    private Button sStartButton;

    @FXML
    private Button sPauseButton;

    @FXML
    private Button sStopButton;
    
    @FXML
    private Label sTimerLabel;

    // Timer Control Variables
    private boolean timerActive = false;

    private Label curLabel = rTimerLabel;

    Timer rTimer = new Timer();

    Timer sTimer = new Timer();

    private int timerMinutes = 0;

    private int secondsLeft = 59;

    // Function to create the countdown timer task for the chosen timer
    private TimerTask createTimerTask(Timer timer) {
        TimerTask minuteCountdown = new TimerTask() {
        
            int count = secondsLeft;
    
            @Override
            public void run(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run(){
                        timerActive = true;
                        count--;
                        secondsLeft = count;
                        updateLabel();
                        //System.out.println(count);
                        if (count <= 0) {
                            count = 59;
                            if (timerMinutes <= 0) {
                                timer.cancel();
                                timerActive = false;
                            }
                            else {
                                timerMinutes--;
                            }
                        }
                    }
                });
            }
        };
        return minuteCountdown;
    }


    // Adds values to the ListView boxes
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        rList.getItems().add(1);
        rList.getItems().add(2);
        rList.getItems().add(3);
        rList.getItems().add(4);
        rList.getItems().add(5);

        sList.getItems().add(5);
        sList.getItems().add(10);
        sList.getItems().add(15);
        sList.getItems().add(20);
        sList.getItems().add(30);
    }


    // Generates a random genre using a binaryjazz api call
    @FXML
    void generateGenre() throws URISyntaxException, IOException {

        URI uri = new URI("https://binaryjazz.us/wp-json/genrenator/v1/genre/");
        URL url = uri.toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("Response Error Code: " + responseCode);
        }
        else {
            StringBuilder apiString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                apiString.append(scanner.nextLine());
            }
            scanner.close();
            
            String genreString = apiString.toString().substring(1, apiString.length() - 1);

            genreLabel.setText(genreString);
        }

        genreGen = true;

    }


    // Gets a random value from the array of keys
    @FXML
    void generateKey() {
        String nextKey = keys[rand.nextInt(keys.length)];
        keyLabel.setText(nextKey);
    }


    // Updates the timer text when a new item is selected
    // from the research timer ListVeiw
    @FXML
    void rListChanged() {
        if (!timerActive) {
            curLabel = rTimerLabel;
            secondsLeft = 1;
            timerMinutes = rList.getSelectionModel().getSelectedItem();
            rTimerLabel.setText(timerMinutes + ":00");
        }
        
    }
        

    // Updates the timer text when a new item is selected
    // from the song timer ListVeiw
    @FXML
    void sListChanged() {
        if (!timerActive) {
            curLabel = sTimerLabel;
            secondsLeft = 1;
            timerMinutes = sList.getSelectionModel().getSelectedItem();
            sTimerLabel.setText(timerMinutes + ":00");
        }
    }


    // Starts the research timer
    @FXML
    void startRTimer() {
        if (genreGen) {
            curLabel = rTimerLabel;
            rTimer.schedule(createTimerTask(rTimer), 0, 1000);
            updateLabel();
            timerActive = true;
        }
    }

    
    // Pauses the research timer
    @FXML
    void pauseRTimer() {
        rTimer.cancel();
        Timer timer2 = new Timer();
        rTimer = timer2;
    }


    // Stops the research timer and resets to 0
    @FXML
    void stopRTimer() {
        rTimer.cancel();
        Timer timer2 = new Timer();
        rTimer = timer2;
        rTimerLabel.setText("0:00");
        timerMinutes = 0;
        secondsLeft = 59;
        timerActive = false;
    }


    // Starts the song timer
    @FXML
    void startSTimer() {
        if (genreGen) {
            curLabel = sTimerLabel;
            sTimer.schedule(createTimerTask(sTimer), 0, 1000);
            updateLabel();
            timerActive = true;
        }
    }


    // Pauses the song timer
    @FXML
    void pauseSTimer() {
        sTimer.cancel();
        Timer timer2 = new Timer();
        sTimer = timer2;
    }


    // Stops the song timer
    @FXML
    void stopSTimer() {
        sTimer.cancel();
        Timer timer2 = new Timer();
        sTimer = timer2;
        sTimerLabel.setText("0:00");
        timerMinutes = 0;
        secondsLeft = 59;
        timerActive = false;
    }
    

    // Sets timer's time and updates the respective label while the timer is counting down
    private void updateLabel() {
        if (secondsLeft < 10) {
            curLabel.setText(timerMinutes + ":0" + secondsLeft);
        }
        else {
            curLabel.setText(timerMinutes + ":" + secondsLeft);
        }
        
    }

}
