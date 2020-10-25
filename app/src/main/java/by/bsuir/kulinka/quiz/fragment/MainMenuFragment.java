package by.bsuir.kulinka.quiz.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import by.bsuir.kulinka.quiz.R;
import by.bsuir.kulinka.quiz.adapter.QuestionsAdapter;
import by.bsuir.kulinka.quiz.databinding.FragmentMainMenuBinding;
import by.bsuir.kulinka.quiz.model.Answer;
import by.bsuir.kulinka.quiz.model.Question;

public class MainMenuFragment extends Fragment implements CategoryListDialogFragment.CategoryDialogItemListener
{
    public static final String TAG = "MainMenuFragment";
    //----------------------------------------------------------------------------------------------
    FragmentMainMenuBinding binding;
    //Список категорий
    ArrayList<String> categories;
    int count;
    //----------------------------------------------------------------------------------------------
    public MainMenuFragment()
    {
        // Required empty public constructor
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    //----------------------------------------------------------------------------------------------

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //Init
        init();

        //Настройка кнопок
        setUpButtons();
    }
    //----------------------------------------------------------------------------------------------
    private void init()
    {
        categories = new ArrayList<>();
        categories.add("Машины");
        categories.add("История");
        categories.add("Искусство");
        categories.add("Технологии");
        categories.add("Наука");
    }
    //----------------------------------------------------------------------------------------------
    //Настройка кнопок
    private void setUpButtons()
    {
        //Слушатель на кнопку "Играть"
        binding.mainMenuPlayButton.setOnClickListener(v ->
        {
            loadFragment(new QuizFragment(setAdapter(), count), QuizFragment.TAG);
        });

        //Слушатель на кнопку "Выход"
        binding.mainMenuExitButton.setOnClickListener(v ->
        {
            requireActivity().finish();
        });

        //Слушатель на кнопку выбора категории
        binding.mainMenuCategoryButton.setOnClickListener(v ->
        {
            //Показать диалог выбора категории
            CategoryListDialogFragment.newInstance(categories, this).show(requireFragmentManager(), "dialog");
        });
    }
    //----------------------------------------------------------------------------------------------
    //Создать адаптер
    private QuestionsAdapter setAdapter()
    {
        QuestionsAdapter questionsAdapter = new QuestionsAdapter(requireActivity());
        questionsAdapter.setQuestions(initQuestions());
        return questionsAdapter;
    }

    private List<Question> initQuestions()
    {
        List<Question> questions = new ArrayList<>();

        List<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("Ответ 1", false));
        answers1.add(new Answer("Ответ 2", true));
        answers1.add(new Answer("Ответ 3", false));
        answers1.add(new Answer("Ответ 4", false));
        Question question1 = new Question("Вопрос 1", answers1);
        questions.add(question1);

        List<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("Ответ 5", false));
        answers2.add(new Answer("Ответ 6", true));
        answers2.add(new Answer("Ответ 7", false));
        answers2.add(new Answer("Ответ 8", false));
        Question question2 = new Question("Вопрос 2", answers2);
        questions.add(question2);

        List<Answer> answers3 = new ArrayList<>();
        answers3.add(new Answer("Ответ 9", false));
        answers3.add(new Answer("Ответ 10", true));
        answers3.add(new Answer("Ответ 11", false));
        answers3.add(new Answer("Ответ 12", false));
        Question question3 = new Question("Вопрос 3", answers3);
        questions.add(question3);

        List<Answer> answers4 = new ArrayList<>();
        answers4.add(new Answer("Ответ 13", false));
        answers4.add(new Answer("Ответ 14", true));
        answers4.add(new Answer("Ответ 15", false));
        answers4.add(new Answer("Ответ 16", false));
        Question question4 = new Question("Вопрос 4", answers4);
        questions.add(question4);
        count = questions.size();
        return questions;
    }

    //----------------------------------------------------------------------------------------------
    //Загрузить нужный фрагмент
    private void loadFragment(Fragment fragment, String tag)
    {
        requireFragmentManager().beginTransaction().replace(R.id.fragment, fragment, tag).addToBackStack(TAG).commit();
    }
    //----------------------------------------------------------------------------------------------
    //Метод интерфейса CategoryDialogItemListener
    @Override
    public void categorySelected(int index)
    {
        binding.mainMenuCategoryButton.setText(categories.get(index));
    }
    //----------------------------------------------------------------------------------------------
}