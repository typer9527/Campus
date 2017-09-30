package com.yl.campus.presenter;

import com.google.gson.Gson;
import com.yl.campus.model.PersonalInfoModel;
import com.yl.campus.model.StudentInfo;
import com.yl.campus.view.PersonalInfoView;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 处理PersonalInfo的View和Model
 * Created by Luke on 2017/9/30.
 */

public class PersonalInfoPresenter {

    private final String infoUrl = "http://172.20.10.8:8080/CampusServer/GetInfoJson";
    private PersonalInfoView view;
    private PersonalInfoModel model;

    public PersonalInfoPresenter(PersonalInfoView view) {
        this.view = view;
        model = new PersonalInfoModel();
    }

    public void showInfoData() {
        view.showProgressDialog();
        Observable.create(new ObservableOnSubscribe<PersonalInfoModel>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<PersonalInfoModel> e) {
                try {
                    String json = view.getInfoPrefs();
                    if (json == null) {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("student_id", view.getIdPrefs())
                                .build();
                        Request request = new Request.Builder()
                                .url(infoUrl).post(requestBody).build();
                        Response response = client.newCall(request).execute();
                        json = response.body().string();
                    }
                    model.setInfoJson(json);
                    StudentInfo studentInfo = new Gson().fromJson(json, StudentInfo.class);
                    model.setStudentInfo(studentInfo);
                    e.onNext(model);
                    e.onComplete();
                } catch (IOException ex) {
                    e.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonalInfoModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PersonalInfoModel personalInfoModel) {
                        view.hideProgressDialog();
                        view.savePrefsInfo(personalInfoModel.getInfoJson());
                        view.setInfoData(personalInfoModel.getStudentInfo());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        view.hideProgressDialog();
                        view.onNetworkError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
