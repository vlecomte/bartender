package be.uclouvain.lsinf1225.v.bartender.dao;

import android.provider.BaseColumns;

final class Contract {

    public static final String TABLE_USER = "User";

    public static abstract class UserTable implements BaseColumns {

        public static final String COL_USERNAME = "Username";
        public static final String COL_PASSWORD = "Password";
        public static final String COL_EMAIL = "Email";
        public static final String COL_RANK = "Rank";
        public static final String RANK_CUSTOMER = "customer";
        public static final String RANK_WAITER = "waiter";
        public static final String RANK_ADMIN = "admin";
        public static final String COL_LANGUAGE = "Language";
    }
}
