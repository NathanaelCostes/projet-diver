package com.projetdiver.review;

public class Review {

    /** Id of the review */
    private Integer id;

    /** Id of the diver */
    private Integer diverId;

    /** Title of the review */
    private String title;

    /** Description of the review */
    private String description;

    /** Rating of the review */
    private Integer rating;

    /**
     * Creates a Review
     * @param id the id of the review
     * @param diverId the id of the diver
     * @param title the title of the review
     * @param description the description of the review
     * @param rating the rating of the review
     */
    public Review(Integer id, Integer diverId, String title, String description, Integer rating) {
        this.id = id;
        this.diverId = diverId;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    /** Default Constructor */
    public Review(){}

    /**
     * Get the id of the review
     * @return the id of the review
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get the id of the diver
     * @return the id of the diver
     */
    public Integer getDiverId() {
        return diverId;
    }

    /**
     * Get the title of the review
     * @return the title of the review
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the description of the review
     * @return the description of the review
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the rating of the review
     * @return the rating of the review
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Set the id of the review
     * @param id the id of the review
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set the title of the review
     * @param title the title of the review
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the description of the review
     * @param description the description of the review
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the rating of the review
     * @param rating the rating of the review
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }


    /**
     * Display the review
     * @return the review
     */
    public String toString() {
    	return "Review [id=" + id + ", diverId=" + diverId + ", title=" + title + ", description=" + description + ", rating=" + rating + "]";
    }


}
