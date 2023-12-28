package com.projetdiver.lesson.controllers;

import com.projetdiver.lesson.Lesson;
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
 * Controller for the lesson modification dialog.
 * @author Costes
 */
public class LessonModificationController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ChoiceBox<LessonType>  typeChoiceBox;

    @FXML
    private Label errorLabel;

    private Lesson lesson;

    private Stage stage;

    /**
     * Initialize the form with the lesson to modify.
     */
    public void initialize() {
        // Set values for the ChoiceBox using the enum values
        ObservableList<LessonType> types = FXCollections.observableArrayList(LessonType.values());
        typeChoiceBox.setItems(types);
    }


    /**
     * Set the lesson to modify. Used to prefill the fields.
     * @param lesson the lesson to modify
     */
    public void setLessonToModify(Lesson lesson) {
        if (lesson != null) {
            this.lesson = lesson;
            nameTextField.setText(lesson.getName());
            descriptionTextField.setText(lesson.getDescription());
            startDatePicker.setValue(lesson.getStartDate().toLocalDate());
            endDatePicker.setValue(lesson.getEndDate().toLocalDate());
            typeChoiceBox.setValue(lesson.getType());
        }
    }

    /**
     * Set the stage of this dialog.
     * @param stage the stage of this dialog
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    /**
     * Modify the lesson with the new values.
     */
    public void modifyLesson() {
        lesson.setName(nameTextField.getText());
        lesson.setDescription(descriptionTextField.getText());
        lesson.setStartDate(Date.valueOf(startDatePicker.getValue()));
        lesson.setEndDate(Date.valueOf(endDatePicker.getValue()));
        lesson.setType(typeChoiceBox.getValue());

        boolean success = com.projetdiver.lesson.LessonFacade.getInstance().updateLesson(lesson);
        if (success) {
            stage.close();
        } else {
            errorLabel.setText("Error while modifying the lesson");
        }
    }
}
