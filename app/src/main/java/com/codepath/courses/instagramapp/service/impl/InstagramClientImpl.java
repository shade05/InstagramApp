package com.codepath.courses.instagramapp.service.impl;

import android.content.res.Resources;

import com.codepath.courses.instagramapp.R;
import com.codepath.courses.instagramapp.service.InstagramClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by deepaks on 11/29/15.
 */
public class InstagramClientImpl implements InstagramClient {

    private Resources mResources;

    public String getPopularMedia() {
        String jsonString = null;
        try {
            jsonString = loadFile(R.raw.data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public String loadFile(int id) throws IOException {
        InputStream iS = null;
        ByteArrayOutputStream oS = null;
        try {
            iS = mResources.openRawResource(id);

            byte[] buffer = new byte[iS.available()];
            iS.read(buffer);
            oS = new ByteArrayOutputStream();
            oS.write(buffer);
        } finally {
            if (oS != null)
                oS.close();
            if (iS != null)
                iS.close();
        }

        return oS.toString();
    }

    public Resources getResources() {
        return mResources;
    }

    public void setResources(Resources resources) {
        mResources = resources;
    }
}
