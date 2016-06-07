package org.ollide.stpauliforum.api.converter;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.ollide.stpauliforum.model.Quote;

import java.util.List;

import static org.junit.Assert.*;

public class PostListResponseBodyConverterTest {

    private static final String MESSAGE_WITH_1_QUOTE = "<span class=\"postbody\"><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>metallipar hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 06.06.2016 12:27 GMT +1)</span><br><br>Noch einer, der gegen uns treffen wird: Sukuta-Pasu wechselt zu Sandhausen.<br>Bin gespannt, wie er sich jetzt in der 2.Liga machen wird.<br><br>Vorher bei Absteiger Cotzbus.</table><br><br>In Kotzbus war er noch einer der Besseren und auch Kapitän. Ich habe aber immer den Eindruck, dass er zimlich schwerfällig und unbeweglich daherkommt.</span>";
    private static final String MESSAGE_WITH_4_QUOTES = "<span class=\"postbody\"><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>Kottan hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 06.06.2016 11:04 GMT +1)</span><br><br><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>ertin hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 06.06.2016 10:59 GMT +1)</span><br><br><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>Achim hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 06.06.2016 10:51 GMT +1)</span><br><br><table align=center border=0 cellpadding=3 cellspacing=1 width=90%><tr><td><span class=genmed><b>Zitat - </b>Kottan hat folgendes geschrieben:</span><tr><td class=quote><span style=font-size:9px;line-height:normal>(Datum Originalbeitrag: 05.06.2016 22:24 GMT +1)</span><br><br>Mahir Saglik soll in Aue im Gespräch sein nach dem geplatzten Transfer mit Königs...</table><br><br>Was für ein geplatzter Transfer?</table><br><br>Er meint bestimmt, dass Königs bei Aue fast schon fix war und dann bei Würzburg unterschrieben hat</table><br><br>Exakt! <img alt=zustimm.gif border=0 src=images/smiles/zustimm.gif></table><br><br>Okay, ich dachte bei Königs... an Königsblau<br><img alt=grin.gif border=0 src=images/smiles/grin.gif></span>";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testParseQuotesFromMessageWithSingleQuote() throws Exception {
        PostListResponseBodyConverter plConverter = new PostListResponseBodyConverter();
        List<Quote> quotes = plConverter.parseQuotesFromMessage(Jsoup.parse(MESSAGE_WITH_1_QUOTE));
        assertEquals(1, quotes.size());
    }

    @Test
    public void testParseQuotesFromMessageWithNestedQuotes() throws Exception {
        PostListResponseBodyConverter plConverter = new PostListResponseBodyConverter();
        List<Quote> quotes = plConverter.parseQuotesFromMessage(Jsoup.parse(MESSAGE_WITH_4_QUOTES));
        assertEquals(4, quotes.size());
    }
}
