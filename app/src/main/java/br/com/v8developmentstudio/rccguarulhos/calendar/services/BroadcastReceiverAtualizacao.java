package br.com.v8developmentstudio.rccguarulhos.calendar.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.com.v8developmentstudio.rccguarulhos.calendar.task.TaskProcessBackground;

/**
 * Created by cleiton.dantas on 18/08/2016.
 */
public class BroadcastReceiverAtualizacao extends BroadcastReceiver {
    private ActivityServices activityServices = new ActivityServicesImpl();
    @Override
    public void onReceive(Context context, Intent intent) {
        atualizaBase(context);
    }


    public void atualizaBase(Context context){
        boolean isOnline = activityServices.isOnline(context);
        if(isOnline){
            TaskProcessBackground taskPross = new TaskProcessBackground(context);
            taskPross.execute();
            Log.i("Script", "-> Base Atualizado in BackGround");
        }
    }

}
