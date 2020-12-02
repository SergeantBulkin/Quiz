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
import android.widget.Button;

import java.util.ArrayList;
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
    //Отвечен ли вопрос
    boolean isAnswered = false;
    //Код выбранного ответа
    //0 - По умолчанию
    //1 - Первый неправильный
    //2 - Второй неправильний
    //3 - Третий неправильный
    //4 - Четвертый неправильный
    //5 - Первый правильный
    //6 - Второй правильный
    //7 - Третий правильный
    //8 - Четвертый правильный
    int chosenAnswer = 0;
    //Текст правильного ответа
    String correctAnswer;
    //Слушатель ответа
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
    public static QuestionFragment newInstance(Question question, OnAnsweredListener listener, int code)
    {
        QuestionFragment fragment = new QuestionFragment(listener);
        Bundle args = new Bundle();
        args.putParcelable("question", question);
        args.putInt("answered", code);
        fragment.setArguments(args);
        return fragment;
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "Fragment - " + this);
        if (getArguments() != null && getArguments().containsKey("question"))
        {
            question = getArguments().getParcelable("question");
        }
        if (getArguments() != null && getArguments().containsKey("answered"))
        {
            isAnswered = true;
            chosenAnswer = getArguments().getInt("answered");
            Log.d("TAG", "Восстановление ответа " + chosenAnswer);
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

        //Список ответов из вопроса
        List<Answer> answers = question.getAnswers();
        //Список кнопок для удобной работы с ними
        List<Button> buttons = new ArrayList<>();
        buttons.add(binding.answer1);
        buttons.add(binding.answer2);
        buttons.add(binding.answer3);
        buttons.add(binding.answer4);

        //Запомнить правильный ответ
        for (Answer answer : answers)
        {
            if (answer.getIsCorrect() == 1)
            {
                correctAnswer = answer.getAnswerText();
            }
        }

        //Перемешать, только если вопрос не был отвечен
        if(!isAnswered) Collections.shuffle(answers);

        //Отобразить ответы
        for (int i = 0; i < answers.size(); i++)
        {
            buttons.get(i).setText(answers.get(i).getAnswerText());
        }

        //Если уже был отвечен
        if (isAnswered)
        {
            Log.d("TAG", "Установить ответы");
            switch (chosenAnswer)
            {
                case 1:
                    showAnsweredButton(1, false, buttons);
                    break;
                case 2:
                    showAnsweredButton(2, false, buttons);
                    break;
                case 3:
                    showAnsweredButton(3, false, buttons);
                    break;
                case 4:
                    showAnsweredButton(4, false, buttons);
                    break;
                case 5:
                    showAnsweredButton(1, true, buttons);
                    break;
                case 6:
                    showAnsweredButton(2, true, buttons);
                    break;
                case 7:
                    showAnsweredButton(3, true, buttons);
                    break;
                case 8:
                    showAnsweredButton(4, true, buttons);
                    break;
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    //Отобразить выбранный ответ
    private void showAnsweredButton(int index, boolean isCorrect, List<Button> buttons)
    {
        //Отобразить выбранный ответ
        if (isCorrect) buttons.get(index).setBackgroundColor(Color.GREEN);
        else buttons.get(index).setBackgroundColor(Color.RED);
        buttons.remove(index);

        //Заблокировать остальные кнопки
        for (Button btn : buttons)
        {
           btn.setEnabled(false);
        }
    }
    //----------------------------------------------------------------------------------------------
    //Установка слушателей
    private void setListeners()
    {
        //Первая кнопка
        binding.answer1.setOnClickListener(v ->
        {
            isAnswered = true;
            if (binding.answer1.getText().equals(correctAnswer))
            {
                binding.answer1.setBackgroundColor(Color.GREEN);
                binding.answer1.setClickable(false);
                listener.onAnswer(true, 5);
            } else
            {
                listener.onAnswer(false, 1);
                binding.answer1.setClickable(false);
                binding.answer1.setBackgroundColor(Color.RED);
            }
            binding.answer2.setEnabled(false);
            binding.answer3.setEnabled(false);
            binding.answer4.setEnabled(false);
        });

        //Вторая кнопка
        binding.answer2.setOnClickListener(v ->
        {
            isAnswered = true;
            if (binding.answer2.getText().equals(correctAnswer))
            {
                binding.answer2.setBackgroundColor(Color.GREEN);
                binding.answer2.setClickable(false);
                listener.onAnswer(true, 6);
            } else
            {
                listener.onAnswer(false, 2);
                binding.answer2.setClickable(false);
                binding.answer2.setBackgroundColor(Color.RED);
            }
            binding.answer1.setEnabled(false);
            binding.answer3.setEnabled(false);
            binding.answer4.setEnabled(false);
        });

        //Третья кнопка
        binding.answer3.setOnClickListener(v ->
        {
            isAnswered = true;
            if (binding.answer3.getText().equals(correctAnswer))
            {
                binding.answer3.setBackgroundColor(Color.GREEN);
                binding.answer3.setClickable(false);
                listener.onAnswer(true, 7);
            } else
            {
                listener.onAnswer(false, 3);
                binding.answer3.setClickable(false);
                binding.answer3.setBackgroundColor(Color.RED);
            }
            binding.answer1.setEnabled(false);
            binding.answer2.setEnabled(false);
            binding.answer4.setEnabled(false);
        });

        //Четвёртая кнопка
        binding.answer4.setOnClickListener(v ->
        {
            isAnswered = true;
            if (binding.answer4.getText().equals(correctAnswer))
            {
                binding.answer4.setBackgroundColor(Color.GREEN);
                binding.answer4.setClickable(false);
                listener.onAnswer(true, 8);
            } else
            {
                listener.onAnswer(false, 4);
                binding.answer4.setClickable(false);
                binding.answer4.setBackgroundColor(Color.RED);
            }
            binding.answer1.setEnabled(false);
            binding.answer2.setEnabled(false);
            binding.answer3.setEnabled(false);
        });
    }
    //----------------------------------------------------------------------------------------------
    //Интерфейс ответа на вопрос
    //Имплементируется QuizFragment
    public interface OnAnsweredListener
    {
        void onAnswer(boolean isCorrect, int code);
    }
    //----------------------------------------------------------------------------------------------
}