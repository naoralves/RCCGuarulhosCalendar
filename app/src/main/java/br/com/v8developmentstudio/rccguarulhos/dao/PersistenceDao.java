package br.com.v8developmentstudio.rccguarulhos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.v8developmentstudio.rccguarulhos.modelo.Evento;

/**
 * Created by cleiton.dantas on 17/03/2016.
 */
public class PersistenceDao extends SQLiteOpenHelper {
    private static final int version =1;

    public static final String DATABASE_NAME = "DB_CALENDARIOS";
    public static final String TB_CAL_DIOCESANO = "TB_CAL_DIOCESANO";
    public static final String ID = "ID";
    public static final String DATAHORAINICIO = "DATAHORAINICIO";
    public static final String DATAHORAFIM = "DATAHORAFIM";
    public static final String LOCAL = "LOCAL";
    public static final String SUMARIO = "SUMARIO";
    public static final String DESCRICAO = "DESCRICAO";

    private static final List<String> sqls = Arrays.asList("CREATE TABLE IF NOT EXISTS " + TB_CAL_DIOCESANO + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATAHORAINICIO + " DATETIME NOT NULL, " + DATAHORAFIM + " DATETIME, " + LOCAL + " VARCHAR (200),"+ SUMARIO + " VARCHAR (200) NOT NULL, "+ DESCRICAO +" TEXT );");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Cursor cursor;
    private Context context;
    public static SQLiteDatabase bancoDados = null;

    public PersistenceDao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public PersistenceDao(Context context){
        super(context, DATABASE_NAME, null, version);
        this.context =context;
    }

    public void salvaNovoEvento(Evento evento){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATAHORAINICIO, dateFormat.format(evento.getDataHoraInicio()));
        contentValues.put(DATAHORAFIM, dateFormat.format(evento.getDataHoraFim()));
        contentValues.put(LOCAL,evento.getLocal());
        contentValues.put(SUMARIO, evento.getSumario());
        contentValues.put(DESCRICAO, evento.getDescricao());
        getWritableDatabase().insert(TB_CAL_DIOCESANO, null, contentValues);
    }

    public List<Evento> recuperaTodosEventos() {
        cursor = getWritableDatabase().query(TB_CAL_DIOCESANO, new String[]{ID, DATAHORAINICIO, DATAHORAFIM, LOCAL, SUMARIO, DESCRICAO}, null, null, null, null, null);
        List<Evento> eventoList = new ArrayList<Evento>();
        Evento evento;
        try {
            while (cursor.moveToNext()) {
                evento = new Evento();
                evento.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                evento.setDataHoraInicio(dateFormat.parse(cursor.getString(cursor.getColumnIndex(DATAHORAINICIO))));
                evento.setDataHoraFim(dateFormat.parse(cursor.getString(cursor.getColumnIndex(DATAHORAFIM))));
                evento.setLocal(cursor.getString(cursor.getColumnIndex(LOCAL)));
                evento.setSumario(cursor.getString(cursor.getColumnIndex(SUMARIO)));
                evento.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
                eventoList.add(evento);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return eventoList;
    }

    public SQLiteDatabase openDB(Context context){
        try{
            bancoDados = context.openOrCreateDatabase(PersistenceDao.DATABASE_NAME, Context.MODE_WORLD_READABLE, null);
        }catch (Exception e){
        }
        return bancoDados;
    }
    public SQLiteDatabase openDB(){
        try{
            bancoDados = context.openOrCreateDatabase(PersistenceDao.DATABASE_NAME, Context.MODE_WORLD_READABLE, null);
        }catch (Exception e){
        }
        return bancoDados;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       for(String sql:sqls) {
           db.execSQL(sql);
       }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}