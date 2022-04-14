package com.example.blogapplication;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.InterestViewHolder> {
    private  List<InterestSataModel> data;
    private List<String> selectedNames=new ArrayList<>();
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
//String title=data.get(0).getTagName();
//holder.text.setText("hi");

holder.cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Log.i("click","click");
//       Toast.makeText(view.getContext(), "clicked"+"is"+title, Toast.LENGTH_LONG).show();
//      if(selectedNames.contains(title)){
//          for(int i=0;i<selectedNames.size();i++) {
//              Log.i("if condition", String.valueOf(selectedNames));
//          }
//              selectedNames.remove(title);
//
//
//       }
//      else{
//          for(int i=0;i<selectedNames.size();i++){
//              Log.i("selected", String.valueOf(selectedNames));
//          }
//          selectedNames.add(title);
//      }
//     // Toast.makeText(this,"Clicker",Toast.LENGTH_LONG).show();
//       // holder.cardView.setBackgroundColor(Color.rgb(226,11,11));
//        holder.text.setText("clicked");

    }
});
//holder.cardView.setBackgroundColor(Color.parseColor("#A9A9A9"));


    }

    @Override
    public int getItemCount() {
        return data.size();
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
