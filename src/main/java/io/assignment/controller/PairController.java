package io.assignment.controller;

import io.assignment.model.Pair;
import io.assignment.model.Record;
import io.assignment.utils.Analyzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PairController {

    File targetFile = null;
    @FXML private Button selectorButton;
    @FXML private Button executorButton;
    @FXML private TableView<Pair> recordTable;
    @FXML private TableColumn<Pair, Integer> personnelId1;
    @FXML private TableColumn<Pair, Integer> personnelId2;
    @FXML private TableColumn<Pair, Integer> projectId;
    @FXML private TableColumn<Pair, Integer> daysWorkedWith;

    @FXML
    public void chooseFile(){
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        targetFile = fc.showOpenDialog(null);

        System.out.println(targetFile.getAbsolutePath());

    }

    @FXML
    public void fillTable() {
        personnelId1.setCellValueFactory(new PropertyValueFactory<Pair, Integer>("personnelId1"));
        personnelId2.setCellValueFactory(new PropertyValueFactory<Pair, Integer>("personnelId2"));
        projectId.setCellValueFactory(new PropertyValueFactory<Pair, Integer>("projectIdWorkedOn"));
        daysWorkedWith.setCellValueFactory(new PropertyValueFactory<Pair, Integer>("daysWorkedWith"));

        ObservableList<Pair> observableList = Analyzer.analyzeFile(targetFile);

        //ObservableList<Pair> observableList = FXCollections.<Pair>observableArrayList(new Pair(1,1,1,1));
        recordTable.setItems(observableList);
    }
}