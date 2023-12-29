package com.projetdiver.lesson.controllers;

import com.projetdiver.SuccessPopup;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.lesson.Lesson;
import com.projetdiver.lesson.LessonFacade;
import com.projetdiver.lesson.LessonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Date;

/**
 * Controller for the lesson creation dialog.
 * @author Costes
 */
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
    private ChoiceBox<LessonType> typeChoiceBox;

    @FXML
    private Label errorLabel;

    public void initialize() {
        // Set values for the ChoiceBox using the enum values
        ObservableList<LessonType> types = FXCollections.observableArrayList(LessonType.values());
        typeChoiceBox.setItems(types);
    }

    /**
     * Create a lesson with the values from the form.
     */
    public void createLesson() {
        Lesson lesson = new Lesson();
        lesson.setName(nameTextField.getText());
        lesson.setDescription(descriptionTextField.getText());
        lesson.setStartDate(Date.valueOf(startDatePicker.getValue()));
        lesson.setEndDate(Date.valueOf(endDatePicker.getValue()));
        lesson.setType(typeChoiceBox.getValue());
        System.out.println(Date.valueOf(endDatePicker.getValue()));

        int diverId = DiverFacade.getInstance().getCurrentDiver().getId();

        //TODO verifier son niveau
        boolean success = LessonFacade.getInstance().createLesson(lesson,diverId);

        if(success && diverId != -1 ) {
            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.close();
            SuccessPopup.showWithOwner("Lesson created successfully", stage);
        } else {
            errorLabel.setText("An error occurred while creating the lesson");
        }
    }
}
