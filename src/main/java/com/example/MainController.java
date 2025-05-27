package com.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
//import java.lang.classfile.Label;
import java.net.URL;
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
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'initialize'");
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
        keyLabel.setText("key worked");
        System.out.print("key worked");
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
