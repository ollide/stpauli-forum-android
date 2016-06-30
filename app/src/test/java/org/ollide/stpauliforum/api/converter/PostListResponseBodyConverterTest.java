package org.ollide.stpauliforum.api.converter;

import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.ollide.stpauliforum.model.Post;
import org.ollide.stpauliforum.model.Quote;
import org.ollide.stpauliforum.model.html.PostList;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

import static org.junit.Assert.*;

public class PostListResponseBodyConverterTest {

    private static final String MESSAGE_WITH_1_QUOTE = "<span class=\"postbody\"><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>metallipar hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 06.06.2016 12:27 GMT +1)</span><br><br>Noch einer, der gegen uns treffen wird: Sukuta-Pasu wechselt zu Sandhausen.<br>Bin gespannt, wie er sich jetzt in der 2.Liga machen wird.<br><br>Vorher bei Absteiger Cotzbus.</table><br><br>In Kotzbus war er noch einer der Besseren und auch Kapitän. Ich habe aber immer den Eindruck, dass er zimlich schwerfällig und unbeweglich daherkommt.</span>";
    private static final String MESSAGE_WITH_4_QUOTES = "<span class=\"postbody\"><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>Kottan hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 06.06.2016 11:04 GMT +1)</span><br><br><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>ertin hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 06.06.2016 10:59 GMT +1)</span><br><br><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>Achim hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 06.06.2016 10:51 GMT +1)</span><br><br><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>Kottan hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 05.06.2016 22:24 GMT +1)</span><br><br>Mahir Saglik soll in Aue im Gespräch sein nach dem geplatzten Transfer mit Königs...</table><br><br>Was für ein geplatzter Transfer?</table><br><br>Er meint bestimmt, dass Königs bei Aue fast schon fix war und dann bei Würzburg unterschrieben hat</table><br><br>Exakt! <img alt=zustimm.gif border=0 src=images/smiles/zustimm.gif></table><br><br>Okay, ich dachte bei Königs... an Königsblau<br><img alt=grin.gif border=0 src=images/smiles/grin.gif></span>";

    private static final String FULL_MESSAGE_WITH_MULTIPLE_QUOTES = "<table><tr><td width=\"150\" align=\"left\" valign=\"top\" class=\"row2\"> <a name=\"4190725\"></a><span class=\"name\"><span class=\"gen\">PK 1871</span></span><div class=\"genmed\"><b><img src=\"images/star4.gif\" alt=\"Alter Stamm Ultra\" title=\"Alter Stamm Ultra\" border=\"0\"><br>Alter Stamm Ultra</b></div><div align=\"left\"><img src=\"http://farm1.static.flickr.com/208/522732482_afbf9405f0.jpg?v=0\" alt=\"Avatar\" border=\"0\" height=\"60\" width=\"60\"></div><br><div align=\"left\" class=\"gensmall\">Mitglied seit:&nbsp;11.04.2009</div><div align=\"left\" class=\"gensmall\">Beiträge:&nbsp;2205</div><div align=\"left\" class=\"gensmall\">Wohnort:&nbsp;Linz</div></td><td class=\"row2\" height=\"28\" valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\"><tbody><tr><td width=\"60%\"><a name=\"2\"></a><a href=\"viewtopic.php?p=4190725#4190725\"><img src=\"templates/forum/images/icon_minipost_new.gif\" width=\"12\" height=\"9\" alt=\"Neuer Beitrag\" title=\"Neuer Beitrag\" border=\"0\"></a><span class=\"postdetails\">Verfasst am: 28.06.2016 20:07&nbsp;&nbsp; &nbsp;Titel: <b></b></span>&nbsp;</td><td width=\"20%\" nowrap=\"nowrap\" valign=\"middle\" align=\"right\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"posting.php?mode=quote&amp;p=4190725\"><img src=\"templates/forum/images/lang_german/icon_quote.gif\" alt=\"Antworten mit Zitat\" title=\"Antworten mit Zitat\" border=\"0\"></a>&nbsp;&nbsp;</td><td width=\"20%\" nowrap=\"nowrap\" valign=\"middle\" align=\"right\"><span class=\"gensmall\"><img src=\"images/div.gif\" border=\"0\">&nbsp;&nbsp;<a href=\"#1\">Vorheriger</a>&nbsp;&nbsp;<a href=\"#3\">Nächster</a>&nbsp;</span> </td></tr><tr><td colspan=\"3\"><hr></td></tr><tr><td colspan=\"3\" valign=\"top\"><br><span class=\"postbody\"><table width=\"90%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" align=\"center\"><tbody><tr> <td><span class=\"genmed\"><b>Zitat - </b>MadSozi hat folgendes geschrieben:</span></td></tr><tr> <td class=\"quote\"><span style=\"font-size: 9px; line-height: normal\">(Datum Originalbeitrag: 27.06.2016 15:02 GMT +1)</span><br><br>BTW die berühmte Gurkenverordnung ist seit ein Jahren nicht mehr existent.</td></tr></tbody></table><br><br>Sie wird aber trotzdem noch umgesetzt. Weil die Gurken in der entsprechenden Größe am besten in Kisten passen.<br><br><table width=\"90%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" align=\"center\"><tbody><tr> <td><span class=\"genmed\"><b>Zitat - </b>MadSozi hat folgendes geschrieben:</span></td></tr><tr> <td class=\"quote\"><span style=\"font-size: 9px; line-height: normal\">(Datum Originalbeitrag: 27.06.2016 15:02 GMT +1)</span><br>Mir geht es um das WIE. Und da haben nicht Argumente wie von linken Kritiker*innen Richtung EU geäußert werden die Oberhand gehabt, sondern die plumpen Dummheiten und Lügen von Rechtspopulisten, Rechtskonsvervativen und all den anderen denen ihre Nation und deren Stolz so wichtig ist.<a href=\"http://www.theguardian.com/politics/2016/jun/20/sayeeda-warsi-quits-leave-campaign-over-hateful-xenophobic-tactics\" target=\"_blank\" class=\"postlink\">Dazu war sogar Hetze gegen Flüchtlinge und Ausländer kein tabu.</a>&nbsp;<a class=\"snap_preview\" href=\"http://www.theguardian.com/politics/2016/jun/20/sayeeda-warsi-quits-leave-campaign-over-hateful-xenophobic-tactics\" target=\"_blank\"><img src=\"/images/link.png\" border=\"0\"></a> Das ist es was mich so ärgert. Nicht die Personen die da an der Spitze standen.</td></tr></tbody></table><br><br>Da sind wir einer Meinung.<br>Man sollte aber nicht übersehen, dass es in Großbritannien auch Linke gibt, die sich für das Leave engagiert haben und dass in anderen Ländern die Rechte mehr oder weniger vollständig \"proeuropäisch\" und die Linke mehr oder weniger vollständig \"europaskeptisch\" ist, das gilt für Lettland z.B.<br><br>Abgesehen davon war und ist es eine Strategie der britischen Bourgeoisie durch Arbeiterzuwanderung das Lohnniveau zu drücken und gleichzeitig das Proletariat durch Rassismus zu spalten. Die Arbeitnehmer-Freizügigkeit wurde wesentlich vom UK in der EU durchgesetzt und, anders als etwa in Deutschland, gezielt zu diesem Zweck eingesetzt. Man könnte sagen, die Zuwanderung aus der EU-Peripherie (und dem Commonwealth) leistet im UK das, was Hartz-4 in Deutschland geleistet hat. Jedenfalls spielt Rassismus eine systemstützende Rolle im UK und die britische EU-Politik ist hierfür eine der Grundlagen. Die rechten politischen Strömungen führen dies nun weiter und ich gehe mit großer Sicherheit davon aus, dass sich an der bürgerlichen Strategie im Klassenkampf Zuwanderung und Rassismus einzusetzen durch den EU-Austritt nichts ändert wird. Zur Not geht man verstärkt zurück zum Commenwealth, also ersetzt die Polen und Spanier durch Pakistani und Jamaicaner. Im 19. Jh. wurde übrigens schon genauso vorgegangen; Damals hat man Iren für den selben Zweck eingesetzt.<br>So gilt: ob In or Out, die Bourgeoisie siegt und Rassismus bleibt ein fundamentales Element der britischen Gesellschaft.</span></td></tr><tr><td colspan=\"3\" valign=\"bottom\"><br><br>_________________<br>Alle meine Gedanken stehen in geordnetem Zusammenhang, wenn ich auch unfähig bin, sie alle auf einmal auseinanderzusetzen <img src=\"images/smiles/freak.gif\" alt=\"freak.gif\" border=\"0\"></td></tr></tbody></table></td></tr></table>";
    private static final String FULL_MESSAGE_WITH_NESTED_QUOTES = "<table><tr><td width=\"150\" align=\"left\" valign=\"top\" class=\"row2\"> <a name=\"4174611\"></a><span class=\"name\"><span class=\"gen\">Babo</span></span><div class=\"genmed\"><b><img src=\"images/star5.gif\" alt=\"Posting - Schinder\" title=\"Posting - Schinder\" border=\"0\"><br>Posting - Schinder</b></div><div align=\"left\"><img src=\"http://fs5.directupload.net/images/160507/69riykec.jpg\" alt=\"Avatar\" border=\"0\" height=\"60\" width=\"60\"></div><br><div align=\"left\" class=\"gensmall\">Wohnort:&nbsp;&lt;°)K(I(E(L&gt;&lt;</div></td><td class=\"row2\" height=\"28\" valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\"><tbody><tr><td width=\"60%\"><a name=\"10\"></a><a href=\"viewtopic.php?p=4174611#4174611\"><img src=\"templates/forum/images/icon_minipost.gif\" width=\"12\" height=\"9\" alt=\"Beitrag\" title=\"Beitrag\" border=\"0\"></a><span class=\"postdetails\">Verfasst am: 20.05.2016 16:18&nbsp;&nbsp; &nbsp;Titel: <b></b></span>&nbsp;</td><td width=\"20%\" nowrap=\"nowrap\" valign=\"middle\" align=\"right\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"posting.php?mode=quote&amp;p=4174611\"><img src=\"templates/forum/images/lang_german/icon_quote.gif\" alt=\"Antworten mit Zitat\" title=\"Antworten mit Zitat\" border=\"0\"></a>&nbsp;&nbsp;</td><td width=\"20%\" nowrap=\"nowrap\" valign=\"middle\" align=\"right\"><span class=\"gensmall\"><img src=\"images/div.gif\" border=\"0\">&nbsp;&nbsp;<a href=\"#9\">Vorheriger</a>&nbsp;&nbsp;<a href=\"#11\">Nächster</a>&nbsp;</span> </td></tr><tr><td colspan=\"3\"><hr></td></tr><tr><td colspan=\"3\" valign=\"top\"><br><span class=\"postbody\"><table width=\"90%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" align=\"center\"><tbody><tr> <td><span class=\"genmed\"><b>Zitat - </b>wasserrand hat folgendes geschrieben:</span></td></tr><tr> <td class=\"quote\"><span style=\"font-size: 9px; line-height: normal\">(Datum Originalbeitrag: 20.05.2016 16:05 GMT +1)</span><br><br><table width=\"90%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" align=\"center\"><tbody><tr> <td><span class=\"genmed\"><b>Zitat - </b>andi_freibeuter1910 hat folgendes geschrieben:</span></td></tr><tr> <td class=\"quote\"><span style=\"font-size: 9px; line-height: normal\">(Datum Originalbeitrag: 20.05.2016 12:21 GMT +1)</span><br><br>Und selbst einige Abgeordnete der CHP haben zugestimmt, unfassbar! Der Nationalismus ist in vielen Schichten der Türkei leider immernoch viel zu sehr verbreitet. Und dann wundern sich Türken, dass die PKK heute so stark wie nie in der kurdischen Bevölkerung verwurzelt ist...<br><br>Wir werden jetzt eine Eskalation sondersgleichen erleben. Die PKK hat seit der Eskalation in Kobane schon viele für den bewaffneten Kampf gewonnen, die sich vorher immer für eine friedliche Lösung eingesetzt haben. Weil sie nicht mehr an eine friedliche Lösung geglaubt hatten, wenn Erdogan den IS unterstützt.<br><br>Dieser Trend wird sich jetzt vervielfachen.<br><br>Und das ist ja auch durchaus nachvollziehbar. Journalisten der linken kurdischen Bewegung werden seit Jahren verhaftet, Demos werden mit scharfer Munition beschossen und nun droht auch noch den Abgeordneten Haft. Was bleibt denn da als Mittel, um seine Menschen- und Bürgerrechte zu verteidigen?</td></tr></tbody></table><br><br>bin leider kein ausgesprochener kenner der lage. aber das find ich so krass und auch beängstigend: offenbar scheint die türkische regierung geradezu auf eine weitere eskalation aus zu sein.</td></tr></tbody></table><br><br>Vielleicht sollte man auch so langsam damit anfangen nicht von Regierung sondern von Diktatur zu sprechen.</span></td></tr><tr><td colspan=\"3\" valign=\"bottom\"><br><br>_________________<br><img src=\"http://fs1.directupload.net/images/150607/eh6mok3q.jpg\" border=\"0\"><img src=\"http://fs2.directupload.net/images/150607/ftlxrgze.jpg\" border=\"0\"><br><div align=\"center\"><a href=\"http://www.youtube.com/watch?v=iQ4jtmHaFHA\" target=\"_blank\" class=\"postlink\">Come on come on ... Antifa - Hooligans!</a>&nbsp;<a class=\"snap_preview\" href=\"http://www.youtube.com/watch?v=iQ4jtmHaFHA\" target=\"_blank\"><img src=\"/images/link.png\" border=\"0\"></a></div><span class=\"gensmall\"><br>_________________<br>Beitrag zuletzt bearbeitet am 20.05.2016 16:21, insgesamt ein Mal bearbeitet</span></td></tr></tbody></table></td></tr></table>";
    private static final String FULL_MESSAGE_WITH_MULTIPLE_NESTED_QUOTES = "<table><tr><td width=\"150\" align=\"left\" valign=\"top\" class=\"row2\"> <a name=\"4191016\"></a><span class=\"name\"><span class=\"gen\">Südwurst</span></span><div class=\"genmed\"><b><img src=\"images/star4.gif\" alt=\"Alter Stamm Ultra\" title=\"Alter Stamm Ultra\" border=\"0\"><br>Alter Stamm Ultra</b></div><div align=\"left\" class=\"gensmall\">Mitglied seit:&nbsp;29.07.2005</div><div align=\"left\" class=\"gensmall\">Beiträge:&nbsp;1861</div></td><td class=\"row2\" height=\"28\" valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\"><tbody><tr><td width=\"60%\"><a name=\"14\"></a><a href=\"viewtopic.php?p=4191016#4191016\"><img src=\"templates/forum/images/icon_minipost_new.gif\" width=\"12\" height=\"9\" alt=\"Neuer Beitrag\" title=\"Neuer Beitrag\" border=\"0\"></a><span class=\"postdetails\">Verfasst am: 29.06.2016 16:34&nbsp;&nbsp; &nbsp;Titel: <b></b></span>&nbsp;</td><td width=\"20%\" nowrap=\"nowrap\" valign=\"middle\" align=\"right\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"posting.php?mode=quote&amp;p=4191016\"><img src=\"templates/forum/images/lang_german/icon_quote.gif\" alt=\"Antworten mit Zitat\" title=\"Antworten mit Zitat\" border=\"0\"></a>&nbsp;&nbsp;</td><td width=\"20%\" nowrap=\"nowrap\" valign=\"middle\" align=\"right\"><span class=\"gensmall\"><img src=\"images/div.gif\" border=\"0\">&nbsp;&nbsp;<a href=\"#13\">Vorheriger</a>&nbsp;&nbsp;<a href=\"#\"></a>&nbsp;</span> </td></tr><tr><td colspan=\"3\"><hr></td></tr><tr><td colspan=\"3\" valign=\"top\"><br><span class=\"postbody\"><table width=\"90%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" align=\"center\"><tbody><tr> <td><span class=\"genmed\"><b>Zitat - </b>Kottan hat folgendes geschrieben:</span></td></tr><tr> <td class=\"quote\"><span style=\"font-size: 9px; line-height: normal\">(Datum Originalbeitrag: 29.06.2016 16:27 GMT +1)</span><br><br><table width=\"90%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" align=\"center\"><tbody><tr> <td><span class=\"genmed\"><b>Zitat - </b>Südwurst hat folgendes geschrieben:</span></td></tr><tr> <td class=\"quote\"><span style=\"font-size: 9px; line-height: normal\">(Datum Originalbeitrag: 29.06.2016 16:26 GMT +1)</span><br><br>Jemand ne Ahnung, wann mit genauen Terminierungen der ersten Spieltage zu rechnen ist?</td></tr></tbody></table><br><br><table width=\"90%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" align=\"center\"><tbody><tr> <td><span class=\"genmed\"><b>Zitat - </b>T.A.L. hat folgendes geschrieben:</span></td></tr><tr> <td class=\"quote\"><span style=\"font-size: 9px; line-height: normal\">(Datum Originalbeitrag: 29.06.2016 11:17 GMT +1)</span><br><br><table width=\"90%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" align=\"center\"><tbody><tr> <td><span class=\"genmed\"><b>Zitat - </b>Breadstar hat folgendes geschrieben:</span></td></tr><tr> <td class=\"quote\"><span style=\"font-size: 9px; line-height: normal\">(Datum Originalbeitrag: Mi 29 Jun 2016, 11:16 GMT +1)</span><br><br>Wann werden die ersten Spieltage terminiert?</td></tr></tbody></table><br><br><table width=\"90%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" align=\"center\"><tbody><tr> <td><span class=\"genmed\"><b>Zitat:</b></span></td></tr><tr> <td class=\"quote\">Die ersten zeitgenauen Ansetzungen sind für die Bundesliga in der 28. Kalenderwoche (11.-15. Juli) geplant, für die 2. Bundesliga in der 27. Kalenderwoche (4.-8. Juli).</td></tr></tbody></table><br><a href=\"http://www.bundesliga.de/de/dfl/mediencenter/pressemitteilungen/dfl-veroeffentlicht-spielplaene-fuer-die-saison-2016-17-pressemitteilung-agmdsp.jsp\" target=\"_blank\" class=\"postlink\">von hier</a>&nbsp;<a class=\"snap_preview\" href=\"http://www.bundesliga.de/de/dfl/mediencenter/pressemitteilungen/dfl-veroeffentlicht-spielplaene-fuer-die-saison-2016-17-pressemitteilung-agmdsp.jsp\" target=\"_blank\"><img src=\"/images/link.png\" border=\"0\"></a></td></tr></tbody></table></td></tr></tbody></table><br><br>danke.</span></td></tr><tr><td colspan=\"3\" valign=\"bottom\"><br><br>_________________<br>xyz</td></tr></tbody></table></td></tr></table>";

    private PostListResponseBodyConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new PostListResponseBodyConverter();
    }

    @Test
    public void testParseQuotesFromMessageWithSingleQuote() throws Exception {
        Quote q = converter.parseQuotesFromMessage(Jsoup.parse(MESSAGE_WITH_1_QUOTE));
        assertNotNull(q);
        assertEquals(0, q.getNestedQuoteCount());
    }

    @Test
    public void testParseQuotesFromMessageWithNestedQuotes() throws Exception {
        Quote q = converter.parseQuotesFromMessage(Jsoup.parse(MESSAGE_WITH_4_QUOTES));
        assertNotNull(q);
        assertEquals(3, q.getNestedQuoteCount());
    }

    @Test
    public void testParsePost() {
        Element postEl = Jsoup.parse(FULL_MESSAGE_WITH_MULTIPLE_QUOTES).getElementsByTag("tr").first();
        Post post = converter.parsePost(postEl);
        assertNotNull(post);
    }

    @Test
    public void testParsePostWithNestedQuotes() {
        Element postEl = Jsoup.parse(FULL_MESSAGE_WITH_NESTED_QUOTES).getElementsByTag("tr").first();
        Post post = converter.parsePost(postEl);
        assertNotNull(post);
    }

    @Test
    public void testParsePostWithMultipleNestedQuotes() {
        Element postEl = Jsoup.parse(FULL_MESSAGE_WITH_MULTIPLE_NESTED_QUOTES).getElementsByTag("tr").first();
        Post post = converter.parsePost(postEl);
        assertNotNull(post);
    }

    @Test
    public void testParseQuoteDateTime() {
        String dateText = "Do 16 Jun 2016, 10:43";
        LocalDateTime localDateTime = converter.parseQuoteDateTime(dateText);
        assertNotNull(localDateTime);
    }

    @Test
    public void testConvert() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/topics/neues_von_den_alten_p1_first.txt");
        ResponseBody body = ResponseBody.create(MediaType.parse("text/html"), IOUtils.toString(is, StandardCharsets.UTF_8));
        PostList postList = converter.convert(body);
        assertEquals("Neues von den Alten #4", postList.getTopicName());
        assertEquals(1, postList.getCurrentPage());
        assertEquals(251, postList.getLastPage());
        assertEquals(74396, postList.getTopicId());

        is = this.getClass().getResourceAsStream("/topics/neues_von_den_alten_p249.txt");
        body = ResponseBody.create(MediaType.parse("text/html"), IOUtils.toString(is, StandardCharsets.UTF_8));
        postList = converter.convert(body);
        assertEquals("Neues von den Alten #4", postList.getTopicName());
        assertEquals(249, postList.getCurrentPage());
        assertEquals(251, postList.getLastPage());
        assertEquals(74396, postList.getTopicId());

        is = this.getClass().getResourceAsStream("/topics/neues_von_den_alten_p251_last.txt");
        body = ResponseBody.create(MediaType.parse("text/html"), IOUtils.toString(is, StandardCharsets.UTF_8));
        postList = converter.convert(body);
        assertEquals("Neues von den Alten #4", postList.getTopicName());
        assertEquals(251, postList.getCurrentPage());
        assertEquals(251, postList.getLastPage());
        assertEquals(74396, postList.getTopicId());
    }

}
