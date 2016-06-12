package com.example.pavlo.apdairy2.views;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavlo.apdairy2.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by pavlo on 08.06.16.
 */
public class PageView extends View {

    private static final String LOG_TAG = "PageView log";

    TextView titleTextView;
    TextView explanationTextView;
    ImageView imageView;

    private Context context;

    private RelativeLayout layout;

    private OnClickListener imageViewClickListener;

    public PageView(Context context, LayoutInflater inflater) {
        super(context);
        this.context = context;

        layout = (RelativeLayout) inflater.inflate(R.layout.page_item, null);

        initComponents();
    }

    private void initComponents() {
        titleTextView = (TextView) layout.findViewById(R.id.titleTextView);
        explanationTextView = (TextView) layout.findViewById(R.id.explanationTextView);
        imageView = (ImageView) layout.findViewById(R.id.imageView);
        imageView.setOnClickListener(getImageViewClickListener());
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setExplanation(String explanation) {
        explanationTextView.setText(explanation);
    }

    public void setImage(String imageUrl) {
        try {
            Picasso.with(getContext()).
                    load(imageUrl).
                    placeholder(R.drawable.ic_android_black_18dp).
                    error(R.drawable.ic_highlight_off_black_18dp).
                    into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    private OnClickListener getImageViewClickListener() {
        if (imageViewClickListener == null) {
            imageViewClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).
                            setTitle(R.string.alert_dialof_title).
                            setMessage(R.string.alert_dialog_message).
                            setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                                        imageView.buildDrawingCache();
                                        Bitmap bitmap = imageView.getDrawingCache();
                                        wallpaperManager.setBitmap(bitmap);
                                        Toast.makeText(context, R.string.alert_dialog_yes_action, Toast.LENGTH_SHORT).show();
                                        Log.d(LOG_TAG, getResources().getString(R.string.alert_dialog_yes_action));
                                    } catch (IOException e) {
                                        Toast.makeText(context, R.string.alert_dialog_error_message, Toast.LENGTH_SHORT).show();
                                        Log.d(LOG_TAG, getResources().getString(R.string.alert_dialog_error_message) + e.getMessage());
                                    }
                                }
                            }).
                            setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, R.string.alert_dialog_no_action, Toast.LENGTH_SHORT).show();
                                }
                            }).
                            show();
                }
            };
        }

        return imageViewClickListener;
    }
}
