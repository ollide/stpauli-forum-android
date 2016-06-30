package org.ollide.stpauliforum.api.converter;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.ollide.stpauliforum.api.ApiModule;
import org.ollide.stpauliforum.model.Message;
import org.ollide.stpauliforum.model.Post;
import org.ollide.stpauliforum.model.PostMessage;
import org.ollide.stpauliforum.model.Quote;
import org.ollide.stpauliforum.model.html.PostList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

public class PostListResponseBodyConverter extends HtmlConverter<PostList> {

    public static final DateTimeFormatter APP_FORMATTER = DateTimeFormat.forPattern("dd.MM.yy HH:mm");
    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
    public static final DateTimeFormatter FORMATTER_QUOTES = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm").withZone(DateTimeZone.forOffsetHours(1));

    // eg. 'Mo 16 Jan ......'
    public static final Pattern QUOTE_ILLEGAL_PATTERN = Pattern.compile("[A-Z][a-z] [0-9]+(.*)");

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

            Element titleRow = mainTrs.remove(0);
            postList.setTopicName(titleRow.children().last().text());

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
        // put latest posts first
        Collections.reverse(posts);
        postList.setPosts(posts);

        return postList;
    }

    protected Post parsePost(Element postRow) {
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

        List<Message> messages = new ArrayList<>();
        PostMessage message = null;
        for (Node node : postbody.childNodesCopy()) {
            if ("table".equals(node.nodeName())) {
                if (message != null && !message.getMessage().isEmpty()) {
                    messages.add(message);
                    message = null;
                }

                messages.add(parseQuotesFromMessage((Element) node));
            } else {
                if (message == null) {
                    message = new PostMessage();
                }
                String msg = processPlainHtmlText(node.outerHtml());
                message.setMessage(message.getMessage() + msg);
            }
        }

        if (message != null && !message.getMessage().isEmpty()) {
            messages.add(message);
        }
        p.setMessages(messages);

        return p;
    }

    private String processPlainHtmlText(String message) {
        message = replaceEmojiImagesWithUnicode(message);
        message = message.replaceAll("(\r\n|\n\r|\r|\n)", "");
        return message;
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

    private String getEmijoByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    protected Quote parseQuotesFromMessage(Element outerQuoteTable) {
        List<Quote> quotes = new ArrayList<>();
        Elements quoteTables = new Elements();
        while (true) {
            Element table = outerQuoteTable.select("table").last();
            if (table == null) {
                break;
            }
            if (table.parentNode() != null) {
                table.remove();
                quoteTables.add(table);
            } else {
                quoteTables.add(table);
                break;
            }
        }

        for (Element quoteTable : quoteTables) {
            quotes.add(parseQuote(quoteTable.getElementsByClass("quote").first(),
                    quoteTable.getElementsByClass("genmed").first()));
        }

        Quote q = null;
        while (!quotes.isEmpty()) {
            Quote nested = q;
            q = quotes.remove(0);
            q.setDepth(quotes.size());
            q.setNestedQuote(nested);
        }

        // remove leading <br>s after quotes
        Elements brs = outerQuoteTable.select("br");
        if (brs.size() >= 2) {
            brs.get(0).remove();
            brs.get(1).remove();
        }

        return q;
    }

    protected Quote parseQuote(Element quoteTd, Element authorSpan) {
        Quote q = new Quote();

        String authorHtml = authorSpan.html();
        q.setAuthor(StringUtils.substringBetween(authorHtml, "</b>", " "));

        // (Datum Originalbeitrag: 06.06.2016 12:27 GMT +1)  [optional]
        if (!quoteTd.children().isEmpty()) {
            Element dateTextEl = quoteTd.child(0);
            String dateText = StringUtils.substringBetween(dateTextEl.text(), "Originalbeitrag:", "GMT");
            if (dateText != null) {
                LocalDateTime quoteDateTime = parseQuoteDateTime(dateText);
                q.setPublishDate(quoteDateTime);
                q.setPublishedAt(APP_FORMATTER.print(quoteDateTime));
            }
            dateTextEl.remove();
        }

        q.setMessage(processPlainHtmlText(quoteTd.text()));
        return q;
    }

    protected LocalDateTime parseQuoteDateTime(String dateText) {
        dateText = dateText.trim();
        if (QUOTE_ILLEGAL_PATTERN.matcher(dateText).matches()) {
            dateText = fixDateTimText(dateText);
        }
        return FORMATTER_QUOTES.parseLocalDateTime(dateText);
    }

    protected String fixDateTimText(String dateText) {
        // strip 'Mo '
        dateText = dateText.substring(3);

        // replace 'Jan', 'Feb', ... with 01, 02,...
        dateText = dateText.replace("Jan", "01");
        dateText = dateText.replace("Feb", "02");
        dateText = dateText.replace("Mar", "03");
        dateText = dateText.replace("Apr", "04");
        dateText = dateText.replace("Mai", "05");
        dateText = dateText.replace("Jun", "06");
        dateText = dateText.replace("Jul", "07");
        dateText = dateText.replace("Aug", "08");
        dateText = dateText.replace("Sep", "09");
        dateText = dateText.replace("Okt", "10");
        dateText = dateText.replace("Nov", "11");
        dateText = dateText.replace("Dez", "12");

        // migrate to other pattern
        dateText = dateText.replaceAll(" ", ".");
        dateText = dateText.replace(",.", " ");
        return dateText;
    }

}
