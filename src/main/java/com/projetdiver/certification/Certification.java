package com.projetdiver.certification;

import com.projetdiver.diver.Diver;
import com.projetdiver.lesson.Lesson;

import java.io.File;

/**
 * Class representing a certification type.
 * @author Costes
 */
public class Certification {

    /** Id of the certification */
    private Integer id;

    /** Name of the certification */
    private String name;

    /** Level obtained by the diver */
    private Level levelObtained;

    /** True if it needs to be checked by a teacher or an admin */
    private boolean pending = true;

    /** File of the certification */
    private File file;

    /** Name of the file */
    private String fileName;

    public Certification() {}

    /**
     * Constructor of a certification
     * @param id the id of the certification
     * @param name the name of the certification
     * @param levelObtained the level obtained by the diver
     * @param pending true if it needs to be checked by a teacher or an admin
     * @param file the file of the certification
     */
    public Certification(Integer id, String name,Level levelObtained , boolean pending, File file, String fileName) {
        this.id = id;
        this.name = name;
        this.levelObtained = levelObtained;
        this.pending = pending;
        this.file = file;
        this.fileName = fileName;
    }

    /**
     * @return the certification as a string
     */
    @Override
    public String toString() {
        return name + " - " + levelObtained.toString() + " - " + (pending ? "Pending" : "Validated") + " - " + fileName;
    }

    /**
     * Check if the diver has the certification required to do the lesson
     * @param lesson the lesson
     * @param diver the diver
     * @return true if the diver has the certification required to do the lesson, false otherwise
     */
    public boolean requirementCertification(Lesson lesson, Diver diver) {
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getLevelObtained() {
        return levelObtained;
    }

    public void setLevelObtained(Level levelObtained) {
        this.levelObtained = levelObtained;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
