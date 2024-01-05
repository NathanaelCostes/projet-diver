package com.projetdiver.lesson;

import com.projetdiver.dao.PostgreDAOFactory;
import com.projetdiver.review.Review;

import java.util.List;

public class LessonFacade {

    private static LessonFacade instance;

    private LessonFacade() {}


    public static LessonFacade getInstance() {

        if(LessonFacade.instance == null){
            LessonFacade.instance = new LessonFacade();
        }
        return LessonFacade.instance;
    }

    public List<Lesson> getAllLessons() {
        return PostgreDAOFactory.getInstance().createLessonDAO().getAllLessons();
    }

    public boolean deleteLesson(int lesson_id) {
        return PostgreDAOFactory.getInstance().createLessonDAO().deleteLesson(lesson_id);
    }

    public boolean createLesson(Lesson lesson, int teacherId) {
        return PostgreDAOFactory.getInstance().createLessonDAO().createLesson(lesson,teacherId);
    }

    public boolean subscribeToALesson(int lesson_id, int diver_id) {
        return PostgreDAOFactory.getInstance().createLessonDAO().subscribeToALesson(lesson_id, diver_id);
    }

    public boolean isLessonCreator(int diverId, int lessonId) {
        return PostgreDAOFactory.getInstance().createLessonDAO().isLessonCreator(diverId, lessonId);
    }

    public boolean unsubscribeToTheLesson(int lessonId, int diverId) {
        return PostgreDAOFactory.getInstance().createLessonDAO().unsubscribeToTheLesson(lessonId, diverId);
    }

    public boolean isSubscribedToLesson(int diverId, int lessonId) {
        return PostgreDAOFactory.getInstance().createLessonDAO().isSubscribedToLesson(diverId, lessonId);
    }

    public boolean updateLesson(Lesson lesson) {
        return  PostgreDAOFactory.getInstance().createLessonDAO().updateLesson(lesson);
    }

    public Lesson getLessonById(int lessonId) {
        return PostgreDAOFactory.getInstance().createLessonDAO().getLessonById(lessonId);
    }


}
