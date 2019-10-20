package com.example.epictureapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gallery extends AppCompatActivity {

    private OkHttpClient httpGetGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        requestData();
    }
    private static class image {
        String id;
        String title;
    }

    private void requestData() {
        httpGetGallery = new OkHttpClient.Builder().build();
        final Request request = new Request.Builder()
                .url("https://api.imgur.com/3/gallery/user/rising/0.json")
                .header("Authorization","Client-ID 480cdf2c81306bc")
                .header("User-Agent","Epicture")
                .build();

        httpGetGallery.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(null, "An error has occurred " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Okreponse(response);
            }
        });
    }
    public void Okreponse(Response response) throws  IOException {
        JSONObject data_info = null;
        try {
            data_info = new JSONObject(response.body().string());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray element = null;
        try {
            element = data_info.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GsonPars(element);
    }
    public void GsonPars(JSONArray element){
        final List<image> photos = new ArrayList<image>();

        for(int i=0; i< element.length();i++) {
            JSONObject item = null;
            try {
                item = element.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            stock_album(item, photos);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    render(photos);
                }
            });
        }
    }

    public void stock_album(JSONObject item, final List<image> photos){
        image pictureFile = new image();
        try {
            if(item.getBoolean("is_album")) {
                pictureFile.id = item.getString("cover");
            } else {
                pictureFile.id = item.getString("id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        stock_title(item, pictureFile, photos);
    }

    public void stock_title(JSONObject item, image pictureFile, final List<image> photos) {
        try {
            pictureFile.title = item.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        photos.add(pictureFile);
    }
    private static class PhotoVH extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView title;

        public PhotoVH(View itemView) {
            super(itemView);
        }
    }
    private void render(final List<image> photos) {
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_of_photos);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecyclerView.Adapter<PhotoVH>() {
            @Override
            public PhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
                PhotoVH vh = new PhotoVH(getLayoutInflater().inflate(R.layout.item, null));
                vh.photo = (ImageView) vh.itemView.findViewById(R.id.photo);
                vh.title = (TextView) vh.itemView.findViewById(R.id.title);
                return vh;
            }

            @Override
            public void onBindViewHolder(PhotoVH holder, int position) {
                Picasso.with(gallery.this).load("https://i.imgur.com/" +
                        photos.get(position).id + ".jpg").into(holder.photo);
                holder.title.setText(photos.get(position).title);
            }

            @Override
            public int getItemCount() {
                return photos.size();
            }
        });
    }
}
