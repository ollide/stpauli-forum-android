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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import timber.log.Timber;

public class PostListResponseBodyConverter extends HtmlConverter<PostList> {

    public static final DateTimeFormatter APP_FORMATTER = DateTimeFormat.forPattern("dd.MM.yy HH:mm");
    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");

    public static final DateTimeFormatter FORMATTER_QUOTES_1 = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm").withZone(DateTimeZone.forOffsetHours(1));
    public static final DateTimeFormatter FORMATTER_QUOTES_2 = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm").withZone(DateTimeZone.forOffsetHours(1));
    public static final DateTimeFormatter[] FORMATTER_QUOTES = new DateTimeFormatter[]{FORMATTER_QUOTES_1, FORMATTER_QUOTES_2};

    // eg. 'Mo 16 Jan ......'
    public static final Pattern QUOTE_ILLEGAL_PATTERN = Pattern.compile("[A-Z][a-z] [0-9]+(.*)");
    public static final Pattern QUERY_PARAM_T_PATTERN = Pattern.compile("t=([^&]+)");

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
    public static final String EMOJI_TONGUE = "<img src=\"images/smiles/tongue.gif\" alt=\"tongue.gif\" border=\"0\">";
    public static final int UNICODE_TONGUE = 0x1F61B;
    public static final String EMOJI_HAPPY = "<img src=\"images/smiles/happy.gif\" alt=\"happy.gif\" border=\"0\">";
    public static final int UNICODE_HAPPY = 0x1F606;
    public static final String EMOJI_NIXWEISS = "<img src=\"images/smiles/nixweiss.gif\" alt=\"nixweiss.gif\" border=\"0\">";
    public static final int UNICODE_NIXWEISS = 0x1F914;
    public static final String EMOJI_DAGEGEN = "<img src=\"images/smiles/bindagegen.gif\" alt=\"bindagegen.gif\" border=\"0\">";
    public static final int UNICODE_DAGEGEN = 0x1F645;
    public static final String EMOJI_VIELPOSTEN = "<img src=\"images/smiles/vielposten.gif\" alt=\"vielposten.gif\" border=\"0\">";
    public static final int UNICODE_VIELPOSTEN = 0x270F;
    public static final String EMOJI_SMOKE = "<img src=\"images/smiles/icon_smoke.gif\" alt=\"icon_smoke.gif\" border=\"0\">";
    public static final int UNICODE_SMOKE = 0x1F60E;
    public static final String EMOJI_NAJA = "<img src=\"images/smiles/naja.gif\" alt=\"naja.gif\" border=\"0\">";
    public static final int UNICODE_NAJA = 0x1F615;
    public static final String EMOJI_ROLLEYES = "<img src=\"images/smiles/icon_rolleyes.gif\" alt=\"icon_rolleyes.gif\" border=\"0\">";
    public static final int UNICODE_ROLLEYES = 0x1F644;
    public static final String EMOJI_RAZZ = "<img src=\"images/smiles/icon_razz.gif\" alt=\"icon_razz.gif\" border=\"0\">";
    public static final int UNICODE_RAZZ = 0x1F61B;
    public static final String EMOJI_LACH = "<img src=\"images/smiles/lach.gif\" alt=\"lach.gif\" border=\"0\">";
    public static final int UNICODE_LACH = 0x1F604;
    public static final String EMOJI_KOTZ = "<img src=\"images/smiles/kotz.gif\" alt=\"kotz.gif\" border=\"0\">";
    public static final int UNICODE_KOTZ = 0x1F922;
    public static final String EMOJI_LACHLAUT = "<img src=\"images/smiles/lachlaut.gif\" alt=\"lachlaut.gif\" border=\"0\">";
    public static final int UNICODE_LACHLAUT = 0x1F603;
    public static final String EMOJI_WALL = "<img src=\"images/smiles/wall.gif\" alt=\"wall.gif\" border=\"0\">";
    public static final int UNICODE_WALL = 0x1F926;
    public static final String EMOJI_JA = "<img src=\"images/smiles/ja.gif\" alt=\"ja.gif\" border=\"0\">";
    public static final int UNICODE_JA = 0x1F642;
    public static final String EMOJI_APPLAUS = "<img src=\"images/smiles/applaus.gif\" alt=\"applaus.gif\" border=\"0\">";
    public static final int UNICODE_APPLAUS = 0x1F917;

    PostListResponseBodyConverter() {
        // package-private constructor
    }

    @Override
    public PostList convert(ResponseBody value) throws IOException {
        PostList postList = new PostList();

        String htmlPage = value.string();
        Document document = Jsoup.parse(htmlPage);

        Elements forumline = document.getElementsByClass("forumline");
        if (forumline.isEmpty()) {
            return null;
        }

        Element pagination = document.select("span.pagination").first();
        parseIdAndCurrentAndLastPage(postList, pagination);

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

    protected void parseIdAndCurrentAndLastPage(PostList postList, Element pagination) {
        int currentPage = 1;
        int totalPages = 1;
        // TODO: topicId is not parsed when there's only 1 page
        int topicId = -1;

        Element paginationHref = pagination.getElementsByTag("a").first();
        if (paginationHref != null) {

            String paginationLink = paginationHref.attr("href");
            Matcher m = QUERY_PARAM_T_PATTERN.matcher(paginationLink);
            if (m.find()) {
                topicId = Integer.parseInt(m.group(1));
            }

            Element last = pagination.children().last();
            Element currentPageB;
            Element lastPage;
            if (last.tagName().equals("b")) {
                currentPageB = last;
                lastPage = last;
            } else {
                currentPageB = pagination.getElementsByTag("b").last();
                lastPage = pagination.child(pagination.children().size() - 2);
            }
            try {
                currentPage = Integer.parseInt(currentPageB.text());
                totalPages = Integer.parseInt(lastPage.text());
            } catch (NumberFormatException e) {
                Timber.w("couldn't parse topicId or current/last page.");
            }
        }

        postList.setTopicId(topicId);
        postList.setCurrentPage(currentPage);
        postList.setLastPage(totalPages);
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
        message = message.replaceAll(EMOJI_WINK, getEmijoByUnicode(UNICODE_WINK));
        message = message.replaceAll(EMOJI_DRINK, getEmijoByUnicode(UNICODE_DRINK));
        message = message.replaceAll(EMOJI_ROFL, getEmijoByUnicode(UNICODE_ROFL));
        message = message.replaceAll(EMOJI_SCHUETTEL, getEmijoByUnicode(UNICODE_SCHUETTEL));
        message = message.replaceAll(EMOJI_FLEHAN, getEmijoByUnicode(UNICODE_FLEHAN));
        message = message.replaceAll(EMOJI_LOVE, getEmijoByUnicode(UNICODE_LOVE));
        message = message.replaceAll(EMOJI_GRIN, getEmijoByUnicode(UNICODE_GRIN));
        message = message.replaceAll(EMOJI_SMILE, getEmijoByUnicode(UNICODE_SMILE));
        message = message.replaceAll(EMOJI_EEK, getEmijoByUnicode(UNICODE_EEK));
        message = message.replaceAll(EMOJI_COOL, getEmijoByUnicode(UNICODE_COOL));
        message = message.replaceAll(EMOJI_TONGUE, getEmijoByUnicode(UNICODE_TONGUE));
        message = message.replaceAll(EMOJI_HAPPY, getEmijoByUnicode(UNICODE_HAPPY));
        message = message.replaceAll(EMOJI_NIXWEISS, getEmijoByUnicode(UNICODE_NIXWEISS));
        message = message.replaceAll(EMOJI_DAGEGEN, getEmijoByUnicode(UNICODE_DAGEGEN));
        message = message.replaceAll(EMOJI_VIELPOSTEN, getEmijoByUnicode(UNICODE_VIELPOSTEN));
        message = message.replaceAll(EMOJI_SMOKE, getEmijoByUnicode(UNICODE_SMOKE));
        message = message.replaceAll(EMOJI_NAJA, getEmijoByUnicode(UNICODE_NAJA));
        message = message.replaceAll(EMOJI_ROLLEYES, getEmijoByUnicode(UNICODE_ROLLEYES));
        message = message.replaceAll(EMOJI_RAZZ, getEmijoByUnicode(UNICODE_RAZZ));
        message = message.replaceAll(EMOJI_LACH, getEmijoByUnicode(UNICODE_LACH));
        message = message.replaceAll(EMOJI_KOTZ, getEmijoByUnicode(UNICODE_KOTZ));
        message = message.replaceAll(EMOJI_LACHLAUT, getEmijoByUnicode(UNICODE_LACHLAUT));
        message = message.replaceAll(EMOJI_WALL, getEmijoByUnicode(UNICODE_WALL));
        message = message.replaceAll(EMOJI_JA, getEmijoByUnicode(UNICODE_JA));
        message = message.replaceAll(EMOJI_APPLAUS, getEmijoByUnicode(UNICODE_APPLAUS));
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

        for (DateTimeFormatter formatterQuote : FORMATTER_QUOTES) {
            try {
                return formatterQuote.parseLocalDateTime(dateText);
            } catch (IllegalArgumentException iae) {
                //
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + dateText);
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
