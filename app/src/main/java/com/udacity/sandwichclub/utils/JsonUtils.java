package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static String TAG = JsonUtils.class.getName();
    public static final String SANDWICH_OBJECT_NAME = "name";
    public static final String SANDWICH_MAIN_NAME = "mainName";
    public static final String SANDWICH_AKA_ARRAY = "alsoKnownAs";
    public static final String SANDWICH_ORIGIN = "placeOfOrigin";
    public static final String SANDWICH_DESCRIPTION = "description";
    public static final String SANDWICH_IMAGE = "image";
    public static final String SANDWICH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        if(json != null || json.trim().length() != 0){
            try {
                JSONObject object = new JSONObject(json);
                JSONObject nameObject = object.getJSONObject(SANDWICH_OBJECT_NAME);
                String name = nameObject.getString(SANDWICH_MAIN_NAME);
                JSONArray akaArray = nameObject.getJSONArray(SANDWICH_AKA_ARRAY);
                String origin = object.getString(SANDWICH_ORIGIN);
                String description = object.getString(SANDWICH_DESCRIPTION);
                String image = object.getString(SANDWICH_IMAGE);
                JSONArray ingredientsArray = object.getJSONArray(SANDWICH_INGREDIENTS);

                List<String> akaList = populateList(SANDWICH_AKA_ARRAY, akaArray);
                List<String> ingredients = populateList(SANDWICH_INGREDIENTS, ingredientsArray);
                sandwich = new Sandwich(name, akaList, origin, description, image, ingredients);

            }catch (JSONException e){
                Log.e(TAG, "Error parsing passed in JSON string", e);
            }
        }
        return sandwich;
    }

    public static List<String> populateList(String name, JSONArray array){
        List<String> list = new ArrayList<>();
        if(array.length() > 0){
            try {
                for (int i = 0; i < array.length(); i++) {
                    Object obj = array.get(i);
                    if(obj != null){
                        list.add(array.get(i).toString());
                    }
                }
            }catch (JSONException e){
                Log.e(TAG, "Error in populateList for: " + name, e);
                list = null;
            }
        }
        return list;
    }

}
