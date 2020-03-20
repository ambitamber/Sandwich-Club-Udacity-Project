package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)
    {
        Sandwich sandwich;
        JSONObject jsonObject;
        String mName = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;

        try{
            JSONObject root = new JSONObject(json);
            jsonObject = root.getJSONObject(Constants.nameJson);
            mName = jsonObject.getString(Constants.mainNameJson);

            JSONArray alsoKnownAs_JA = jsonObject.getJSONArray(Constants.alsoKnownAsJson);
            alsoKnownAs = new ArrayList<>();

            if (alsoKnownAs_JA.length() != 0){
                for (int i = 0; i < alsoKnownAs_JA.length(); i++) {
                    alsoKnownAs.add(alsoKnownAs_JA.getString(i));
                }
            }
            placeOfOrigin = root.optString(Constants.placeOfOriginJson);
            description = root.optString(Constants.descriptionJson);
            image = root.optString(Constants.imageJson);
            JSONArray ingredients_JA = root.getJSONArray(Constants.ingredientsJson);
            ingredients = new ArrayList<>();
            if (ingredients_JA.length() != 0) {
                for (int i = 0; i < ingredients_JA.length(); i++) {
                    ingredients.add(ingredients_JA.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sandwich = new Sandwich(mName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        return sandwich;
    }
}
