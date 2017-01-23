package pausasactivas.francisco.com.grabilityapp;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImage;
import com.github.snowdream.android.widget.SmartImageView;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.DatabaseConnection;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

public class MainActivity extends AppCompatActivity {

    private SmartImageView ima;
    GridView gridView;
    private SmartImageView imagens[];
    Context context = this;


    String urlimag = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";

    private Bitmap[] imaarr = new Bitmap[20];
    private String[] arrsummary = new String[20];
    private String[] arrappnam = new String[20];
    private String[] arrcategory = new String[20];
    public static final int NOTIFICACION_ID = 1;
    private boolean internet = true;

    private static final int MNU_OPC1 = 1;
    private static final int MNU_OPC2 = 2;
    private static final int MNU_OPC3 = 3;


    Imagenes usuario = new Imagenes();
    DBHelper helper = null;
   // int sw1 = 0;
    int sw2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validaInternet();


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MnuOpc1:
                Intent intent = new Intent(MainActivity.this, ListaApps.class);
                startActivity(intent);
                return true;
            case R.id.MnuOpc2:
                intent = new Intent(MainActivity.this, ListaCategory.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    ////////////////////////////////proceso interno/////////////////////////////////////

    public void llenaGrid() {
        gridView = (GridView) findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(MainActivity.this, imaarr, arrsummary);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //    Toast.makeText(MainActivity.this,"Clicked " +arrsummary[position], Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Detalle");
                builder.setMessage(arrsummary[position]);
                builder.setPositiveButton("0K", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Toast.makeText(MainActivity.this, R.string.mensaje, Toast.LENGTH_SHORT).show();

                    }
                });
                Dialog dialog = builder.create();
                dialog.show();

            }
        });


    }

    private class ProcesoDescargar extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Por favor espere...");
            pDialog.setCancelable(false);
            pDialog.show();

            if (internet == true) {

                try {

                        limpiarTable();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {


            if (internet == true) {

                    HttpHandler sh = new HttpHandler();
                    // Making a request to url and getting response
                    String jsonStr = sh.makeServiceCall(urlimag);
                    if (jsonStr != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            JSONObject feed = jsonObj.getJSONObject("feed");

                            //feed.length()

                            for (int i = 0; i < feed.length(); i++) {
                                JSONObject vauthor = feed.getJSONObject("author");
                                JSONObject vupdated = feed.getJSONObject("updated");
                                JSONObject vrights = feed.getJSONObject("rights");
                            }

                            JSONArray objarryentry = feed.getJSONArray("entry");


                            for (int j = 0; j < objarryentry.length(); j++) {


                                JSONObject c = objarryentry.getJSONObject(j);

                                JSONObject vname = c.getJSONObject("im:name");
                                arrappnam[j] = vname.getString("label");
                                JSONObject vcategory = c.getJSONObject("category");
                                JSONObject atrryb = vcategory.getJSONObject("attributes");
                                arrcategory[j] = atrryb.getString("label");
                                JSONObject vsummary = c.getJSONObject("summary");
                                JSONArray imaarray = c.getJSONArray("im:image");
                                JSONObject vlabel = imaarray.getJSONObject(2);
                                Bitmap varimagen = descargarImagen(vlabel.getString("label"));
                                imaarr[j] = varimagen;
                                String descrImagen = vsummary.getString("label");
                                arrsummary[j] = descrImagen;

                            }


                        } catch (final JSONException e) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            });

                        }
                    } else {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Couldn't get json from server. Check LogCat for possible errors!",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }


                ////////

            }else{

                try {
                    obtenerData();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (internet == true) {



                    llenaGrid();
                    for (int i = 0; i < imaarr.length; i++) {
                        //////////////////////////////
                        Bitmap bmp = imaarr[i];
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        usuario.setImagen(byteArray);
                        ////////////////////////////

                        usuario.setDesc(arrsummary[i].toString());
                        usuario.setAppname(arrappnam[i].toString());
                        usuario.setAppcategory(arrcategory[i].toString());

                        try {
                            registrarBD();


                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    llenaGrid();

            ////////
            }
            if (internet == false) {
                llenaGrid();
            }


            pDialog.setCancelable(true);
            pDialog.cancel();
        }

        private Bitmap descargarImagen(String imageHttpAddress) {
            URL imageUrl = null;
            Bitmap imagen = null;
            try {
                imageUrl = new URL(imageHttpAddress);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagen = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return imagen;
        }

    }


    public void limpiarTable() throws SQLException {

        if (helper == null) {
            helper = OpenHelperManager.getHelper(this, DBHelper.class);

        }

        RuntimeExceptionDao<Imagenes, Integer> usuarioDao = helper.getUsuarioDBruntimeDao();


        String table = usuarioDao.getTableName();


        if (table.equals("imagenes")) {
            DeleteBuilder<Imagenes, Integer> deleteBuilder = usuarioDao.deleteBuilder();
            deleteBuilder.prepare();
            onPause();

                OpenHelperManager.releaseHelper();
                usuarioDao.delete(deleteBuilder.prepare());
            //usuarioDao.commit(Savepoint sw1);

            helper.close();

        }

    }



  public void registrarBD() throws SQLException {
        helper = OpenHelperManager.getHelper(this, DBHelper.class);

                RuntimeExceptionDao<Imagenes, Integer> usuarioDao = helper.getUsuarioDBruntimeDao();
                    usuarioDao.create(new Imagenes(usuario.getImagen(), usuario.getDesc(), usuario.getAppname(), usuario.getAppcategory()));

                //Toast.makeText(getApplicationContext(),"Informacion Registrada", Toast.LENGTH_SHORT).show();

                OpenHelperManager.releaseHelper();

        helper.close();


    }

    public void validaInternet(){

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo ninfo = cManager.getActiveNetworkInfo();
        if(ninfo!=null && ninfo.isConnected()){
            internet = true;

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("ATENCION");
            builder.setMessage("La aplicación se encuentra en modo online, por lo que " +
                    "se acutualizarar la informacion desde internet");
            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
           new ProcesoDescargar().execute();

                }
            });
            Dialog dialog = builder.create();
            dialog.show();
        }else{
            internet = false;

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("ATENCION");
            builder.setMessage("La aplicación se encuentra en modo offline, por lo que " +
                    "se mostrara la ultima informacion descargada");
            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    new ProcesoDescargar().execute();

                }
            });
            Dialog dialog = builder.create();
            dialog.show();


        }

    }




///////////////////////////////////////////////////FIN////////////////////////////////////////////////

    public void obtenerData() throws SQLException, IOException {

        helper = OpenHelperManager.getHelper(context, DBHelper.class);

        RuntimeExceptionDao<Imagenes, Integer> usuarioDao = helper.getUsuarioDBruntimeDao();

        int cant = (int) usuarioDao.countOf();
        int i = 0;
        byte[] image = null;
        Bitmap bitmap = null;

        QueryBuilder<Imagenes, Integer> queryBuilder = usuarioDao.queryBuilder().selectColumns("imagen");
        PreparedQuery<Imagenes> preparedQuery = queryBuilder.prepare();
        CloseableIterator<Imagenes> iteratorq = usuarioDao.iterator(preparedQuery);
        AndroidDatabaseResults resul = (AndroidDatabaseResults) iteratorq.getRawResults();
        Cursor cursor = resul.getRawCursor();
        //byte[] bm = cursor.getBlob(0);
        imaarr = new Bitmap[resul.getCount()];
        int v = 0;
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    //labels.add(cursor.getString(1));
                    byte[] bm = cursor.getBlob(0);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmapx = BitmapFactory.decodeByteArray(bm, 0, bm.length, options);
                    imaarr[v] = bitmapx;
                    v++;
                } while (cursor.moveToNext());
            }
        }

      //  iteratorq.close();
//////////////////////////////OBTENEMOS LA DESCRIPCION DE LAS IMAGENES DE LA BD//////////////////////

        queryBuilder = usuarioDao.queryBuilder().selectColumns("descripcion");
        preparedQuery = queryBuilder.prepare();
        CloseableIterator<Imagenes> iteratorx = usuarioDao.iterator(preparedQuery);
        resul = (AndroidDatabaseResults) iteratorx.getRawResults();
        cursor = resul.getRawCursor();

        int z = 0;
        arrsummary = new String[resul.getCount()];
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    String resdesc = cursor.getString(0);
                    arrsummary[z] = resdesc;
                    z++;
                } while (cursor.moveToNext());
            }
        }
    }
    }




