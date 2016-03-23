
package br.com.v8developmentstudio.rccguarulhos;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.marcohc.robotocalendar.RobotoCalendarView;
import com.marcohc.robotocalendar.RobotoCalendarView.RobotoCalendarListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.v8developmentstudio.rccguarulhos.bo.Adaptador;

import br.com.v8developmentstudio.rccguarulhos.dao.PersistenceDao;
import br.com.v8developmentstudio.rccguarulhos.modelo.Evento;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity implements RobotoCalendarListener {

    private RobotoCalendarView robotoCalendarView;
    private int currentMonthIndex =0;
    private Calendar currentCalendar;
    private  ArrayAdapter<Evento> eventoArrayAdapter;
    private List<Evento> listEventos;
    private  List<String> listsumariodomes = new ArrayList<String>();
    private ListView listView;

    private PersistenceDao persistenceDao=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        listView  = (ListView) findViewById(R.id.listview);
        robotoCalendarView = (RobotoCalendarView) findViewById(R.id.robotoCalendarPicker);
        //-----
        persistenceDao = new PersistenceDao(this);
        listEventos = persistenceDao.recuperaTodosEventos();
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        robotoCalendarView.setRobotoCalendarListener(this);
        robotoCalendarView.markDayAsCurrentDay(currentCalendar.getTime());

        updateCalendar();


        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
              Evento evento=(Evento)listView.getAdapter().getItem(p3);
                redirectDescricaoDoEvento(evento);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDateSelected(Date date) {
        robotoCalendarView.markDayAsSelectedDay(date);
        listEventos = persistenceDao.recuperaEventosPorDia(date);
        gerarListaMarcarCalendario();
    }

    @Override
    public void onRightButtonClick() {
        currentMonthIndex++;
        updateCalendar();
    }

    @Override
    public void onLeftButtonClick() {
        currentMonthIndex--;
        updateCalendar();
    }

    private void updateCalendar() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        robotoCalendarView.initializeCalendar(currentCalendar);
        listEventos = persistenceDao.recuperaEventosPorMes(currentCalendar.getTime());
        gerarListaMarcarCalendario();
    }

    private void gerarListaMarcarCalendario(){
        listsumariodomes.clear();
        for(Evento evento:listEventos) {
             robotoCalendarView.markFirstUnderlineWithStyle(RobotoCalendarView.RED_COLOR, evento.getDataHoraInicio());
            listsumariodomes.add(evento.getSumario());
        }
        construtorAdapter();
    }

    private void construtorAdapter(){
        eventoArrayAdapter = new Adaptador(this.getApplicationContext(),listEventos);
        listView.setAdapter(eventoArrayAdapter);
    }
    private void redirectDescricaoDoEvento(final Evento evento) {
        Intent intent = new Intent(MainActivity.this, DescricaoActivity.class);
        Bundle dados = new Bundle();
        dados.putInt(Constantes.ID,evento.getId().intValue());
        dados.putString(Constantes.CALENDARIO, PersistenceDao.TB_CAL_DIOCESANO);
        intent.putExtras(dados);
          this.startActivity(intent);
    }
}
