package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.getHandler;

import java.util.Objects;

import it.unibo.oop.myworkoutbuddy.view.AccessView;
import it.unibo.oop.myworkoutbuddy.view.AppView;
import it.unibo.oop.myworkoutbuddy.view.CreateRoutineView;
import it.unibo.oop.myworkoutbuddy.view.RegistrationView;
import it.unibo.oop.myworkoutbuddy.view.SelectRoutineView;
import it.unibo.oop.myworkoutbuddy.view.UserSettingsView;
import it.unibo.oop.myworkoutbuddy.view.ViewObserver;

/**
 * 
 * All views of the application to pass to Controller.
 *
 */
public final class ViewHandler implements AppView {

    private static ViewObserver observer;

    @Override
    public AccessView getAccessView() {
        return getHandler();
    }

    @Override
    public CreateRoutineView getCreateRoutineView() {
        return getHandler();
    }

    @Override
    public RegistrationView getRegistrationView() {
        return getHandler();
    }

    @Override
    public SelectRoutineView getSelectRoutineView() {
        return getHandler();
    }

    @Override
    public UserSettingsView getUserSettingsView() {
        return getHandler();
    }

    @Override
    public void setViewsObserver(final ViewObserver viewObserver) {
        observer = Objects.requireNonNull(viewObserver);
    }

    /**
     * Allows each GUI to get Controller reference.
     * 
     * @return view observer.
     */
    public static ViewObserver getObserver() {
        return observer;
    }

}
