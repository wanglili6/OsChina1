package com.bestteam.oschina.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.sip.SipAudioCall;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JTY on 2017/2/22.
 * 星系效果的自定义View
 */
public class GalaxyView extends View {
    public static final int FLUSH_RATE = 30; //刷新速率，30fps就够了
    private int paintCount; //表示绘制次数
    private float centerX;
    private float centerY;
    private List<Planet> planets;
    private Timer timer;
    private TimerTask runTask;
    private MyHandler mhandler;



    public GalaxyView(Context context) {
        this(context, null);
    }

    public GalaxyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalaxyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化星系集合
        planets = new ArrayList<>();
        //初始化Handler
        mhandler = new MyHandler();
        //初始化画笔
        initPaint();
    }


    private Paint mTrackPaint;
    private Paint mPlanetPaint;

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //轨道的画笔
        mTrackPaint = new Paint();
        mTrackPaint.setStyle(Paint.Style.STROKE);
        mTrackPaint.setAntiAlias(true);

        //行星的画笔
        mPlanetPaint = new Paint();
        mPlanetPaint.setStyle(Paint.Style.FILL);
        mPlanetPaint.setAntiAlias(true);
    }

    /**
     * 设置星系的中心点
     *
     * @param x 中心点的x轴
     * @param y 中心点的Y轴
     */
    public void setGalaxyCenter(float x, float y) {
        centerX = x;
        centerY = y;
        paintCount = 0;
    }


    /**
     * 添加一堆行星
     *
     * @param planets 装着行星的集合
     */
    public void addPlanets(List<Planet> planets) {
        this.planets.addAll(planets);
    }

    /**
     * 添加一颗行星
     *
     * @param planet 行星对象
     */
    public void addPlanet(Planet planet) {
        planets.add(planet);
    }

    /**
     * 获取默认的星系
     * 默认为5颗
     */
    public void setDefaultPlanets() {
        planets.add(new Planet(60, 1, 0, true));
        planets.add(new Planet(110, 2, 30, false));
        planets.add(new Planet(150, 0.5f, 90, true));
        planets.add(new Planet(200, 1.8F, 155, true));
        planets.add(new Planet(230, 1.5F, 237, false));
    }

    /**
     * 清除所有行星
     */
    public void clear() {
        planets.clear();
    }

    /**
     * 开始旋转！
     */
    public void startRotate() {
        timer = new Timer();
        runTask = new TimerTask() {
            @Override
            public void run() {
                //子线程
                mhandler.sendEmptyMessage(RE_DRAW);
            }
        };
        timer.schedule(runTask, 0, FLUSH_RATE);
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        //如果没有行星，返回
        if (planets.size() == 0) return;
        //保存画布状态
        int count = canvas.save();

        //绘制所有行星
        for (Planet planet : planets) {
            double y;
            double x;
            float angle;
            //判断是否顺时针，计算绘制角度
            if (planet.isClockwise()) {
                angle = (planet.getOriginAngle() + paintCount * planet.getAngleRate()) % 360;

            } else {
                angle = 360 - (planet.getOriginAngle() + paintCount * planet.getAngleRate()) % 360;

            }
            //根据角度设置行星绘制坐标
            x = Math.cos(Math.toRadians(angle)) * planet.getRadius() + centerX;
            y = Math.sin(Math.toRadians(angle)) * planet.getRadius() + centerY;
            //根据行星颜色设置画笔
            mPlanetPaint.setColor(planet.getColor());
            //绘制行星
            canvas.drawCircle((float) x, (float) y, planet.getSelfRadius(), mPlanetPaint);

            //绘制行星轨道
            mTrackPaint.setColor(planet.getTrackColor());
            mTrackPaint.setStrokeWidth(planet.getTrackWidth());
            canvas.drawCircle(centerX, centerY, planet.getRadius(), mTrackPaint);
        }

        //取出画布状态
        canvas.restoreToCount(count);
        ++paintCount;
        //当绘制次数超过int的最大值，重置为0
        if (paintCount < 0) {
            paintCount = 0;
        }
    }

    //View消失后，关闭计时刷新任务
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
    }

    /**
     * 行星的实体类
     */
    public static class Planet {
        /**
         * 行星轨道半径
         * 默认值是100
         */
        private int trackRadius = 100;

        /**
         * 行星轨道宽度
         * 单位px,默认值是1
         */
        private int trackWidth = 1;

        /**
         * 行星自身半径
         * 默认6
         */
        private int selfRadius = 5;

        /**
         * 行星颜色
         * 默认颜色是0XFF6FDB94
         */
        private int selfColor = 0XFF6FDB94;

        /**
         * 轨道颜色
         * 默认颜色是0XFF6FDB94
         */
        private int trackColor = 0XFF6FDB94;

        /**
         * 行星旋转的角速度
         * 每次刷新旋转的角度，单位：度
         */
        private float angleRate = 1F;

        /**
         * 行星起始旋转角度
         * 单位：度
         */
        private int startAngle = 0;

        /**
         * 是否顺时针旋转
         */
        private boolean isClockwise = true;

        /**
         * 使用的默认颜色和行星轨道
         *
         * @param trackRadius 轨道半径
         * @param angleRate   角速度
         * @param startAngle  起始角度
         * @param isClockwise 是否顺时针
         */
        public Planet(int trackRadius, float angleRate, int startAngle, boolean isClockwise) {
            this.trackRadius = trackRadius;
            this.angleRate = angleRate;
            this.startAngle = startAngle;
            this.isClockwise = isClockwise;
        }

        public Planet() {

        }

        public int getRadius() {
            return trackRadius;
        }

        public void setRadius(int mRadius) {
            this.trackRadius = mRadius;
        }

        public int getSelfRadius() {
            return selfRadius;
        }

        public void setSelfRadius(int mSelfRadius) {
            this.selfRadius = mSelfRadius;
        }

        public int getTrackWidth() {
            return trackWidth;
        }

        public void setTrackWidth(int mTrackWidth) {
            this.trackWidth = mTrackWidth;
        }

        public int getColor() {
            return selfColor;
        }

        public void setColor(int mColor) {
            this.selfColor = mColor;
        }

        public int getTrackColor() {
            return trackColor;
        }

        public void setTrackColor(int mTrackColor) {
            this.trackColor = mTrackColor;
        }

        public float getAngleRate() {
            return angleRate;
        }

        public void setAngleRate(float mAngleRate) {
            this.angleRate = mAngleRate;
        }

        public boolean isClockwise() {
            return isClockwise;
        }

        public void setClockwise(boolean clockwise) {
            isClockwise = clockwise;
        }

        public int getOriginAngle() {
            return startAngle;
        }

        public void setOriginAngle(int mOriginAngle) {
            this.startAngle = mOriginAngle;
        }
    }

    /**
     * 在主线程的Handler
     */
    private final int RE_DRAW = 127;

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RE_DRAW:
                    invalidate();
                    break;
            }
        }
    }
}
