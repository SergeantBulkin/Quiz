package by.bsuir.kulinka.quiz.retrofit;

import java.util.List;

import by.bsuir.kulinka.quiz.model.Answer;
import by.bsuir.kulinka.quiz.model.Question;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyServerApi
{
    //Получить вопросы по категории
    @GET("questions/category")
    Observable<List<Question>> getQuestionsFromCategory(@Query(value = "category_id") int category_id);

    //Получить ответы по категории
    @GET("answers/category")
    Observable<List<Answer>> getAnswersFromCategory(@Query(value = "category_id") int category_id);
}
