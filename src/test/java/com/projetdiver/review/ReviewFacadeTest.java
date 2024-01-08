package com.projetdiver.review;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewFacadeTest {

    /**
     * Test the updateReview method of the ReviewFacade
     */
    @Test
    void updateReview() {
        ReviewFacade reviewFacade = ReviewFacade.getInstance();
        Review review = reviewFacade.getReview(1);
        review.setTitle("JUnit test modification");
        assertTrue(reviewFacade.updateReview(review));
    }
}
