package org.ollide.stpauliforum.api.converter;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ollide.stpauliforum.api.ApiModule;
import org.ollide.stpauliforum.model.Post;
import org.ollide.stpauliforum.model.Quote;
import org.ollide.stpauliforum.model.html.PostList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;

public class PostListResponseBodyConverter extends HtmlConverter<PostList> {

    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
    public static final DateTimeFormatter FORMATTER_QUOTES = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm").withZone(DateTimeZone.forOffsetHours(1));

    public static final String CLASS_LINK_ICON = "snap_preview";

    public static final String EMOJI_WINK = "<img src=\"images/smiles/wink.gif\" alt=\"wink.gif\" border=\"0\">";
    public static final int UNICODE_WINK = 0x1f609;
    public static final String EMOJI_DRINK = "<img src=\"images/smiles/drink.gif\" alt=\"drink.gif\" border=\"0\">";
    public static final int UNICODE_DRINK = 0x1F37B;
    public static final String EMOJI_ROFL = "<img src=\"images/smiles/rofl.gif\" alt=\"rofl.gif\" border=\"0\">";
    public static final int UNICODE_ROFL = 0x1F606;
    public static final String EMOJI_SCHUETTEL = "<img src=\"images/smiles/schuettel.gif\" alt=\"schuettel.gif\" border=\"0\">";
    public static final int UNICODE_SCHUETTEL = 0x1F616;
    public static final String EMOJI_FLEHAN = "<img src=\"images/smiles/flehan.gif\" alt=\"flehan.gif\" border=\"0\">";
    public static final int UNICODE_FLEHAN = 0x1F64F;
    public static final String EMOJI_LOVE = "<img src=\"images/smiles/love.gif\" alt=\"love.gif\" border=\"0\">";
    public static final int UNICODE_LOVE = 0x1F60D;
    public static final String EMOJI_GRIN = "<img src=\"images/smiles/grin.gif\" alt=\"grin.gif\" border=\"0\">";
    public static final int UNICODE_GRIN = 0x1F601;
    public static final String EMOJI_SMILE = "<img src=\"images/smiles/smile.gif\" alt=\"smile.gif\" border=\"0\">";
    public static final int UNICODE_SMILE = 0x1F642;
    public static final String EMOJI_EEK = "<img src=\"images/smiles/eek.gif\" alt=\"eek.gif\" border=\"0\">";
    public static final int UNICODE_EEK = 0x1F631;
    public static final String EMOJI_COOL = "<img src=\"images/smiles/cool.gif\" alt=\"cool.gif\" border=\"0\">";
    public static final int UNICODE_COOL = 0x1F60E;

    PostListResponseBodyConverter() {
        // package-private constructor
    }

    @Override
    public PostList convert(ResponseBody value) throws IOException {
        String htmlPage = value.string();
        Document document = Jsoup.parse(htmlPage);

        Elements forumline = document.getElementsByClass("forumline");
        if (forumline.isEmpty()) {
            return null;
        }

        PostList postList = new PostList();

        Element postsTable = forumline.get(0);
        Element tbody = postsTable.child(0);

        // remove 'external link' icons
        tbody.getElementsByClass(CLASS_LINK_ICON).remove();

        Elements mainTrs = tbody.children();

        // Structure of Topic Page in HTML
        // <tbody>
        //  <tr> - 0 - Nav Header
        //  <tr> - 1 - Title of Topic

        //  <tr> - 2 - Actual Post Content
        //  <tr> - 3 - Links (User's posts / Answer)
        //  <tr> - 4 - Space Row

        // repeat 2-4 n Times

        // <tr> - 5 - Last row
        // </tbody>


        // remove intro and outro rows
        if (mainTrs.size() > 3) {
            mainTrs.remove(0);
            mainTrs.remove(0);
            mainTrs.remove(mainTrs.size() - 1);
        }

        List<Post> posts = new ArrayList<>();
        while (!mainTrs.isEmpty() && mainTrs.size() % 3 == 0) {
            Post p = parsePost(mainTrs.remove(0));
            posts.add(p);

            // remove Links & Space Row
            mainTrs.remove(0);
            mainTrs.remove(0);
        }
        postList.setPosts(posts);

        return postList;
    }

    private Post parsePost(Element postRow) {
        Post p = new Post();

        // Structure Of 1 Post in HTML
        // <tr>
        //   <td>
        //     <a> - 0 - stuff
        //     <span class="name"> -> text = Author name
        //     ...
        //   </td>
        //   <td>
        //
        //   </td>
        // </tr>

        Elements authorAndContent = postRow.children();

        Element authorTd = authorAndContent.get(0);
        String authorName = authorTd.getElementsByClass("name").text();
        p.setAuthor(authorName);

        Elements imgs = authorTd.getElementsByTag("img");
        if (imgs.size() > 1) {
            String src = imgs.last().attr("src");
            if (src.startsWith("images/")) {
                src = ApiModule.BASE_URL + src;
            }
            p.setAvatarUrl(src);
        }

        Element contentEl = authorAndContent.get(1);

        Element postdetails = contentEl.getElementsByClass("postdetails").get(0);
        // e.g. 'Verfasst am: 03.03.2016 09:50    Titel: '
        String postDetails = postdetails.text();
        String publishedAt = postDetails.substring(13, 29);
        p.setPublishedAt(publishedAt);
        p.setPublishDate(FORMATTER.parseLocalDateTime(publishedAt));

        Element postbody = contentEl.getElementsByClass("postbody").get(0);

        p.setQuotes(parseQuotesFromMessage(postbody));

        String message = postbody.html();
        message = replaceEmojiImagesWithUnicode(message);
        p.setMessage(message);

        return p;
    }

    private String replaceEmojiImagesWithUnicode(String message) {
        message = message.replace(EMOJI_WINK, getEmijoByUnicode(UNICODE_WINK));
        message = message.replace(EMOJI_DRINK, getEmijoByUnicode(UNICODE_DRINK));
        message = message.replace(EMOJI_ROFL, getEmijoByUnicode(UNICODE_ROFL));
        message = message.replace(EMOJI_SCHUETTEL, getEmijoByUnicode(UNICODE_SCHUETTEL));
        message = message.replace(EMOJI_FLEHAN, getEmijoByUnicode(UNICODE_FLEHAN));
        message = message.replace(EMOJI_LOVE, getEmijoByUnicode(UNICODE_LOVE));
        message = message.replace(EMOJI_GRIN, getEmijoByUnicode(UNICODE_GRIN));
        message = message.replace(EMOJI_SMILE, getEmijoByUnicode(UNICODE_SMILE));
        message = message.replace(EMOJI_EEK, getEmijoByUnicode(UNICODE_EEK));
        message = message.replace(EMOJI_COOL, getEmijoByUnicode(UNICODE_COOL));
        return message;
    }

    private String getEmijoByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    protected List<Quote> parseQuotesFromMessage(Element postBody) {
        Element tables = postBody.getElementsByTag("table").first();
        if (tables == null) {
            return Collections.emptyList();
        }

        List<Quote> quotes = new ArrayList<>();
        Elements quoteTables = new Elements();
        while (true) {
            Element table = tables.select("table").last();
            if (table == null) {
                break;
            }
            if (table.parentNode() != null) {
                table.remove();
                quoteTables.add(table);
            } else {
                break;
            }
        }

        for (Element quoteTable : quoteTables) {
            quotes.add(parseQuote(quoteTable.getElementsByClass("quote").first(),
                    quoteTable.getElementsByClass("genmed").first()));
        }

        // remove leading <br>s after quotes
        Elements brs = postBody.select("br");
        if (brs.size() >= 2) {
            brs.get(0).remove();
            brs.get(1).remove();
        }

        return quotes;
    }

    protected Quote parseQuote(Element quoteTd, Element authorSpan) {
        Quote q = new Quote();

        String authorHtml = authorSpan.html();
        q.setAuthor(StringUtils.substringBetween(authorHtml, "</b>", " "));

        // (Datum Originalbeitrag: 06.06.2016 12:27 GMT +1)
        Element dateTextEl = quoteTd.child(0);
        String dateText = StringUtils.substringBetween(dateTextEl.text(), "Originalbeitrag:", "GMT");
        if (dateText != null) {
            LocalDateTime quoteDateTime = FORMATTER_QUOTES.parseLocalDateTime(dateText.trim());
            q.setPublishDate(quoteDateTime);
            q.setPublishedAt(FORMATTER.print(quoteDateTime));
        }
        dateTextEl.remove();

        q.setMessage(replaceEmojiImagesWithUnicode(quoteTd.text()));

        return q;
    }

}
