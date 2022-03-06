package edu.udb.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroPersona extends AppCompatActivity {

    private EditText edt5,edt6,edt7;
    private Button edt8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_form);
        edt5=(EditText)findViewById(R.id.edt5);
        edt6=(EditText)findViewById(R.id.edt6);
        edt7=(EditText)findViewById(R.id.edt7);
        edt8= (Button) findViewById(R.id.edt8);

        edt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegistroPersona.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void altaPerson(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String cod = edt5.getText().toString();
        String nombres = edt6.getText().toString();
        String edad = edt7.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("codigo", cod);
        registro.put("nombres", nombres);
        registro.put("edad", edad);

        try {
            bd.insertOrThrow("personas", null, registro);
            bd.close();
            edt5.setText("");
            edt6.setText("");
            edt7.setText("");
            Toast.makeText(this, "Los datos de la persona han sido cargados correctamente",Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, "ERROR!! No se han podido cargar los datos" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void consultaporcodigoPerson(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = edt5.getText().toString();
        Cursor fila = bd.rawQuery("select nombres,edad from personas where codigo=" + cod, null);
        if (fila.moveToFirst()) {
            edt6.setText(fila.getString(0));
            edt7.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe una persona con el codigo ingresado código",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void consultapordescripcionPerson(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String descri = edt6.getText().toString();
        Cursor fila = bd.rawQuery("select codigo,edad from personas where nombres='" + descri +"'", null);
        if (fila.moveToFirst()) {
            edt5.setText(fila.getString(0));
            edt7.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe un artículo con dicha descripción",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void bajaporcodigoPerson(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod= edt5.getText().toString();
        int cant = bd.delete("personas", "codigo=" + cod, null);
        bd.close();
        edt5.setText("");
        edt6.setText("");
        edt7.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borró la persona con el  código ingresado",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe una persona con el código ingresado",
                    Toast.LENGTH_SHORT).show();
    }

    public void modificacionPerson(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = edt5.getText().toString();
        String nombres = edt6.getText().toString();
        String edad = edt7.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("nombres", nombres);
        registro.put("edad", edad);
        int cant = bd.update("personas", registro, "codigo=" + cod, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe una persona con el código ingresado",
                    Toast.LENGTH_SHORT).show();
    }
}