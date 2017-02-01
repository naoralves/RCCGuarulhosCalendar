package br.com.v8developmentstudio.rccguarulhos.calendar.modelo;

/**
 * Created by cleiton.dantas on 01/04/2016.
 */
public class Notificacao {

    private Integer id;
    private String tituloTicker;
    private String titulo;
    private String texto;
    private String key;
    private String value;
    private Boolean isAtivo;

    private int drawableSmallIcon;
    private int drawableLargeIcon;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTituloTicker() {
        return tituloTicker;
    }

    public void setTituloTicker(String tituloTicker) {
        this.tituloTicker = tituloTicker;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getDrawableSmallIcon() {
        return drawableSmallIcon;
    }

    public void setDrawableSmallIcon(int drawableSmallIcon) {
        this.drawableSmallIcon = drawableSmallIcon;
    }

    public int getDrawableLargeIcon() {
        return drawableLargeIcon;
    }

    public void setDrawableLargeIcon(int drawableLargeIcon) {
        this.drawableLargeIcon = drawableLargeIcon;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Boolean getAtivo() {
        return isAtivo;
    }

    public void setAtivo(Boolean ativo) {
        isAtivo = ativo;
    }
}
