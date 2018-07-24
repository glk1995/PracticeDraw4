package com.hencoder.hencoderpracticedraw4.practice;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice14FlipboardView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degree;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 180);

    public Practice14FlipboardView(Context context) {
        super(context);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

        animator.setDuration(2500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        canvas.save();

        canvas.save();
        canvas.clipRect(x, y, x + bitmapWidth, centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        camera.save();

        //Sample代码是写在这里的，Canvas是反着的，所以相当于是根据旋转角度来选择截上半部分还是下半部分
        //其实需要裁剪的永远是下半部分，只是degree > 90 被旋转到上半部分了
//        if (degree < 90) {
//            canvas.clipRect(0, centerY, getWidth(), getHeight());
//        } else {
//            canvas.clipRect(0, 0, getWidth(), centerY);
//        }

        camera.rotateX(degree);
        canvas.translate(centerX, centerY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        //这里是先裁剪后旋转
        canvas.clipRect(x, centerY, x + bitmapWidth, y + bitmapHeight);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}
