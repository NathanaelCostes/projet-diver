package com.projetdiver.lesson;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class LessonFacadeTest {


    /**
     * Test the updateLesson method of the LessonFacade
     */
    @Test
    void updateLesson() {
        LessonFacade lessonFacade = LessonFacade.getInstance();
        Lesson lesson = lessonFacade.getLessonById(1);
        lesson.setName("JUnit test modification");
        assertTrue(lessonFacade.updateLesson(lesson));
    }
}