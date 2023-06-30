package lu.uni.sep.group5.UniLux.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import lu.uni.sep.group5.UniLux.R;

public class Activity_quiz extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private static ArrayList<Question> QUESTIONS;
    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    private long backPressedTime;

    protected static String language = Locale.getDefault().getLanguage();

    public static ArrayList<Question> getQUESTIONS() {
        return QUESTIONS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById((R.id.text_view_question_count));
        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();

        parseXML();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        switch(language) {
                            case "fr":
                                Toast.makeText(Activity_quiz.this, "Sélectionnez un choix svp", Toast.LENGTH_SHORT).show(); break;
                            case "de":
                                Toast.makeText(Activity_quiz.this, "Bitte wählen Sie eine Antwort", Toast.LENGTH_SHORT).show(); break;
                            default:
                                Toast.makeText(Activity_quiz.this, "Please select an answer", Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            String language = Locale.getDefault().getLanguage();
  //          language = language.substring(0,3);
            InputStream is;
            switch(language) {
                case "fr":
                    is = getAssets().open("questions_fr.xml"); break;
                case "de":
                    is = getAssets().open("questions_de.xml"); break;
                default:
                    is = getAssets().open("questions.xml"); break;

            }
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            QUESTIONS = processParsing(parser);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Question> processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Question> questions = new ArrayList<>();
        int eventType = parser.getEventType();
        Question currentQuestion = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("Question".equals(eltName)) {
                        currentQuestion = new Question();
                        questions.add(currentQuestion);
                    } else if (currentQuestion != null) {
                        if ("question".equals(eltName)) {
                            currentQuestion.setQuestion((parser.nextText()));
                        } else if ("option1".equals(eltName)) {
                            currentQuestion.setOption1(parser.nextText());
                        } else if ("option2".equals((eltName))) {
                            currentQuestion.setOption2(parser.nextText());
                        } else if ("option3".equals(eltName)) {
                            currentQuestion.setOption3(parser.nextText());
                        } else if ("answerNr".equals(eltName)) {
                            currentQuestion.setAnswerNr(Integer.parseInt(parser.nextText()));
                        } else if ("solution".equals(eltName)) {
                            currentQuestion.setSolution(parser.nextText());
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        return questions;
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            answered = false;

            switch(language) {
                case "fr":
                    textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
                    buttonConfirmNext.setText("Confirmer"); break;
                case "de":
                    textViewQuestionCount.setText("Frage: " + questionCounter + "/" + questionCountTotal);
                    buttonConfirmNext.setText("Bestätigen"); break;
                default:
                    textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
                    buttonConfirmNext.setText("Confirm");
            }

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishQuiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;

            switch(language) {
                case "fr":
                    textViewScore.setText("Points: " + score); break;
                case "de":
                    textViewScore.setText("Ergebnis: " + score); break;
                default:
                    textViewScore.setText("Score: " + score);
            }
        }

        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
        }

        textViewQuestion.setText(currentQuestion.getSolution());


        if (questionCounter < questionCountTotal) {

            switch(language) {
                case "fr":
                    buttonConfirmNext.setText("Suivant");break;
                case "de":
                    buttonConfirmNext.setText("Nächste"); break;
                default:
                    buttonConfirmNext.setText("Next");
            }
        } else {
            switch(language) {
                case "fr":
                    buttonConfirmNext.setText("Terminer");break;
                case "de":
                    buttonConfirmNext.setText("Beenden"); break;
                default:
                    buttonConfirmNext.setText("Finish");
            }
        }
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            switch(language) {
                case "fr":
                    Toast.makeText(this, "Appuyez à nouveau pour sortir", Toast.LENGTH_SHORT).show(); break;
                case "de":
                    Toast.makeText(this, "Drücken Sie erneut, um den Vorgang abzuschließen", Toast.LENGTH_SHORT).show(); break;
                default:
                    Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
            }
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public static String getLanguage() {
        switch(language) {
            case "fr":
                return "Quiz_fr.db";
            case "de":
                return "Quiz_de.db";
            default:
                return "Quiz.db";
        }
    }
}
