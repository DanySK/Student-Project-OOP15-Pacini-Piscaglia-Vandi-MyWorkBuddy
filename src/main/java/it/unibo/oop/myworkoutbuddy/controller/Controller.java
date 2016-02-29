package it.unibo.oop.myworkoutbuddy.controller;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.validator.routines.EmailValidator;

import it.unibo.oop.myworkoutbuddy.controller.db.MongoService;
import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModel;
import it.unibo.oop.myworkoutbuddy.util.DateConverter;
import it.unibo.oop.myworkoutbuddy.util.DateFormats;
import it.unibo.oop.myworkoutbuddy.view.AppView;
import it.unibo.oop.myworkoutbuddy.view.ViewObserver;

/**
 * Controller.
 */
public final class Controller implements ViewObserver {

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
        getService(DBCollectionName.GYM_TOOLS)
                .getAll()
                .forEach(m -> {
                    model.addGymTool(
                            (String) m.get("description"),
                            (String) m.get("name"),
                            1, // Not used at the moment
                            1, // Not used at the moment
                            10); // Not used at the moment
                });
    }

    @Override
    public List<String> loginUser() {
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
            final int age = (int) user.get("age");
            model.addAccount(username, password);
            model.addUser(firstName, lastName, age, email);
            model.loginUser(username, password);
            model.resetBody();
            addCurrentUserResults();
            final Optional<Boolean> firstTime = Optional.of(true);
            getService(DBCollectionName.MEASURES)
                    .getByParams(newParameter("username", username))
                    .forEach(m -> {
                final double height = (double) m.get("height");
                final double weight = (double) m.get("weight");
                final Date date = DateFormats.parseUTC((String) m.get("date"));
                System.out.println(height + " " + weight + " " + firstTime.get());
                model.addDataMeasure(DateConverter.dateToLocalDate(date));
                model.addBodyMeasure("HEIGHT", height, firstTime.get());
                model.addBodyMeasure("WEIGHT", weight, firstTime.get());
                firstTime.map(b -> false);
            });
        });
        return validator.getErrorMessages();
    }

    @Override
    public List<String> registerUser() {
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
            final Map<String, Object> newUser = newParameter("username", username);
            newUser.put("password", password);
            newUser.put("name", firstName);
            newUser.put("surname", lastName);
            newUser.put("email", email);
            newUser.put("age", age);
            getService(DBCollectionName.USERS).create(newUser);
            final Map<String, Object> newMeasure = newParameter("username", username);
            newMeasure.put("date", DateFormats.toUTCString(new Date()));
            newMeasure.put("height", height / 100.0);
            newMeasure.put("weight", weight);
            getService(DBCollectionName.MEASURES).create(newMeasure);
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
                .addValidation(passwordMinLengthValidator(MIN_PASSWORD_LENGTH), newPassword,
                        "The password must contain at least " + MIN_PASSWORD_LENGTH + " characters")
                .addValidation(passwordConfirmValidator(newPasswordasswordConfirm), newPassword,
                        "The two passwords do not match")
                .addValidation(emailAlreadyTakenValidator().or(e -> getCurrentUserData().get("email").equals(e)),
                        newEmail, "Email already taken");
        validator.validate();
        validator.ifValid(() -> {
            // Update the current user data
            final Map<String, Object> newUserData = newParameter("password", newPassword);
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
    public Map<String, String> getExerciseInfo(final String exerciseName) {
        final Map<String, Object> params = newParameter("name", exerciseName);
        final Map<String, String> exerciseInfo = new HashMap<>();
        getService(DBCollectionName.EXERCISES)
                .getOneByParams(params)
                .ifPresent(m -> {
                    exerciseInfo.put("description", (String) m.get("description"));
                    exerciseInfo.put("videoURL", (String) m.get("videoURL"));
                });
        return exerciseInfo;
    }

    @Override
    public boolean saveRoutine() {
        final Map<String, Object> routine = currentUsernameAsQueryParams();
        final Service routines = getService(DBCollectionName.ROUTINES);
        routine.put("name", view.getCreateRoutineView().getRoutineName());
        routine.put("description", view.getCreateRoutineView().getRoutineDescription());
        routine.put("routineId", routines.getAll().stream()
                .mapToInt(m -> (int) m.get("routineId"))
                .max()
                .orElse(0) + 1);
        try {
            final List<Map<String, Object>> workouts = view.getCreateRoutineView()
                    .getRoutine().entrySet().stream()
                    .map(w -> {
                        final Map<String, Object> workout = newParameter("name", w.getKey());
                        workout.put("exercises", w.getValue().entrySet().stream()
                                .map(e -> {
                            final Map<String, Object> exercise = newParameter("exerciseName", e.getKey());
                            exercise.put("repetitions", e.getValue());
                            return exercise;
                        })
                                .collect(Collectors.toList()));
                        return workout;
                    })
                    .collect(Collectors.toList());
            routine.put("workouts", workouts);
            return routines.create(routine);
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Triple<String, String, Map<String, Map<String, List<Integer>>>>> getRoutines() {
        final Set<Triple<String, String, Map<String, Map<String, List<Integer>>>>> routines = new HashSet<>();
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
                            (String) r.get("name"),
                            (String) r.get("description"),
                            workouts));
                });
        return routines;
    }

    @Override
    public boolean addResults() {
        final Map<String, List<Pair<String, Pair<List<Integer>, Integer>>>> userResults = view
                .getSelectRoutineView()
                .getUserResults();
        userResults.entrySet().stream()
                .map(e1 -> {
                    return e1.getValue().stream()
                            .map(e2 -> {
                        final Map<String, Object> result = currentUsernameAsQueryParams();
                        final String workoutName = e1.getKey();
                        result.put("workoutName", workoutName);
                        result.put("exerciseName", e2.getKey());
                        final Pair<List<Integer>, Integer> v = e2.getValue();
                        final List<Integer> repetitions = v.getLeft();
                        final int weight = v.getRight();
                        result.put("repetitions", repetitions);
                        result.put("weight", weight);
                        final Date date = new Date();
                        result.put("date", DateFormats.toUTCString(date));

                        final Map<String, Object> params = currentUsernameAsQueryParams();
                        params.put("name", view.getSelectRoutineView().getSelectedRoutine());
                        final int routineId = getService(DBCollectionName.ROUTINES)
                                .getOneByParams(params)
                                .map(m -> (int) m.get("routineId"))
                                .get();
                        result.put("routineId", routineId);
                        model.addRoutine(routineId, workoutName, DateConverter.dateToLocalDate(date));
                        model.addExerciseValue(repetitions.stream()
                                .map(i -> weight)
                                .collect(Collectors.toList()));
                        return result;
                    })
                            .collect(Collectors.toList());
                })
                .forEach(m -> getService(DBCollectionName.RESULTS).create(m));
        return true;
    }

    @Override
    public boolean updateWeight() {
        final Map<String, Object> newMeasure = new HashMap<>();
        final OptionalDouble newWeight = view.getSelectRoutineView().getWeight();
        newWeight.ifPresent(w -> {
            newMeasure.putAll(currentUsernameAsQueryParams());
            newMeasure.put("weight", w);
            final int height = (int) getService(DBCollectionName.MEASURES)
                    .getOneByParams(currentUsernameAsQueryParams())
                    .get().get("height");
            newMeasure.put("height", height);
            newMeasure.put("date", DateFormats.toUTCString(new Date()));
            model.addDataMeasure(LocalDate.now());
            model.addBodyMeasure("HEIGHT", (double) height, false);
            model.addBodyMeasure("WEIGHT", w, false);
        });
        return newMeasure.isEmpty() || getService(DBCollectionName.MEASURES).create(newMeasure);
    }

    @Override
    public boolean deleteRoutine() {
        final String routineName = view.getSelectRoutineView().getSelectedRoutine();
        final Map<String, Object> deleteParams = currentUsernameAsQueryParams();
        deleteParams.put("name", routineName);
        final Service routines = getService(DBCollectionName.ROUTINES);
        routines.getOneByParams(deleteParams)
                .map(m -> (int) m.get("routineId"))
                .ifPresent(model::removeRoutine);
        return routines.deleteByParams(deleteParams) > 0;
    }

    @Override
    public Map<String, Map<String, Number>> getChartsData() {
        final Map<String, Map<String, Number>> chartsData = new HashMap<>();
        final Map<String, Number> weightChart = getService(DBCollectionName.MEASURES)
                .getByParams(currentUsernameAsQueryParams())
                .stream()
                .map(m -> new SimpleImmutableEntry<>(
                        (String) m.get("date"),
                        (double) m.get("weight")))
                .sorted((e1, e2) -> {
                    final Date p = DateFormats.parseUTC(e1.getKey());
                    final Date q = DateFormats.parseUTC(e2.getKey());
                    return p.before(q) ? -1
                            : p.after(q) ? 1 : 0;
                })
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        chartsData.put("weightChart", weightChart);
        chartsData.put("timePerformanceChart", getChartData("pieChartData"));
        return chartsData;
    }

    @Override
    public Map<String, Number> getIndexes() {
        final Map<String, Number> indexes = new HashMap<>();
        final List<Double> bmi = model.trendBodyBMI(); // Current BMI
        final List<Double> bmr = model.trendBodyBMR(); // Current BMR

        System.out.println(bmi);
        System.out.println(bmr);
        indexes.put("BMI", bmi.get(bmi.size() - 1));
        indexes.put("BMR", bmr.get(bmr.size() - 1));
        return indexes;
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

    private String getCurrentUsername() {
        return model.getCurrentUserName().get();
    }

    /**
     * This method gets called in {@link Controller#loginUser}. Passes to the model all the routines and the workouts
     * that the current user has done.
     */
    @SuppressWarnings("unchecked")
    private void addCurrentUserResults() {
        final List<Map<String, Object>> results = getService(DBCollectionName.RESULTS)
                .getByParams(currentUsernameAsQueryParams());
        results.forEach(r -> { // A single result
            final String currentWorkoutName = (String) r.get("workoutName");
            model.addWorkout(currentWorkoutName, currentWorkoutName, ""); // Not used
            final Map<String, Object> exercise = getService(DBCollectionName.EXERCISES)
                    .getOneByParams(newParameter("name", r.get("exerciseName"))).get();
            model.addGymExcercise(
                    currentWorkoutName,
                    (String) exercise.get("exerciseGoal"),
                    (String) exercise.get("gymTool"),
                    (List<Integer>) r.get("repetitions"));
            if (!model.getWorkoutList().stream().anyMatch(w -> w.getCode().equals(currentWorkoutName))) {
                results.stream()
                        .filter(m -> m.get("workoutName").equals(currentWorkoutName))
                        .forEach(r2 -> {
                    final Date date = DateFormats.parseUTC((String) r2.get("date"));
                    final LocalDate when = DateConverter.dateToLocalDate(date);
                    model.addRoutine(
                            (int) r2.get("routineId"),
                            currentWorkoutName,
                            when);
                    final List<Integer> valueList = ((List<Integer>) r2.get("repetitions")).stream()
                            .map(d -> (int) r2.get("weight"))
                            .collect(Collectors.toList());
                    model.addExerciseValue(valueList);
                });
            }
        });
    }

    private static Map<String, Object> usernameAsQueryParam(final String username) {
        return newParameter("username", username);
    }

    private Map<String, Object> currentUsernameAsQueryParams() {
        return usernameAsQueryParam(getCurrentUsername());
    }

    private static boolean checkIfUserExists(final String username) {
        return getUserData(username).isPresent();
    }

    private static boolean checkIfEmailExists(final String email) {
        return getService(DBCollectionName.USERS)
                .getOneByParams(newParameter("email", email))
                .isPresent();
    }

    private static Optional<Map<String, Object>> getUserData(final String username) {
        return getService(DBCollectionName.USERS)
                .getOneByParams(usernameAsQueryParam(username));
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

    private static Map<String, Object> newParameter(final String name, final Object value) {
        final Map<String, Object> param = new HashMap<>();
        param.put(name, value);
        return param;
    }

    private static Service getService(final DBCollectionName service) {
        return SERVICES.get(service);
    }

    /**
     * An {@code enum} that represents all the collections used by this {@code Controller}.
     */
    private enum DBCollectionName {

        EXERCISES, ROUTINES, CHARTS, USERS, RESULTS, GYM_TOOLS, MEASURES;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

    }

    private static Service newService(final DBCollectionName collectionName) {
        return new MongoService(collectionName.toString());
    }

    private static final Map<DBCollectionName, Service> SERVICES;

    static {
        final Map<DBCollectionName, Service> services = new EnumMap<>(DBCollectionName.class);
        Arrays.stream(DBCollectionName.values())
                .forEach(n -> services.put(n, newService(n)));
        SERVICES = Collections.unmodifiableMap(services);
    }

}
