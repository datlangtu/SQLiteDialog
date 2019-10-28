package com.example.student.thtuan11bai1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tacGia: {
                final Dialog dg1 = new Dialog(MainActivity.this);
                dg1.setContentView(R.layout.author_editor);
                dg1.setTitle("Author editor");
                final EditText etID = dg1.findViewById(R.id.editText_AuthorID);
                final EditText etName = dg1.findViewById(R.id.editText_AuthorName);
                final EditText etAddress = dg1.findViewById(R.id.editText_AuthorAddress);
                final EditText etEmail = dg1.findViewById(R.id.editText_AuthorEmail);
                Button btnSave = dg1.findViewById(R.id.button_AuthorSave);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Author author = new Author();
                        author.setAuthorID(Integer.parseInt(etID.getText().toString()));
                        author.setAuthorName(etName.getText().toString());
                        author.setAuthorAddress(etAddress.getText().toString());
                        author.setAuthorEmail(etEmail.getText().toString());
                        boolean s = dbHelper.insertAuthor(author);
                        if (s)
                            Toast.makeText(MainActivity.this,
                                    "Author added successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                Button btnExit = dg1.findViewById(R.id.button_AuthorExit);
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dg1.dismiss();
                    }
                });
                Button btnSelect = dg1.findViewById(R.id.button_AuthorSelect);
                final GridView gvDisplay = dg1.findViewById(R.id.gridView_Author);
                btnSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> list = new ArrayList<>();
                        ArrayList<Author> listauthor = new ArrayList<>();
                        listauthor = dbHelper.getAllAuthor();
                        for (Author a : listauthor) {
                            list.add(a.getAuthorID() + "");
                            list.add(a.getAuthorName());
                            list.add(a.getAuthorEmail());
                            list.add(a.getAuthorAddress());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                        gvDisplay.setAdapter(adapter);
                    }
                });
                Button btnUpdate = dg1.findViewById(R.id.button_AuthorUpdate);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Author author = new Author();
                        author.setAuthorID(Integer.parseInt(etID.getText().toString()));
                        author.setAuthorName(etName.getText().toString());
                        author.setAuthorAddress(etAddress.getText().toString());
                        author.setAuthorEmail(etEmail.getText().toString());
                        if (dbHelper.updateAuthor(author))
                            Toast.makeText(MainActivity.this,
                                    "Author updated successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                Button btnDelete = dg1.findViewById(R.id.button_AuthorDelete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dbHelper.deleteAuthor(Integer.parseInt(etID.getText().toString()))) {
                            Toast.makeText(MainActivity.this,
                                    "Author updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dg1.show();
                Window window = dg1.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                return true;
            }
        }
        return false;
    }
}
