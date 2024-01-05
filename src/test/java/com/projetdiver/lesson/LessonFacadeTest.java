package com.projetdiver.lesson;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class LessonFacadeTest {


    /**
     * Test the deleteLesson method of the LessonFacade
     */
    @Test
    void deleteLesson() {

        LessonFacade lessonFacade = LessonFacade.getInstance();

        Lesson lesson = new Lesson(1, "Lesson1", "Description1", Date.valueOf("2019-01-01"), Date.valueOf("2019-01-01"), null, LessonType.PRACTICAL);
        lessonFacade.createLesson(lesson, 1);

        assertTrue(lessonFacade.deleteLesson(1));


    }
}