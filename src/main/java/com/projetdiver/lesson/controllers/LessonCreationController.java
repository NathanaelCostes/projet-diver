package com.projetdiver.lesson.controllers;

import com.projetdiver.lesson.Lesson;
import com.projetdiver.lesson.LessonFacade;
import com.projetdiver.lesson.LessonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

public class LessonCreationController {

    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField descriptionTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ChoiceBox typeChoiceBox;

    public void initialize() {
        // Set values for the ChoiceBox using the enum values
        ObservableList<LessonType> types = FXCollections.observableArrayList(LessonType.values()); //TODO peut etre recup le LessonType d'ailleur : reduire coupling
        typeChoiceBox.setItems(types);
    }


    public void createLesson(ActionEvent actionEvent) {
        Lesson lesson = new Lesson();
        lesson.setName(nameTextField.getText());
        lesson.setDescription(descriptionTextField.getText());
        lesson.setStartDate(Date.valueOf(startDatePicker.getValue()));
        lesson.setEndDate(Date.valueOf(endDatePicker.getValue()));
        lesson.setType((LessonType) typeChoiceBox.getValue());
        System.out.println(Date.valueOf(endDatePicker.getValue()));

        boolean success = LessonFacade.getInstance().createLesson(lesson);

        if(success){
            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.close();
        } else {
            //TODO : display error
        }
    }


}
