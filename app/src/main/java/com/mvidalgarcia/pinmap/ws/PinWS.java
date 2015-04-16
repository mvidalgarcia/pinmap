package com.mvidalgarcia.pinmap.ws;

import com.mvidalgarcia.pinmap.model.Pin;

import org.json.JSONException;
import java.io.IOException;
import java.util.List;

public interface PinWS
{
    public List<Pin> getPinsByGooglePlusId(String googlePlusId) throws JSONException, IOException;
    public Pin getPinById(int id) throws IOException;
    public void insertPin(Pin pin) throws IOException;
    public void deletePin(int id) throws IOException;

}
