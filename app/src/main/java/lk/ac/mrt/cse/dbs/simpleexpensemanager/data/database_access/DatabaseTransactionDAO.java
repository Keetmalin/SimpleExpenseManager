package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.database.MyDBHandler;

/**
 * Created by KeetMalin on 12/4/2015.
 */
public class DatabaseTransactionDAO implements TransactionDAO {

    //-------------------------------------------------------variables-------------------------------------------------------//
    MyDBHandler dbHandler = null;

    //------------------------------------------------------Methods--------------------------------------------------------//
    public DatabaseTransactionDAO(MyDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        //create transaction object with given parameters
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        //add content values
        ContentValues values = new ContentValues();
        values.put(MyDBHandler.COLUMN_ACCOUNT_NO, accountNo);

        //create date format to support SQLite
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(date);
        values.put(MyDBHandler.COLUMN_DATE , formattedDate);
        values.put(MyDBHandler.COLUMN_EXPENSE_TYPE , expenseType.toString());
        values.put(MyDBHandler.COLUMN_AMOUNT, amount);

        //call insert on database
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.insert(MyDBHandler.TABLE_TRANSACTION, null, values);
        db.close();

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {

        String query = "Select * FROM " + MyDBHandler.TABLE_TRANSACTION ;
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //this list will store the Transaction objects
        List<Transaction> transactionsList =  new LinkedList<>();

        if (cursor.moveToFirst()) {
            do{
                Transaction transaction = null;
                //get proper statement
                Date date = null;
                String dateString = cursor.getString(0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    date = simpleDateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Create transaction object
                transaction= new Transaction(date, cursor.getString(1) , ExpenseType.valueOf(cursor.getString(2)), cursor.getDouble(3));
                transactionsList.add(transaction);
            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return transactionsList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        String query = "Select * FROM " + MyDBHandler.TABLE_TRANSACTION + " ORDER BY " + MyDBHandler.COLUMN_DATE + " LIMIT " + limit ;
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //create list for Transaction objects
        List<Transaction> transactionsList =  new LinkedList<>();

        if (cursor.moveToFirst()) {
            do{
                Transaction transaction = null;
                //do proper formatting for the date
                Date date = null;
                String dateString = cursor.getString(1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    date = simpleDateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                transaction= new Transaction(date, cursor.getString(0) , ExpenseType.valueOf(cursor.getString(2)), cursor.getDouble(3));
                transactionsList.add(transaction);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactionsList;
    }
}
