package com.example.gridviewimage.view.controls;

/**
 * Created by 焦思远 on 2016/9/8.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.gridviewimage.view.acitvity.MaxPictureActivity;

public class TestImageView extends ImageView {
    private static final int MAX_DISTANCE_FOR_CLICK = 100;
    //    当前状态，当Status = 5时,是两个手指再屏幕上离开一根后的状态
    int Status = 0;
    //    单击
    int Click_One = 1;
    //    双击第一次
    int Click_Two_One = 2;
    //    移动
    int Click_Move = 3;
    //    缩放
    int Zoom = 4;
    //    双击状态保存
    int Double_Click = 0;
    //    双击第二次
    int Click_Two_Two = 3;
    //    双击第二次时放大倍数
    float Click_Two_Scale = 2.0f;
    //    当前按下的位置
    float downX, downY;
    //    当前离开时位置
    float upX, upY;
    //    记录上一次点击的位置
    float eventX, eventY;
    //    计算后的双指之间的距离
    float old_distance, new_distance;
    //    最小间距
    private static final int DOUBLE_POINT_DISTANCE = 10;
    //    两指中心点
    private PointF centerPoint = new PointF();
    //    第二手指头，第一次按下的时候的度数
    float oldRotation = 0;
    //    旋转度数
    float rotation = 0;
    //    上下文
    Context context;
    //    用来区分单点还是双击的Handler
    Handler SleepHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0) {
                Log.e("Tag", "单击成功");
                Status = 0;
                eventX = 0;
                eventY = 0;
                ((MaxPictureActivity) context).finish();
            }
        }
    };
    // 睡眠线程
    SleepThead sleepThead;
    //    初始化时的Matrix
    private Matrix mMatrix;
    //    不改变的Matrix
    private Matrix matrix;
    //    改变后的Matrix
    private Matrix currentMatrix = new Matrix();
    //用来判断是否是第一次移动
    private boolean firstMove = true;
    //    基础Bitmap
    Bitmap bitmap = null;
    //    改变后的Bitmap
    Bitmap currentBitmap = null;
    //双指缩放时改变的scale
    float uScale;

    public TestImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mMatrix = new Matrix();
        mMatrix.set(getImageMatrix());
        currentMatrix = new Matrix();
        matrix = new Matrix();
        matrix.set(getImageMatrix());
    }

    public void imageBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.currentBitmap = bitmap;
        System.out.println(bitmap.getHeight() + "----------图片高度");
        System.out.println(bitmap.getWidth() + "----------图片宽度");

    }

    /*触摸事件*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            单点触摸动作
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                if (Status == 5) {
                    Status = 0;
                }
                break;
//            单点触摸离开动作
            case MotionEvent.ACTION_UP:
                upX = event.getRawX();
                upY = event.getRawY();
                if (Math.abs(upX - downX) > MAX_DISTANCE_FOR_CLICK || Math.abs(upY - downY) > MAX_DISTANCE_FOR_CLICK) {
                    Log.e("Tag", "当前点击按下和抬起大于了100");
                } else {
//                   判断是否为第一次点击
                    if (Status == 0 || Status == 5) {
                        eventX = downX;
                        eventY = downY;
                        /*在ACTION_UP时间中启动线程来延迟300ms，如果300ms之前就又一次执行了ACTION_UP，则代表是两次点击，
                如果没来，清空基本数据，执行Handler中的单点事件*/
                        if (Status == 0) {
                            Status = Click_One;
                            sleepThead = new SleepThead();
                            sleepThead.start();
                        } else {
                            Status = 5;
                        }
                    } else if (Status == Click_One) {
                        try {
                            sleepThead.interrupt();
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                        /*在这个范围内都算是双击*/
                        if (eventX - downX < 10 || eventX - downX > -10 || eventY - downY < 10 || eventY - downY > -10) {
                            if (Double_Click == 0) {
//                            第一次双击
//                                setScaleType(ScaleType.CENTER_CROP);
                                mMatrix.set(getImageMatrix());
                                DoubleClick(Click_Two_Scale, event, mMatrix);
                                resetTheData();
                                Double_Click = Click_Two_One;
                            } else if (Double_Click == Click_Two_One) {
//                            第二次双击
                                mMatrix.set(getImageMatrix());
                                DoubleClick(Click_Two_Scale, event, mMatrix);
                                Double_Click = Click_Two_Two;
                            } else if (Double_Click == Click_Two_Two) {
//                            第三次双击
                                currentMatrix.set(mMatrix);
                                currentMatrix.postScale(0, 0, getWidth() / 2, getHeight() / 2);
                                setImageMatrix(currentMatrix);
                                setScaleType(ScaleType.FIT_CENTER);
                                Double_Click = 0;
                                resetTheData();
                            }
                        }
                    } else if (Status == Click_Move) {
                        resetTheData();
                    }
                }
                break;
//            双指点击第二指头点击时触发
            case MotionEvent.ACTION_POINTER_DOWN:
                old_distance = spacing(event);
                Status = Zoom;
                oldRotation = rotation(event);
                if (old_distance > DOUBLE_POINT_DISTANCE) {
                    midPoint(centerPoint, event);
                }
                break;
//            双指点击第二指头离开其中一个时触发
            case MotionEvent.ACTION_POINTER_UP:
                Status = 5;
                eventX = 0;
                eventY = 0;
                mMatrix.set(getImageMatrix());
                setImageMatrix(mMatrix);
                float[] value = new float[9];
                getImageMatrix().getValues(value);
                System.out.println(uScale + "------------------uScale");
//                这里存在滑动速度过快图片消失问题
                if (uScale < 1.0) {

                    System.out.println(getWidth() * uScale + "---------------bitmap.getWidth()/uScale");
                    if (bitmap.getWidth() * value[Matrix.MSCALE_X] < getWidth()) {
                        mMatrix.set(matrix);
                        mMatrix.setScale(0, 0, getWidth() / 2, getHeight() / 2);
                        setImageMatrix(mMatrix);
                        mMatrix.set(getImageMatrix());
                        currentMatrix.set(getImageMatrix());
                        setScaleType(ScaleType.FIT_CENTER);
                        firstMove = true;
                    }
                } else if (uScale >= 1.0) {
//                    这里存在判断不通过的问题
                    if (value[Matrix.MSCALE_X] >= 1.0 && value[Matrix.MSCALE_X] <= 4.0) {
                        DoubleClick(4.0f, event, matrix);
                        resetTheData();
                    }
                }
//                mMatrix.set(getImageMatrix());
//                setImageMatrix(mMatrix);
                break;
//            触摸点移动动作
            case MotionEvent.ACTION_MOVE:
                Log.e("Tag", "触摸点移动动作" + Status);
                try {
                    sleepThead.interrupt();
                } catch (NullPointerException e) {
//                    e.printStackTrace();
                }
                setScaleType(ScaleType.MATRIX);
                if (firstMove) {
                    mMatrix.set(getImageMatrix());
                    firstMove = false;
                }
                if (Status == 0 || Status == Click_Move) {
                    if (event.getPointerCount() == 1) {
                        SingleRefersToMobile(event);
                    }
                } else if (Status == Zoom) {
                    new_distance = spacing(event);
                    rotation = rotation(event) - oldRotation;
                    float scale = new_distance / old_distance;
//                    设置最小缩小值和最大放大值
                    currentMatrix.set(mMatrix);
                    currentMatrix.postScale(scale, scale, centerPoint.x, centerPoint.y);// 缩放
                    currentMatrix.postRotate(rotation, centerPoint.x, centerPoint.y);// 旋轉
                    uScale = scale;
                    setImageMatrix(currentMatrix);

                }
                break;
//            触摸动作取消,移动到控件之外时会被触发
            case MotionEvent.ACTION_CANCEL:
                try {
                    sleepThead.interrupt();
                } catch (NullPointerException e) {
//                    e.printStackTrace();
                }
                Log.e("Tag", "触摸动作取消");
                firstMove = true;
                resetTheData();
                break;
//            触摸动作超出边界
            case MotionEvent.ACTION_OUTSIDE:
                try {
                    sleepThead.interrupt();
                } catch (NullPointerException e) {
//                    e.printStackTrace();
                }
                Log.e("Tag", "触摸动作超出边界");
                resetTheData();
                break;
        }
        return super.onTouchEvent(event);
    }

    // 点击的两个点之间的直线距离
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        if (x < 0) {
            x = -x;
        }
        float y = event.getY(0) - event.getY(1);
        if (y < 0) {
            y = -y;
        }
//        该处通过勾股定理计算出两个点之间的直线距离
        return (float) Math.sqrt(x * x + y * y);
    }

    // 取旋转角度
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    // 取手势中心点
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /*单指移动操作*/
    private void SingleRefersToMobile(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        x = x - downX;
        y = y - downY;
        if (x >= 10 || y >= 10 || x <= -10 || y <= -10) {
            Status = Click_Move;
            mMatrix.set(getImageMatrix());
            mMatrix.postTranslate(x, y);
            setImageMatrix(mMatrix);
            downX = event.getRawX();
            downY = event.getRawY();

        }
    }


    /*双击时触发*/
    private void DoubleClick(float scale, MotionEvent event, Matrix matrix) {
        //                        如果为第二次点击,判断和第一次点击的位置是否大于100
        if (Math.abs(eventX - downX) > MAX_DISTANCE_FOR_CLICK || Math.abs(eventY - downY) > MAX_DISTANCE_FOR_CLICK) {
            Log.e("Tag", "第一次和第二次位置相差大于100");
            sleepThead.interrupt();
            resetTheData();
        } else {
            Log.e("Tag", "双击成功");
            setScaleType(ScaleType.MATRIX);
            currentMatrix.set(matrix);
            currentMatrix.postScale(scale, scale, event.getRawX(), event.getRawY());
            setImageMatrix(currentMatrix);
            resetTheData();
        }
    }

    /*重置基本数据*/
    private void resetTheData() {
        Status = 0;
        eventX = 0;
        eventY = 0;
    }

    private class SleepThead extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                sleep(500);
                Message msg = new Message();
                msg.arg1 = 0;
                TestImageView.this.SleepHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}