package by.bsuir.kulinka.quiz;

import androidx.appcompat.app.AppCompatActivity;
import by.bsuir.kulinka.quiz.fragment.MainMenuFragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, new MainMenuFragment(), MainMenuFragment.TAG)
                .addToBackStack(null)
                .commit();
    }
}