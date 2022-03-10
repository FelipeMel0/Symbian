package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLHelper  extends  SQLiteOpenHelper {

    private static final String DB_NAME = "symbian";
    private static final int DB_VERSION = 3;
    private static SQLHelper INSTANCE;

    public static SQLHelper getINSTANCE(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new SQLHelper(context);
        }

        return INSTANCE;

    }

    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE tbl_cliente" +
                "(cod_cliente INTEGER PRIMARY KEY," +
                "nome TEXT," +
                "sobrenome TEXT," +
                "login TEXT," +
                "senha TEXT)");

       Log.d("SQLITE-", "BANCO DE DADOS CRIADO! - " + DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("CREATE TABLE tbl_endereco" +
                "(cod_endereco INTEGER PRIMARY KEY," +
                "cod_cliente INTEGER," +
                "cep TEXT," +
                "numero TEXT," +
                "complemento TEXT," +
                "FOREIGN KEY (cod_cliente) REFERENCES tbl_cliente (cod_cliente))");
    }


    public int addUser(String nome, String sobrenome, String login, String senha) {

        //Configura o SQLITE para escrita:

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try {

            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("nome", nome);
            values.put("sobrenome", sobrenome);
            values.put("login", login);
            values.put("senha", senha);

            int cod_cliente = (int) sqLiteDatabase.insertOrThrow("tbl_cliente", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return cod_cliente;

        } catch (Exception e) {

            Log.d("SQLITE-", e.getMessage());
            return 0;

        } finally {

            if(sqLiteDatabase.isOpen()){

                sqLiteDatabase.endTransaction();

            }

        }

    }

    public boolean addAddress(int cod_cliente, String cep, String numero, String complemento) {

        //Configura o SQLITE para escrita:

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try {

            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("cod_cliente", cod_cliente);
            values.put("cep", cep);
            values.put("numero", numero);
            values.put("complemento", complemento);

            sqLiteDatabase.insertOrThrow("tbl_endereco", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;

        } catch (Exception e) {

            Log.d("SQLITE-", e.getMessage());
            return false;

        } finally {

            if(sqLiteDatabase.isOpen()){

                sqLiteDatabase.endTransaction();

            }

        }

    }

}
