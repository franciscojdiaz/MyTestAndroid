package pausasactivas.francisco.com.grabilityapp;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.IOException;
import java.sql.SQLException;

public class ListaCategory extends AppCompatActivity {


    private String[] arrappname = new String[19];
    private String[] arrappnameaux = new String[19];

    ProgressDialog pDialogw = null;
    CloseableIterator<Imagenes> iteratorz = null;

    // Imagenes usuario = new Imagenes();
    DBHelper helper = null;
    int sw = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_apps);

        ////////LOGO DE LA APLICACION//////////
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        // actionBar.setLogo(R.drawable.logo_blanco);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("   Lista De Categorias");
        ///////////////////////////////////////
        if(sw ==0) {
            new ProcesoDescargar().execute();
        }


    }
    @Override
    public void onPause(){
        super.onPause();
        sw = 1;
        //helper = OpenHelperManager.getHelper(ListaApps.this, DBHelper.class);
        OpenHelperManager.releaseHelper();

    }

    @Override
    public void onStop(){
        super.onStop();
        sw = 1;

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        pDialogw.dismiss();
    }


    private class ProcesoDescargar extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            // Showing progress dialog
            helper = OpenHelperManager.getHelper(ListaCategory.this, DBHelper.class);


            pDialogw = new ProgressDialog(ListaCategory.this);
            pDialogw.setMessage("Por favor espere...");
            pDialogw.setCancelable(false);
            pDialogw.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                try {
                    obtieneDatdaBD();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            pDialogw.setCancelable(true);

            pDialogw.cancel();
            llenarLisView();

        }



    }


    public void  llenarLisView() {

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, arrappname);
        ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);


    }

    public void  llenarLisView2() {
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, arrappnameaux);

        ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);

    }

    public void obtieneDatdaBD() throws SQLException, IOException {

        RuntimeExceptionDao<Imagenes, Integer> usuarioDao = helper.getUsuarioDBruntimeDao();
        QueryBuilder<Imagenes, Integer> queryBuilder = usuarioDao.queryBuilder().selectColumns("appcategory").distinct();
        PreparedQuery<Imagenes> preparedQuery = queryBuilder.prepare();
        iteratorz = this.helper.getUsuarioDao().iterator(preparedQuery);
        AndroidDatabaseResults resul = (AndroidDatabaseResults) iteratorz.getRawResults();
        Cursor cursor = resul.getRawCursor();

        arrappname = new String[resul.getCount()];
        int v = 0;

        if (cursor != null && cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {

                do {

                    String resdesc = cursor.getString(0);

                    arrappname[v] = resdesc;

                    v++;

                } while (cursor.moveToNext());

            }

        }

        if(cursor != null && !cursor.isClosed()){
            iteratorz = null;
        }

    }

}
