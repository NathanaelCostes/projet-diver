package com.projetdiver.certification;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CertificationFacadeTest {

    /**
     * Test the createCertification method of the CertificationFacade
     */
    @Test
    void createCertification() {
        CertificationFacade certificationFacade = CertificationFacade.getInstance();
        Certification certif;
        Level level = new Level(1, CertificationType.AutonomousDiver);

        // Obtenez le répertoire de travail du projet
        String projectDir = System.getProperty("user.dir");

        // Créez le chemin relatif du fichier par rapport au répertoire de travail
        String filePath = projectDir + File.separator + "test.pdf";


        File file = new File("src/test/java/com/projetdiver/certification/test.pdf");

        certif = new Certification(null, "PADI", level, true, file, "test.pdf");
        assertTrue(certificationFacade.createCertification(certif, 1));
    }
}