package com.projetdiver.lesson;

import java.util.List;

/**
 * Class representing a lesson of diving.
 * @author Costes
 */
public abstract class LessonDAO {
    private static LessonDAO instance;

    /**
     * Default constructor
     */
    public LessonDAO() {} //TODO mettre en privé

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
     * @param lesson the lesson to create
     * @return true if the lesson was created, false otherwise
     */
    public abstract boolean createLesson(Lesson lesson, int teacherId);

    /**
     * Delete a lesson
     * @param lessonId the id of the lesson
     * @return true if the lesson was deleted, false otherwise
     */
    public abstract boolean deleteLesson(int lessonId);

    /**
     * Subscribe a diver to a lesson
     * @param lessonId the id of the lesson
     * @param diverId the diver that wants to subscribe to the lesson
     * @return true if the subscription was successful, false otherwise
     */
    public abstract boolean subscribeToALesson(int lessonId, int diverId);



    /**
     * Update a lesson
     * @param lesson the lesson to update
     * @return true if the lesson was updated, false otherwise
     */
    public abstract boolean updateLesson(Lesson lesson);

    /**
     * Get all the lessons
     * @return a list of all the lessons
     */
    public abstract List<Lesson> getAllLessons();

    /**
     * Check if a diver is the creator of a lesson
     * @param diverId the id of the diver
     * @param lessonId the id of the lesson
     * @return true if the diver is the creator of the lesson, false otherwise
     */
    public abstract boolean isLessonCreator(int diverId, int lessonId);

    /**
     * Check if a diver is subscribed to a lesson
     * @param diverId the id of the diver
     * @param lessonId  the id of the lesson
     * @return true if the diver is subscribed to the lesson, false otherwise
     */
    public abstract boolean isSubscribedToLesson(int diverId, int lessonId);

    /**
     *  Unsubscribe a diver to a lesson he was subscribed to
     * @param lessonId the id of the lesson
     * @param diverId the id of the diver
     * @return true if the unsubscription was successful, false otherwise
     */
    public abstract boolean unsubscribeToTheLesson(int lessonId, int diverId);
}
