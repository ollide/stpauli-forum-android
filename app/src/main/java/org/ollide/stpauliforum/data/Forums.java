package org.ollide.stpauliforum.data;

import org.ollide.stpauliforum.model.Forum;
import org.ollide.stpauliforum.model.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Forums {

    public static final Forum FCSP_FUSSBALLGOETTER = new Forum(1, "FC St. Pauli - Fußballgötter", "Hier kann alles zu unserer Fussballabteilung, dem Herzstück unseres Vereins, rein", Collections.<Topic>emptyList());
    public static final Forum FCSP_ANDERE_SPORTARTEN = new Forum(42, "FC St. Pauli - andere Sportarten", "Alle weiteren Sportarten des FC St. Pauli", Collections.<Topic>emptyList());
    public static final Forum SPORT_A_Z = new Forum(34, "Sport von A bis Z", "Alles Sportliche, was nicht mit dem FC zu tun hat...", Collections.<Topic>emptyList());
    public static final Forum TIPPSPIEL = new Forum(12, "Tippspiel", "Teilnahme nur für registrierte User", Collections.<Topic>emptyList());

    public static final Forum SP_FANAKTIONEN = new Forum(4, "St. Pauli Fanaktionen", "Fanverhalten, Aktionen, Choreographien...", Collections.<Topic>emptyList());
    public static final Forum SP_FANLADEN = new Forum(2, "St. Pauli Fanladen", "Alles zum Fanladen und den dort beheimateten Gruppen (Übersteiger, Fish e.V. ...)", Collections.<Topic>emptyList());
    public static final Forum FANRAEUME = new Forum(66, "Fanräume", "mehr Raum für Fans", Collections.<Topic>emptyList());
    public static final Forum FANCLUB_SPRECHERRAT = new Forum(39, "Fanclub Sprecherrat", "", Collections.<Topic>emptyList());
    public static final Forum SOZIALROMANTIKER = new Forum(59, "Sozialromantiker und andere große Protestaktionen", "", Collections.<Topic>emptyList());
    public static final Forum FANS_WELTWEIT = new Forum(43, "Fans weltweit", "", Collections.<Topic>emptyList());
    public static final Forum FLOHMARKT = new Forum(13, "Flohmarkt", "", Collections.<Topic>emptyList());

    public static final Forum FCSTPAULI = new Forum(3, "FC St. Pauli", "Vorstand, Abteilungen, Vermarktung", Collections.<Topic>emptyList());
    public static final Forum FANSHOP = new Forum(44, "Fanshop/Merchandising", "Klamotten, Merch und sonstiger Krams direkt aus dem Fanshop", Collections.<Topic>emptyList());
    public static final Forum KARTENCENTER = new Forum(78, "Kartencenter", "Für alle Fragen und Diskussionen rund um die Kartensituation und das Ticketing, aber nicht für Karten-Verkäufe/ -Gesuche (die bitte im Flohmarkt in den jeweiligen Spieltags-Sammelthread)", Collections.<Topic>emptyList());
    public static final Forum VIVA_CON_AGUA = new Forum(40, "Viva con Agua de Sankt Pauli", "Alles rund um das Projekt \"Sauberes Trinkwasser\" von Benny Adrion & Co.", Collections.<Topic>emptyList());

    public static final Forum ABROAD = new Forum(5, "For our visitors from abroad", "Only english postings in this forum please.", Collections.<Topic>emptyList());

    public static final Forum FAQ = new Forum(7, "FAQ / Anregungen / Kritik / News", "Probleme, Fragen oder einfach nur Feedback? Moderatoren- oder Userkritik? Kein Problem, hier bist du richtig!", Collections.<Topic>emptyList());
    public static final Forum LABERECKE = new Forum(6, "Klönschnack / Laberecke", "Hier könnt ihr euren Klönschnack durchführen, Spielberichte und Fotos ankündigen usw...", Collections.<Topic>emptyList());
    public static final Forum UNPAULITISCH = new Forum(11, "Unpaulitisch?", "Tages- und Weltgeschehen / Politik, die nicht in die anderen Kategorien passt.", Collections.<Topic>emptyList());
    public static final Forum RATGEBER = new Forum(75, "Ratgeber und Lebenshilfe", "Empfehlungen, Tipps und Tricks, Informationsaustausch, Ratgeber...", Collections.<Topic>emptyList());
    public static final Forum INTERNETHILFE = new Forum(8, "Internethilfe / Computerfragen / Webmasteraustausch", "Der Platz für technische Fragen, die nicht direkt mit diesem Forum zu tun haben. // Fragen zum Forum bitte in FAQ.", Collections.<Topic>emptyList());
    public static final Forum TESTFORUM = new Forum(10, "Testforum", "Für Newbies, testet alles aus ;-)", Collections.<Topic>emptyList());

    public static final List<Forum> CAT_SPORT = new ArrayList<>(4);
    static {
        CAT_SPORT.add(FCSP_FUSSBALLGOETTER);
        CAT_SPORT.add(FCSP_ANDERE_SPORTARTEN);
        CAT_SPORT.add(SPORT_A_Z);
        CAT_SPORT.add(TIPPSPIEL);
    }

    public static final List<Forum> CAT_FANSZENE = new ArrayList<>(7);
    static {
        CAT_FANSZENE.add(SP_FANAKTIONEN);
        CAT_FANSZENE.add(SP_FANLADEN);
        CAT_FANSZENE.add(FANRAEUME);
        CAT_FANSZENE.add(FANCLUB_SPRECHERRAT);
        CAT_FANSZENE.add(SOZIALROMANTIKER);
        CAT_FANSZENE.add(FANS_WELTWEIT);
        CAT_FANSZENE.add(FLOHMARKT);
    }

    public static final List<Forum> CAT_OFFIZIELLES = new ArrayList<>(4);

    static {
        CAT_OFFIZIELLES.add(FCSTPAULI);
        CAT_OFFIZIELLES.add(FANSHOP);
        CAT_OFFIZIELLES.add(KARTENCENTER);
        CAT_OFFIZIELLES.add(VIVA_CON_AGUA);
    }

    public static final List<Forum> CAT_ENGLISH = new ArrayList<>(1);

    static {
        CAT_ENGLISH.add(ABROAD);
    }

    public static final List<Forum> CAT_SONSTIGES = new ArrayList<>(6);

    static {
        CAT_SONSTIGES.add(FAQ);
        CAT_SONSTIGES.add(LABERECKE);
        CAT_SONSTIGES.add(UNPAULITISCH);
        CAT_SONSTIGES.add(RATGEBER);
        CAT_SONSTIGES.add(INTERNETHILFE);
        CAT_SONSTIGES.add(TESTFORUM);
    }

    private Forums() {
        // do not instantiate
    }

}
