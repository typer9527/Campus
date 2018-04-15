package com.yl.campus.presenter;

import com.yl.campus.model.LoginModel;
import com.yl.campus.view.LoginView;

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
 * 登陆的数据和界面
 * Created by Luke on 2017/9/29.
 */

public class LoginPresenter {
    private final String loginUrl =
            "http://127.0.0.1:8080/CampusServer/LoginAction";
    private LoginView view;
    private LoginModel model;

    public LoginPresenter(LoginView view) {
        this.view = view;
        model = new LoginModel();
    }

    public void login() {
        model.setStudentId(view.getStudentId());
        model.setPassword(view.getPassword());
        if (model.isLoginInfoNull()) {
            view.onLoginInfoNull();
            return;
        }
        view.showProgressDialog();
        Observable.create(new ObservableOnSubscribe<LoginModel>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<LoginModel> e) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("student_id", model.getStudentId())
                            .add("password", model.getPassword())
                            .build();
                    Request request = new Request.Builder()
                            .url(loginUrl).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    model.setLoginResult(response.body().string());
                    e.onNext(model);
                    e.onComplete();
                } catch (IOException ex) {
                    e.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LoginModel loginModel) {
                        view.hideProgressDialog();
                        String result = loginModel.getLoginResult();
                        if ("id_error".equals(result)) {
                            view.onIdError();
                        } else if ("password_error".equals(result)) {
                            view.onPasswordError();
                        } else {
                            view.saveStudentNameAndId(result, model.getStudentId());
                            view.onLoginSucceed();
                        }
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
