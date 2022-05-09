package com.example.blogapplication;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        String title = blog.getBlogTitle();
        if(title.length() > 18) {
            title = title.substring(0, 18) + "...";
        }
        holder.text.setText(title);
        new RetrieveBitmapFromUrl(holder.image).execute(blog.getBlogImageUrl());
        String blogHighlight = blog.getBlogDescription();
        if(blogHighlight.length() > 22) {
            blogHighlight = blogHighlight.substring(0, 22) + "...";
        }
        holder.description.setText(blogHighlight);
        holder.saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (blog.getBlogIsSaved()) {
                    holder.saved.setImageResource(R.drawable.ic_not_saved_star);
                    blog.setBlogIsSaved(false);
                } else {
                    holder.saved.setImageResource(R.drawable.ic_saved_star);
                    blog.setBlogIsSaved(true);
                }
            }
        });
//        Log.i("onbind", "onbind");
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
        ImageView image;
        TextView description;
        ImageView saved;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.blogText);
            cardView = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.blogImage);
            description = itemView.findViewById(R.id.blogDescription);
            saved = itemView.findViewById(R.id.saved);
        }
    }
}
