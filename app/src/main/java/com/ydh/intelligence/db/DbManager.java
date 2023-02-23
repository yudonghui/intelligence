package com.ydh.intelligence.db;

import android.annotation.SuppressLint;

import com.ydh.intelligence.BaseApplication;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ydh on 2022/9/21
 */
public class DbManager {
    private static DbManager manager;

    public static DbManager getInstance() {
        if (manager == null)
            return new DbManager();
        else return manager;
    }

    @SuppressLint("CheckResult")
    public void queryAll(final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<List<ClickEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ClickEntity>> emitter) throws Exception {
                try {
                    List<ClickEntity> clickEntities = ClickDatabase.getInstance(BaseApplication.getContext())
                            .getClickDao()
                            .queryAll();
                    emitter.onNext(clickEntities);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new Observer<List<ClickEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ClickEntity> connectEntitie) {
                        mListener.success(connectEntitie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void queryByTime(final Long creatTime, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<List<ClickEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ClickEntity>> emitter) throws Exception {
                try {
                    List<ClickEntity> clickEntities = ClickDatabase.getInstance(BaseApplication.getContext())
                            .getClickDao()
                            .queryByTime(creatTime);
                    emitter.onNext(clickEntities);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new Observer<List<ClickEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ClickEntity> connectEntitie) {
                        mListener.success(connectEntitie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void insert(final ClickEntity clickEntity, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ClickDatabase.getInstance(BaseApplication.getContext())
                            .getClickDao()
                            .insert(clickEntity);
                    emitter.onNext("添加成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void insert(final List<ClickEntity> clickEntity, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ClickDatabase.getInstance(BaseApplication.getContext())
                            .getClickDao()
                            .insert(clickEntity);
                    emitter.onNext("添加成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void update(final ClickEntity clickEntity, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ClickDatabase.getInstance(BaseApplication.getContext())
                            .getClickDao()
                            .update(clickEntity);
                    emitter.onNext("更新成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void delete(final Long creatTime, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ClickDatabase.getInstance(BaseApplication.getContext())
                            .getClickDao()
                            .delete(creatTime);
                    emitter.onNext("删除成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void insertHistory(final HistoryEntity historyEntity, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ClickDatabase.getInstance(BaseApplication.getContext())
                            .getHistoryDao()
                            .insert(historyEntity);
                    emitter.onNext("添加成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void queryAllHis(final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<List<HistoryEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HistoryEntity>> emitter) throws Exception {
                try {
                    List<HistoryEntity> clickEntities = ClickDatabase.getInstance(BaseApplication.getContext())
                            .getHistoryDao()
                            .queryAll();
                    emitter.onNext(clickEntities);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new Observer<List<HistoryEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<HistoryEntity> connectEntitie) {
                        mListener.success(connectEntitie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void update(final HistoryEntity clickEntity, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ClickDatabase.getInstance(BaseApplication.getContext())
                            .getHistoryDao()
                            .update(clickEntity);
                    emitter.onNext("更新成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 自动化操作
     */
    @SuppressLint("CheckResult")
    public void queryByTimeAction(final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<List<ActionEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ActionEntity>> emitter) throws Exception {
                try {
                    List<ActionEntity> actionentitys = ClickDatabase.getInstance(BaseApplication.getContext())
                            .getActionDao()
                            .queryAll();
                    emitter.onNext(actionentitys);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new Observer<List<ActionEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ActionEntity> actionentitys) {
                        mListener.success(actionentitys);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 自动化操作
     */
    @SuppressLint("CheckResult")
    public void insertAction(final List<ActionEntity> actionEntities, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ClickDatabase.getInstance(BaseApplication.getContext())
                            .getActionDao()
                            .insert(actionEntities);
                    emitter.onNext("添加成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 自动化操作
     */
    @SuppressLint("CheckResult")
    public void updateAction(final ActionEntity actionEntity, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ClickDatabase.getInstance(BaseApplication.getContext())
                            .getActionDao()
                            .update(actionEntity);
                    emitter.onNext("更新成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 自动化操作
     */
    @SuppressLint("CheckResult")
    public void deleteAction(final Long creatTime, final DbInterface mListener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    ClickDatabase.getInstance(BaseApplication.getContext())
                            .getActionDao()
                            .delete(creatTime);
                    emitter.onNext("删除成功");
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mListener.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
