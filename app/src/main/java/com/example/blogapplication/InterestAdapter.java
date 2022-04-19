package com.example.blogapplication;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.InterestViewHolder> {
    private  List<InterestSataModel> data;

    private List<String> selectedNames=new ArrayList<>();
    private ArrayList<InterestSataModel> selected=new ArrayList<>();
    public  InterestAdapter(List<InterestSataModel> interests){
        this.data=interests;

    }

    @NonNull
    @Override
    public InterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.interest_card_layout,parent,false);
        return new InterestViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull InterestViewHolder holder, int position) {
        InterestSataModel interest=data.get(position);
        holder.text.setText(interest.getTagName());
        Log.i("onbind","onbind");
String title=interest.getTagName();
Integer tag=interest.getTag_id();
//holder.text.setText("hi");

holder.cardView.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
       // Log.i("selected",selected);
        Log.i("click", String.valueOf(selected.contains(new InterestSataModel(tag,title).getTagName())));
//        //selectedNames.add(title);
//        for(int i=0;i<selected.size();i++) {
//           selected
//        }
//        Predicate<InterestSataModel> condition= obj -> obj.getTagName()==title;
//       selected.removeAll(Collections.singleton(condition));

        boolean flag=true;
        for(int i=0;i<selected.size();i++){
            if(selected.get(i).getTagName()==title){
                selected.remove(i);
                flag=false;
            }
        }
        if(flag==false){
            holder.text.setBackgroundResource(R.drawable.gradient_disbled);
        }
        else{
            selected.add(new InterestSataModel(tag, title));
            holder.text.setBackgroundResource(R.drawable.gradient);

        }




    }
});

//holder.cardView.setBackgroundColor(Color.parseColor("#A9A9A9"));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public ArrayList<InterestSataModel> getSelected(){
   return selected;
    }


    public class InterestViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        CardView cardView;


        public InterestViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.interestText);
            cardView=itemView.findViewById(R.id.card);

        }
    }
}
