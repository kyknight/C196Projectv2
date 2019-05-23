package com.example.c195projectv2.Models;

/**
 *
 */
public class Note {
    private int id;
    private String title;
    private String text;
    private int courseId;

    /**
     * This method builds a note instance
     * @param id
     * @param title
     * @param text
     * @param courseId
     */
    public Note(int id, String title, String text, int courseId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.courseId = courseId;
    }

    /**
     * This method get yo note the ID
     * @return id
     */
    public int getId() { return this.id; }

    /**
     * This method get yo note title
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * This method get yo note text
     * @return text
     */
    public String getText() {
        return this.text;
    }

    /**
     * This method get yo note related course ID.
     * @return associated course ID
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * This method creates yo note as a string representation
     * @return string repr
     */
    @Override
    public String toString() {
        return this.title;
    }

    /**
     * This method get yo note validation of the model
     * @return is it valid?
     */
    public boolean isValid() {
        if (title.isEmpty() || text.isEmpty()) {
            return false;
        }
        return true;
    }
}
