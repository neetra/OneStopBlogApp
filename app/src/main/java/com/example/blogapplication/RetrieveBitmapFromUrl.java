package com.example.blogapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;

public class RetrieveBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public RetrieveBitmapFromUrl(ImageView bmImage) {
        this.bmImage = bmImage;
    }
    @Override
    protected Bitmap doInBackground(String ...urls) {
        Bitmap bm = null;
        try {
            InputStream in = new java.net.URL(urls[0]).openStream();
            bm = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.e("TAG", "Error getting bitmap", e);
        }
        return bm;
    }
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
