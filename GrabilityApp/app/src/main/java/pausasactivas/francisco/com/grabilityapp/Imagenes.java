package pausasactivas.francisco.com.grabilityapp;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by francisco.diaz on 16/01/2017.
 */
@DatabaseTable
public class Imagenes {


    public static final byte[] IMAGEN = null;
    public static final String DESC = "descripcion";
    public static final String APPNAME = "appname";
    public static final String APPCATEGORY = "appcategory";

    //@DatabaseField(dataType = DataType.BYTE_ARRAY)
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] imagen;
    @DatabaseField(columnName = DESC)
    private String desc;
    @DatabaseField(columnName = APPNAME)
    private String appname;
    @DatabaseField(columnName = APPCATEGORY)
    private String appcategory;



    public Imagenes()
    {

    }

    public Imagenes(byte[] imagen, String desc, String appnam, String appcatego) {
        this.imagen = imagen;
        this.desc = desc;
        this.appname = appnam;
        this.appcategory = appcatego;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getAppcategory() {
        return appcategory;
    }

    public void setAppcategory(String appcategory) {
        this.appcategory = appcategory;
    }



}
