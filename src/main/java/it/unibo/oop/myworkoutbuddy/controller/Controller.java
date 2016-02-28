package it.unibo.oop.myworkoutbuddy.controller;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.validator.routines.EmailValidator;

import it.unibo.oop.myworkoutbuddy.controller.db.MongoService;
import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModel;
import it.unibo.oop.myworkoutbuddy.view.AppView;
import it.unibo.oop.myworkoutbuddy.view.ViewObserver;

/**
 * Controller.
 */
public class Controller implements ViewObserver {

    private static final int MIN_USERNAME_LENGTH = 8;
    private static final int MAX_USERNAME_LENGTH = 15;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final MyWorkoutBuddyModel model;
    private final AppView view;

    /**
     * Constructs a new controller instance.
     * 
     * @param model
     *            The reference to the model
     * @param view
     *            The refernce to the views
     */
    public Controller(final MyWorkoutBuddyModel model, final AppView view) {
        this.model = requireNonNull(model);
        this.view = requireNonNull(view);
        view.setViewsObserver(this);
    }

    @Override
    public List<String> loginUser() {
        checkIfUserIsNotLoggedIn();
        final String username = view.getAccessView().getUsername();
        final String password = view.getAccessView().getPassword();
        final Optional<Map<String, Object>> optUserData = getUserData(username);
        if (!optUserData.isPresent()) {
            return Arrays.asList(username + " does not exist");
        }
        final Map<String, Object> user = optUserData.get();
        final Validator validator = new Validator()
                .addValidation(p -> user.get("password").equals(p), password, "Invalid password");
        validator.validate();
        // Login the user
        validator.ifValid(() -> {
            final String firstName = (String) user.get("name");
            final String lastName = (String) user.get("surname");
            final String email = (String) user.get("email");
            final Integer age = (int) user.get("age");
            // TODO: uncomment as soon as the model is ready
            // model.setPerson(firstName, lastName, email);
            //model.addAccount(username, password, "");
            model.addUser(firstName, lastName, age, email);
            model.loginUser(username, password);
        });
        return validator.getErrorMessages();
    }

    @Override
    public List<String> registerUser() {
        checkIfUserIsNotLoggedIn();
        // Fields to validate
        final String username = view.getRegistrationView().getUsername();
        final String password = view.getRegistrationView().getPassword();
        final String passwordConfirm = view.getRegistrationView().getPasswordConfirm();
        final String firstName = view.getRegistrationView().getName();
        final String lastName = view.getRegistrationView().getSurname();
        final String email = view.getRegistrationView().getEmail();
        final int age = view.getRegistrationView().getAge();
        final int height = view.getRegistrationView().getHeight();
        final double weight = view.getRegistrationView().getWeight();

        final Validator validator = new Validator()
                .addValidation(usernameMinLengthValidator(MIN_USERNAME_LENGTH), username,
                        "Username must contain at least " + MIN_USERNAME_LENGTH + " characters")
                .addValidation(usernameMaxLengthValidator(MAX_USERNAME_LENGTH), username,
                        "Username cannot contain more then " + MAX_USERNAME_LENGTH + " characters")
                .addValidation(usernameAlreadyTakenValidator(), username, "Username already taken")
                .addValidation(nameValidator(), firstName, "Invalid first name")
                .addValidation(nameValidator(), lastName, "Invalid last name")
                .addValidation(numberValidator(), age, "Invalid age")
                .addValidation(numberValidator(), weight, "Invalid weight")
                .addValidation(numberValidator(), height, "Invalid height")
                .addValidation(passwordMinLengthValidator(MIN_PASSWORD_LENGTH), password,
                        "Password must contain at least " + MIN_PASSWORD_LENGTH + " characters")
                .addValidation(passwordConfirmValidator(passwordConfirm), password, "The two passwords do not match")
                .addValidation(emailValidator(), email, "Invalid email or email already taken")
                .addValidation(emailAlreadyTakenValidator(), email, "Email already taken");
        validator.validate();
        validator.ifValid(() -> {
            // Add the new user in the database
            final Map<String, Object> newUser = new HashMap<>();
            newUser.put("username", username);
            newUser.put("password", password);
            newUser.put("name", firstName);
            newUser.put("surname", lastName);
            newUser.put("email", email);
            newUser.put("height", height);
            newUser.put("weight", weight);
            newUser.put("age", age);
            getService(DBCollectionName.USERS).create(newUser);
        });
        return validator.getErrorMessages();
    }

    @Override
    public void logoutUser() {
        model.logoutUser();
    }

    @Override
    public Map<String, Object> getCurrentUserData() {
        return getUserData(getCurrentUsername()).get();
    }

    @Override
    public List<String> setUserData() {
        final String newFirstName = view.getUserSettingsView().getNewName();
        final String newLastName = view.getUserSettingsView().getNewSurname();
        final String newPassword = view.getUserSettingsView().getNewPassword();
        final int newAge = view.getUserSettingsView().getNewAge();
        final String newPasswordasswordConfirm = view.getUserSettingsView().getPasswordConfirm();
        final String newEmail = view.getUserSettingsView().getNewEmail();

        final Validator validator = new Validator()
                .addValidation(nameValidator(), newFirstName, "Invalid first name")
                .addValidation(nameValidator(), newLastName, "Invalid last name")
                .addValidation(numberValidator(), newAge, "Invalid age")
                .addValidation(emailValidator(), newEmail, "Invalid email")
                .addValidation(passwordMinLengthValidator(MIN_PASSWORD_LENGTH), newPassword, "Invalid password")
                .addValidation(passwordConfirmValidator(newPasswordasswordConfirm), newPassword,
                        "The two passwords do not match")
                .addValidation(emailAlreadyTakenValidator().or(e -> getCurrentUserData().get("email").equals(e)),
                        newEmail, "Email already taken");
        validator.validate();
        validator.ifValid(() -> {
            // Update the current user data
            final Map<String, Object> newUserData = new HashMap<>();
            newUserData.put("password", newPassword);
            newUserData.put("name", newFirstName);
            newUserData.put("surname", newLastName);
            newUserData.put("email", newEmail);
            newUserData.put("age", newAge);
            getService(DBCollectionName.USERS).updateByParams(
                    currentUsernameAsQueryParams(),
                    newUserData);
        });
        return validator.getErrorMessages();
    }

    @Override
    public Map<String, Set<String>> getExercises() {
        final Map<String, Set<String>> exercises = new HashMap<>();
        getService(DBCollectionName.EXERCISES).getAll().forEach(m -> {
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
    public boolean saveRoutine() {
        final Map<String, Object> routine = new HashMap<>();
        final Service routines = getService(DBCollectionName.ROUTINES);
        routine.put("username", getCurrentUsername());
        routine.put("routineIndex", routines.getAll().stream()
                .mapToInt(m -> (int) m.get("routineIndex"))
                .max()
                .orElse(0) + 1);
        routine.put("description", view.getCreateRoutineView().getRoutineDescription());
        final List<Map<String, Object>> workouts = view.getCreateRoutineView()
                .getRoutine().entrySet().stream()
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
        routine.put("workouts", workouts);
        return routines.create(routine);
    }

    @Override
    public Map<String, String> getExerciseInfo(final String exerciseName) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", exerciseName);
        final Map<String, String> exerciseInfo = new HashMap<>();
        getService(DBCollectionName.EXERCISES)
                .getOneByParams(params)
                .ifPresent(m -> {
                    exerciseInfo.put("description", (String) m.get("description"));
                    exerciseInfo.put("videoURL", (String) m.get("videoURL"));
                });
        return exerciseInfo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Triple<Integer, String, Map<String, Map<String, List<Integer>>>>> getRoutines() {
        final Set<Triple<Integer, String, Map<String, Map<String, List<Integer>>>>> routines = new HashSet<>();
        getService(DBCollectionName.ROUTINES)
                .getByParams(currentUsernameAsQueryParams()).forEach(r -> {
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
                    routines.add(new ImmutableTriple<>(
                            (int) r.get("routineIndex"),
                            (String) r.get("description"),
                            workouts));
                });
        return routines;
    }

    @Override
    public boolean addResults() {
        final List<Pair<String, Pair<List<Integer>, Integer>>> userResults = view
                .getSelectRoutineView()
                .getUserResults();
        final List<Map<String, Object>> results = userResults.stream()
                .map(e -> {
                    final Map<String, Object> result = new HashMap<>();
                    result.put("username", getCurrentUsername());
                    result.put("exerciseName", e.getKey());
                    final Pair<List<Integer>, Integer> v = e.getValue();
                    result.put("repetitions", v.getLeft());
                    result.put("weight", v.getRight());
                    return result;
                })
                .collect(Collectors.toList());
        return getService(DBCollectionName.RESULTS)
                .create(results) >= 0;
    }

    @Override
    public boolean updateWeight() {
        return false;
    }

    @Override
    public boolean deleteRoutine() {
        final int routineIndex = view.getSelectRoutineView().getRoutineIndex();
        final Map<String, Object> deleteParams = currentUsernameAsQueryParams();
        deleteParams.put("routineIndex", routineIndex);
        return getService(DBCollectionName.ROUTINES).deleteByParams(deleteParams) > 0;
    }

    @Override
    public Map<String, Map<String, Number>> getChartsData() {
        final Map<String, Map<String, Number>> chartsData = new HashMap<>();
        chartsData.put("weightChart", getChartData("pieChartData"));
        chartsData.put("line", getChartData("pieChartData"));
        return chartsData;
    }

    @Override
    public Map<String, Number> getIndexes() {
        // TODO
        return null;
    }

    private void checkIfUserIsNotLoggedIn() {
        checkState(!getCurrentUser().isPresent());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Number> getChartData(final String chartName) {
        final Optional<Map<String, Object>> chartData = getService(DBCollectionName.CHARTS)
                .getByParams(currentUsernameAsQueryParams())
                .stream().findFirst();
        return chartData.isPresent()
                ? ((List<Map<String, Object>>) chartData.get().get(chartName))
                        .stream()
                        .map(m -> new SimpleImmutableEntry<>(
                                (String) m.get("bodyPart"),
                                (Number) m.get("times")))
                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
                : Collections.emptyMap();
    }

    private Optional<String> getCurrentUser() {
        return model.getCurrentNameAccount();
    }

    private String getCurrentUsername() {
        // return getCurrentUser().get().getAccount().getUsername();
        return getCurrentUser().get();
    }

    private static Map<String, Object> usernameAsQueryParam(final String username) {
        final Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return params;
    }

    private Map<String, Object> currentUsernameAsQueryParams() {
        return usernameAsQueryParam(getCurrentUsername());
    }

    private static boolean checkIfUserExists(final String username) {
        return getUserData(username).isPresent();
    }

    private static boolean checkIfEmailExists(final String email) {
        final Map<String, Object> param = new HashMap<>();
        param.put("email", email);
        return getService(DBCollectionName.USERS).getOneByParams(param).isPresent();
    }

    private static Optional<Map<String, Object>> getUserData(final String username) {
        return getService(DBCollectionName.USERS)
                .getOneByParams(usernameAsQueryParam(username));
    }

    private static Service getService(final DBCollectionName service) {
        return SERVICES.get(service);
    }

    // Validation strategies

    private static Predicate<String> usernameMinLengthValidator(final int minLength) {
        return u -> !isNull(u) && (u.length() >= minLength);
    }

    private static Predicate<String> usernameMaxLengthValidator(final int maxLength) {
        return u -> !isNull(u) && (u.length() <= maxLength);
    }

    private static Predicate<String> usernameAlreadyTakenValidator() {
        return u -> !checkIfUserExists(u);
    }

    private static Predicate<String> passwordMinLengthValidator(final int minLength) {
        return p -> !isNull(p) && p.length() >= minLength;
    }

    private static Predicate<String> passwordConfirmValidator(final String other) {
        return p -> p.equals(other);
    }

    private static Predicate<String> emailValidator() {
        return e -> !isNull(e) && EmailValidator.getInstance().isValid(e);
    }

    private static Predicate<String> emailAlreadyTakenValidator() {
        return e -> !checkIfEmailExists(e);
    }

    private static Predicate<String> nameValidator() {
        return n -> !isNull(n) && n.length() > 0;
    }

    private static Predicate<Number> numberValidator() {
        return n -> !isNull(n) && Double.compare(n.doubleValue(), 0) > 0;
    }

    /**
     * An {@code enum} that represents all the collections used by this {@code Controller}.
     */
    private enum DBCollectionName {

        EXERCISES, ROUTINES, CHARTS, USERS, RESULTS;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

    }

    private static final Map<DBCollectionName, Service> SERVICES;

    static {
        final Map<DBCollectionName, Service> services = new EnumMap<>(DBCollectionName.class);
        services.put(DBCollectionName.EXERCISES, new MongoService(DBCollectionName.EXERCISES.toString()));
        services.put(DBCollectionName.ROUTINES, new MongoService(DBCollectionName.ROUTINES.toString()));
        services.put(DBCollectionName.CHARTS, new MongoService(DBCollectionName.CHARTS.toString()));
        services.put(DBCollectionName.USERS, new MongoService(DBCollectionName.USERS.toString()));
        services.put(DBCollectionName.RESULTS, new MongoService(DBCollectionName.RESULTS.toString()));
        SERVICES = Collections.unmodifiableMap(services);
    }

}
