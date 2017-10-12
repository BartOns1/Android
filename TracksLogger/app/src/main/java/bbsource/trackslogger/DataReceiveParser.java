package bbsource.trackslogger;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbsource.trackslogger.domain.Coordinate;
import bbsource.trackslogger.domain.Participant;

/**
 * Created by vdabcursist on 11/10/2017.
 */

public final class DataReceiveParser {
    private void DataReceiverParser(){};

    private static List<Participant> participants = new ArrayList<>();


    public static List<Participant> jSonParser(String groupName){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                "http://10.0.2.2:8080/api/group/"+ groupName + "/participants", null  ,new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response){
                Log.d("Response", response.toString());
                String info;
                try {

                    for(int i=0; i<response.length();i++){
                        JSONObject sensingParticipant = response.getJSONObject(i);
                        String participantName = sensingParticipant.getString("label");

                        Participant participant=new Participant(participantName,"logfileTODO");
                        JSONArray coords = sensingParticipant.getJSONArray("coordinates");

                        List<Coordinate> coordinates=null;



                    //parse coordinates from JSONobject
                        for (int j=0; j<coords.length();j++){
                            JSONObject coord= coords.getJSONObject(j);
                            if (coordinates != null) {
                                coordinates.add(j,new Coordinate(Date.valueOf(coord.getString("time")),
                                        Double.parseDouble(coord.getString("latitude")),Double.parseDouble(coord.getString("longitude"))));
                            }
                        }

                        //Update coordinates depending wether person already exist with some coordinates(IF-clause) or not(ELSE-clause)
                        if ( participants.contains(participant)){
                            if (coordinates != null) {
                                for(int j=participants.get(i).getCoordinates().size(); j<coordinates.size(); j++) {
                                   participants.get(i).getCoordinates().add(coordinates.get(j));
                                }
                            }
                        } else {
                            Participant p = participant;
                            if(coordinates!=null){
                            p.setCoordinates(coordinates);
                            }

                            participants.add(p);
                        }

                    }
                    System.out.println("+++++++++++++++++++++++++++++++++++++");

                } catch (JSONException e) {
                    System.out.println("--------------------------------------");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d("response", "Error:" + error.getMessage());
                System.out.println("///////////////////////////////");
            }
        });
        AppController.getInstance().addToRequestQueue(request);
        return participants;
    }

}
