package com.example.blogapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BlogAdapterSave extends RecyclerView.Adapter<BlogAdapterSave.BlogViewHolder> {
    private List<BlogDataModel> data;
    DBHandler dbHandler;
    private List<String> selectedNames = new ArrayList<>();
    private BlogDataModel selected;
    List<CardView> cardList = new ArrayList<>();
    private int row_idx = 0;
    private Context context;

    public BlogAdapterSave(List<BlogDataModel> blogs, Context context) {
        this.data = blogs;
        this.context = context;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.blog_card_layout, parent, false);
        return new BlogAdapterSave.BlogViewHolder(view);
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
        new RetrieveBitmapFromUrl(holder.image).execute(blog.blog_thumbnail);
        String blogHighlight = blog.getBlogDescription();
        if(blogHighlight.length() > 22) {
            blogHighlight = blogHighlight.substring(0, 22) + "...";
        }
        holder.description.setText(blogHighlight);
        if(blog.blog_is_saved){
            holder.saved.setImageResource(R.drawable.ic_saved_star);
        }
    else{
            holder.saved.setImageResource(R.drawable.ic_not_saved_star);
        }
        holder.saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler = new DBHandler(v.getContext());
                String[] inValues = { blog.id, blog.blog_title, blog.blog_description, blog.blog_image_url, blog.blog_thumbnail};
                Log.e("BLOGSAVEADAPTER", String.valueOf(blog.blog_is_saved));
                if (blog.getBlogIsSaved()) {
                    holder.saved.setImageResource(R.drawable.ic_not_saved_star);
                    dbHandler.deleteBlog(blog.id);
                    blog.setBlogIsSaved(false);
                    data.remove(position);
                    notifyItemRemoved(position);
                } else {
                    holder.saved.setImageResource(R.drawable.ic_saved_star);
                    blog.setBlogIsSaved(true);

                }
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)

            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), DetailActivity.class);
                Bundle args = new Bundle();
                args.putString("BLOG_TITLE", blog.blog_title);
                args.putString("BLOG_DESCRIPTION", blog.blog_description);
                args.putString("BLOG_IMAGE_LINK", blog.blog_image_url);
                i.putExtras(args);
                context.startActivity(i, args);
            }
        });

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
