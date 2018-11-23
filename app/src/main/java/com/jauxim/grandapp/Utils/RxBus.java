package com.jauxim.grandapp.Utils;

import rx.Observable;
import rx.subjects.PublishSubject;

public class RxBus {

    private static RxBus instance;

    private PublishSubject<String> subject = PublishSubject.create();
    private PublishSubject<SingleShotLocationProvider.GPSCoordinates> subjectLocation = PublishSubject.create();

    public static RxBus instanceOf() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    public void setString(String string) {
        subject.onNext(string);
    }

    public Observable<String> getStringObservable() {
        return subject;
    }

    public void setLocation(SingleShotLocationProvider.GPSCoordinates location) {
        subjectLocation.onNext(location);
    }

    public Observable<SingleShotLocationProvider.GPSCoordinates> getLocationObservable() {
        return subjectLocation;
    }

}