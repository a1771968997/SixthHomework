package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Date;

import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.Priority;
import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private CheckBox checkBoxLow,checkBoxNormal,checkBoxHigh;
    TodoDbHelper todoDbHelper ;
    SQLiteDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        checkBoxHigh = findViewById(R.id.checkhigh);
        checkBoxNormal = findViewById(R.id.checknormal);
        checkBoxLow = findViewById(R.id.checklow);

        checkBoxLow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxHigh.setChecked(false);
                checkBoxNormal.setChecked(false);
            }
        });
        checkBoxNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxHigh.setChecked(false);
                checkBoxLow.setChecked(false);
            }
        });
        checkBoxHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxLow.setChecked(false);
                checkBoxNormal.setChecked(false);
            }
        });


        todoDbHelper = new TodoDbHelper(this);
        db = todoDbHelper.getWritableDatabase();
    }

    @Override
    protected void onDestroy() {
        todoDbHelper.close();
        super.onDestroy();
    }

    private boolean saveNote2Database(String content) {
        // TODO 插入一条新数据，返回是否插入成功
        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoEntry.DATE,new Date().getTime());
        values.put(TodoContract.TodoEntry.CONTENT,content);
        values.put(TodoContract.TodoEntry.STATE,0);
        values.put(TodoContract.TodoEntry.Priority,getPriority());
        long newid = db.insert(TodoContract.TodoEntry.TABLE_NAME,null,values);
        System.out.println(newid);
        if(newid!=-1)
            return true;
        else
            return false;
    }

    private int getPriority()
    {
        if(checkBoxHigh.isChecked())
            return Priority.High.intValue;
        if(checkBoxNormal.isChecked())
            return Priority.Normal.intValue;
        if (checkBoxLow.isChecked())
            return Priority.Low.intValue;
        return Priority.Low.intValue;
    }

}
