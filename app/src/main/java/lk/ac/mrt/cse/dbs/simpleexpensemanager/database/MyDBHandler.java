package lk.ac.mrt.cse.dbs.simpleexpensemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by KeetMalin on 12/4/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "130581H.db";

    //required variables for Table ACCOUNT
    public static final String TABLE_ACCOUNT = "`account`";
    public static final String COLUMN_ACCOUNT_NO1 = "`accountNo`";
    public static final String COLUMN_BANK_NAME = "`bankName`";
    public static final String COLUMN_ACCOUNT_HOLDER_NAME = "`accountHolderName`";
    public static final String COLUMN_BALANCE = "`balance`";

    //required variables for table TRANSACTION
    public static final String TABLE_TRANSACTION = "`transaction`";
    public static final String COLUMN_DATE = "`date`";
    public static final String COLUMN_ACCOUNT_NO2 = "`accountNo`";
    public static final String COLUMN_EXPENSE_TYPE = "`expenseType`";
    public static final String COLUMN_AMOUNT = "`amount`";


    private static MyDBHandler instance = null;

    public static MyDBHandler getInstance(Context context)
    {
        if(instance == null)
            instance = new MyDBHandler(context);
        return instance;
    }

    private MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + "("
                + COLUMN_ACCOUNT_NO1 + " VARCHAR(6) PRIMARY KEY," + COLUMN_BANK_NAME
                + " VARCHAR(50)," + COLUMN_ACCOUNT_HOLDER_NAME + " VARCHAR(100)," + COLUMN_BALANCE + " DOUBLE "+ ")";

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + "("
                + COLUMN_ACCOUNT_NO2 + " VARCHAR(6)," + COLUMN_DATE
                + " VARCHAR(10)," + COLUMN_EXPENSE_TYPE + " VARCHAR(10), " + COLUMN_AMOUNT +
                " DOUBLE " + " , FOREIGN KEY(" + COLUMN_ACCOUNT_NO2 + ") REFERENCES "
                + TABLE_ACCOUNT+"("+COLUMN_ACCOUNT_NO1+")"+")";

        //execute table creation sql queries

        System.out.println(CREATE_ACCOUNT_TABLE);
        System.out.println(CREATE_TRANSACTION_TABLE);

        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);

        Log.d("Database Created","");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);

    }
//
//    public  SQLiteDatabase getWritableDatabase(){
//        return this.getWritableDatabase();
//    }

    public void removeAccount(String accountNo){
        String query = "Select * FROM " + MyDBHandler.TABLE_ACCOUNT + " WHERE " + MyDBHandler.COLUMN_ACCOUNT_NO1 + " =  \"" + accountNo + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            String accountNumber = (cursor.getString(0));
            db.delete(MyDBHandler.TABLE_ACCOUNT,  MyDBHandler.COLUMN_ACCOUNT_NO1 + " = ?",
                    new String[] { accountNumber });
            cursor.close();
        }
        db.close();
    }
    public void addAccount(Account account){

        ContentValues values = new ContentValues();
        values.put(MyDBHandler.COLUMN_ACCOUNT_NO1, account.getAccountNo());
        values.put(MyDBHandler.COLUMN_BANK_NAME , account.getBankName());
        values.put(MyDBHandler.COLUMN_ACCOUNT_HOLDER_NAME , account.getAccountHolderName());
        values.put(MyDBHandler.COLUMN_BALANCE, account.getBalance());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(MyDBHandler.TABLE_ACCOUNT, null, values);
        db.close();
    }


}
