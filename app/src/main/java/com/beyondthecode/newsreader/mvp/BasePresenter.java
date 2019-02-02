package com.beyondthecode.newsreader.mvp;

public abstract class BasePresenter<V extends BaseView> {

    protected V view;

    public void subscribe(V view){
        this.view = view;
    }

    public void unsubscribe(){
        //drop the view reference
        view = null;
    }
}
