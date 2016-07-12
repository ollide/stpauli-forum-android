package org.ollide.stpauliforum.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ollide.stpauliforum.R;

public class PagingView extends LinearLayout {

    private View firstPage;
    private View prevPage;
    private TextView currentPage;
    private View nextPage;
    private View lastPage;

    private PagingListener listener = null;

    public PagingView(Context context) {
        super(context);
        setup();
    }

    public PagingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public PagingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PagingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    protected void setup() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_paging, this, true);

        firstPage = getChildAt(0);
        firstPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onFirst();
                }
            }
        });
        prevPage = getChildAt(1);
        prevPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onPrev();
                }
            }
        });
        currentPage = (TextView) getChildAt(2);
        nextPage = getChildAt(3);
        nextPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onNext();
                }
            }
        });
        lastPage = getChildAt(4);
        lastPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onLast();
                }
            }
        });
    }

    public PagingListener getListener() {
        return listener;
    }

    public void setListener(PagingListener listener) {
        this.listener = listener;
    }

    interface PagingListener {

        void onFirst();

        void onPrev();

        void onNext();

        void onLast();

    }

}
