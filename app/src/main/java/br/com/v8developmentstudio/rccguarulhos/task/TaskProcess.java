package br.com.v8developmentstudio.rccguarulhos.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import br.com.v8developmentstudio.rccguarulhos.activitys.MainActivity;
import br.com.v8developmentstudio.rccguarulhos.bo.ConstrutorIcal;
import br.com.v8developmentstudio.rccguarulhos.dao.PersistenceDao;
import br.com.v8developmentstudio.rccguarulhos.modelo.Calendario;
import br.com.v8developmentstudio.rccguarulhos.modelo.Evento;
import br.com.v8developmentstudio.rccguarulhos.services.ActivityServices;
import br.com.v8developmentstudio.rccguarulhos.services.ActivityServicesImpl;
import br.com.v8developmentstudio.rccguarulhos.services.Preferences;
import br.com.v8developmentstudio.rccguarulhos.util.AssetsPropertyReader;
import br.com.v8developmentstudio.rccguarulhos.util.FileUtil;

/**
 * Created by cleiton.dantas on 17/03/2016.
 */
public class TaskProcess extends AsyncTask<String, Object, String> {
    private Context context;
    private ProgressDialog progress;
    private ProcessServiceTask processServiceTask;
    private ActivityServices ac = new ActivityServicesImpl();

    public TaskProcess(Context context) {
        this.context = context;
        processServiceTask = new ProcessServiceTaskImpl(context);
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        postMensagem("Garregando...");
        processServiceTask.preProcess();
    }

    @Override
    protected String doInBackground(String... urls) {
        processServiceTask.runProcessBackgrund();
        return null;
    }

    @Override
    protected void onProgressUpdate(Object... params) {
        Log.i("DEBUG", "onProgressUpdate" + params);
        super.onProgressUpdate(params);
    }

    @Override
    protected void onPostExecute(String params) {
        progress.setMessage("Base atualizada !");
        progress.dismiss();
        processServiceTask.posProcess();
        ac.redirect(context, MainActivity.class, null);
    }


    private void postMensagem(String mensagem){
        progress.setMessage(mensagem);
        progress.show();
    }


}
