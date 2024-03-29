package com.byted.camp.todolist.db;

import android.provider.BaseColumns;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public final class TodoContract {

    // TODO 定义表结构和 SQL 语句常量

    private TodoContract() {
    }

    public  static  class TodoEntry implements BaseColumns{
        public  static final String TABLE_NAME = "entry";
        //public  static final String ID ="id";
        public  static final String DATE = "date";
        public  static final String STATE ="state";
        public  static final String CONTENT ="content";
        public  static final String Priority = "priority";
    }
    public static  final  String SQL_CREATE_ENTRIES =
                    "CREATE TABLE "+
                    TodoEntry.TABLE_NAME + " ("+
                    TodoEntry._ID + " INTEGER PRIMARY KEY," +
                    TodoEntry.DATE + " LONG,"+
                    TodoEntry.STATE + " INTEGER,"+
                    TodoEntry.CONTENT + " TEXT)";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISIS "+ TodoEntry.TABLE_NAME;
}
