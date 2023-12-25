package com.projetdiver.lesson;

import java.util.List;

public abstract class LessonDAO {
    private static LessonDAO instance;

    public LessonDAO() {}

    /**
     * Get the instance of the LessonDAO or crear it if it doesn't exist
     * @return the instance of the LessonDAO
     */
    public static LessonDAO getInstance() {
        if (instance == null) {
            instance = new LessonDAOPostgre();
        }
        return instance;
    }

    /**
     * Get a lesson by its id
     * @param id the id of the lesson
     * @return the lesson
     */
    public abstract Lesson getLessonById(int id);

    /**
     * Create a lesson
     * @param lesson
     * @return true if the lesson was created, false otherwise
     */
    public abstract boolean createLesson(Lesson lesson);

    /**
     * Delete a lesson
     * @param lesson_int
     * @return true if the lesson was deleted, false otherwise
     */
    public abstract boolean deleteLesson(int lesson_int);

    /**
     * Update a lesson
     * @param lesson
     * @return true if the lesson was updated, false otherwise
     */
    public abstract boolean updateLesson(Lesson lesson);


    public abstract List<Lesson> getAllLessons();


}
