package com.example.blogapplication;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {
    private List<BlogDataModel> data;

    private List<String> selectedNames = new ArrayList<>();
    private BlogDataModel selected;
    List<CardView> cardList = new ArrayList<>();
    private int row_idx = 0;


    public BlogAdapter(List<BlogDataModel> blogs) {
        this.data = blogs;

    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.blog_card_layout, parent, false);
        return new BlogAdapter.BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        BlogDataModel blog = data.get(position);
        Log.i("position", String.valueOf(position));
        holder.text.setText(blog.getBlogTitle());
        Log.i("onbind", "onbind");
        String title = blog.getBlogTitle();
        String description = blog.getBlogDescription();
//holder.text.setText("hi");

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//
//            @Override
//            public void onClick(View view) {
//                row_idx=position;
//                boolean flag = true;
//                for (int i = 0; i < selected.size(); i++) {
//                    // Log.i("Selected items"+selected.get(i).getTagName());
//                    Log.i("Selected items", selected.get(i).getTagName());
//                    if (selected.get(i).getTagName() == title) {
//                        selected.remove(i);
//                        flag = false;
//                    }
//                }
//                if (flag == false) {
//                    holder.text.setBackgroundResource(R.drawable.gradient_disbled);
//                } else {
//                    if (row_idx == position) {
//                        selected.add(new InterestSataModel(tag, title));
//                        holder.text.setBackgroundResource(R.drawable.gradient);
//
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount()  {
        return data.size();
    }

    public BlogDataModel getSelected() {
        return selected;
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        CardView cardView;


        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.blogText);
            cardView = itemView.findViewById(R.id.card);

        }
    }
}
