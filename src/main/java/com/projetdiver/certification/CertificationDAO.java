package com.projetdiver.certification;

import java.util.List;

/**
 * Class defining the different operations over a certification.
 * @author Costes
 */
public abstract class CertificationDAO {
    private static CertificationDAO instance;

    /**
     * Default constructor
     */
    public CertificationDAO() {}

    /**
     * Get the instance of the CertificationDAO or crear it if it doesn't exist
     * @return the instance of the CertificationDAO
     */
    public static CertificationDAO getInstance() {
        if (instance == null) {
            instance = new CertificationDAOPostgre();
        }
        return instance;
    }

    /**
     * Get all the certifications in the database
     * @return the list of all the certifications
     */
    public abstract List<Certification> getAllCertificationsOfDiver(int diverId);

    /**
     * Get all the certifications required to do a lesson
     * @param lessonId the id of the lesson
     * @return the list of all the certifications required to do the lesson
     */
    public abstract List<Certification> getAllCertificationsOfLesson(int lessonId);


    /**
     * Create a certification for a diver in the database
     * @param certification the certification to create
     * @param diverId the id of the diver
     */
    public abstract boolean createCertification(Certification certification, int diverId);

    /**
     * Update a certification in the database
     * @param certification the certification to update
     */
    public abstract boolean updateCertification(Certification certification);

    /**
     * Delete a certification in the database
     * @param certificationId the id of the certification
     */
    public abstract void deleteCertification(int certificationId);

    /**
     * Get all the certifications in the database
     * @return the list of all the certifications
     */
    public abstract List<Certification> getAllCertifications();

    /**
     * Get a certification by its id
     * @param certificationId the id of the certification
     * @return the certification
     */
    public abstract Certification getCertificationById(Integer certificationId);
}
