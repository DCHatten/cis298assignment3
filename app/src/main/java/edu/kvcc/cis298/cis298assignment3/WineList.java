package edu.kvcc.cis298.cis298assignment3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import edu.kvcc.cis298.cis298assignment3.database.WineListHelper;

/**
 * Created by dhatt_000 on 11/14/2016.
 */

public class WineList {
    //Static variable to hold the instance of the CrimeLab
    private static WineList sWineList;
    private List<Wine> mWines;

    //Class level variable to hold the sqlite database.
    private SQLiteDatabase mDatabase;
    //This context will be the hosting activity. It will get
    //assigned in the constructor.
    private Context mContext;

    //This is a static get method to get the single instance of the class.
    public static WineList get(Context context) {
        //If we dont' have an instace, we create a new one.
        if (sWineList == null) {
            sWineList = new WineList(context);
        }
        //Regardless of whether we created it, or
        //it already existed, we need to return it
        return sWineList;
    }

    //This is the constructor. It is private.
    //It can't be used from outside classes.
    private WineList(Context context) {
        //Create a new array list of crimes
        mWines = new ArrayList<>();
        //Set the class level context to the one passed in.
        mContext = context.getApplicationContext();
        //Set the class level database
        mDatabase = new WineListHelper(mContext)
                .getWritableDatabase();

        //Load the crime list
        loadWineList();
    }

    public void addWine(Wine c) {
        mWines.add(c);
    }

    public List<Wine> getWines() {
        return mWines;
    }

    public Wine getWine(UUID id) {
        for (Wine wine : mWines) {
            //If the current wine we are looking at has a id that
            //matches the passed in one, we return the wine
            if (wine.getId().equals(id)) {
                return wine;
            }
        }
        //Didn't find a match, return null.
        return null;
    }

    private void loadWineList() {

        //Define a Scanner which will be used to read in the file
        Scanner scanner = null;

        try {
            //instanciate a new scanner
            //Use some method chaining to get access to the file.
            //Use the context to get at resources, and once we have the resources
            //call openRawResource passing in the location of the file.
            //The location of the file will be R.raw."The name of the file"
            //Remember that files here must be all lowercase.
            scanner = new Scanner(mContext.getResources().openRawResource(R.raw.beverage_list));

            //while the scanner has another line to read
            while(scanner.hasNextLine()) {

                //Get the next line and split it into parts
                String line = scanner.nextLine();
                String parts[] = line.split(",");

                //Assign each part to a local variable
                String stringUUID = parts[0];
                String description = parts[1];
                String caseSize = parts[2];
                String stringPrice = parts[3];
                String stringActive = parts[4];

                //Setup some vars for doing parsing
                UUID uuid = UUID.fromString(stringUUID);

                double price = Double.parseDouble(stringPrice);

                //Set a bool as to whether it is active or not
                //This is a ternary operator. It is a shorthand if else
                //The part between the ? and : happens when true.
                //The part after the : happens when false.
                boolean active = (stringActive.equals("1")) ? true : false;

                //Add the Crime to the list
                mWines.add(new Wine(uuid, description, caseSize, price, active));
            }

        } catch (Exception e) {
            //On exception, just log out the exception to string
            Log.e("Read CSV", e.toString());
        } finally {
            //Check to make sure that the scanner actually got instanciated
            //and if so, close it.
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
