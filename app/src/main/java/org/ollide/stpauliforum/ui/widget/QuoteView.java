package org.ollide.stpauliforum.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ollide.stpauliforum.R;
import org.ollide.stpauliforum.model.Quote;
import org.ollide.stpauliforum.util.Utils;

/**
 * Created by ollide on 29.06.16.
 */
public class QuoteView extends LinearLayout {

    private Quote quote;

    public QuoteView(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setBackgroundResource(R.drawable.view_quote_background);
    }

    public QuoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public QuoteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;

        if (quote.getDepth() > 0) {
            int margin = (int) (Utils.convertDpToPixel(12, getContext()));
            LinearLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            layoutParams.setMargins(margin, 0, 0, 0);
        }

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_quote, this, true);

        TextView authorTv = (TextView) findViewById(R.id.quoteAuthorTv);
        authorTv.setText(quote.getAuthor());
        TextView publishedTv = (TextView) findViewById(R.id.quotePublishDateTv);
        publishedTv.setText(quote.getPublishedAt());

        TextView msgTv = (TextView) findViewById(R.id.message);
        msgTv.setText(Html.fromHtml(quote.getMessage()));
        msgTv.setMovementMethod(LinkMovementMethod.getInstance());

        if (quote.getNestedQuote() != null) {
            QuoteView nestedQv = new QuoteView(getContext());
            nestedQv.setQuote(quote.getNestedQuote());
            addView(nestedQv, 0);
        }
    }

}
