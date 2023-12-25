package com.projetdiver.lesson;

import com.projetdiver.diver.Diver;

import java.sql.Date;
import java.util.List;

public class Lesson {

    private int id;

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private Diver teacher;

    private List<Diver> diverList;

    private LessonType type;

    //TODO relier avec les Level
    private String levelRequired;

    //TODO relier avec les Review
    private List<String> reviewList;

    public Lesson() {}


    public Lesson(int id, String name, String description, Date startDate, Date endDate, Diver teacher, List<Diver> diverList, LessonType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
        this.diverList = diverList;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Lesson: %s%nDescription: %s%nStart Date: %s%nEnd Date: %s%nTeacher: %s%nType: %s%nLevel Required: %s",
                name, description, startDate, endDate, teacher, type, levelRequired);
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Diver getTeacher() {
        return teacher;
    }

    public void setTeacher(Diver teacher) {
        this.teacher = teacher;
    }

    public List<Diver> getDiverList() {
        return diverList;
    }

    public void setDiverList(List<Diver> diverList) {
        this.diverList = diverList;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public String getLevelRequired() {
        return levelRequired;
    }

    public void setLevelRequired(String levelRequired) {
        this.levelRequired = levelRequired;
    }

    public List<String> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<String> reviewList) {
        this.reviewList = reviewList;
    }
}
