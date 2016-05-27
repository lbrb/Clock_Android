package com.example.liangbin.canvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView imageView = null;
    private int width, height;
    private float h, m, s;
    private Calendar calendar;
    private float degree;

    private float hw,mw,sw;

    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        width = 500;
        height = 500;
        hw = width/40;
        mw = width/80;
        sw = width/120;

        new ClockTask().execute("");
    }

    private Bitmap drawClock() {
        Bitmap bitmap = drawClockFace();
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        float x0,y0,x1,y1;

        calendar = Calendar.getInstance();
        h = calendar.get(Calendar.HOUR);
        m = calendar.get(Calendar.MINUTE);
        s = calendar.get(Calendar.SECOND);
        Log.d(TAG, "drawClock: h:"+h+",m:"+m+",s:"+s);

        //小时
        degree = (h+m/60+s/3600)/12*360;
        Log.d(TAG, "drawClock: degree:"+degree);
        paint.setStrokeWidth(hw);
        canvas.rotate(degree,width/2,height/2);
        x0 = width/2;
        y0 = width/15*4;
        x1 = width/2;
        y1 = width/2;
        Log.d(TAG, "drawClock:h: x0："+x0+",y0:"+y0+",x1:"+x1+",y1:"+y1);
        canvas.drawLine(x0,y0,x1,y1,paint);
        canvas.rotate(-degree, width/2, height/2);

        //分钟
        degree = (m+s/60)/60*360;
        Log.d(TAG, "drawClock: degree:"+degree);
        paint.setStrokeWidth(mw);
        canvas.rotate(degree,width/2,height/2);
        x0 = width/2;
        y0 = width/15*3;
        x1 = width/2;
        y1 = width/2;
        Log.d(TAG, "drawClock:m: x0："+x0+",y0:"+y0+",x1:"+x1+",y1:"+y1);
        canvas.drawLine(x0,y0,x1,y1,paint);
        canvas.rotate(-degree,width/2,height/2);

        //秒
        degree = s/60*360;
        Log.d(TAG, "drawClock: degree:"+degree);
        paint.setStrokeWidth(sw);
        canvas.rotate(degree, width/2, height/2);
        x0 = width/2;
        y0 = width/15*2;
        x1 = width/2;
        y1 = width/2;
        Log.d(TAG, "drawClock：s: x0："+x0+",y0:"+y0+",x1:"+x1+",y1:"+y1);
        canvas.drawLine(x0,y0,x1,y1,paint);
        canvas.rotate(-degree, width/2, height/2);

        return bitmap;
    }

    private Bitmap drawClockFace() {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(width/2, height/2, width/2, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width/2, height/2, 10,paint);

        for (int i=0;i<12;i++) {
            int h = i%3 == 0 ? width/15:width/30;
            int w = i%3 == 0 ? 10:5;
            paint.setStrokeWidth(w);
            canvas.drawLine(width/2,0,width/2,h, paint);
            canvas.rotate(30, width/2, height/2);
        }
    return bitmap;
    }

    private class ClockTask extends AsyncTask<Object,Object,Object> {

        @Override
        protected Object doInBackground(Object[] params) {
            Log.d(TAG, "doInBackground: ");
            while (isRunning) {
                publishProgress("");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            imageView.setImageBitmap(drawClock());
        }
    }
}
