package com.example.geo.List;

import android.provider.BaseColumns;

public class TrackContract {

    public static final class TrackEntry implements BaseColumns {

        public static final String TABLE_NAME = "trackable";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION= "description";
        public static final String COLUMN_WEB = "website";
        public static final String COLUMN_CATEGORY = "category";
    }
}
