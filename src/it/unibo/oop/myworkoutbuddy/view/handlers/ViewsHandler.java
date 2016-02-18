package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.Objects;

import it.unibo.oop.myworkoutbuddy.view.AccessView;
import it.unibo.oop.myworkoutbuddy.view.AppViews;
import it.unibo.oop.myworkoutbuddy.view.CreateRoutineView;
import it.unibo.oop.myworkoutbuddy.view.RegistrationView;
import it.unibo.oop.myworkoutbuddy.view.SelectRoutineView;
import it.unibo.oop.myworkoutbuddy.view.UserSettingsView;
import it.unibo.oop.myworkoutbuddy.view.ViewsObserver;
import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.getHandler;

/**
 * 
 * All views of the application to pass to Controller.
 *
 */
public final class ViewsHandler implements AppViews {

    private static ViewsObserver observer;

    @Override
    public AccessView getAccessView() {
        return (AccessView) getHandler();
    }

    @Override
    public CreateRoutineView getCreateRoutineView() {
        return (CreateRoutineView) getHandler();
    }

    @Override
    public RegistrationView getRegistrationView() {
        return (RegistrationView) getHandler();
    }

    @Override
    public SelectRoutineView getSelectRoutineView() {
        return (SelectRoutineView) getHandler();
    }

    @Override
    public UserSettingsView getUserSettingsView() {
        return (UserSettingsView) getHandler();
    }

    @Override
    public void setViewsObserver(final ViewsObserver viewObserver) {
        observer = Objects.requireNonNull(viewObserver);
    }

    /**
     * Allows each GUI to get Controller reference.
     * 
     * @return view
     *         observer.
     */
    static ViewsObserver getObserver() {
        return observer;
    }

}
