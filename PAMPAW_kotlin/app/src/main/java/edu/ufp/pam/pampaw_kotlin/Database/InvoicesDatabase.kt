package edu.ufp.pam.pampaw_kotlin.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.ufp.pam.pampaw_kotlin.retrofit.RestApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The Room database that contains the tables for all entities (e.g. Customer, CustomerTask, etc.).
 *
 * Get an instance of created database using following code:
 *   val db =
 *   Room.databaseBuilder(applicationContext, CustomersDatabase::class.java, "Customers.db").build()
 *
 * Whenever it is need to modify the database schema:
 *      1) update the version number and
 *      2) define a migration strategy
 *
 * When experience errors in Android Studio execute:
 *      Build > Clean Project
 *      Build > Rebuild Project
 */
@Database(
    entities = [Invoice::class],
    version = 1
)
abstract class InvoicesDatabase : RoomDatabase() {

    abstract fun invoiceDao(): InvoiceDAO

    //Behaves like a static attribute
    companion object {
        @Volatile
        private var INSTANCE: InvoicesDatabase? = null

        fun getInvoiceDatabaseInstance(context: Context): InvoicesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                InvoicesDatabase::class.java,
                "Invoices.db"
            )
                .fallbackToDestructiveMigration()
                //May use migration objets or each new schema
                //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()

        fun getInvoiceDatabaseInstance(
            context: Context,
            scope: CoroutineScope
        ): InvoicesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, scope).also { INSTANCE = it }
            }

        /** Populate DB through the use of a RoomDatabase.Callback use in Room.databaseBuilder(). */
        private fun buildDatabase(context: Context, scope: CoroutineScope) =
            Room.databaseBuilder(
                context.applicationContext,
                InvoicesDatabase::class.java,
                "Invoices.db"
            )
                .fallbackToDestructiveMigration()
                //Use migration objects for each new schema evolution
                //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                //Use RoomDatabase.Callback() to clear and repopulate DB instead of migrating
                .addCallback(InvoicesDatabaseCallback(scope))
                .build()
    }

    /**
     * The RoomDatabase.Callback() is called on DB databaseBuilder():
     *  1. override onOpen(): clear and repopulate DB whenever app is started;
     *  2. override the onCreate(): populate DB only the first time the app is launched.
     */
    private class InvoicesDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        /** Override onOpen() to clear and populate DB every time app is started. */
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            // To keep DB data through app restarts comment coroutine exec:
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    cleanAndPopulateInvoicesDatabase(database.invoiceDao())
                }
            }
        }

        /** Overrite onCreate() to populate DB only first time app is launched. */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            //To clear and repopulate DB every time app is started comment coroutine exec:
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    //cleanAndPopulateCustomersDatabase(database.customerDao())
                }
            }
        }

        /**
         * Remove all customers from DB and populate with some customers.
         */
        fun cleanAndPopulateInvoicesDatabase(invoiceDAO: InvoiceDAO) {
            // Clear all customers from DB
            invoiceDAO.deleteAllInvoices()

            val apiService = RestApiService()

            //Populate
            apiService.getUserInvoices { it ->
                it?.data?.forEach {
                    val invoice = Invoice(it.id, it.name, it.userid, it.info,it.image)
                    //Add
                    invoiceDAO.insertInvoice(invoice)
                }
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE `tasktypes` (`id` INTEGER, `tasktitle` TEXT, " +
                    "PRIMARY KEY(`id`))"
        )
    }
}

val MIGRATION_2_3: Migration
    get() = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE tasktypes ADD COLUMN taskpriority INTEGER")
        }
    }

