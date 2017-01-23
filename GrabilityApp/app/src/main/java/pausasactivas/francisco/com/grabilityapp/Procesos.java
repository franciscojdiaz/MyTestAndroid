package pausasactivas.francisco.com.grabilityapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;

/**
 * Created by francisco.diaz on 19/01/2017.
 */

public class Procesos {

    private Bitmap[] imaarr = new Bitmap[20];
    private String[] arrsummary = new String[20];
    public static final int NOTIFICACION_ID = 1;
    Imagenes usuario = new Imagenes();
    DBHelper helper;
    private boolean internet = true;


}
