package by.bsuir.kulinka.quiz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import by.bsuir.kulinka.quiz.databinding.FragmentMainMenuBinding;

public class MainMenuFragment extends Fragment implements CategoryListDialogFragment.CategoryDialogItemListener
{
    //----------------------------------------------------------------------------------------------
    FragmentMainMenuBinding binding;
    //Список категорий
    ArrayList<String> categories;
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
    //Метод интерфейса CategoryDialogItemListener
    @Override
    public void categorySelected(int index)
    {
        binding.mainMenuCategoryButton.setText(categories.get(index));
    }
    //----------------------------------------------------------------------------------------------
}