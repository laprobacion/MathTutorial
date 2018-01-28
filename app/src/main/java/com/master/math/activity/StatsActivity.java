package com.master.math.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.master.math.R;
import com.master.math.activity.util.Item;
import com.master.math.activity.util.SaveState;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        TextView correctAnswers = (TextView) findViewById(R.id.correctAnswers);
        correctAnswers.setText("Number of correct answers: " + SaveState.get(this).getCorrectAnswerCount());
        TextView multiplicationMistakes = (TextView) findViewById(R.id.multiplicationMistakes);
        multiplicationMistakes.setText("Multiplication Mistakes: " + SaveState.get(this).getMultiplicationMistakeCount());
        TextView timeSpent = (TextView) findViewById(R.id.timeSpent);
        timeSpent.setText("Time Spent: " + SaveState.get(this).getTimeSpentString());
        final TextView message = (TextView) findViewById(R.id.message);
        Button capture = (Button) findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = null;
                if(checkPermissionREAD_EXTERNAL_STORAGE(StatsActivity.this)){
                    try {
                        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                        v.getRootView().setDrawingCacheEnabled(true);
                        bitmap = v.getRootView().getDrawingCache();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream);
                        File file = generateFile();
                        FileOutputStream fileoutputstream = new FileOutputStream(file);
                        fileoutputstream.write(bytearrayoutputstream.toByteArray());
                        fileoutputstream.close();
                        message.setText("Saved in " + file.getAbsolutePath());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }



            }
        });
    }



    private File generateFile() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date();
        String filename = "Math_Screenshot_" + dateFormat.format(date) + ".png";
        File file = new File(Environment.getExternalStorageDirectory() ,filename);
        if (file.exists()) {
            file.delete();
            file = new File(Environment.getExternalStorageDirectory(),filename);
        }
        file.createNewFile();
        return file;
    }
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    public void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(StatsActivity.this, "GET_ACCOUNTS Denied",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        }
    }
}
