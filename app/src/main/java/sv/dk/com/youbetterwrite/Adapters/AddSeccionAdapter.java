package sv.dk.com.youbetterwrite.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import sv.dk.com.youbetterwrite.Modelos.SectionsItem;
import sv.dk.com.youbetterwrite.R;

public class AddSeccionAdapter extends ArrayAdapter<SectionsItem> implements View.OnClickListener {

    private ArrayList<SectionsItem> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView seccionName;
        ImageView seccionImage;
    }

    private int lastPosition = -1;

    public AddSeccionAdapter(ArrayList<SectionsItem> data, Context mContext) {
        super(mContext, R.layout.Row_Seccion, data);
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        SectionsItem dataModel=(SectionsItem) object;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SectionsItem dataModel = dataSet.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.Row_Seccion, parent, false);
            viewHolder.seccionName = (TextView) convertView.findViewById(R.id.seccionName);
            viewHolder.seccionImage = (ImageView) convertView.findViewById(R.id.seccionImage);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.seccionName.setText(dataModel.getName());
        Bitmap image = loadBitmap(dataModel.getUrl());
        viewHolder.seccionImage.setImageBitmap(image);
        // Return the completed view to render on screen
        return convertView;
    }

    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}
