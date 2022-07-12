package com.freanja.holigo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.freanja.holigo.Model.LocationBean;
import com.freanja.holigo.Model.OrderBean;
import com.freanja.holigo.Model.RecommendBean;
import com.freanja.holigo.Model.SpotItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatabaseUtil extends SQLiteOpenHelper {

//    private Context context;
    private static final String DATABASE_NAME = "ScenicSpot.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "cardAssets";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_RATING = "rating";
    private static final String TIME_LENGTH = "time";
    private static final String PEOPLE_LIMIT = "people";
    private static final String BRIEF_LOCATION = "b_location";
    private static final String BRIEF_INTO = "b_intro";
    private static final String DETAILED_INTO = "d_intro";


    private static final String ACCOUNT_INFO = "accountInfo";
    private static final String USER_ID = "account_id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";

    private static final String USER_FAV = "userFavSpots";
    private static final String RECORD_ID = "record_id";

    private static final String SPOT_PLACE = "spotTripPlace";
    private static final String SPOT_ID = "spot_id";
    private static final String PLACE_ID = "place_id";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";

    private static final String ORDER_TABLE = "orderTable";
    private static final String CHICK_IN = "check_in";
    private static final String CHICK_OUT = "check_out";
    private static final String ADULT_NUM = "adult_num";
    private static final String CHILD_NUM = "child_num";
    private static final String INFANT_NUM = "infant_num";
    private static final String ADULT_PRICE = "adult_price";
    private static final String CHILD_PRICE = "child_price";
    private static final String INFANT_PRICE = "infant_price";

    public DatabaseUtil(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);
        setInitData(sqLiteDatabase);
    }

    private void createTables(SQLiteDatabase sqLiteDatabase) {
        // Spot Info
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_RATING + " INTEGER," +
                TIME_LENGTH + " INTEGER," +
                PEOPLE_LIMIT + " INTEGER," +
                BRIEF_LOCATION + " TEXT," +
                BRIEF_INTO + " TEXT," +
                DETAILED_INTO + " TEXT);";
        sqLiteDatabase.execSQL(query);

        // User Info
        query = "CREATE TABLE " + ACCOUNT_INFO +
                " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT, " +
                USER_EMAIL + " TEXT UNIQUE, " +
                USER_PASSWORD + " TEXT);";
        sqLiteDatabase.execSQL(query);

        // User favorite Spot
        query = "CREATE TABLE " + USER_FAV +
                " (" + RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_ID + " TEXT, " +
                COLUMN_ID + " TEXT);";
        sqLiteDatabase.execSQL(query);

        // Spot trip place
        query = "CREATE TABLE " + SPOT_PLACE +
                " (" + SPOT_ID + " INTEGER, " +
                PLACE_ID + " INTEGER, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                LONGITUDE + " TEXT, " +
                LATITUDE + " TEXT, " +
                COLUMN_RATING + " INTEGER," +
                "PRIMARY KEY ( " + SPOT_ID + ", " + PLACE_ID + " ));";
        sqLiteDatabase.execSQL(query);

        // Order
        query = "CREATE TABLE " + ORDER_TABLE +
                " (" + RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SPOT_ID + " TEXT, " +
                USER_ID + " TEXT, " +
                CHICK_IN + " TEXT, " +
                CHICK_OUT + " TEXT, " +
                ADULT_NUM + " INTEGER, " +
                CHILD_NUM + " INTEGER, " +
                INFANT_NUM + " INTEGER, " +
                ADULT_PRICE + " INTEGER, " +
                CHILD_PRICE + " INTEGER, " +
                INFANT_PRICE + " INTEGER);";
        sqLiteDatabase.execSQL(query);

        System.out.println("finish create tables");

    }

    private void setInitData(SQLiteDatabase sqLiteDatabase) {
        // Spot Info
        String query = "INSERT INTO " + TABLE_NAME + " VALUES " +
                " (10001, 'Hawaii', 327, 'Osenia, America', '4.5', 7, 10, 'Osenia', 'The beauty of the Beach in America', 'Hawaii is one of the favorite tourist destinations for travelers from around the world when it comes to the United States.'), " +
                " (10002, 'Taj Mahal', 278, 'Pradesh, India', '4.7', 7, 9, 'Pradesh', 'Proof of love that will never be seen', 'The Taj Mahal is a monument located in Agra, India. Built at the wish of Mughal Emperor Shāh Jahān, son of Jahangir.'), " +
                " (10003, 'Mount Fuji', 315, 'Kitayama, Japan', '4.6', 7, 9, 'Kitayama', 'The highest mountain in Japan', 'Mount Fuji is a well-known symbol of Japan and is often depicted in works of art and photographs, as well as visited by mountaineers and tourists.'), " +
                " (10004, 'Easter Island', 437, 'Isla de Pascua, Chili', '4.6', 7, 9, 'Isla Pascua', 'Historical heritage mystery tour', 'Easter Island (Polynesian: Rapa Nui, Spanish: Isla de Pascua) is an island belonging to Chile located in the southern Pacific Ocean.')," +
                " (20001, 'Mount Bromo', 219, 'East Java, Indonesia', '4.3', 8, 5, 'East Java', '', '')," +
                " (20002, 'Eiffel Tower', 310, 'Paris, France', '4.6', 6, 7, 'Paris', '', '')," +
                " (20003, 'The Colosseum', 341, 'Roma, Italia', '4.7', 3, 10, 'Isla de Pascua', '', '');";
        sqLiteDatabase.execSQL(query);

        // User Info
        query = "INSERT INTO " + ACCOUNT_INFO +
                " (" + USER_NAME + ", " + USER_EMAIL + ", " + USER_PASSWORD + ")" + " VALUES " +
                " ('admin', 'admin@freanja.cn', 'admin123')," +
                " ('test', 'test@freanja.cn', 'qwe')," +
                " ('FreanJa', 'root@freanja.cn', '123')," +
                " ('Q', 'q', 'q')," +
                " ('clj', 'clj', 'clj')";
        sqLiteDatabase.execSQL(query);

        // Spot trip place
        query = "INSERT INTO " + SPOT_PLACE + " VALUES " +
                " (10001, 1, 'Lolani Palace', 'Oahu Island','-157.8588', '21.3068', '4.3'), " +
                " (10001, 2, 'Molokai Beach', 'Molokai Island','-157.0333', '21.1333', '4.5'), " +
                " (10001, 3, 'Waimeya Canyon', 'Grand Canyon','-159.6592', '22.0537', '4.6'), " +
                " (10001, 4, 'Oahu Beach', 'Oahu Island','-157.834549', '21.280693', '4.4'), " +

                " (10002, 1, 'Agra fort', 'Uttar Pradesh','78.0195', '27.1743', '4.3'), " +
                " (10002, 2, 'Mehtab Bagh', 'Uttar Pradesh','77.9944', '27.16888', '4.5'), " +
                " (10002, 3, 'Itmad ud-daula', 'Uttar Pradesh','78.030981', '27.192887', '4.6'), " +
                " (10002, 4, 'Kau ban Mosque', 'Uttar Pradesh','79.4169356', '28.575876', '4.4'), " +

                " (10003, 1, 'Lake kawaguchi', 'Fujikawaguchiko','138.756668', '35.514999', '4.3'), " +
                " (10003, 2, 'Lake Yamanaka', 'Yamanakako','138.86827', '35.43055', '4.5'), " +
                " (10003, 3, 'Fuji-Q Highland', 'Fujiyoshida','138.7805', '35.4873', '4.6'), " +
                " (10003, 4, 'Shiraito Falls', 'Fujinomiya','138.58754', '35.312794', '4.4'), " +

                " (10004, 1, 'Anakena', 'Isla de pascua','-109.320332052', '-27.070999716', '4.3'), " +
                " (10004, 2, 'Rano Kau', 'Isla de pascua','-109.42734', '-27.18768', '4.5'), " +
                " (10004, 3, 'Rano Rakaru', 'Isla de pascua','-109.28499886', '-27.120999516', '4.6'), " +
                " (10004, 4, 'Orongo', 'Isla de pascua','-109.4425', '-27.18938', '4.4');";
        sqLiteDatabase.execSQL(query);


        System.out.println("finish init data");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readDetailedData() {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " < 20000 ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readBriefData() {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " >= 20000 ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public int verifySignIn(String email, String passwd) {
        String query = "SELECT * FROM " + ACCOUNT_INFO + " WHERE " + USER_EMAIL + " = '" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        if (db == null)
            return -1;

        Cursor cursor = db.rawQuery(query, null);
        if (!cursor.moveToFirst()){
            return -2;
        }

        System.out.println(cursor.getString(2) + ": " + cursor.getString(3));
        if (Objects.equals(cursor.getString(3), passwd)){
            return 1;
        }

        return 0;
    }

    public String[] getUserInfo(String email) {
        String query = "SELECT * FROM " + ACCOUNT_INFO + " WHERE " + USER_EMAIL + " = '" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2)};
    }

    public String[] createAccount(String name, String email, String password, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        String query = "SELECT * FROM " + ACCOUNT_INFO + " WHERE " + USER_EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Toast.makeText(context, "Registered mailbox!", Toast.LENGTH_SHORT).show();
            return new String[]{};
        }

        cv.put(USER_NAME, name);
        cv.put(USER_EMAIL, email);
        cv.put(USER_PASSWORD, password);
        long result = db.insert(ACCOUNT_INFO, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Registration successful, go to Log In!", Toast.LENGTH_SHORT).show();
            return new String[]{email, password};
        }

        return new String[]{};
    }

    public Boolean selectFav(String user, String spot) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_FAV + " WHERE " + USER_ID + " = " + user + " and " + COLUMN_ID + " = " + spot;

        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    public Boolean toggleFav(String user, String spot){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        String query = "SELECT * FROM " + USER_FAV + " WHERE " + USER_ID + " = " + user + " and " + COLUMN_ID + " = " + spot;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            System.out.println("cursor found");
            query = "DELETE FROM " + USER_FAV + " WHERE " + RECORD_ID + " = " + cursor.getString(0);
            db.execSQL(query);
            return false;
        }
        else {
            cv.put(USER_ID, user);
            cv.put(COLUMN_ID, spot);
            long result = db.insert(USER_FAV, null, cv);
            return true;
        }
    }

    public Boolean accountModify(int i, String uid, String input) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "UPDATE " + ACCOUNT_INFO + " SET ";
        switch (i) {
            case 0:
                query += USER_NAME;
                break;
            case 1:
                query += USER_EMAIL;
                break;
            case 2:
                query += USER_PASSWORD;
                break;
            default:
                return false;
        }
        query += " = '" + input + "' WHERE " + USER_ID + " = " + uid;
        db.execSQL(query);

        return true;
    }

    public Cursor readSpotPlace(String spotId) {
        String query = "SELECT * FROM " + SPOT_PLACE + " WHERE " + SPOT_ID + " = " + spotId;
        SQLiteDatabase db = this.getReadableDatabase();

        System.out.println(SPOT_ID + ": " + spotId);
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public ArrayList<LocationBean> getLocations(String spotId) {
        String query = "SELECT * FROM " + SPOT_PLACE + " WHERE " + SPOT_ID + " = " + spotId;
        SQLiteDatabase db = this.getReadableDatabase();

        System.out.println(SPOT_ID + ": " + spotId);
        Cursor cursor = null;
        cursor = db.rawQuery(query, null);

        ArrayList<LocationBean> locationBeanList = new ArrayList<>();

        while (cursor.moveToNext()){
            locationBeanList.add(new LocationBean(cursor.getString(2),
                    Double.parseDouble(cursor.getString(5)),
                    Double.parseDouble(cursor.getString(4))));
        }

        return locationBeanList;
    }

    public String[] spotInfo(String spotId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + spotId;
        SQLiteDatabase db = this.getReadableDatabase();

        System.out.println(SPOT_ID + ": " + spotId);
        Cursor cursor = null;
        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            return new String[]{
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)
            };
        }

        return new String[]{};
    }

    public List<RecommendBean> getSpots() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery(query, null);

        List<SpotItem> spotItemList = new ArrayList<>();
        List<RecommendBean> recommendBeanList = new ArrayList<>();

        while (cursor.moveToNext()) {
            spotItemList.add(new SpotItem(cursor.getString(0), cursor.getString(1)));
            recommendBeanList.add(new RecommendBean(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3)));
        }
        return recommendBeanList;
    }

    public int getPrice(String spotId) {
        String query = "SELECT " + COLUMN_PRICE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + spotId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery(query, null);

        int price = -1;
        while (cursor.moveToNext()) {
            price = cursor.getInt(0);
        }

        return price;
    }

    public int getPeople(String spotId) {
        String query = "SELECT " + PEOPLE_LIMIT + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + spotId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery(query, null);

        int people = -1;
        while (cursor.moveToNext()) {
            people = cursor.getInt(0);
        }

        return people;
    }

    public void setOrder(String spotId, String userId, String checkIn, String checkOut, int an, int cn, int in, int ap, int cp, int ip, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SPOT_ID, spotId);
        cv.put(USER_ID, userId);
        cv.put(CHICK_IN, checkIn);
        cv.put(CHICK_OUT, checkOut);
        cv.put(ADULT_NUM, an);
        cv.put(ADULT_PRICE,ap);
        cv.put(CHILD_NUM, cn);
        cv.put(CHILD_PRICE, cp);
        cv.put(INFANT_NUM, in);
        cv.put(INFANT_PRICE, ip);
        long result = db.insert(ORDER_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<OrderBean> getOrders(String uid) {
        String query = "SELECT * FROM " + ORDER_TABLE + " WHERE " + USER_ID + " = " + uid + " ORDER BY " + RECORD_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery(query, null);

        ArrayList<OrderBean> orderBeanArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            orderBeanArrayList.add(new OrderBean(cursor.getString(0), cursor.getString(1),
                    getBriefInto(cursor.getString(1)), getLocation(cursor.getString(1)),
                    getRating(cursor.getString(1)), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getString(7),
                    cursor.getString(8), cursor.getString(9), cursor.getString(10)));
        }

        return orderBeanArrayList;
    }

    public String getLocation(String spotId){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + spotId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery(query, null);
        String location = "";
        if (cursor.moveToFirst()) {
            location = cursor.getString(3);
        }
        return location;
    }

    public String getBriefInto(String spotId){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + spotId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery(query, null);
        String info = "";
        if (cursor.moveToFirst()) {
            info = cursor.getString(8);
        }
        return info;
    }

    public String getRating(String spotId){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + spotId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery(query, null);
        String rating = "";
        if (cursor.moveToFirst()) {
            rating = cursor.getString(4);
        }
        return rating;
    }
}
