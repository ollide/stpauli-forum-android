package org.ollide.stpauliforum.ui.widget;

import android.annotation.SuppressLint;
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
    private TextView pageInfo;
    private View nextPage;
    private View lastPage;

    private int currentPage;
    private int totalPages;

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
                    currentPage = 1;
                    listener.onPageRequested(currentPage);
                }
            }
        });
        prevPage = getChildAt(1);
        prevPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    currentPage = currentPage > 1 ? 1 : currentPage;
                    listener.onPageRequested(currentPage);
                }
            }
        });
        pageInfo = (TextView) getChildAt(2);
        nextPage = getChildAt(3);
        nextPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    currentPage = currentPage < totalPages ? currentPage + 1 : currentPage;
                    listener.onPageRequested(currentPage);
                }
            }
        });
        lastPage = getChildAt(4);
        lastPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    currentPage = totalPages;
                    listener.onPageRequested(currentPage);
                }
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public void setPageInfo(int current, int total) {
        currentPage = current;
        totalPages = total;

        pageInfo.setText(String.format("%d / %d", current, total));
    }

    public PagingListener getListener() {
        return listener;
    }

    public void setListener(PagingListener listener) {
        this.listener = listener;
    }

    public interface PagingListener {

        void onPageRequested(int page);

    }

}
