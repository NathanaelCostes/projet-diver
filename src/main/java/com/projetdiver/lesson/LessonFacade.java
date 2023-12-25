package com.projetdiver.lesson;

import com.projetdiver.dao.PostgreDAOFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
        List<Lesson> lessonList = PostgreDAOFactory.getInstance().createLessonDAO().getAllLessons();

        return lessonList;
    }

    public boolean deleteLesson(int lesson_id) {
        return PostgreDAOFactory.getInstance().createLessonDAO().deleteLesson(lesson_id);
    }

    public boolean createLesson(Lesson lesson) {
        return PostgreDAOFactory.getInstance().createLessonDAO().createLesson(lesson);
    }

}
