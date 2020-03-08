package com.kye.myrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView,recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CardItem> list = new ArrayList<>();

        list.add(new CardItem("설명", "내용"));
        list.add(new CardItem("설명", "내용"));
        list.add(new CardItem("설명", "내용"));
        list.add(new CardItem("설명", "내용"));
        list.add(new CardItem("설명", "내용"));
        list.add(new CardItem("설명", "내용"));

        MyAdapter adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);

        recyclerView2 = findViewById(R.id.recyclerView1);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager1);

        ArrayList<String> title = new ArrayList<>();

        title.add("첫번째");
        title.add("두번째");
        title.add("세번째");
        title.add("네번째");
        title.add("다섯번째");

        MyAdapter2 adapter2 = new MyAdapter2(this,title);
        recyclerView2.setAdapter(adapter2);

    }

    class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder>{

        ArrayList<String> title;
        Context context;

        public MyAdapter2(Context context,ArrayList<String> title) {
            this.title = title;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_title,parent,false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String titles = title.get(position);
            holder.title.setText(titles);

        }

        @Override
        public int getItemCount() {
            return title.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView title;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);

                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,title.getText(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

}
