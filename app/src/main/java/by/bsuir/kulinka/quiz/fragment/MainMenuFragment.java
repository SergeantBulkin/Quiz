package by.bsuir.kulinka.quiz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import by.bsuir.kulinka.quiz.databinding.FragmentMainMenuBinding;

public class MainMenuFragment extends Fragment
{
    FragmentMainMenuBinding binding;

    public MainMenuFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}