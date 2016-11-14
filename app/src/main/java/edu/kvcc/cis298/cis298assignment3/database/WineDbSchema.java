package edu.kvcc.cis298.cis298assignment3.database;

/**
 * Created by dhatt_000 on 11/14/2016.
 */

public class WineDbSchema {

    //This inner class will define a constant for the name
    //of the table that we will be creating
    public static final class WineTable {
        public static final String NAME = "wines";

        //This inner class will define some constants for the name
        //of the columns in the table that we will be creating
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DESCRIPTION = "description";
            public static final String CASESIZE = "case";
            public static final String PRICE = "price";
            public static final String ACTIVE = "active";
        }
    }
}
