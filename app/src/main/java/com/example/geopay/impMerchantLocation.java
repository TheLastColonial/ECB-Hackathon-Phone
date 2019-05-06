package com.example.geopay;

import java.io.Serializable;

public class impMerchantLocation implements Serializable {
    private float radius;
    private double longitude;
    private double latitude;
    private String googleRef;
    private String merchantName;
    private int accountNumber;
    private int merchantId;

    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }
    public String getMerchantName() { return merchantName; }

    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }
    public int getAccountNumber() { return accountNumber; }

    public void setMerchantId(int merchantId) { this.merchantId = merchantId; }
    public int getMerchantId() { return merchantId; }

    public void setRadius(float radius) { this.radius = radius; }
    public float getRadius() { return radius; }

    public void setLongitude(double longitude) { this.longitude = longitude; }
    public double getLongitude() { return longitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLatitude() { return latitude; }

    public void setGoogleRef(String googleRef) { this.googleRef = googleRef; }
    public String getGoogleRef() { return googleRef; }
}
