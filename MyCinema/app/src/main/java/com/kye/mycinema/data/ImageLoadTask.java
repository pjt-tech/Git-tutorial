package com.kye.mycinema.data;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ImageLoadTask extends AsyncTask<Void,Void, Bitmap> {
    private String urlStr;
    private ImageView imageView;
    private ProgressBar pb;

    private static HashMap<String,Bitmap> bitmapHash = new HashMap<>();

    public ImageLoadTask(String urlStr, ImageView imageView, ProgressBar progressBar){

        this.urlStr = urlStr;
        this.imageView = imageView;
        this.pb = progressBar;
        pb.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Bitmap doInBackground(Void... voids) {

        Bitmap bitmap = null;
        try {
            if(bitmapHash.containsKey(urlStr)){
                Bitmap oldbitmap =  bitmapHash.remove(urlStr);
                if(oldbitmap!=null){
                    oldbitmap.recycle();
                    oldbitmap = null;
                }
            }
            URL url = new URL(urlStr);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            bitmapHash.put(urlStr,bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
        pb.setVisibility(View.INVISIBLE);
    }
}
