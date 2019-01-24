package com.example.busarrival;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{
        private final List<Transportation> mDataList;
        Transportation transportationItem;
        private final List<ViewHolder> holderList;
        private Handler mHandler = new Handler();
        private Runnable updateTimeRunnable = new Runnable() {
        @Override
            public void run() {
                synchronized (holderList) {
                    for (ViewHolder holder : holderList) {
                        if(holder.viewHolderLock)   holder.updateTime();
                    }
                }

            }
        };

    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateTimeRunnable);
            }
        }, 1000, 10000);
    }



    public MyRecyclerAdapter(List<Transportation> DataList) {
        this.mDataList = DataList;
        holderList = new ArrayList<>();
        startUpdateTimer();
    }

    @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final ViewHolder tHolder= holder;
            transportationItem = mDataList.get(position);
            holder.setData(transportationItem);
            holder.imageVIewReload.setOnClickListener(new View.OnClickListener(){

                public void onClick(View v){
                    tHolder.viewHolderLock = true;
                    tHolder.itemlist.resetFlagBus();
                    tHolder.updateTime();
                }

            });
            holder.imageVIewRightArrow.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    String[] str = new String[4];
                    tHolder.viewHolderLock = false;

                    str[0] = tHolder.textViewFirstBusArriveText.getText().toString();
                    str[1] = tHolder.textViewNextBusArriveText.getText().toString();
                    str[2] = tHolder.textViewFirstSubArriveText.getText().toString();
                    str[3] = tHolder.textViewNextSubArriveText.getText().toString();

                    str = tHolder.itemlist.getNextButton(str);
                    tHolder.textViewFirstBusArriveText.setText(str[0]);
                    tHolder.textViewNextBusArriveText.setText(str[1]);
                    tHolder.textViewFirstSubArriveText.setText(str[2]);
                    tHolder.textViewNextSubArriveText.setText(str[3]);

                }

            });
            holder.imageVIewLeftArrow.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    String[] str = new String[4];
                    tHolder.viewHolderLock = false;

                    str[0] = tHolder.textViewFirstBusArriveText.getText().toString();
                    str[1] = tHolder.textViewNextBusArriveText.getText().toString();
                    str[2] = tHolder.textViewFirstSubArriveText.getText().toString();
                    str[3] = tHolder.textViewNextSubArriveText.getText().toString();

                    str = tHolder.itemlist.getPrevButton(str);
                    tHolder.textViewFirstBusArriveText.setText(str[0]);
                    tHolder.textViewNextBusArriveText.setText(str[1]);
                    tHolder.textViewFirstSubArriveText.setText(str[2]);
                    tHolder.textViewNextSubArriveText.setText(str[3]);

                }

            });

            Message message = Message.obtain();
            message.what = position;
            mHandler.sendMessage(message);

            synchronized (holderList) {
                holderList.add(holder);
            }

            holder.updateTime();



        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }




    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewDepartText, textViewArriveText, textViewFirstBusArriveText, textViewNextBusArriveText, textViewFirstSubArriveText, textViewNextSubArriveText;
        ImageView imageVIewReload, imageVIewRightArrow, imageVIewLeftArrow;
        Transportation itemlist;
        boolean viewHolderLock = true;
        public void setData(Transportation item){
            this.itemlist = item;
           String[] str, str1;
            str= item.getBusSchedule();
            str1 = item.getSubSchedule();

            textViewDepartText.setText(item.getDepartText());
            textViewArriveText.setText((item.getArriveText()));
            textViewFirstBusArriveText.setText(str[0]);
            textViewNextBusArriveText.setText(str[1]);
            textViewFirstSubArriveText.setText(str1[0]);
            textViewNextSubArriveText.setText(str1[1]);
        }

        public void updateTime(){
            String str[];
            Transportation.calendar = Calendar.getInstance();
            str = itemlist.getBusSchedule();
            textViewFirstBusArriveText.setText(str[0]);
            textViewNextBusArriveText.setText(str[1]);

            str = itemlist.getSubSchedule();
            textViewFirstSubArriveText.setText(str[0]);
            textViewNextSubArriveText.setText((str[1]));

        }
        public ViewHolder(View itemView){
            super(itemView);
            textViewDepartText = itemView.findViewById(R.id.textViewDepartTextL);
            textViewArriveText = itemView.findViewById(R.id.textViewArriveTextL);
            textViewFirstBusArriveText = itemView.findViewById(R.id.textViewFirstBusArriveTimeL);
            textViewNextBusArriveText = itemView.findViewById(R.id.textViewNextBusArriveTimeL);
            textViewFirstSubArriveText = itemView.findViewById(R.id.textViewFirstSubArriveTimeL);
            textViewNextSubArriveText = itemView.findViewById(R.id.textViewNextSubArriveTimeL);

            imageVIewReload = itemView.findViewById(R.id.imageViewReloadL);
            imageVIewLeftArrow = itemView.findViewById(R.id.imageViewLeftArrowL);
            imageVIewRightArrow = itemView.findViewById(R.id.imageViewRightArrowL);
        }
    }



}


