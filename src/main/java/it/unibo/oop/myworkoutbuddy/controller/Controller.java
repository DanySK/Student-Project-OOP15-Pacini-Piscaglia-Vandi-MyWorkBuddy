package it.unibo.oop.myworkoutbuddy.controller;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.EmailValidator;

import it.unibo.oop.myworkoutbuddy.controller.db.MongoService;
import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModel;
import it.unibo.oop.myworkoutbuddy.util.Preconditions;
import it.unibo.oop.myworkoutbuddy.util.Triple;
import it.unibo.oop.myworkoutbuddy.util.UnmodifiableTriple;
import it.unibo.oop.myworkoutbuddy.view.AppViews;
import it.unibo.oop.myworkoutbuddy.view.ViewsObserver;

/**
 * Controller.
 */
public class Controller implements ViewsObserver {

    private static final Map<String, Service> SERVICES;

    private final MyWorkoutBuddyModel model;
    private final AppViews views;

    // TODO: remove
    private Optional<String> currentUser;

    /**
     * Constructs a new controller instance.
     * 
     * @param model
     *            The reference to the model
     * @param views
     *            The refernce to the views
     */
    public Controller(final MyWorkoutBuddyModel model, final AppViews views) {
        this.model = requireNonNull(model);
        this.views = requireNonNull(views);
        views.setViewsObserver(this);
        currentUser = Optional.empty();
    }

    @Override
    public boolean loginUser() {
        checkIfUserIsNotLoggedIn();
        final Map<String, Object> loginTry = new HashMap<>();
        final String username = views.getAccessView().getUsername();
        loginTry.put("username", username);
        final Optional<Map<String, Object>> user = SERVICES.get("userService")
                .getOneByParams(loginTry);
        if (user.isPresent()) {
            // TODO: use some password hashing library!
            final String password = views.getAccessView().getPassword();
            if (user.get().get("password").equals(password)) {
                currentUser = Optional.of(username);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean registerUser() {
        checkIfUserIsNotLoggedIn();
        // Fields to validate
        final String username = views.getRegistrationView().getUsername();
        // TODO: use some password hashing library!
        final String password = views.getRegistrationView().getPassword();
        final String passwordConfirm = views.getRegistrationView().getPasswordConfirm();
        final String firstName = views.getRegistrationView().getName();
        final String lastName = views.getRegistrationView().getSurname();
        final String email = views.getRegistrationView().getEmail();
        final int age = views.getRegistrationView().getAge();
        final int height = views.getRegistrationView().getHeight();
        final double weight = views.getRegistrationView().getWeight();
        if (validate(username, usernameValidator()) && validate(firstName, nameValidator())
                && validate(lastName, nameValidator()) && validate(weight, numberValidator())
                && validate(email, emailValidator(true)) && validate(height, numberValidator())
                && validate(password, passwordValidator(passwordConfirm)) && validate(age, numberValidator())) {
            System.out.println("Validation passed");
            final Map<String, Object> newUser = new HashMap<>();
            newUser.put("username", username);
            newUser.put("password", password);
            newUser.put("name", firstName);
            newUser.put("surname", lastName);
            newUser.put("email", email);
            newUser.put("height", height);
            newUser.put("weight", weight);
            newUser.put("age", age);
            newUser.put("favouriteTheme", "");
            return SERVICES.get("userService").create(newUser);
        }
        return false;
    }

    @Override
    public void logoutUser() {
        currentUser = Optional.empty();
    }

    @Override
    public void setFavouriteTheme(final String selectedTheme) {
        final Map<String, Object> updateParams = new HashMap<>();
        updateParams.put("favouriteTheme", requireNonNull(selectedTheme));
        SERVICES.get("userService").updateByParams(currentUserUsernameAsQueryParam(), updateParams);
    }

    @Override
    public String getFavouriteTheme() {
        final StringBuilder sb = new StringBuilder("");
        SERVICES.get("userService")
                .getOneByParams(currentUserUsernameAsQueryParam())
                .ifPresent(u -> sb.append(u.get("favouriteTheme")));
        return sb.toString();
    }

    @Override
    public Map<String, Set<String>> getExercises() {
        final Map<String, Set<String>> exercises = new HashMap<>();
        SERVICES.get("exerciseService").getAll().forEach(m -> {
            final Set<String> s = new HashSet<>();
            s.add((String) m.get("name"));
            exercises.merge((String) m.get("mainTarget"), s, (o, n) -> {
                o.addAll(n);
                return o;
            });
        });
        return exercises;
    }

    @Override
    public List<String> getExerciseInfo(final String exerciseName) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", exerciseName);
        final Map<String, String> exerciseInfo = new HashMap<>();
        SERVICES.get("exerciseService").getOneByParams(params).ifPresent(m -> {
            exerciseInfo.put("description", (String) m.get("description"));
            exerciseInfo.put("videoURL", (String) m.get("videoURL"));
        });
        // return exerciseInfo; (to use)
        return null;
    }

    @Override
    public boolean saveRoutine() {
        final List<Map<String, Object>> workouts = views.getCreateRoutineView().getRoutine().entrySet().stream()
                .map(w -> {
                    final Map<String, Object> workout = new HashMap<>();
                    workout.put("name", w.getKey());
                    workout.put("exercises", w.getValue().entrySet().stream()
                            .map(e -> {
                        final Map<String, Object> exercise = new HashMap<>();
                        exercise.put("exerciseName", e.getKey());
                        exercise.put("repetitions", e.getValue());
                        return exercise;
                    })
                            .collect(Collectors.toList()));
                    return workout;
                })
                .collect(Collectors.toList());
        final Map<String, Object> routine = new HashMap<>();
        routine.put("username", currentUser.get());
        routine.put("routineIndex", SERVICES.get("routineService").getAll().stream()
                .mapToInt(m -> (int) m.get("routineIndex"))
                .max()
                .orElse(0) + 1);
        routine.put("description", views.getCreateRoutineView().getRoutineDescription());
        routine.put("workouts", workouts);
        return SERVICES.get("routineService").create(routine);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Triple<Integer, String, Map<String, Map<String, List<Integer>>>>> getRoutines() {
        final Set<Triple<Integer, String, Map<String, Map<String, List<Integer>>>>> routines = new HashSet<>();
        SERVICES.get("routineService")
                .getByParams(currentUserUsernameAsQueryParam()).forEach(r -> {
                    final Map<String, Map<String, List<Integer>>> workouts = new HashMap<>();
                    ((List<Map<String, Object>>) r.get("workouts")).forEach(w -> {
                        final Map<String, List<Integer>> exercises = new HashMap<>();
                        ((List<Map<String, Object>>) w.get("exercises")).forEach(e -> {
                            exercises.put(
                                    (String) e.get("exerciseName"),
                                    ((List<Object>) e.get("repetitions")).stream()
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
    public boolean addResults() {
        // TODO
        return false;
    }

    @Override
    public boolean deleteRoutine() {
        final int routineIndex = views.getSelectRoutineView().getRoutineIndex();
        final Map<String, Object> deleteParams = currentUserUsernameAsQueryParam();
        deleteParams.put("routineIndex", routineIndex);
        return SERVICES.get("routineService").deleteByParams(deleteParams) > 0;
    }

    @Override
    public Map<String, Map<String, Number>> getChartsData() {
        final Map<String, Map<String, Number>> chartsData = new HashMap<>();
        chartsData.put("pie", getChartData("pieChartData"));
        chartsData.put("line", getChartData("pieChartData"));
        return chartsData;
    }

    @Override
    public Map<String, Object> getUserData() {
        return SERVICES.get("userService")
                .getOneByParams(currentUserUsernameAsQueryParam())
                .get();
    }

    @Override
    public boolean setUserData() {
        final String newFirstName = views.getUserSettingsView().getNewName();
        final String newLastName = views.getUserSettingsView().getNewSurname();
        final String newPassword = views.getUserSettingsView().getNewPassword();
        final int newAge = views.getUserSettingsView().getNewAge();
        final String passwordConfirm = views.getUserSettingsView().getPasswordConfirm();
        final String newEmail = views.getUserSettingsView().getNewEmail();
        if (validate(newFirstName, nameValidator())
                && validate(newLastName, nameValidator())
                && validate(newAge, numberValidator())
                && validate(newEmail, emailValidator(false))
                && validate(newPassword, passwordValidator(passwordConfirm))) {
            final Map<String, Object> newUserData = new HashMap<>();
            newUserData.put("password", newPassword);
            newUserData.put("name", newFirstName);
            newUserData.put("surname", newLastName);
            newUserData.put("email", newEmail);
            newUserData.put("age", newAge);
            return SERVICES.get("userService").updateByParams(currentUserUsernameAsQueryParam(), newUserData) >= 0;
        }
        return false;
    }

    @Override
    public double getBMI() {
        // TODO
        return 0;
    }

    @Override
    public int getBMR() {
        // TODO
        return 0;
    }

    @Override
    public double getLBM() {
        // TODO
        return 0;
    }

    @Override
    public double getFMI() {
        // TODO
        return 0;
    }

    @Override
    public Map<String, Number> getIndexes() {
        // TODO
        return null;
    }

    private static <T> boolean validate(final T t, final Predicate<T> validator) {
        return validator.test(t);
    }

    private static boolean checkIfUserExists(final String username) {
        final Map<String, Object> param = new HashMap<>();
        param.put("username", username);
        return SERVICES.get("userService").getOneByParams(param).isPresent();
    }

    private static boolean checkIfEmailExists(final String email) {
        final Map<String, Object> param = new HashMap<>();
        param.put("email", email);
        return SERVICES.get("userService").getOneByParams(param).isPresent();
    }

    // Validation strategies
    private static Predicate<String> usernameValidator() {
        final int minUsernameLength = 8;
        final int maxUsernameLength = 15;
        return u -> !isNull(u) && (u.length() >= minUsernameLength
                && u.length() <= maxUsernameLength) && !checkIfUserExists(u);
    }

    private static Predicate<String> emailValidator(final boolean checkInDatabase) {
        return e -> !isNull(e) && EmailValidator.getInstance().isValid(e) && checkInDatabase
                ? !checkIfEmailExists(e) : true;
    }

    private static Predicate<String> nameValidator() {
        return n -> !isNull(n) && n.length() > 0;
    }

    private static Predicate<Number> numberValidator() {
        return n -> !isNull(n) && Double.compare(n.doubleValue(), 0) > 0;
    }

    private static Predicate<String> passwordValidator(final String other) {
        final int minPasswordLength = 6;
        return p -> !isNull(p) && p.length() > minPasswordLength && p.equals(other);
    }

    private void checkIfUserIsNotLoggedIn() {
        Preconditions.checkState(!currentUser.isPresent());
    }

    private Map<String, Object> currentUserUsernameAsQueryParam() {
        final Map<String, Object> username = new HashMap<>();
        username.put("username", currentUser.get());
        return username;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Number> getChartData(final String chartName) {
        final Optional<Map<String, Object>> chartData = SERVICES.get("chartService")
                .getByParams(currentUserUsernameAsQueryParam())
                .stream().findFirst();
        return chartData.isPresent()
                ? ((List<Map<String, Object>>) chartData.get().get(chartName))
                        .stream()
                        .map(m -> new SimpleImmutableEntry<>((String) m.get("bodyPart"), (Number) m.get("times")))
                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
                : Collections.emptyMap();
    }

    static {
        final Map<String, Service> services = new HashMap<>();
        services.put("exerciseService", new MongoService("exercises"));
        services.put("routineService", new MongoService("routines"));
        services.put("chartService", new MongoService("charts"));
        services.put("userService", new MongoService("users"));
        SERVICES = Collections.unmodifiableMap(services);
    }

}
