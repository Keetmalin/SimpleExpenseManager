package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database_access;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.database.MyDBHandler;

/**
 * Created by KeetMalin on 12/4/2015.
 */
public class DatabaseTransactionDAO implements TransactionDAO{

    MyDBHandler dbHandler = null;

    public DatabaseTransactionDAO(MyDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        ContentValues values = new ContentValues();
        values.put(MyDBHandler.COLUMN_ACCOUNT_NO2, account.getAccountNo());
        values.put(MyDBHandler.COLUMN_BANK_NAME , account.getBankName());
        values.put(MyDBHandler.COLUMN_ACCOUNT_HOLDER_NAME , account.getAccountHolderName());
        values.put(MyDBHandler.COLUMN_BALANCE, account.getBalance());

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        db.insert(MyDBHandler.TABLE_ACCOUNT, null, values);
        db.close();

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return null;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        return null;
    }
}
