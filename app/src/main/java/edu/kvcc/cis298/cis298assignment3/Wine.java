package edu.kvcc.cis298.cis298assignment3;

import java.util.UUID;

/**
 * Created by dhatt_000 on 11/14/2016.
 */

public class Wine {

    //Variables
    private UUID mId;
    private String mDescription;
    private String mCase;
    private double mPrice;
    private boolean mActive;

    //Make a 4 parameter constructor to restore a Wine Item from stored data
    public Wine(UUID uuid, String description, String caseSize, double price, boolean isActive) {
        mId = uuid;
        mDescription = description;
        mCase = caseSize;
        mPrice = price;
        mActive = isActive;
    }

    //Getters and Setters
    public UUID getId() {
        return mId;
    }

    public void setId(UUID ID) { mId = ID;  }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }

    public String getCase() {
        return mCase;
    }

    public void setCase(String caseSize){ mCase = caseSize; }

    public double getPrice() { return mPrice; }

    public void setPrice(double price){ mPrice = price; }

}
