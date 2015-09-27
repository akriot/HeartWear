package com.clemed.heartwear.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nic on 9/26/2015.
 */
public class EmergencyContact {

    public EmergencyContact(String phoneNumber, String gcmId, String name) {
        this.phoneNumber = phoneNumber;
        this.gcmId = gcmId;
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String phoneNumber;
    private String gcmId;
    private String name;

    public static final String NAME = "name";
    public static final String GCM_ID = "gcm_id";
    public static final String PHONE_NUMBER = "phone_number";

    public JSONObject toJSONObject() throws JSONException{
        JSONObject object = new JSONObject();
        object.put(NAME, name);
        object.put(GCM_ID, gcmId);
        object.put(PHONE_NUMBER, phoneNumber);

        /*
        {
        "name": "Nic Linscott",
        "gcm_id" : "1234lolid",
        "phone_number" : "123456789"
        }
        */

        return object;

    }

    public static EmergencyContact fromJSONObject(JSONObject object) throws JSONException{
        return new EmergencyContact(
                        object.get(PHONE_NUMBER).toString(),
                        object.get(GCM_ID).toString(),
                        object.get(NAME).toString()
        );
    }

}
