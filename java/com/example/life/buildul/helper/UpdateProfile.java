package com.example.life.buildul.helper;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile {

    public String firName;
    public String lasName;
    public String eMail;
    public String pHone;
    public String aDdress;
    public String cIty;
    public String sTate;
    public String pOstalCode;

    public UpdateProfile(){

    }

    public UpdateProfile(String firName, String lasName, String eMail, String pHone, String aDdress, String cIty, String sTate, String pOstalCode){
        this.firName = firName;
        this.lasName = lasName;
        this.eMail = eMail;
        this.pHone = pHone;
        this.aDdress = aDdress;
        this.cIty = cIty;
        this.sTate = sTate;
        this.pOstalCode = pOstalCode;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("firName", firName);
        result.put("lasName", lasName);
        result.put("eMail", eMail);
        result.put("pHone", pHone);
        result.put("aDdress", aDdress);
        result.put("cIty", cIty);
        result.put("sTate", sTate);
        result.put("pOstalCode", pOstalCode);

        return result;
    }
}
