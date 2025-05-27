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

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class MainController implements Initializable{

    // Generation Controls
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
    private Label rTimer;

    // Song Timer Control
    @FXML
    private Button sStartButton;

    @FXML
    private Button sPauseButton;

    @FXML
    private Button sStopButton;
    
    @FXML
    private Label sTimer;

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

    }

    @FXML
    void generateKey() {
        String nextKey = keys[rand.nextInt(keys.length)];
        keyLabel.setText(nextKey);
    }

    @FXML
    void rListChanged() {
        rTimer.setText(Integer.toString(rList.getSelectionModel().getSelectedItem()) + ":00");
    }

    @FXML
    void sListChanged() {
        sTimer.setText(Integer.toString(sList.getSelectionModel().getSelectedItem()) + ":00");
    }

}
