package com.projetdiver.certification;

import java.util.List;

public class CertificationFacade {
    private static CertificationFacade instance;

    private CertificationFacade() {}

    /**
     * Gets the instance of the singleton
     * @return the instance of the singleton
     */
    public static CertificationFacade getInstance() {
        if (CertificationFacade.instance == null) {
            CertificationFacade.instance = new CertificationFacade();
        }
        return CertificationFacade.instance;
    }

    /**
     * Creates a certification
     * @param certification the certification to create
     * @param diverId the id of the diver to whom the certification belongs
     * @return true if the certification was created, false otherwise
     */
    public boolean createCertification(Certification certification, int diverId) {
        return CertificationDAO.getInstance().createCertification(certification, diverId);
    }

    /**
     * Updates a certification
     * @param certification the certification to update
     * @return true if the certification was updated, false otherwise
     */
    public boolean updateCertification(Certification certification) {
        return CertificationDAO.getInstance().updateCertification(certification);
    }

    /**
     * Deletes a certification
     * @param certificationId the id of the certification to delete
     */
    public void deleteCertification(int certificationId) {
        CertificationDAO.getInstance().deleteCertification(certificationId);
    }

    /**
     * Gets all the certifications of a diver
     * @param diverId the id of the diver
     * @return the List of certifications of this diver
     */
    public List<Certification> getAllCertificationsOfDiver(int diverId) {
        return CertificationDAO.getInstance().getAllCertificationsOfDiver(diverId);
    }

    /**
     * Gets all the certifications of a lesson
     * @param lessonId the id of the lesson
     * @return the List of certifications of this lesson
     */
    public List<Certification> getAllCertificationsOfLesson(int lessonId) {
        return CertificationDAO.getInstance().getAllCertificationsOfLesson(lessonId);
    }

    /**
     * Gets all the certifications
     * @return the List of certifications
     */
    public List<Certification> getAllCertifications() {
        return CertificationDAO.getInstance().getAllCertifications();
    }
}
