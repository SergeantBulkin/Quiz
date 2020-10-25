package by.bsuir.kulinka.quiz.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import by.bsuir.kulinka.quiz.R;
import by.bsuir.kulinka.quiz.databinding.FragmentQuestionBinding;
import by.bsuir.kulinka.quiz.model.Answer;
import by.bsuir.kulinka.quiz.model.Question;

public class QuestionFragment extends Fragment
{
    public static final String TAG = "QuestionFragment";
    //----------------------------------------------------------------------------------------------
    FragmentQuestionBinding binding;
    Question question;
    String correctAnswer;
    OnAnsweredListener listener;
    //----------------------------------------------------------------------------------------------
    public QuestionFragment()
    {
        //Required empty public constructor
    }

    public QuestionFragment(OnAnsweredListener listener)
    {
        this.listener = listener;
    }

    //----------------------------------------------------------------------------------------------
    public static QuestionFragment newInstance(Question question, OnAnsweredListener listener)
    {
        QuestionFragment fragment = new QuestionFragment(listener);
        Bundle args = new Bundle();
        args.putParcelable("question", question);
        fragment.setArguments(args);
        return fragment;
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("question"))
        {
            question = getArguments().getParcelable("question");
        }
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //Отобразить вопрос и ответы
        setUpViews();

        //Установить слушателей на кнопки
        setListeners();
    }
    //----------------------------------------------------------------------------------------------
    private void setUpViews()
    {
        //Отобразить текст вопроса
        binding.questionTextView.setText(question.getQuestionText());

        List<Answer> answers = question.getAnswers();
        Collections.shuffle(answers);

        //Отобразить ответы
        binding.answer1.setText(answers.get(0).getAnswerText());
        binding.answer2.setText(answers.get(1).getAnswerText());
        binding.answer3.setText(answers.get(2).getAnswerText());
        binding.answer4.setText(answers.get(3).getAnswerText());

        //Запомнить правильный ответ
        for (Answer answer : answers)
        {
            if (answer.isCorrect())
            {
                correctAnswer = answer.getAnswerText();
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    private void setListeners()
    {
        //Первая кнопка
        binding.answer1.setOnClickListener(v ->
        {
            if (binding.answer1.getText().equals(correctAnswer))
            {
                binding.answer1.setBackgroundColor(Color.GREEN);
                listener.onAnswer(true);
            } else
            {
                listener.onAnswer(false);
                binding.answer1.setBackgroundColor(Color.RED);
            }
            binding.answer2.setEnabled(false);
            binding.answer3.setEnabled(false);
            binding.answer4.setEnabled(false);
        });

        //Вторая кнопка
        binding.answer2.setOnClickListener(v ->
        {
            if (binding.answer2.getText().equals(correctAnswer))
            {
                binding.answer2.setBackgroundColor(Color.GREEN);
                listener.onAnswer(true);
            } else
            {
                listener.onAnswer(false);
                binding.answer2.setBackgroundColor(Color.RED);
            }
            binding.answer1.setEnabled(false);
            binding.answer3.setEnabled(false);
            binding.answer4.setEnabled(false);
        });

        //Третья кнопка
        binding.answer3.setOnClickListener(v ->
        {
            if (binding.answer3.getText().equals(correctAnswer))
            {
                binding.answer3.setBackgroundColor(Color.GREEN);
                listener.onAnswer(true);
            } else
            {
                listener.onAnswer(false);
                binding.answer3.setBackgroundColor(Color.RED);
            }
            binding.answer1.setEnabled(false);
            binding.answer2.setEnabled(false);
            binding.answer4.setEnabled(false);
        });

        //Четвёртая кнопка
        binding.answer4.setOnClickListener(v ->
        {
            if (binding.answer4.getText().equals(correctAnswer))
            {
                binding.answer4.setBackgroundColor(Color.GREEN);
                listener.onAnswer(true);
            } else
            {
                listener.onAnswer(false);
                binding.answer4.setBackgroundColor(Color.RED);
            }
            binding.answer1.setEnabled(false);
            binding.answer2.setEnabled(false);
            binding.answer3.setEnabled(false);
        });
    }
    //----------------------------------------------------------------------------------------------
    public interface OnAnsweredListener
    {
        void onAnswer(boolean isCorrect);
    }
    //----------------------------------------------------------------------------------------------
}