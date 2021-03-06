package dev.qbikkx.keepsolidone.storage.database;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsDbSchema {
    /**
     * URL - unique field
     */
    public static final class NewsTable {
        public static final String NAME = "news";
        public static final int MAX_CAPACITY = 30;

        public static final class Cols {
            public static final String _ID = "_id";
            public static final String AUTHOR = "author";
            public static final String TITLE = "title";
            public static final String DESC = "description";
            public static final String URL = "url";
            public static final String URL_TO_IMAGE = "url_to_image";
            public static final String PUBLISHED_AT = "published_at";
        }
    }
}
