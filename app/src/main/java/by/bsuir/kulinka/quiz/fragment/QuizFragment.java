package by.bsuir.kulinka.quiz.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.viewpager2.widget.ViewPager2;
import by.bsuir.kulinka.quiz.R;
import by.bsuir.kulinka.quiz.adapter.QuestionsAdapter;
import by.bsuir.kulinka.quiz.databinding.FragmentQuizBinding;
import by.bsuir.kulinka.quiz.model.Question;

public class QuizFragment extends Fragment
{
    public static final String TAG = "QuizFragment";
    //----------------------------------------------------------------------------------------------
    QuestionsAdapter adapter;
    FragmentQuizBinding binding;
    int questionsCountInt;
    //----------------------------------------------------------------------------------------------
    public QuizFragment () {}
    public QuizFragment(QuestionsAdapter adapter, int questionsCount)
    {
        this.adapter = adapter;
        this.questionsCountInt = questionsCount;
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setUp();
    }
    //----------------------------------------------------------------------------------------------
    private void setUp()
    {
        //Установка адаптера
        adapter.setCorrectAnswersCountTextView(binding.fragmentQuizCorrectAnsweredCount, binding.fragmentQuizAnsweredCount);
        binding.viewPagerQuestions.setAdapter(adapter);

        //Установить количество вопросов
        binding.fragmentQuizQuestionsCount.setText(String.valueOf(questionsCountInt));
        binding.fragmentQuizQuestionsCount2.setText(String.valueOf(questionsCountInt));
        binding.fragmentQuizAnsweredCount.setText(String.valueOf(0));
        binding.fragmentQuizCorrectAnsweredCount.setText(String.valueOf(0));

        //Обработка нажатия назад
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(new OnBackPressedCallback(true)
                {
                    @Override
                    public void handleOnBackPressed()
                    {
                        if (binding.viewPagerQuestions.getCurrentItem() == 0)
                        {
                            //Обработка нажатия назад
                            Log.d("TAG", "handleOnBackPressed");
                            new AlertDialog.Builder(requireContext())
                                    .setTitle("Выход")
                                    .setMessage("Вы действительно хотите выйти?")
                                    .setPositiveButton("Выход", (dialog, which) -> requireFragmentManager().popBackStack())
                                    .setNegativeButton("Отмена", null)
                                    .show();
                        } else
                        {
                            binding.viewPagerQuestions.setCurrentItem(binding.viewPagerQuestions.getCurrentItem() - 1);
                        }

                    }
                });
    }
    //----------------------------------------------------------------------------------------------
}