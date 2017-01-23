package pausasactivas.francisco.com.grabilityapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImage;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.*;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

/**
 * Created by francisco.diaz on 12/01/2017.
 */

public class GridAdapter extends BaseAdapter {




    private Bitmap imag [] = new Bitmap[60];
    private String summ [];
    private String sumarys [];

    private Context context;
    private int icons [];
    private LayoutInflater inflater;


    public GridAdapter(Context context, Bitmap[] imagxx , String sumary []){

        this.context=context;
        this.imag=imagxx;
        this.sumarys=sumary;



    }



    @Override
    public int getCount() {
        return sumarys.length;
    }

    @Override
    public Object getItem(int position) {
        return sumarys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View gridView = convertView;

        //SmartImageView imagen = (SmartImageView) gridView.findViewById(R.id.my_imagen);

        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.custom_layout,null);

        }

        ImageView icon = (ImageView) gridView.findViewById(R.id.icons);
        icon.setImageBitmap(imag[position]);


        return gridView;
    }


}
