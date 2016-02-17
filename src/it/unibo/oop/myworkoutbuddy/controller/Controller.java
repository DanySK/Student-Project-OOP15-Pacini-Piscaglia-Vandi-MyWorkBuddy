package it.unibo.oop.myworkoutbuddy.controller;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.oop.myworkoutbuddy.controller.db.MongoService;
import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModel;
import it.unibo.oop.myworkoutbuddy.model.User;
import it.unibo.oop.myworkoutbuddy.util.Preconditions;
import it.unibo.oop.myworkoutbuddy.util.Triple;
import it.unibo.oop.myworkoutbuddy.util.UnmodifiableTriple;
import it.unibo.oop.myworkoutbuddy.view.AppViews;
import it.unibo.oop.myworkoutbuddy.view.ViewsObserver;

public class Controller implements ViewsObserver {

    private final MyWorkoutBuddyModel model;
    private final AppViews views;

    private final Map<String, Service> services;

    private boolean servicesInitialized;

    public Controller(final MyWorkoutBuddyModel model, final AppViews views) {
        // this.model = requireNonNull(model); this is the right one
        this.model = model; // Just for testing (to remove in future)
        this.views = requireNonNull(views);
        views.setViewsObserver(this);
        services = Collections.unmodifiableMap(initializeServices());
    }

    @Override
    public boolean loginUser() {
        // checkIfUserIsNotLoggedIn();
        final Map<String, Object> loginTry = new HashMap<>();
        loginTry.put("username", views.getAccessView().getUsername());
        final Optional<Map<String, Object>> user = services.get("userDataService")
                .getOneByParams(loginTry);
        if (user.isPresent()) {
            // TODO: use some password hashing library!
            final String password = views.getAccessView().getPassword();
            if (user.get().get("password").equals(views.getAccessView().getPassword())) {
                model.loginUser((String) loginTry.get("username"), password);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean registerUser() {
        // checkIfUserIsNotLoggedIn();
        final Map<String, Object> userData = new HashMap<>();
        userData.put("username", views.getRegistrationView().getUsername());
        userData.put("password", views.getRegistrationView().getPassword());
        userData.put("firstName", views.getRegistrationView().getName());
        userData.put("lastName", views.getRegistrationView().getSurname());
        userData.put("email", views.getRegistrationView().getEmail());
        userData.put("height", views.getRegistrationView().getHeight());
        userData.put("weight", views.getRegistrationView().getWeight());
        userData.put("age", views.getRegistrationView().getAge());
        userData.put("routinesId", new ArrayList<>());
        return services.get("userDataService").create(userData);
    }

    @Override
    public void setFavouriteTheme(final String selectedTheme) {
        final Map<String, Object> updateParams = new HashMap<>();
        updateParams.put("favouriteTheme", requireNonNull(selectedTheme));
        services.get("userDataService").updateByParams(currentUserUsernameAsQueryParam(), updateParams);
    }

    @Override
    public String getFavouriteTheme() {
        final User loggedInUser = getLoggedInUser();
        final Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("favouriteTheme", loggedInUser.getAccount().getUserName());
        final Optional<Map<String, Object>> user = services.get("userDataService")
                .getOneByParams(queryParams);
        return (String) (user.isPresent() ? user.get().get("favouriteTheme") : null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Set<String>> getExercises() {
        return services.get("exerciseService").getAll().stream()
                .collect(Collectors.toMap(
                        m -> (String) m.get("name"),
                        m -> ((List<Object>) m.get("exerciseType")).stream()
                                .map(o -> (String) o)
                                .collect(Collectors.toSet())));
    }

    @Override
    public void saveRoutine() {
        final User loggedInUser = getLoggedInUser();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Triple<Integer, String, Map<String, Map<String, List<Integer>>>>> getRoutines() {
        final Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("id", currentUserUsernameAsQueryParam());
        final List<Map<String, Object>> routinesFromDB = services.get("routineService").getByParams(queryParams);
        Set<Triple<Integer, String, Map<String, Map<String, List<Integer>>>>> routines = new HashSet<>();
        routinesFromDB.forEach(r -> {
            final Map<String, Map<String, List<Integer>>> workouts = new HashMap<>();
            final List<Map<String, Object>> workoutsFromDB = (List<Map<String, Object>>) r.get("workouts");
            workoutsFromDB.forEach(w -> {
                final List<Map<String, Object>> exercisesFromDB = (List<Map<String, Object>>) w.get("exercises");
                final Map<String, List<Integer>> exercises = new HashMap<>();
                exercisesFromDB.forEach(e -> {
                    exercises.put((String) e.get("exerciseName"), ((List<Object>) e.get("sets")).stream()
                            .map(Object::toString)
                            .map(Integer::valueOf)
                            .collect(Collectors.toList()));
                });
                workouts.put((String) w.get("name"), exercises);
            });
            routines.add(new UnmodifiableTriple<>(
                    (int) r.get("routineIndex"),
                    (String) r.get("description"),
                    workouts));
        });
        return routines;
    }

    @Override
    public Map<String, Map<String, Number>> getChartsData() {
        final Map<String, Map<String, Number>> chartsData = new HashMap<>();
        chartsData.put("pieChartData", getChartData("pieChartData"));
        chartsData.put("lineChartData", getChartData("pieChartData"));
        return chartsData;
    }

    @Override
    public Map<String, Object> getUserData() {
        return services.get("userDataService")
                .getOneByParams(currentUserUsernameAsQueryParam())
                .orElse(null);
    }

    @Override
    public boolean setUserData() {
        final Map<String, Object> newUserData = new HashMap<>();
        newUserData.put("password", views.getUserSettingsView().getNewPassword());
        newUserData.put("firstName", views.getUserSettingsView().getNewName());
        newUserData.put("lastName", views.getUserSettingsView().getNewSurname());
        newUserData.put("email", views.getUserSettingsView().getNewEmail());
        return services.get("userDataService").updateByParams(currentUserUsernameAsQueryParam(), newUserData) > 0;
    }

    private Map<String, Service> initializeServices() {
        Preconditions.checkState(!servicesInitialized, "Services already initialized");
        final Map<String, Service> services = new HashMap<>();
        services.put("exerciseService", new MongoService("exercises"));
        services.put("routineService", new MongoService("routines"));
        services.put("chartService", new MongoService("charts"));
        services.put("userDataService", new MongoService("usersData"));
        servicesInitialized = true;
        return services;
    }

    private Map<String, Object> currentUserUsernameAsQueryParam() {
        final User loggedInUser = getLoggedInUser();
        final Map<String, Object> username = new HashMap<>();
        username.put("username", loggedInUser.getAccount().getUserName());
        return username;
    }

    private User getLoggedInUser() {
        final Optional<User> loggedInUser = model.getLoginUser();
        Preconditions.checkState(loggedInUser.isPresent());
        return loggedInUser.get();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Number> getChartData(final String chartName) {
        final Map<String, Number> chartData = new HashMap<>();
        final Optional<Map<String, Object>> cdFromDatabase = services.get("chartService")
                .getByParams(currentUserUsernameAsQueryParam())
                .stream().findFirst();
        ((List<Map<String, Object>>) cdFromDatabase.get().get(chartName)).forEach(m -> {
            chartData.put((String) m.get("bodyPart"), (Number) m.get("times"));
        });
        return chartData;
    }

    private void checkIfUserIsNotLoggedIn() {
        Preconditions.checkState(!model.getLoginUser().isPresent());
    }

}
