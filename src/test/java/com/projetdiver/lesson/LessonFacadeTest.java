package com.projetdiver.lesson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LessonFacadeTest {


    /**
     * Test the deleteLesson method of the LessonFacade
     */
    @Test
    void deleteLesson() {
        LessonFacade lessonFacade = LessonFacade.getInstance();
        assertTrue(lessonFacade.deleteLesson(1));
    }
}