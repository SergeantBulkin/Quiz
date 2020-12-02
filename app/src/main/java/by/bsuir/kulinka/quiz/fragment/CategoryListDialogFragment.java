package by.bsuir.kulinka.quiz.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.kulinka.quiz.R;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     CategoryListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class CategoryListDialogFragment extends BottomSheetDialogFragment
{
    private CategoryDialogItemListener listener;
    //----------------------------------------------------------------------------------------------
    private static final String ARG_CATEGORIES = "categories_list";
    //----------------------------------------------------------------------------------------------
    public static CategoryListDialogFragment newInstance(ArrayList<String> categoriesList, CategoryDialogItemListener listener)
    {
        final CategoryListDialogFragment fragment = new CategoryListDialogFragment();
        fragment.setListener(listener);
        final Bundle args = new Bundle();
        args.putStringArrayList(ARG_CATEGORIES, categoriesList);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(CategoryDialogItemListener listener)
    {
        this.listener = listener;
    }

    //----------------------------------------------------------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_dialog_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments() != null)
        {
            recyclerView.setAdapter(new CategoryAdapter(getArguments().getStringArrayList(ARG_CATEGORIES), listener));
        }
    }
    //----------------------------------------------------------------------------------------------
    //Адаптер выбора категории
    private class CategoryAdapter extends RecyclerView.Adapter<ViewHolder>
    {
        //------------------------------------------------------------------------------------------
        private List<String> categories;
        CategoryDialogItemListener listener;
        //------------------------------------------------------------------------------------------
        CategoryAdapter(List<String> categoriesList, CategoryDialogItemListener listener)
        {
            this.categories = categoriesList;
            this.listener = listener;
        }
        //------------------------------------------------------------------------------------------
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }
        //------------------------------------------------------------------------------------------
        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            holder.text.setText(categories.get(position));
            holder.setListener(position);
        }
        //------------------------------------------------------------------------------------------
        @Override
        public int getItemCount()
        {
            if (categories == null)
            {
                return 0;
            }
            return categories.size();
        }
        //------------------------------------------------------------------------------------------
    }
    //----------------------------------------------------------------------------------------------
    private class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView text;
        //------------------------------------------------------------------------------------------
        ViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.fragment_dialog_category_item, parent, false));
            text = itemView.findViewById(R.id.category_name);
        }
        //------------------------------------------------------------------------------------------
        //Установить слушателя
        private void setListener(int position)
        {
            itemView.setOnClickListener(v ->
            {
                listener.categorySelected(position);
                requireDialog().dismiss();
            });
        }
        //------------------------------------------------------------------------------------------
    }
    //----------------------------------------------------------------------------------------------
    //Интерфейс для выбора категории
    //Имплементируется MainMenuFragment
    public interface CategoryDialogItemListener
    {
        void categorySelected(int index);
    }
    //----------------------------------------------------------------------------------------------
}