package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.database.MyDBHandler;

/**
 * Created by KeetMalin on 12/4/2015.
 */

/*****this class implements the AccountDAO interface******/
public class DatabaseAccountDAO implements AccountDAO {

    //-------------------------------------------------------variables-----------------------------------------------------------//
    MyDBHandler dbHandler = null;

    //-------------------------------------------------------Methods-----------------------------------------------------------//
    public DatabaseAccountDAO(MyDBHandler dbHandler){
        this.dbHandler = dbHandler;
    }

    @Override
    public List<String> getAccountNumbersList() {

        String query = "Select"+ MyDBHandler.COLUMN_ACCOUNT_NO +"FROM " + MyDBHandler.TABLE_ACCOUNT ;
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //this list will store the account numbers
        List<String> accountsList =  new LinkedList<>();

        if (cursor.moveToFirst()) {
            do{
                //add the account numbers
                accountsList.add(cursor.getString(0));
            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return accountsList;
    }

    @Override
    public List<Account> getAccountsList() {

        String query = "Select * FROM " + MyDBHandler.TABLE_ACCOUNT ;
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //this list will store the account variables
        List<Account> accountsList =  new LinkedList<>();

        if (cursor.moveToFirst()) {
            do{
                Account account = null;
                //revoke the constructor of Account class to create an account object
                account= new Account(cursor.getString(0), cursor.getString(1) , cursor.getString(2), cursor.getDouble(3));
                accountsList.add(account);
            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return accountsList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {


        String query = "Select * FROM " + MyDBHandler.TABLE_ACCOUNT + " WHERE " + MyDBHandler.COLUMN_ACCOUNT_NO
                + " = ? "  ;
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        //binding into a prepared statement
        Cursor cursor = db.rawQuery(query, new String[]{"'" + accountNo + "'"});

        Account account = null;

        if (cursor.moveToFirst()) {
            account= new Account(cursor.getString(0), cursor.getString(1) , cursor.getString(2), cursor.getDouble(3));
        }

        cursor.close();
        db.close();
        return account;
    }

    @Override
    public void addAccount(Account account) {

        //add contents ro Content Values
        ContentValues values = new ContentValues();
        values.put(MyDBHandler.COLUMN_ACCOUNT_NO, account.getAccountNo());
        values.put(MyDBHandler.COLUMN_BANK_NAME , account.getBankName());
        values.put(MyDBHandler.COLUMN_ACCOUNT_HOLDER_NAME , account.getAccountHolderName());
        values.put(MyDBHandler.COLUMN_BALANCE, account.getBalance());


        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.insert(MyDBHandler.TABLE_ACCOUNT, null, values);
        db.close();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

        String query = "Select * FROM " + MyDBHandler.TABLE_ACCOUNT + " WHERE " + MyDBHandler.COLUMN_ACCOUNT_NO + " =  ? ";

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{ "'"+accountNo+ "'"});

        //if the account is available
        if (cursor.moveToFirst()) {
            String accountNumber = (cursor.getString(0));
            db.delete(MyDBHandler.TABLE_ACCOUNT,  MyDBHandler.COLUMN_ACCOUNT_NO + " = ?",
                    new String[] { accountNumber });
            cursor.close();
        }
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        //request for account
        Account account = getAccount(accountNo);
        //add contant values
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBHandler.COLUMN_ACCOUNT_NO , account.getAccountNo());
        contentValues.put(MyDBHandler.COLUMN_BANK_NAME , account.getBankName());
        contentValues.put(MyDBHandler.COLUMN_ACCOUNT_HOLDER_NAME , account.getBankName());

        //select expense type
        if (expenseType == ExpenseType.INCOME){

            contentValues.put(MyDBHandler.COLUMN_BALANCE , account.getBalance()+ amount);

        }else if (expenseType == ExpenseType.EXPENSE){
            contentValues.put(MyDBHandler.COLUMN_BALANCE , account.getBalance()- amount);
        }

        //run the update on database
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.update(MyDBHandler.TABLE_ACCOUNT , contentValues , MyDBHandler.COLUMN_ACCOUNT_NO + " = ? " , new String[]{ "'"+accountNo+ "'"});

    }
}
