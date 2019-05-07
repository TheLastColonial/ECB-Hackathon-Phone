package com.example.geopay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonApiParser {

    public static impMerchantList getMerchants(String data) throws JSONException {
        impMerchantList merchantList = new impMerchantList();
        merchantList.merchantList = new ArrayList<impMerchantLocation>();

        JSONObject Obj = new JSONObject(data);

        JSONArray arrayJson = (JSONArray) Obj.get("merchants");
        // iterating merchants
        for (int i = 0; i < arrayJson.length(); i++) {
            impMerchantLocation merch = new impMerchantLocation();
            JSONObject currentObj = arrayJson.getJSONObject(i);
            merch.setLatitude(getLat("latitude", currentObj));
            merch.setLongitude(getLng("longitude", currentObj));
            merch.setRadius(getRad("radius", currentObj));
            merch.setGoogleRef(getRef("googleReference", currentObj));
            merch.setAccountNumber(getAccNumber("accountNumber", currentObj));
            merch.setMerchantId(getId("id", currentObj));
            merch.setMerchantName(getName("name", currentObj));
            merchantList.merchantList.add(merch);
        }

        return merchantList;
    }

    public static boolean paymentResult(String data) throws JSONException {
        JSONObject jObj = new JSONObject(data);

        return getPaymentResult("success", jObj);
    }

    private static boolean getPaymentResult(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getBoolean(tagName);
    }

    private static String getName(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static int getAccNumber(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
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
