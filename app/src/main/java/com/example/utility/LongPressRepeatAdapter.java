package com.example.utility;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class LongPressRepeatAdapter {
    private static final int DEFAULT_INTERVAL_MS = 1000;

    public abstract class Callbacks {
        public abstract void onClick(View v);
        public void onLongPressed(View v) {
            onClick(v);
        }
        public void onKeyRepeat(View v) {
            onClick(v);
        }
    }

    public static void bless(@NonNull View view, @NonNull Callbacks callbacks) {
        bless(view, DEFAULT_INTERVAL_MS, callbacks);
    }

    public static void bless(@NonNull final View view, final int interval, @NonNull final Callbacks callbacks) {
        final Handler handler = new Handler();
        final BooleanWrapper isContinue = new BooleanWrapper(false);

        final Runnable repeatRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isContinue.value) {
                    return;
                }

                callbacks.onLongPressed(view);
                handler.postDelayed(this, interval);
            }
        };

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbacks.onClick(view);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isContinue.value = true;
                callbacks.onKeyRepeat(view);
                handler.postDelayed(repeatRunnable, interval);
                return true;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    isContinue.value = false;
                }
                return false;
            }
        });
    }

    private static class BooleanWrapper {
        private boolean value;

        private BooleanWrapper(boolean value) {
            this.value = value;
        }
    }
}
