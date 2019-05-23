package com.example.c195projectv2.Models;

/**
 *
 */
public class Mentor {
    private int id;
    private String name;
    private String phone;
    private String email;
    private int courseId;

    /**
     * This method builds a mentor instance
     * @param id
     * @param name
     * @param phone
     * @param email
     * @param courseId
     */
    public Mentor(int id, String name, String phone, String email, int courseId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.courseId = courseId;
    }

    /**
     * This method get yo mentor ID
     * @return id
     */
    public int getId() { return this.id; }

    /**
     * This method get yo mentor email
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * This method get yo mentor name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method get yo mentor phone number
     * @return phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * This method get yo mentor related course ID.
     * @return courseId
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * This method get yo string representation
     * @return
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * This method get yo mentor validation of the model
     * @return
     */
    public boolean isValid() {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            return false;
        }
        return true;
    }
}
