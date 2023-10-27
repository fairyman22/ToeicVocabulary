package com.example.toeicvocabulary.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Vocab implements Parcelable {
    private String word;
    private String mean;
    private int id;
    private String pronunc;
    private String sound;
    private String image;

    public Vocab(String word, String mean, int id, String pronunc, String sound, String image) {
        this.word = word;
        this.mean = mean;
        this.id = id;
        this.pronunc = pronunc;
        this.sound = sound;
        this.image = image;
    }

    public Vocab(String word, String pronunc) {
        this.word = word;
        this.pronunc = pronunc;
    }

    public Vocab(String word, String mean, String pronunc, String sound, String image) {
        this.word = word;
        this.mean = mean;
        this.pronunc = pronunc;
        this.sound = sound;
        this.image = image;
    }

    public Vocab() {
    }

    protected Vocab(Parcel in) {
        word = in.readString();
        mean = in.readString();
        id = in.readInt();
        pronunc = in.readString();
        sound = in.readString();
        image = in.readString();
    }

    public static final Creator<Vocab> CREATOR = new Creator<Vocab>() {
        @Override
        public Vocab createFromParcel(Parcel in) {
            return new Vocab(in);
        }

        @Override
        public Vocab[] newArray(int size) {
            return new Vocab[size];
        }
    };

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPronunc() {
        return pronunc;
    }

    public void setPronunc(String pronunc) {
        this.pronunc = pronunc;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(word);
        parcel.writeString(mean);
        parcel.writeInt(id);
        parcel.writeString(pronunc);
        parcel.writeString(sound);
        parcel.writeString(image);
    }
}
