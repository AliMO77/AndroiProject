package lu.uni.sep.group5.UniLux.ui.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lu.uni.sep.group5.UniLux.ui.quiz.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuizDbHelper extends SQLiteOpenHelper {

//    protected static  String DATABASE_NAME;

    protected static final int DATABASE_VERSION = 1;
    private static ArrayList<Question> questions;

    private static SQLiteDatabase db;

    public QuizDbHelper(@Nullable Context context) {

        super(context, Activity_quiz.getLanguage(), null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QuestionsTable.TABLE_NAME
                + " ( " + QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QuestionsTable.COLUMN_QUESTION + " TEXT, " + QuestionsTable.COLUMN_OPTION1
                + " TEXT, " + QuestionsTable.COLUMN_OPTION2 + " TEXT, " + QuestionsTable.COLUMN_OPTION3 + " TEXT, "
                + QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " + QuestionsTable.COLUMN_SOLUTION + " TEXT" + ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        // onUpgrade(db, 1,2);
        questions = Activity_quiz.getQUESTIONS();
        fillQuestionTable(questions);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionTable(ArrayList<Question> questions) {
        for (Question q : questions) {
            addQuestion(q);
        }
    }

    protected static void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_SOLUTION, question.getSolution());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        // db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setSolution(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_SOLUTION)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
