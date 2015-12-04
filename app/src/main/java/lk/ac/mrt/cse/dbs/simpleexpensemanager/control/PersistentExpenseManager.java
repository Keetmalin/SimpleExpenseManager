package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database_access.DatabaseAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database_access.DatabaseTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.database.MyDBHandler;

/**
 * Created by KeetMalin on 12/4/2015.
 */
public class PersistentExpenseManager extends ExpenseManager {

    private Context context = null;
    MyDBHandler dbHandler = null;

    public PersistentExpenseManager(Context context) throws ExpenseManagerException {
        this.context = context;
        dbHandler = new MyDBHandler(context);
        setup();

    }

    @Override
    public void setup() throws ExpenseManagerException {

        TransactionDAO inMemoryTransactionDAO = new DatabaseTransactionDAO(dbHandler);
        setTransactionsDAO(inMemoryTransactionDAO);

        AccountDAO inMemoryAccountDAO = new DatabaseAccountDAO(dbHandler);
        setAccountsDAO(inMemoryAccountDAO);




    }
}
