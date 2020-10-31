package by.bsuir.kulinka.quiz.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable
{
    //----------------------------------------------------------------------------------------------
    String answerText;
    int isCorrect;
    //----------------------------------------------------------------------------------------------
    public Answer(String answerText, int isCorrect)
    {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    protected Answer(Parcel in)
    {
        answerText = in.readString();
        isCorrect = in.readInt();
    }
    //----------------------------------------------------------------------------------------------
    public String getAnswerText()
    {
        return answerText;
    }

    public int getIsCorrect()
    {
        return isCorrect;
    }

    //----------------------------------------------------------------------------------------------
    public static final Creator<Answer> CREATOR = new Creator<Answer>()
    {
        @Override
        public Answer createFromParcel(Parcel in)
        {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size)
        {
            return new Answer[size];
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
        dest.writeString(answerText);
        dest.writeInt(isCorrect);
    }
    //----------------------------------------------------------------------------------------------
}
