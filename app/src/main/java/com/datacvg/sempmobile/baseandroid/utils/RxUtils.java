package com.datacvg.sempmobile.baseandroid.utils;

import android.view.View;

import com.datacvg.sempmobile.baseandroid.mvp.MvpView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * FileName: RxUtils
 * Author: 曹伟
 * Date: 2019/9/16 17:08
 * Description:
 */

public class RxUtils {
    public static <T> ObservableTransformer<T, T> applySchedulersLifeCycle(final LifecycleProvider lifecycleProvider) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxUtils.<T>bindToLifecycle(lifecycleProvider));
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applySchedulersLifeCycle(final MvpView mvpView) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxUtils.<T>bindToLifecycle((LifecycleProvider) mvpView));
            }
        };
    }

    static <T> LifecycleTransformer<T> bindToLifecycle(LifecycleProvider lifecycleProvider) {
        if (lifecycleProvider instanceof RxFragment) {
            return lifecycleProvider.bindUntilEvent((FragmentEvent.DESTROY));
        } else {
            return lifecycleProvider.bindUntilEvent((ActivityEvent.DESTROY));
        }
    }

    public static Observable<List<Integer>> clickCountStream(View view) {
        Observable<Integer> clickStreamObservable = Observable.create(new ClickStream(view));
        return clickStreamObservable.buffer(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<List<Integer>>() {
                    @Override
                    public boolean test(List<Integer> integers) throws Exception {
                        return integers.size() >= 3;
                    }
                });
    }

    public static Observable<Integer> clickDebounceStream(View view) {
        Observable<Integer> clickStreamObservable = Observable.create(new ClickStream(view));
        return clickStreamObservable.debounce(800, TimeUnit.MILLISECONDS);
    }

    public static class ClickStream implements ObservableOnSubscribe<Integer> {

        private View mView;

        public ClickStream(View view) {
            mView = view;
        }

        @Override
        public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emitter.onNext(1);
                }
            });
        }
    }

}
