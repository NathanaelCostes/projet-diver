package com.projetdiver.session;

import com.projetdiver.diver.Diver;

import java.sql.Date;

public class Session {

    /** id of the session */
    private Integer sessionId;

    /** title of the session */
    private String title;

    /** date of the session */
    private Date date;

    /** comment of the session */
    private String comment;

    /** duration of the session */
    private Float duration;

    /** temperature of the session */
    private Integer temp;

    /** depth of the session */
    private Integer depth;

    private Diver owner;

    /**
     * Create a session
     * @param date of the session
     * @param comment of the session
     * @param duration of the session
     * @param temp temperature of the session
     * @param depth of the session
     * @param owner of the session
     */
    public Session(Integer sessionId, String title, Date date, String comment, Float duration, Integer temp, Integer depth, Diver owner) {
        this.sessionId = sessionId;
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.duration = duration;
        this.temp = temp;
        this.depth = depth;
        this.owner = owner;
    }

    /** Default constructor */
    public Session() {}

    /**
     * @return sessionId
     */
    public Integer getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return duration
     */
    public Float getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Float duration) {
        this.duration = duration;
    }

    /**
     * @return temp
     */
    public Integer getTemp() {
        return temp;
    }

    /**
     * @param temp the temperature to set
     */
    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    /**
     * @return depth
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * @return the owner of the session
     */
    public Diver getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                ", duration=" + duration +
                ", temp=" + temp +
                ", depth=" + depth +
                ", owner=" + owner +
                '}';
    }
}
