package com.jmricop.weatherapp.interactor;

public interface MainInteractor {

    interface MainView {
        void showAlert();
        void showProgressDialog();
        void dismissProgressDialog();
    }

    interface MainPresenter {
        void setVista(MainView vista);
        void searchCity(String name);
    }
}
