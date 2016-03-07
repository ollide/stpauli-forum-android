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

    private Forums() {
        // do not instantiate
    }

}
