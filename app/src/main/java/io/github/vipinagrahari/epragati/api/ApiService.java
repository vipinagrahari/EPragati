package io.github.vipinagrahari.epragati.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vivek on 22/8/16.
 */
public interface ApiService {

    String API_BASE_URL = "http://androidnote.com/files/";

    @GET("member.json")
    Call<JsonObject> getMemberDetails();

    @GET("member_finance.json")
    Call<JsonObject> getMemberFinance();

    @GET("group.json")
    Call<JsonObject> getGroupDetails();

    @GET("group_finance.json")
    Call<JsonObject> getGroupFinance();

    @GET("meetings.json")
    Call<JsonObject> getGroupMeetings();

    @GET("resources.json")
    Call<JsonObject> getResources();
}
