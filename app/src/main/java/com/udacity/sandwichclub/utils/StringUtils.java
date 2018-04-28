package com.udacity.sandwichclub.utils;

import java.util.List;

/**
 * Created by david-rainey on 4/27/18.
 */

public class StringUtils {
    /* Convenience method that takes a list of strings and returns a comma separated String
     * representation of the list.
     */
    public static String getListAsCommaSeparatedString(List<String> list){
        String listString = "";
        int listLength = list.size();
        for(int i = 0; i < listLength - 1; i++){
            if(i < listLength - 2){
                listString += list.get(i) + ", ";
            } else {
                listString += list.get(i);
            }
        }

        return listString;
    }
}
