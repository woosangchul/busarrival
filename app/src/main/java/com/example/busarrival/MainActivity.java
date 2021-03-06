package com.example.busarrival;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    Transportation bus2camToJi, busjiTo2Cam;
     Map<Integer, Integer> bus2camToJiMap, busJiTo2camMap;
    Map<Integer, Integer> subjiToInMap;
    ArrayList<String> bus2camToJiSchedule, busJito2camSchedule;
    ArrayList<String> subjiToInSchedule;
    List<Transportation>  dataList;


    //여기서부터 삭제------------------------------------------------------------------------------------------------------------------------------------------------------
    TransportationNew newbus;
    ImageView imageLeftButton, imageRightButton;
    TextView textViewDepartText, textViewArriveText, textViewFirstBusArriveText, textViewNextBusArriveText, textViewFirstSubArriveText, textViewNextSubArriveText;
    // 여기까지 -----------------------------------------------------------------------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s[] = {"2캠", "지행"};
        String s1[] = {"지행", "2캠"};
        String str[] = new String[4];

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewMain);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initTrnasportation();
        bus2camToJi = new Transportation(bus2camToJiMap, bus2camToJiSchedule, subjiToInMap, subjiToInSchedule, s);
        dataList = new ArrayList<>();
        dataList.add( new Transportation(bus2camToJiMap, bus2camToJiSchedule, subjiToInMap, subjiToInSchedule, s));
        dataList.add(new Transportation(busJiTo2camMap, busJito2camSchedule, subjiToInMap, subjiToInSchedule, s1));
        dataList.add(new Transportation(busJiTo2camMap, busJito2camSchedule, subjiToInMap, subjiToInSchedule, s1));
        dataList.add(new Transportation(busJiTo2camMap, busJito2camSchedule, subjiToInMap, subjiToInSchedule, s1));
        dataList.add(new Transportation(busJiTo2camMap, busJito2camSchedule, subjiToInMap, subjiToInSchedule, s1));
        dataList.add(new Transportation(busJiTo2camMap, busJito2camSchedule, subjiToInMap, subjiToInSchedule, s1));
        dataList.add(new Transportation(busJiTo2camMap, busJito2camSchedule, subjiToInMap, subjiToInSchedule, s1));
        dataList.add(new Transportation(busJiTo2camMap, busJito2camSchedule, subjiToInMap, subjiToInSchedule, s1));
        dataList.add(new Transportation(busJiTo2camMap, busJito2camSchedule, subjiToInMap, subjiToInSchedule, s1));



        MyRecyclerAdapter adapter = new MyRecyclerAdapter(dataList);
        recyclerView.setAdapter(adapter);

       // ------------- 여기서부터 수정본 시작 삭제할것------------------------------------------------------------------------------------------------------------------------

        /*
        initTrnasportation();
        newbus = new TransportationNew(bus2camToJiMap, bus2camToJiSchedule, subjiToInMap, subjiToInSchedule, s);
        imageLeftButton = (ImageView)findViewById(R.id.imageViewLeftArrowL);
        imageRightButton = (ImageView)findViewById(R.id.imageViewRightArrowL);
        textViewFirstBusArriveText = (TextView)findViewById(R.id.textViewFirstBusArriveTimeL);
        textViewNextBusArriveText= (TextView)findViewById(R.id.textViewNextBusArriveTimeL);
        textViewFirstSubArriveText= (TextView)findViewById(R.id.textViewFirstSubArriveTimeL);
        textViewNextSubArriveText= (TextView)findViewById(R.id.textViewNextSubArriveTimeL);


        str = newbus.getSchedule();

        textViewFirstBusArriveText.setText(str[0]);
        textViewNextBusArriveText.setText(str[1]);
        textViewFirstSubArriveText.setText(str[2]);
        textViewNextSubArriveText.setText(str[3]);


        imageLeftButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String[] str = new String[4];


                str[0] = textViewFirstBusArriveText.getText().toString();
                str[1] = textViewNextBusArriveText.getText().toString();
                str[2] = textViewFirstSubArriveText.getText().toString();
                str[3] = textViewNextSubArriveText.getText().toString();

                str = newbus.getNextButton(str);
                textViewFirstBusArriveText.setText(str[0]);
                textViewNextBusArriveText.setText(str[1]);
                textViewFirstSubArriveText.setText(str[2]);
                textViewNextSubArriveText.setText(str[3]);

            }

        });

        imageRightButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String[] str = new String[4];


                str[0] = textViewFirstBusArriveText.getText().toString();
                str[1] = textViewNextBusArriveText.getText().toString();
                str[2] = textViewFirstSubArriveText.getText().toString();
                str[3] = textViewNextSubArriveText.getText().toString();

                str = newbus.getPrevButton(str);
                textViewFirstBusArriveText.setText(str[0]);
                textViewNextBusArriveText.setText(str[1]);
                textViewFirstSubArriveText.setText(str[2]);
                textViewNextSubArriveText.setText(str[3]);

            }

        });

------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    }

    public void initTrnasportation() {
        bus2camToJiMap = new HashMap<Integer,Integer>();
        busJiTo2camMap = new HashMap<Integer, Integer>();
        subjiToInMap = new HashMap<Integer,Integer>();
        bus2camToJiSchedule = new ArrayList<>();
        subjiToInSchedule = new ArrayList<>();
        busJito2camSchedule = new ArrayList<>();

        String[] busTimeTable2camToJi = {"08:10",	"08:25",	"08:35",	"08:45",	"09:00",	"09:25",	"09:30",	"09:40",	"10:00",	"10:20",	"10:35",	"10:55",	"11:30",	"12:00",	"12:30",	"13:00",
                "13:30",	"14:00",	"14:30",	"14:55",	"15:00",	"15:25",	"15:30",	"15:55",	"16:25",	"16:50",	"17:00",	"17:15",	"17:30",	"17:40",	"17:55",	"18:10",	"18:30",
                "19:00",	"19:30",	"20:00",	"20:30",	"21:00",	"21:30",	"22:00"};
        Integer[] busIndex2camToJi = {8,	9,	10,	11,	12,	13,	14,	15,	16,	17,	18,	19,	20,	21,	22}; //각 요소는 시간을 의미 8시 9시 10시---
        Integer[] busIndex2CamToJi1 = {0,	4,	8,	12,	13,	15,	17,	20,	24,	26,	31,	33,	35,	37,	39}; //각 시간에 타임테이블 시작 인덱스값


        String[] busTimeTableJiTo2cam = {"08:30",	"08:35",	"08:45",	"08:45",	"09:00",	"09:00",	"09:15",	"09:15",	"09:45",	"09:45",	"09:45",	"09:50",	"09:50",	"10:20",	"10:35",
                "10:35",	"10:50",	"10:50",	"11:15",	"11:45",	"12:15",	"12:45",	"13:15",	"13:45",	"14:15",	"14:45",	"15:15",	"15:15",	"15:40",	"15:45",	"16:10",	"16:10",
                "16:35",	"16:35",	"17:00",	"17:05",	"17:10",	"17:25",	"17:40",	"17:50",	"18:05",	"18:20",	"18:40",	"19:10",	"19:40",	"20:10",	"20:40",	"21:10",	"21:40",
                "22:10"};
        Integer[] busIndexJiTo2cam = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
        Integer[] busIndexJiTo2Cam = {0, 4, 13, 18, 20, 22, 24, 26, 30, 34, 40, 43, 45, 47, 49};



        String[] subwayTimeTableJiToIn = {"05:24","05:55","06:14","06:33","06:48","06:57(급)","07:09","07:15","07:21","07:28","07:36(급)","07:43","07:54","07:59","08:07(급)","08:12","08:22","08:28","08:38",
                "08:53","09:13","09:24","09:43","10:01","10:24","10:44","11:14","11:33(급)","11:43","12:12","12:33(급)","12:43","13:14","13:48","13:52","14:14","14:43","15:08","15:12(급)","15:43","16:13","16:38(급)",
                "16:47","17:11","17:29","17:51","18:14","18:29","18:44","18:59","19:16","19:39","19:46","20:00","20:10","20:29","20:49","20:58","21:24","21:37","21:53","22:13","22:47","22:59","23:17","23:48"};
        Integer[] subwayIndexJiToIn = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        Integer[] subwayIndexJiToIn1 = {0,	2,	6,	14,	20,	23,	26,	29,	32,	35,	37,	40,	43,	46,	50,	53,	58,	61,	64};

        for (int i = 0; i < busIndex2camToJi.length; i++)
            bus2camToJiMap.put(busIndex2camToJi[i], busIndex2CamToJi1[i]);

        for (int i = 0; i < busIndexJiTo2Cam.length; i++)
            busJiTo2camMap.put(busIndexJiTo2cam[i], busIndexJiTo2Cam[i]);


        for (int i = 0; i < busTimeTable2camToJi.length; i++)
            bus2camToJiSchedule.add(busTimeTable2camToJi[i]);

        for (int i = 0; i < busTimeTableJiTo2cam.length; i++)
            busJito2camSchedule.add(busTimeTableJiTo2cam[i]);

        for (int i = 0; i < subwayIndexJiToIn.length; i++)
            subjiToInMap.put(subwayIndexJiToIn[i], subwayIndexJiToIn1[i]);

        for (int i = 0; i < subwayTimeTableJiToIn.length; i++)
            subjiToInSchedule.add(subwayTimeTableJiToIn[i]);
    }





}
