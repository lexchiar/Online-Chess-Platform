package com.tco.database;

public class Credential {
    final static String USER = "cs414_team15";
    final static String PASSWORD = "B6eNoNJDcm";
    static String useTunnel = System.getenv("CS314_USE_DATABASE_TUNNEL");

    public static String url() {
        if(useTunnel != null && useTunnel.equals("true")) {
            return "jdbc:mariadb://127.0.0.1:56247/cs414_team15";
        } 
        else {
            return "jdbc:mariadb://faure.cs.colostate.edu/cs414_team15";
        }
    }
}
