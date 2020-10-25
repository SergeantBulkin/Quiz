package by.bsuir.kulinka.quiz.adapter;

import android.util.Log;
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
    private TextView answeredCount;
    private TextView correctAnsweredCount;
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

    public void setCorrectAnswersCountTextView(TextView correctAnswersCountTextView, TextView answeredCount)
    {
        this.correctAnswersCountTextView = correctAnswersCountTextView;
        this.answeredCount = answeredCount;
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
    @Override
    public void onAnswer(boolean isCorrect)
    {
        if (isCorrect)
        {
            correctAnswersCountInt++;
            correctAnswersCountTextView.setText(String.valueOf(correctAnswersCountInt));
        }
        answeredCountInt++;
        answeredCount.setText(String.valueOf(answeredCountInt));
    }
    //----------------------------------------------------------------------------------------------
}
