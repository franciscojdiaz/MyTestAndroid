package pausasactivas.francisco.com.grabilityapp;

/**
 * Created by francisco.diaz on 16/01/2017.
 */

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "pausas_act.db";
    private static final int DATABASE_VERSION = 1;


    private Dao<Imagenes, Integer> usuarioDao = null;
    private RuntimeExceptionDao<Imagenes, Integer> usuarioDBruntimeDao = null;

    public DBHelper(Context context) {
        // TODO: Al finalizar el helper crear archivo de configuracion
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }



    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

        try {

            Log.i(DBHelper.class.getSimpleName(),"onCreate");
            TableUtils.createTable(connectionSource, Imagenes.class);

        } catch (SQLException e) {
            Log.e(DBHelper.class.getSimpleName(),"Error al crear la BD");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(db, connectionSource);

        try {
            Log.i(DBHelper.class.getSimpleName(),"onUpgrade");
            TableUtils.dropTable(connectionSource, Imagenes.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DBHelper.class.getSimpleName(),"Imposible eliminar la BD");
            throw new RuntimeException(e);
        }
    }

    ////////////////////////////////////TABLAS//////////////////////////////
    public Dao<Imagenes, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null)
        usuarioDao = getDao(Imagenes.class);
        return usuarioDao;

    }


    public RuntimeExceptionDao<Imagenes, Integer> getUsuarioDBruntimeDao(){
        if (usuarioDBruntimeDao == null)
        usuarioDBruntimeDao = getRuntimeExceptionDao(Imagenes.class);
        return usuarioDBruntimeDao;

    }

///////////////////////////////////////////////////////////////////////////

    @Override
    public void close() {
        super.close();
        usuarioDao = null;
    }




}
