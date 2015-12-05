package com.codepath.courses.instagramapp;

import com.codepath.courses.instagramapp.beans.InstagramPhoto;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepaks on 11/28/15.
 */
public class TestGsonParsing {


    @Test
    public void test() throws IOException {

        final URL resource = this.getClass().getClassLoader().getResource("data1.json");
        System.out.println("THe file name is : " + resource.toString());
        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("data1.json");

        BufferedReader bufRead = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = bufRead.readLine()) != null) {
            builder.append(line).append("\n");
        }
        //System.out.println(builder.toString());

        JsonArray photosJSON = null;
        JsonArray commentsJSON = null;
        List<InstagramPhoto> photoList = new ArrayList<>();
        try {
            JsonObject jsonObject = new JsonParser().parse(builder.toString()).getAsJsonObject();

            photosJSON = jsonObject.getAsJsonArray("data");
            for (int i = 0; i < photosJSON.size(); i++) {
                JsonObject photoJSON = photosJSON.get(i).getAsJsonObject(); // 1, 2, 3, 4
                InstagramPhoto photo = new InstagramPhoto();
                photo.profileUrl = photoJSON.get("user").getAsJsonObject().get("profile_picture").getAsString();
                photo.username = photoJSON.get("user").getAsJsonObject().get("username").getAsString();


                if (photoJSON.has("caption") && photoJSON.get("caption") != null) {
                    photo.caption = photoJSON.getAsJsonObject("caption").get("text").getAsString();
                }


                photo.createdTime = photoJSON.get("created_time").getAsString();
                photo.imageUrl = photoJSON.getAsJsonObject("images").getAsJsonObject("standard_resolution").get("url").getAsString();
                photo.imageHeight = photoJSON.getAsJsonObject("images").getAsJsonObject("standard_resolution").get("height").getAsInt();
                photo.likesCount = photoJSON.getAsJsonObject("likes").get("count").getAsInt();


                if (photoJSON.has("comments") && photoJSON.get("comments") != null) {
                    photo.commentsCount = photoJSON.getAsJsonObject("comments").get("count").getAsInt();
                    commentsJSON = photoJSON.getAsJsonObject("comments").getAsJsonArray("data");
                    if (commentsJSON.size() > 0) {
                        photo.comment1 = commentsJSON.get(commentsJSON.size() - 1).getAsJsonObject().get("text").getAsString();
                        photo.user1 = commentsJSON.get(commentsJSON.size() - 1).getAsJsonObject().getAsJsonObject("from").get("username").getAsString();
                        if (commentsJSON.size() > 1) {
                            photo.comment2 = commentsJSON.get(commentsJSON.size() - 2).getAsJsonObject().get("text").getAsString();
                            photo.user2 = commentsJSON.get(commentsJSON.size() - 2).getAsJsonObject().getAsJsonObject("from").get("username").getAsString();
                        }
                    } else {
                        photo.commentsCount = 0;
                    }
                }

                photo.id = photoJSON.get("id").getAsString();
                photoList.add(photo);
            }
        } catch (Exception e ) {
            // Fire if things fail, json parsing is invalid
            e.printStackTrace();
        }
        System.out.println("List is : " + photoList);
    }
}
