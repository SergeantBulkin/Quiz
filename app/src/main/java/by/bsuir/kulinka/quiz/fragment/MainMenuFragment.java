package by.bsuir.kulinka.quiz.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import by.bsuir.kulinka.quiz.R;
import by.bsuir.kulinka.quiz.adapter.QuestionsAdapter;
import by.bsuir.kulinka.quiz.databinding.FragmentMainMenuBinding;
import by.bsuir.kulinka.quiz.model.DisposableManager;
import by.bsuir.kulinka.quiz.model.Question;
import by.bsuir.kulinka.quiz.retrofit.MyServerNetworkService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainMenuFragment extends Fragment implements CategoryListDialogFragment.CategoryDialogItemListener
{
    public static final String TAG = "MainMenuFragment";
    //----------------------------------------------------------------------------------------------
    FragmentMainMenuBinding binding;
    //Адаптер вопросов для ViewPager2
    QuestionsAdapter questionsAdapter;
    //Список категорий
    ArrayList<String> categories;
    //Количество вопросов
    int count;
    //Выбранная категория
    int selectedCategory;
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

        //Инициализация рекламы
        MobileAds.initialize(requireActivity(), code -> Log.d("TAG", "Инициализация рекламы во фрагменте"));
    }
    //----------------------------------------------------------------------------------------------
    private void init()
    {
        //Инициализация адаптера
        questionsAdapter = new QuestionsAdapter(requireActivity());

        //Инициализация списка категорий, если он не существует
        if (categories == null)
        {
            categories = new ArrayList<>();
        }

        //Если существует и пуст, то добавить
        if (categories.isEmpty())
        {
            categories.add("Машины");
            categories.add("История");
            categories.add("Искусство");
            categories.add("Технологии");
            categories.add("Наука");
        }

        //Установить первую категорию выбранной
        selectedCategory = 1;
        binding.mainMenuCategoryButton.setText(categories.get(0));

        //Загрузить вопросы для первой категории
        initQuestions();
    }
    //----------------------------------------------------------------------------------------------
    //Настройка кнопок
    private void setUpButtons()
    {
        //Слушатель на кнопку "Играть"
        binding.mainMenuPlayButton.setOnClickListener(v ->
        {
            //Если в адаптере нет вопрсоов, то ошибка
            if (questionsAdapter.isEmpty())
            {
                //Показать Snackbar с пояснением
                Snackbar.make(binding.getRoot(), "Вопросы не найдены", Snackbar.LENGTH_SHORT).show();
            } else
            {
                loadFragment(new QuizFragment(questionsAdapter, count));
            }
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
    private void setAdapterQuestions(List<Question> questions)
    {
        //Установить загруженные вопросы в адаптер
        questionsAdapter.setQuestions(questions);

        //Установить слушателя для показа рекламы
        questionsAdapter.setListener(this::showAdMob);

        //После установки вопросов разблокировать Views
        viewsForDownloadingEnd();
    }
    //----------------------------------------------------------------------------------------------
    //Инициализация вопросов
    private void initQuestions()
    {
        //Начало загрузки
        viewsForDownloadingStart();

        //Создать список вопросов
        List<Question> questions = new ArrayList<>();

        //Обнулить количество вопросов
        count = 0;

        DisposableManager.add(MyServerNetworkService
                .getInstance()
                .getJSONApi()
                .getQuestionsFromCategory(selectedCategory)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::fromIterable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Question>()
                {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Question question)
                    {
                        questions.add(question);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e)
                    {
                        //Показать ошибку
                        showError();
                        Log.d("TAG", "Ошибка");
                        Log.d("TAG", Objects.requireNonNull(e.getMessage()));

                        //Разблокировать Views
                        viewsForDownloadingEnd();

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete()
                    {
                        //Количество вопросов
                        count = questions.size();

                        //Если загруженных вопросов 0
                        if (count == 0)
                        {
                            setAdapterQuestions(null);
                            //Показать Snackbar с пояснением
                            Snackbar.make(binding.getRoot(), "Вопросы не найдены", Snackbar.LENGTH_SHORT).show();
                        } else
                        {
                            //Установить загруженные вопросы в адаптер
                            setAdapterQuestions(questions);
                        }
                    }
                }));
    }
    //----------------------------------------------------------------------------------------------
    //Начало загрузки
    private void viewsForDownloadingStart()
    {
        //Заблокировать кнопку "Играть"
        binding.mainMenuPlayButton.setEnabled(false);
        //Показать ProgressBar загрузки
        binding.mainMenuProgressBar.setVisibility(View.VISIBLE);
    }
    private void viewsForDownloadingEnd()
    {
        //Разблокировать кнопку "Играть"
        binding.mainMenuPlayButton.setEnabled(true);
        //Скрыть ProgressBar загрузки
        binding.mainMenuProgressBar.setVisibility(View.GONE);
    }
    private void showError()
    {
        Snackbar.make(binding.getRoot(), "Ошибка", BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("Повторить", v -> initQuestions())
                .show();
    }
    //----------------------------------------------------------------------------------------------
    //Загрузить нужный фрагмент
    private void loadFragment(Fragment fragment)
    {
        requireFragmentManager().beginTransaction().replace(R.id.fragment, fragment, QuizFragment.TAG).addToBackStack(TAG).commit();
    }
    //----------------------------------------------------------------------------------------------
    //Показать рекламу
    public void showAdMob()
    {
        Log.d("TAG", "Показ рекламы");
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd interstitialAd = new InterstitialAd(requireContext());
        interstitialAd.setAdUnitId("ca-app-pub-8501671653071605/2568258533");
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdLoaded()
            {
                Log.d("TAG", "Загружена");
                interstitialAd.show();
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //Метод интерфейса CategoryDialogItemListener
    @Override
    public void categorySelected(int index)
    {
        //Установить выбранную категорию на кнопку
        binding.mainMenuCategoryButton.setText(categories.get(index));

        //Запомнить выбранную категорию
        selectedCategory = index + 1;

        //Загрузить вопросы
        initQuestions();
    }
    //----------------------------------------------------------------------------------------------
}