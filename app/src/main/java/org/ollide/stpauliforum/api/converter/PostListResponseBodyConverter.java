package org.ollide.stpauliforum.api.converter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ollide.stpauliforum.api.ApiModule;
import org.ollide.stpauliforum.model.Post;
import org.ollide.stpauliforum.model.html.PostList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class PostListResponseBodyConverter extends HtmlConverter<PostList> {

    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");

    public static final String CLASS_LINK_ICON = "snap_preview";

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

        String message = postbody.html();
        p.setMessage(message);

        return p;
    }
}
