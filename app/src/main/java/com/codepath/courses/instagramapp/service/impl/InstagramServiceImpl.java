package com.codepath.courses.instagramapp.service.impl;

import com.codepath.courses.instagramapp.R;
import com.codepath.courses.instagramapp.beans.InstagramPhoto;
import com.codepath.courses.instagramapp.service.InstagramClient;
import com.codepath.courses.instagramapp.service.InstagramService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by deepaks on 11/29/15.
 */
public class InstagramServiceImpl implements InstagramService {

    InstagramClient mInstagramClient;

    @Override
    public List<InstagramPhoto> getPopularPhotos() {
        String jsonString = mInstagramClient.getPopularMedia();
        return getInstagramPhotos(jsonString);
    }

    private List<InstagramPhoto> getInstagramPhotos(String jsonString) {
        JsonArray photosJSON = null;
        JsonArray commentsJSON = null;
        List<InstagramPhoto> photoList = new ArrayList<>();
        try {
            JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

            photosJSON = jsonObject.getAsJsonArray("data");
            for (int i = 0; i < photosJSON.size(); i++) {
                JsonObject photoJSON = photosJSON.get(i).getAsJsonObject();
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
                if (photoJSON.getAsJsonObject("videos") != null) {
                    photo.videoUrl = photoJSON.getAsJsonObject("videos").getAsJsonObject("standard_resolution").get("url").getAsString();
                    photo.videoHeight = photoJSON.getAsJsonObject("videos").getAsJsonObject("standard_resolution").get("height").getAsInt();
                }

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoList;
    }

    public InstagramClient getInstagramClient() {
        return mInstagramClient;
    }

    public void setInstagramClient(final InstagramClient instagramClient) {
        mInstagramClient = instagramClient;
    }
}
