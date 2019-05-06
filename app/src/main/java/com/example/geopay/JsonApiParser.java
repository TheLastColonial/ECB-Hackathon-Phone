package com.example.geopay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonApiParser {

    public static impMerchantList getMerchants(String data) throws JSONException {
        impMerchantList merchantList = new impMerchantList();

        JSONObject Obj = new JSONObject(data);

        JSONArray arrayJson = (JSONArray) Obj.get("merchant");
        // iterating merchants
        for (int i = 0; i < arrayJson.length(); i++) {
            impMerchantLocation merch = new impMerchantLocation();
            JSONObject currentObj = arrayJson.getJSONObject(i);
            merch.setLatitude(getLat("Latitude", currentObj));
            merch.setLongitude(getLng("Longitude", currentObj));
            merch.setRadius(getRad("Radius", currentObj));
            merch.setMerchantId(getId("MerchantId", currentObj));
            merch.setGoogleRef(getRef("GoogleReference", currentObj));

            merchantList.merchantList.add(merch);
        }

        return merchantList;
    }

    private static double  getLat(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getDouble(tagName);
    }

    private static double  getLng(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getDouble(tagName);
    }

    private static float  getRad(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getId(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    private static String getRef(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }
}
