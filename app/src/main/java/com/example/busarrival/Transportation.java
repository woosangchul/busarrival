package com.example.busarrival;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Transportation extends TimeCalculator {
    private Map<Integer, Integer> busMap;
    private Map<Integer, Integer> subMap;
    private ArrayList<String> busSchedule;
    private ArrayList<String> subSchedule;
    private String departText, arriveText;
    private int flagBus=-1;
    private int flagSub=-1;


    public Transportation(Map<Integer, Integer> busMap, ArrayList<String> busSchedule, Map<Integer, Integer> subMap,  ArrayList<String> subSchedule, String[] text) {
        this.busMap = busMap;
        this.subMap = subMap;
        this.busSchedule = busSchedule;
        this.subSchedule = subSchedule;
        this.departText = text[0]; //카드뷰에 표시할 출발지 이름(2캠 >> 지행)
        this.arriveText = text[1]; //카드뷰에 표시할 도착지 이름
    }

    public String getDepartText() {
        return departText;
    }

    public String getArriveText() {
        return arriveText;
    }

    public void resetFlagBus(){
        flagBus = -1;
    }

    private String[] getScheduleCalc(Map<Integer, Integer> transMap, ArrayList<String> transSchedule){
        String str[] = new String[2];
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (transMap.get(hour) != null) {
            Log.d("1234", String.valueOf(transSchedule.size()));
            for (int i = transMap.get(hour); i < transSchedule.size(); i++) {
                if (timeCompare(transSchedule.get(i))) {
                    if (i == (transSchedule.size() - 1)) {
                        str[0] = timeCalc(transSchedule.get(i)) + "(" + transSchedule.get(i) + ")";
                        str[1] = "다음차종료";
                    } else if (i <= (transSchedule.size() - 2)) {
                        str[0] = timeCalc(transSchedule.get(i)) + "(" + transSchedule.get(i) + ")";
                        str[1] = timeCalc(transSchedule.get(i + 1)) + "(" + transSchedule.get(i + 1) + ")";
                    }
                    break;
                }else if(i == transSchedule.size()-1){
                    str[0] = "운행종료.."; //마지막차 떠난경우
                    str[1] = "운행종료..";
                }
            }
        }else {
            str[0] = "운행종료";
            str[1] = "운행종료";
        }

        return str;
    }


    public String[] getBusSchedule() {
        String str[];
        str = getScheduleCalc(busMap, busSchedule);
        return str;
    }

    public String[] getSubSchedule() {
        String str[];
        str = getScheduleCalc(subMap, subSchedule);
        return str;
    }



    private int getScheduleIndex(int hour,Map<Integer, Integer> transMap, ArrayList<String> transSchedule){
        int index=0;

        if (transMap.get(hour) != null) {
            Log.d("1234","넥스트스케쥴");
            Log.d("1234", String.valueOf(transSchedule.size()));
            for (int i = transMap.get(hour); i < transSchedule.size(); i++) {
                if (timeCompare(transSchedule.get(i))) {
                    if (i == (transSchedule.size() - 1)) {
                        index = i;
                    }else if(i <= (transSchedule.size()-2)){
                        index = i;
                    }
                    break;
                }else if(i == transSchedule.size()-1){
                    index = -1;
                }
            }
        }else{
            index = -1;
        }
    return index;

    }
    private int setSubFlag(int tempFlagBus){
        int tempFlagSub = 0;
        if(tempFlagBus == -1)
            return -1;

        while(true){
            if(timeCompare(subSchedule.get(tempFlagSub), busSchedule.get(tempFlagBus))){
                break;
            }else{
                tempFlagSub++;
            }
        }
        return tempFlagSub;
    }
    public String[] getNextButton(String[] text){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String[] str = new String[4];
        int tempFlagBus = flagBus;
        int tempFlagSub = 0;

        if(tempFlagBus == -1){
            tempFlagBus = getScheduleIndex(hour, busMap, busSchedule);

            if(tempFlagBus <= busSchedule.size()-2 && tempFlagBus!=-1)
                tempFlagBus++;

        }else{
            if(!timeCompare(busSchedule.get(tempFlagBus))){
                tempFlagBus = getScheduleIndex(hour, busMap, busSchedule);
                if(tempFlagBus <= busSchedule.size()-2 && tempFlagBus!=-1)
                    tempFlagBus++;

            }else{
                if(tempFlagBus <= busSchedule.size()-2 && tempFlagBus!=-1)
                    tempFlagBus++;

            }

        }
        Log.d("1234", ""+setSubFlag(tempFlagBus) + " " + subSchedule.size());
        if(tempFlagBus == busSchedule.size()-1){
            Log.d("1234", "1범네뉴");
            tempFlagSub = setSubFlag(tempFlagBus);
            str[0] = timeCalc(busSchedule.get(tempFlagBus)) + "(" + busSchedule.get(tempFlagBus) + ")";
            str[1] = "다음차종료";
            str[2] = timeCalc(subSchedule.get(tempFlagSub)) + "(" + subSchedule.get(tempFlagSub) + ")";
            str[3] = timeCalc(subSchedule.get(tempFlagSub+1)) + "(" + subSchedule.get(tempFlagSub+1) + ")";

        }else if(tempFlagBus <= (busSchedule.size()-2) && tempFlagBus != -1){
            Log.d("1234", "2범네뉴");
            tempFlagSub = setSubFlag(tempFlagBus);
            str[0] = timeCalc(busSchedule.get(tempFlagBus)) + "(" + busSchedule.get(tempFlagBus)+")";
            str[1] = timeCalc(busSchedule.get(tempFlagBus + 1)) + "(" + busSchedule.get(tempFlagBus + 1) + ")";
            str[2] = timeCalc(subSchedule.get(tempFlagSub)) + "(" + subSchedule.get(tempFlagSub) + ")";
            str[3] = timeCalc(subSchedule.get(tempFlagSub+1)) + "(" + subSchedule.get(tempFlagSub+1) + ")";
        }else if(tempFlagBus == -1){
            Log.d("1234", "3범네뉴");
            str[0] = text[0];
            str[1] = text[1];
            str[2] = text[2];
            str[3] = text[3];
        }
        flagBus = tempFlagBus;
        return str;


    }

    public String[] getPrevButton(String[] text){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String[] str = new String[4];
        int tempFlagBus = flagBus;
        int tempFlagSub = 0;

        if(tempFlagBus > 0){
            tempFlagBus--;
            if(!timeCompare(busSchedule.get(tempFlagBus))){
                tempFlagBus++;
            }
                tempFlagSub = setSubFlag(tempFlagBus);
        }


        Log.d("1234", "tempflagbus : " + tempFlagBus);
        if(tempFlagBus <= (busSchedule.size()-2) && tempFlagBus != -1){
            Log.d("1234", "1범네뉴1");
            tempFlagSub = setSubFlag(tempFlagBus);
            str[0] = timeCalc(busSchedule.get(tempFlagBus)) + "(" + busSchedule.get(tempFlagBus)+")";
            str[1] = timeCalc(busSchedule.get(tempFlagBus + 1)) + "(" + busSchedule.get(tempFlagBus + 1) + ")";
            str[2] = timeCalc(subSchedule.get(tempFlagSub)) + "(" + subSchedule.get(tempFlagSub) + ")";
            str[3] = timeCalc(subSchedule.get(tempFlagSub+1)) + "(" + subSchedule.get(tempFlagSub+1) + ")";
        }else if(tempFlagBus == -1){
            Log.d("1234", "2범네뉴1");
            str[0] = text[0];
            str[1] = text[1];
            str[2] = text[2];
            str[3] = text[3];
        }
        flagBus = tempFlagBus;
        return str;


        }



}
