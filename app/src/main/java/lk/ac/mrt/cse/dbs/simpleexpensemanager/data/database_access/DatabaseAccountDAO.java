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
public class DatabaseAccountDAO implements AccountDAO {

    MyDBHandler dbHandler = null;

    public DatabaseAccountDAO(MyDBHandler dbHandler){
        this.dbHandler = dbHandler;
    }


    @Override
    public List<String> getAccountNumbersList() {
        String query = "Select * FROM " + MyDBHandler.TABLE_ACCOUNT ;

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        List<String> accountsList =  new LinkedList<>();
        Account account = null;


        if (cursor.moveToFirst()) {
            do{

                account= new Account(cursor.getString(0), cursor.getString(1) , cursor.getString(2), cursor.getDouble(3));
                accountsList.add(account.getAccountNo());
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

        List<Account> accountsList =  new LinkedList<>();
        Account account = null;


        if (cursor.moveToFirst()) {
            do{

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
        String query = "Select * FROM " + MyDBHandler.TABLE_ACCOUNT + " WHERE " + MyDBHandler.COLUMN_ACCOUNT_NO1
                + " = " + "'"+accountNo+"'"  ;

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

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
        dbHandler.addAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        dbHandler.removeAccount(accountNo);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        Account account = getAccount(accountNo);
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyDBHandler.COLUMN_ACCOUNT_NO1 , account.getAccountNo());
        contentValues.put(MyDBHandler.COLUMN_BANK_NAME , account.getBankName());
        contentValues.put(MyDBHandler.COLUMN_ACCOUNT_HOLDER_NAME , account.getBankName());

        //String query = "";

        if (expenseType == ExpenseType.INCOME){

            contentValues.put(MyDBHandler.COLUMN_BALANCE , account.getBalance()+ amount);
//            query = "UPDATE " + MyDBHandler.TABLE_ACCOUNT + " SET " + MyDBHandler.COLUMN_AMOUNT
//                    + " = " + (account.getBalance()+amount) + " WHERE " + MyDBHandler.COLUMN_ACCOUNT_NO1 +
//                    " = " + accountNo;

        }else if (expenseType == ExpenseType.EXPENSE){
            contentValues.put(MyDBHandler.COLUMN_BALANCE , account.getBalance()- amount);
//            query = "UPDATE " + MyDBHandler.TABLE_ACCOUNT + " SET " + MyDBHandler.COLUMN_AMOUNT
//                    + " = " + (account.getBalance()-amount) + " WHERE " + MyDBHandler.COLUMN_ACCOUNT_NO1 +
//                    " = " + accountNo;
        }

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.update(MyDBHandler.TABLE_ACCOUNT , contentValues , MyDBHandler.COLUMN_ACCOUNT_NO1 + " = " + "'"+accountNo+"'" , null);

    }
}
