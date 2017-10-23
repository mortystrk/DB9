package panasuk.db9;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import database.DBHelp;

public class MainActivity extends AppCompatActivity {

    DBHelp dbHelp;
    SQLiteDatabase database;
    EditText id, f, t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelp = new DBHelp(this);
        database = dbHelp.getWritableDatabase();

        id = (EditText) findViewById(R.id.editID);
        f = (EditText)findViewById(R.id.editF);
        t = (EditText) findViewById(R.id.editT);
    }

    public boolean checkEntries(){
        SQLiteDatabase database = dbHelp.getWritableDatabase();
        Cursor cursor = database.query("SimpleTable", null, null, null, null, null, null);
        return  cursor.getCount() != 0;
    }

    public boolean checkCursor(Cursor cursor){
        return cursor.getCount() != 0;
    }

    public void insertData(float f, String t){

        ContentValues values = new ContentValues();
        values.put("F", f);
        values.put("T", t);
        database.insert("SimpleTable", null, values);

    }


    public void onInsert(View view){

        if(f.getText().toString().trim().length() == 0 ||
                t.getText().toString().trim().length() == 0){
            showToast("Не все поля заполнены");
            return;
        }

        insertData(Float.parseFloat(f.getText().toString()), t.getText().toString());
        id.setText("");
        f.setText("");
        t.setText("");
        showToast("Запись добавлена");
    }

    public void onSelect(View view){

        if(!checkEntries()){
            showToast("Записей нет");
            f.setText("");
            id.setText("");
            t.setText("");
            return;
        }

        Cursor cursor = database.query("SimpleTable", new String[]{"F", "T"},
                "ID == ?", new String[]{id.getText().toString()}, null, null, null);

        if(!checkCursor(cursor)){
            f.setText("");
            id.setText("");
            t.setText("");
            showToast("Запись не найдена");
            return;
        }

        cursor.moveToFirst();
        f.setText(cursor.getString(0));
        t.setText(cursor.getString(1));
        id.setText("");
        cursor.close();
    }

    public void onSelectRaw(View view){

        if(!checkEntries()){
            showToast("Записей нет");
            f.setText("");
            id.setText("");
            t.setText("");
            return;
        }

        Cursor cursor = database.rawQuery("SELECT F, T from SimpleTable WHERE ID == ?",
                new String[]{id.getText().toString()});

        if(!checkCursor(cursor)){
            f.setText("");
            id.setText("");
            t.setText("");
            showToast("Запись не найдена");
            return;
        }

        cursor.moveToFirst();
        f.setText(cursor.getString(0));
        t.setText(cursor.getString(1));
        id.setText("");
        cursor.close();
    }

    public void onUpdate(View view){

        if(id.getText().toString().trim().length() == 0){
            showToast("Не введено ID");
            return;
        }

        if(!checkEntries()){
            showToast("Записей нет");
            f.setText("");
            id.setText("");
            t.setText("");
            return;
        }

        ContentValues values = new ContentValues();
        values.put("F", Float.parseFloat(f.getText().toString()));
        values.put("T", t.getText().toString());

        database.update("SimpleTable", values, "ID == ?", new String[]{id.getText().toString()});
        f.setText("");
        id.setText("");
        t.setText("");
        showToast("Данные обновлены");
    }

    public void onDelete(View view){

        if(id.getText().toString().trim().length() == 0){
            showToast("Не введено ID");
            return;
        }

        if(!checkEntries()){
            showToast("Записей нет");
            f.setText("");
            id.setText("");
            t.setText("");
            return;
        }

        database.delete("SimpleTable", "ID == ?", new String[]{id.getText().toString()});
        f.setText("");
        id.setText("");
        t.setText("");
        showToast("Данные удалены");
    }

    private void showToast(String text){
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 8);
        toast.show();
    }
}
