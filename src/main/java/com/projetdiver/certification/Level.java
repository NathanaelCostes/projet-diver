package com.projetdiver.certification;

import javafx.fxml.FXML;

/**
 * Class representing a Level of diving.
 * @author Costes
 */
public class Level {
    private Integer level;

    private CertificationType certificationType;

    /**
     * Default constructor
     */
    public Level() {}

    /**
     * Constructor of a level
     * @param level the level
     * @param certificationType the type of the certification
     */
    public Level(Integer level, CertificationType certificationType) {
        this.level = level;
        this.certificationType = certificationType;
    }

    @Override
    public String toString() {
        return certificationType + " - " + level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public CertificationType getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(CertificationType certificationType) {
        this.certificationType = certificationType;
    }
}
