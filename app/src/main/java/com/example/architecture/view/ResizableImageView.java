package com.example.architecture.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * The purpose of this Image view is best explained in the SO post:
 * https://stackoverflow.com/questions/8232608/fit-image-into-imageview-keep-aspect-ratio-and-then-resize-imageview-to-image-d
 * Problem: Given an image of any size, how do we fit it into an image view of some other size such
 * that its larger dimension is scaled to fit inside the image view and the smaller dimension is
 * shrunk to preserve its aspect ratio. While this can be achieved without the help of this custom
 * view, the problematic behavior is that the image view does not shrink to the lower dimension
 * resulting in an empty space surrounding the image view.
 *
 * @hide
 */
// TODO (ashwinraghav) tests pending
public class ResizableImageView extends AppCompatImageView {
    private int mDensityDpi;

    public ResizableImageView(Context context) {
        super(context);
        init(context);
    }

    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ResizableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mDensityDpi = (int) (displayMetrics.density * 160f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            Drawable d = getDrawable();
            boolean adjustViewBounds = getAdjustViewBounds();

            if (d != null && adjustViewBounds) {
                scalePxToDp(d);
                checkMinDim();
            }
        }
    }

    private void checkMinDim() {
        int minWidth = Math.max(getMinimumWidth(), getSuggestedMinimumWidth());
        int minHeight = Math.max(getMinimumHeight(), getSuggestedMinimumHeight());
        int widthSpec = getMeasuredWidth();
        int heightSpec = getMeasuredHeight();

        // Scale TOP if the size is too small
        float scaleW = 1.0f;
        float scaleH = 1.0f;

        if (widthSpec < minWidth) {
            scaleW = (float) minWidth / (float) widthSpec;
        }
        if (heightSpec < minHeight) {
            scaleH = (float) minHeight / (float) heightSpec;
        }

        float scale = (scaleW > scaleH) ? scaleW : scaleH;

        if (scale > 1.0) {
            int targetW = (int) Math.ceil(widthSpec * scale);
            int targetH = (int) Math.ceil(heightSpec * scale);
            Dimensions t = bound(targetW, targetH);
            setMeasuredDimension(t.w, t.h);
        }
    }

    private void scalePxToDp(Drawable d) {
        int widthSpec = d.getIntrinsicWidth();
        int heightSpec = d.getIntrinsicHeight();

        // Convert 1px to 1dp while keeping bounds
        int targetW = (int) Math.ceil(widthSpec * mDensityDpi / 160);
        int targetH = (int) Math.ceil(heightSpec * mDensityDpi / 160);

        Dimensions t = bound(targetW, targetH);

        setMeasuredDimension(t.w, t.h);
    }

    private Dimensions bound(int targetW, int targetH) {
        int maxWidth = getMaxWidth();
        int maxHeight = getMaxHeight();

        if (targetW > maxWidth) {
            targetH = targetH * maxWidth / targetW;
            targetW = maxWidth;
        }

        if (targetH > maxHeight) {
            targetW = targetW * maxHeight / targetH;
            targetH = maxHeight;
        }

        return new Dimensions(targetW, targetH);
    }

    /** Basically a Pair of integers */
    private static class Dimensions {
        final int w;
        final int h;

        private Dimensions(int w, int h) {
            this.w = w;
            this.h = h;
        }
    }
}
