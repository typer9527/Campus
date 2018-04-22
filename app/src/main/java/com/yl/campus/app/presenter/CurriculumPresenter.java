package com.yl.campus.app.presenter;

import com.google.gson.Gson;
import com.yl.campus.app.model.Curriculum;
import com.yl.campus.app.model.CurriculumModel;
import com.yl.campus.app.view.CurriculumView;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 获取课表数据并展示课表内容
 * Created by Luke on 2017/10/9.
 */

public class CurriculumPresenter {
    private final String curriculumUrl =
            "http://wanandroid.com/tools/mockapi/2301/course";
    private CurriculumView view;
    private CurriculumModel model;

    public CurriculumPresenter(CurriculumView view) {
        this.view = view;
        model = new CurriculumModel();
    }

    public void getCurriculumData() {
        view.showProgressBar();
        Observable.create(new ObservableOnSubscribe<CurriculumModel>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<CurriculumModel> e) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(curriculumUrl).build();
                    Response response = client.newCall(request).execute();
                    String json = response.body().string();
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("Curriculum");
                    String curriculumJson = jsonArray.get(0).toString();
                    model.setCurriculumJson(curriculumJson);
                    e.onNext(model);
                    e.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    e.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurriculumModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CurriculumModel curriculumModel) {
                        Curriculum curriculum = new Gson().fromJson(
                                curriculumModel.getCurriculumJson(), Curriculum.class);
                        view.hideProgressBar();
                        view.showCourseContent(curriculum);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        view.hideProgressBar();
                        view.onLoadFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
