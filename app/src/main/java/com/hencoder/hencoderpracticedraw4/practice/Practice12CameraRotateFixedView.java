package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);
    Camera camera = new Camera();
    Matrix matrix = new Matrix();

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX1 = point1.x + bitmapWidth / 2;
        int centerY1 = point1.y + bitmapHeight / 2;
        int centerX2 = point2.x + bitmapWidth / 2;
        int centerY2 = point2.y + bitmapHeight / 2;

        //先移动到原点，然后投射，最后移动回来，Canvas的绘制是反的，所以代码是反写
        canvas.save();
        camera.save();
        camera.rotateX(30);
        canvas.translate(centerX1, centerY1);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.translate(-centerX1, -centerY1);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        camera.save();
        canvas.translate(centerX2, centerY2);
        camera.rotateY(30);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX2, -centerY2);
        camera.restore();
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();

        //使用camera.getMatrix配合Matrix的preTranslate/postTranslare
        //这么做的优点主要是逻辑比较顺
//        canvas.save();
//        camera.save();
//        camera.rotateX(30);
//        camera.getMatrix(matrix);
//        camera.restore();
//        matrix.preTranslate(-centerX1, -centerY1);
//        matrix.postTranslate(centerX1, centerY1);
//        canvas.concat(matrix);
//        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
//        canvas.restore();
//
//        canvas.save();
//        camera.save();
//        matrix.reset();
//        camera.rotateY(30);
//        camera.getMatrix(matrix);
//        camera.restore();
//        matrix.preTranslate(-centerX2, -centerY2);
//        matrix.postTranslate(centerX2, centerY2);
//        canvas.concat(matrix);
//        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
//        canvas.restore();

    }
}
