package by.bsuir.kulinka.quiz.adapter;

import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import by.bsuir.kulinka.quiz.fragment.QuestionFragment;
import by.bsuir.kulinka.quiz.model.Question;

public class QuestionsAdapter extends FragmentStateAdapter implements QuestionFragment.OnAnsweredListener
{
    //----------------------------------------------------------------------------------------------
    private List<Question> questionList;
    private int answeredCountInt = 0;
    private int correctAnswersCountInt = 0;
    private TextView correctAnswersCountTextView;
    private TextView answersCountTextView;
    //Объект интерфейса для показа рекламы
    private AdMobInterface listener;
    //----------------------------------------------------------------------------------------------
    public QuestionsAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
    }
    //----------------------------------------------------------------------------------------------
    public void setQuestions(List<Question> questions)
    {
        this.questionList = questions;
    }
    //----------------------------------------------------------------------------------------------
    public void setCorrectAnswersCountTextView(TextView correctAnswersCountTextView, TextView answeredCount)
    {
        this.correctAnswersCountTextView = correctAnswersCountTextView;
        this.answersCountTextView = answeredCount;
    }
    //----------------------------------------------------------------------------------------------
    //Установить слушателя, который покажет рекламу
    public void setListener(AdMobInterface listener)
    {
        this.listener = listener;
    }
    //----------------------------------------------------------------------------------------------
    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        return QuestionFragment.newInstance(questionList.get(position), this);
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public int getItemCount()
    {
        if (questionList != null) return questionList.size();
        return 0;
    }
    //----------------------------------------------------------------------------------------------
    //Проверить список вопросов
    public boolean isEmpty()
    {
        if (questionList == null) return true;
        return questionList.isEmpty();
    }
    //----------------------------------------------------------------------------------------------
    //Вызывается при ответе
    @Override
    public void onAnswer(boolean isCorrect)
    {
        //Если ответ верный
        if (isCorrect)
        {   //Инкремент количества правильно отвеченных
            correctAnswersCountInt++;
            //Отобразить количество
            correctAnswersCountTextView.setText(String.valueOf(correctAnswersCountInt));
        }
        //Инкремент отвеченных
        answeredCountInt++;
        //Если отвечены все вопросы, то показать рекламу
        if (answeredCountInt == questionList.size())
        {
            listener.showAdMob();
        }
        //Отобразить количество
        answersCountTextView.setText(String.valueOf(answeredCountInt));
    }
    //----------------------------------------------------------------------------------------------
    //Интерфейс для сообщения MainMenuFragment о показе рекламы
    public interface AdMobInterface
    {
        void showAdMob();
    }
    //----------------------------------------------------------------------------------------------
}
