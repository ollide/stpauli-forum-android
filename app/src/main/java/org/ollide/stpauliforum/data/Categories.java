package org.ollide.stpauliforum.data;

import org.ollide.stpauliforum.model.Category;

import java.util.ArrayList;
import java.util.List;

public final class Categories {

    public static final Category RUND_UM_DEN_SPORT = new Category(1, "Rund um den Sport", Forums.CAT_SPORT);
    public static final Category RUND_UM_DIE_FANSZENE = new Category(15, "Rund um die Fanszene", Forums.CAT_FANSZENE);
    public static final Category OFFIZIELLES = new Category(16, "Offizielles", Forums.CAT_OFFIZIELLES);
    public static final Category ENGLISH_FORUM = new Category(2, "English Forum", Forums.CAT_ENGLISH);
    public static final Category SONSTIGES = new Category(3, "Sonstiges", Forums.CAT_SONSTIGES);

    public static final List<Category> ALL = new ArrayList<>(5);
    static {
        ALL.add(RUND_UM_DEN_SPORT);
        ALL.add(RUND_UM_DIE_FANSZENE);
        ALL.add(OFFIZIELLES);
        ALL.add(ENGLISH_FORUM);
        ALL.add(SONSTIGES);
    }

    private Categories() {
        // do not instantiate
    }
}
