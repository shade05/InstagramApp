package com.codepath.courses.instagramapp;

import com.codepath.courses.instagramapp.beans.InstagramPhoto;
import com.codepath.courses.instagramapp.service.InstagramClient;
import com.codepath.courses.instagramapp.service.impl.InstagramClientApiImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
public class TestInstagramClientApiImpl {

    @Test
    public void getPopularMedia() throws IOException {
        InstagramClient instagramClient = new InstagramClientApiImpl();
        String jsonString = instagramClient.getPopularMedia();
        System.out.println("Json string " + jsonString);
    }
}
