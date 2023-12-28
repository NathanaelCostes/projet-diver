package com.projetdiver.lesson;

import com.projetdiver.diver.Diver;

import java.sql.Date;
import java.util.List;

/**
 * Class representing a lesson of diving.
 * @see LessonType
 * @see Diver
 * @author Costes
 */
public class Lesson {

    /** Id of the lesson */
    private int id;

    /** Name of the lesson */
    private String name;

    /** Description of the lesson */
    private String description;

    /** Start date of the lesson */
    private Date startDate;

    /** End date of the lesson */
    private Date endDate;

    /** List of the divers registered to the lesson */
    private List<Diver> diverList;

    /** Type of the lesson */
    private LessonType type;

    //TODO relier avec les Level
    /** Level required to register to the lesson */
    private String levelRequired;

    //TODO relier avec les Review
    /** List of the reviews of the lesson */
    private List<String> reviewList;

    public Lesson() {}

    public Lesson(int id, String name, String description, Date startDate, Date endDate, List<Diver> diverList, LessonType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.diverList = diverList;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Lesson: %s%nDescription: %s%nStart Date: %s%nEnd Date: %s%nType: %s%nLevel Required: %s",
                name, description, startDate, endDate, type, levelRequired);
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
