package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database_access;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

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
        return null;
    }

    @Override
    public List<Account> getAccountsList() {

        return null;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return null;
    }

    @Override
    public void addAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put(MyDBHandler.COLUMN_ACCOUNT_NO1, account.getAccountNo());
        values.put(MyDBHandler.COLUMN_BANK_NAME , account.getBankName());
        values.put(MyDBHandler.COLUMN_ACCOUNT_HOLDER_NAME , account.getAccountHolderName());
        values.put(MyDBHandler.COLUMN_BALANCE, account.getBalance());

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        db.insert(MyDBHandler.TABLE_ACCOUNT, null, values);
        db.close();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
