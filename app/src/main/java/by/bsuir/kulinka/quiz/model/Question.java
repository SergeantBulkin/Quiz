package by.bsuir.kulinka.quiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Question implements Parcelable
{
    //----------------------------------------------------------------------------------------------
    String questionText;
    List<Answer> answers;
    //----------------------------------------------------------------------------------------------
    public Question(String questionText, List<Answer> answers)
    {
        this.questionText = questionText;
        this.answers = answers;
    }

    public Question(Parcel in)
    {
        questionText = in.readString();
        answers = in.createTypedArrayList(Answer.CREATOR);
    }
    //----------------------------------------------------------------------------------------------
    public String getQuestionText()
    {
        return questionText;
    }

    public List<Answer> getAnswers()
    {
        return answers;
    }
    //----------------------------------------------------------------------------------------------
    public static final Creator<Question> CREATOR = new Creator<Question>()
    {
        @Override
        public Question createFromParcel(Parcel in)
        {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size)
        {
            return new Question[size];
        }
    };
    //----------------------------------------------------------------------------------------------
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(questionText);
        dest.writeTypedList(answers);
    }
    //----------------------------------------------------------------------------------------------
}
