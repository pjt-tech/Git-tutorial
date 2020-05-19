package com.kye.mycinema.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kye.mycinema.data.AppHelper;
import com.kye.mycinema.data.CommentInfo;
import com.kye.mycinema.data.CommentList;

import com.kye.mycinema.data.DetailInfo;
import com.kye.mycinema.data.MovieInfo;
import com.kye.mycinema.data.MovieList;
import com.kye.mycinema.data.ResponseInfo;
import com.kye.mycinema.data.ReviewItem;
import com.kye.mycinema.data.Reviewer;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {

    ArrayList<ReviewItem> item = new ArrayList<>();
    Context context;
    int index;

    public MyListAdapter(Context context){
        this.context = context;
        item.add(new ReviewItem("dltkd12**","꿀잼보장, 다음에 한번 더 볼거에요"));
        item.add(new ReviewItem("kye5***","돈아까워요, 보지마세요"));
        item.add(new ReviewItem("pjt33**","간만에 재밌는 영화!, 추천해요"));
        requestMovieList();
    }

    public MyListAdapter(Context context,int index){
        this(context);
        this.context = context;
        this.index = index;
    }
    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //view가 이미 있으면 재활용하겠다 리싸이클러뷰와 유사(convertView)
        Reviewer reviewer = null;
        if(convertView==null) {
            reviewer = new Reviewer(context);
            ReviewItem items = item.get(position);
            reviewer.setName(items.getName());
            reviewer.setContents(items.getContents());
        }
        return reviewer;
    }


    public void requestMovieList(){

        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id="+(index+1);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        //processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    public void processResponse(String response){
        Gson gson = new Gson();
        CommentList commentList = gson.fromJson(response, CommentList.class);
        for (int i = 0; i < commentList.result.size(); i++) {
            CommentInfo commentInfo = commentList.result.get(i);
            String writer = commentInfo.writer;
            String contents = commentInfo.contents;
            //item.add(new ReviewItem(writer,contents));
        }
    }
}
