package it.unibo.oop.myworkoutbuddy.view;

/**
 * It returns the references to the views of the application.
 */
public interface AppViews {

    /**
     * @return reference to access view.
     */
    AccessView getAccessView();

    /**
     * @return reference to CreateRoutine view.
     */
    CreateRoutineView getCreateRoutineView();

    /**
     * @return reference to Registration view.
     */
    RegistrationView getRegistrationView();

    /**
     * @return reference to SelectRoutine view.
     */
    SelectRoutineView getSelectRoutineView();

    /**
     * @return reference to UserSettings view.
     */
    UserSettingsView getUserSettingsView();

    /**
     * @param observer
     *            controller of the views.
     */
    void setViewsObserver(ViewsObserver observer);

}
