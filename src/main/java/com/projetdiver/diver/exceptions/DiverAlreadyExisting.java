package com.projetdiver.diver.exceptions;

/**
 * DiverAlreadyExisting is an exception that is raised when a diver is already existing in the database
 */
public class DiverAlreadyExisting extends Exception{

        /**
        * Creates a DiverAlreadyExisting
        * @param errorMessage
        */
        public DiverAlreadyExisting(String errorMessage) {
            super(errorMessage);
        }


}
