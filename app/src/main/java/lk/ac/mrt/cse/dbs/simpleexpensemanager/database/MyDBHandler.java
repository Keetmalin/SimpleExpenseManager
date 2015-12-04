package lk.ac.mrt.cse.dbs.simpleexpensemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KeetMalin on 12/4/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "130581H.db";

    //required variables for Table ACCOUNT
    public static final String TABLE_ACCOUNT = "account";
    public static final String COLUMN_ACCOUNT_NO1 = "accountNo";
    public static final String COLUMN_BANK_NAME = "bankName";
    public static final String COLUMN_ACCOUNT_HOLDER_NAME = "accountHolderName";
    public static final String COLUMN_BALANCE = "balance";

    //required variables for table TRANSACTION
    public static final String TABLE_TRANSACTION = "transaction";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ACCOUNT_NO2 = "accountNo";
    public static final String COLUMN_EXPENSE_TYPE = "expenseType";
    public static final String COLUMN_AMOUNT = "amount";


    private static MyDBHandler instance = null;
    public MyDBHandler getInstance(Context context)
    {
        if(instance == null)
            instance = new MyDBHandler(context);
        return instance;
    }

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + "("
                + COLUMN_ACCOUNT_NO1 + " VARCHAR(6) PRIMARY KEY," + COLUMN_BANK_NAME
                + " VARCHAR(50)," + COLUMN_ACCOUNT_HOLDER_NAME + " VARCHAR(100)," + COLUMN_BALANCE + " DOUBLE"+ ")";

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + "("
                + COLUMN_ACCOUNT_NO2 + " VARCHAR(6) PRIMARY KEY," + COLUMN_DATE
                + " DATE," + COLUMN_EXPENSE_TYPE + " VARACHAR(10)" + COLUMN_AMOUNT + " DOUBLE" + " , )";

        //execute table creation sql queries
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);

    }

    public  SQLiteDatabase getWritableDatabase(){
        return this.getWritableDatabase();
    }


}
