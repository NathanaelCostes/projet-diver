package com.projetdiver.lesson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LessonFacadeTest {


    /**
     * Test the deleteLesson method of the LessonFacade
     */
    @Test
    void updateLesson() {
        LessonFacade lessonFacade = LessonFacade.getInstance();
        Lesson lesson = lessonFacade.getLessonById(1);
        lesson.setName("JUnit test modification");
        assertTrue(lessonFacade.updateLesson(lesson));
    }
}