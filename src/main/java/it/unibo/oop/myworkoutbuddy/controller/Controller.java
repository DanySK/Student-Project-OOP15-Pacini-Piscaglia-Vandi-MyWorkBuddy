package it.unibo.oop.myworkoutbuddy.controller;

import static it.unibo.oop.myworkoutbuddy.controller.Service.BODY_ZONES;
import static it.unibo.oop.myworkoutbuddy.controller.Service.EXERCISES;
import static it.unibo.oop.myworkoutbuddy.controller.Service.GYM_TOOLS;
import static it.unibo.oop.myworkoutbuddy.controller.Service.MEASURES;
import static it.unibo.oop.myworkoutbuddy.controller.Service.RESULTS;
import static it.unibo.oop.myworkoutbuddy.controller.Service.ROUTINES;
import static it.unibo.oop.myworkoutbuddy.controller.Service.USERS;
import static it.unibo.oop.myworkoutbuddy.controller.validation.ValidationStrategies.alreadyTakenValidator;
import static it.unibo.oop.myworkoutbuddy.controller.validation.ValidationStrategies.confirmValidator;
import static it.unibo.oop.myworkoutbuddy.controller.validation.ValidationStrategies.emailValidator;
import static it.unibo.oop.myworkoutbuddy.controller.validation.ValidationStrategies.maxLengthValidator;
import static it.unibo.oop.myworkoutbuddy.controller.validation.ValidationStrategies.minLengthValidator;
import static it.unibo.oop.myworkoutbuddy.controller.validation.ValidationStrategies.positiveNumberValidator;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import it.unibo.oop.myworkoutbuddy.controller.db.DBService;
import it.unibo.oop.myworkoutbuddy.controller.validation.Validator;
import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModel;
import it.unibo.oop.myworkoutbuddy.util.DateConverter;
import it.unibo.oop.myworkoutbuddy.util.DateFormats;
import it.unibo.oop.myworkoutbuddy.view.AppView;
import it.unibo.oop.myworkoutbuddy.view.ViewObserver;

/**
 * The main controller of the application. Responsible for the Responsible for the communication between model and
 * the view application.
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
        GYM_TOOLS.getDBService()
                .getAll()
                .forEach(m -> {
                    model.addGymTool(
                            (String) m.get("name"),
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
                .addValidation(confirmValidator(user.get("password")), password, "Invalid password");
        validator.validate();
        // Login the user
        validator.ifValid(() -> {
            final String firstName = (String) user.get("name");
            final String lastName = (String) user.get("surname");
            final String email = (String) user.get("email");
            final int age = (int) user.get("age");
            model.addAccount(username, password);
            model.resetBody();
            model.addUser(firstName, lastName, age, email);
            model.loginUser(username, password);
            addCurrentUserMeasures();
            resetCurrentUserBody();
            addCurrentUserWorkouts();
            addCurrentUserResults();
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
                .addValidation(minLengthValidator(MIN_USERNAME_LENGTH), username,
                        "Username must contain at least " + MIN_USERNAME_LENGTH + " characters")
                .addValidation(maxLengthValidator(MAX_USERNAME_LENGTH), username,
                        "Username cannot contain more then " + MAX_USERNAME_LENGTH + " characters")
                .addValidation(alreadyTakenValidator(Service.USERS, "username"), username,
                        "Username already taken")
                .addValidation(minLengthValidator(1), firstName,
                        "Invalid first name")
                .addValidation(minLengthValidator(1), lastName,
                        "Invalid last name")
                .addValidation(positiveNumberValidator(), age,
                        "Invalid age")
                .addValidation(positiveNumberValidator(), weight,
                        "Invalid weight")
                .addValidation(positiveNumberValidator(), height,
                        "Invalid height")
                .addValidation(minLengthValidator(MIN_PASSWORD_LENGTH), password,
                        "Password must contain at least " + MIN_PASSWORD_LENGTH + " characters")
                .addValidation(confirmValidator(passwordConfirm), password,
                        "The two passwords do not match")
                .addValidation(emailValidator(), email,
                        "Invalid email")
                .addValidation(alreadyTakenValidator(Service.USERS, "email"), email,
                        "Email already taken");
        validator.validate();
        validator.ifValid(() -> {
            // Add the new user in the database
            final Map<String, Object> newUser = newParameter("username", username);
            newUser.put("password", password);
            newUser.put("name", firstName);
            newUser.put("surname", lastName);
            newUser.put("email", email);
            newUser.put("age", age);
            USERS.getDBService().create(newUser);
            final Map<String, Object> newMeasure = newParameter("username", username);
            newMeasure.put("date", DateFormats.toUTCString(new Date()));
            newMeasure.put("height", height / 100.0);
            newMeasure.put("weight", weight);
            MEASURES.getDBService().create(newMeasure);
        });
        return validator.getErrorMessages();
    }

    @Override
    public void logoutUser() {
        model.logoutUser();
        model.getWorkoutList().clear();
        model.getRoutineList().clear();
        model.resetBody();
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
                .addValidation(minLengthValidator(1), newFirstName, "Invalid first name")
                .addValidation(minLengthValidator(1), newLastName, "Invalid last name")
                .addValidation(positiveNumberValidator(), newAge, "Invalid age")
                .addValidation(emailValidator(), newEmail, "Invalid email")
                .addValidation(minLengthValidator(MIN_PASSWORD_LENGTH), newPassword,
                        "The password must contain at least " + MIN_PASSWORD_LENGTH + " characters")
                .addValidation(confirmValidator(newPasswordasswordConfirm), newPassword,
                        "The two passwords do not match")
                .addValidation(alreadyTakenValidator(Service.USERS, "email")
                        .or(e -> getCurrentUserData().get("email").equals(e)),
                        newEmail, "Email already taken");
        validator.validate();
        validator.ifValid(() -> {
            // Update the current user data
            final Map<String, Object> newUserData = newParameter("password", newPassword);
            newUserData.put("name", newFirstName);
            newUserData.put("surname", newLastName);
            newUserData.put("email", newEmail);
            newUserData.put("age", newAge);
            USERS.getDBService().updateByParams(
                    currentUsernameAsQueryParams(),
                    newUserData);
        });
        return validator.getErrorMessages();
    }

    @Override
    public Map<String, Set<String>> getExercises() {
        final Map<String, Set<String>> exercises = new HashMap<>();
        EXERCISES.getDBService().getAll().forEach(m -> {
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
        EXERCISES.getDBService()
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
        final DBService routines = ROUTINES.getDBService();
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
        ROUTINES.getDBService()
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

    @SuppressWarnings("unchecked")
    @Override
    public boolean addResults() {
        final Map<String, List<Pair<String, Pair<List<Integer>, Integer>>>> userResults = view
                .getSelectRoutineView()
                .getUserResults();
        final Date date = new Date();
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
                        result.put("date", DateFormats.toUTCString(date));

                        final Map<String, Object> params = currentUsernameAsQueryParams();
                        params.put("name", view.getSelectRoutineView().getSelectedRoutine());
                        final int routineId = ROUTINES.getDBService()
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
                .forEach(l -> {
                    RESULTS.getDBService().create(l);
                    l.forEach(m -> {
                        addCurrentUserResult(
                                (int) m.get("routineId"),
                                (String) m.get("workoutName"),
                                DateConverter.dateToLocalDate(date),
                                ((List<Integer>) m.get("repetitions")).stream()
                                        .map(e -> (int) m.get("weight"))
                                        .collect(Collectors.toList()));
                    });
                });
        return true;
    }

    @Override
    public boolean updateWeight() {
        final Map<String, Object> newMeasure = new HashMap<>();
        final OptionalDouble newWeight = view.getSelectRoutineView().getWeight();
        newWeight.ifPresent(w -> {
            newMeasure.putAll(currentUsernameAsQueryParams());
            newMeasure.put("weight", w);
            // The height does't change
            final double height = (double) MEASURES.getDBService()
                    .getOneByParams(currentUsernameAsQueryParams())
                    .get().get("height");
            newMeasure.put("height", height);
            newMeasure.put("date", DateFormats.toUTCString(new Date()));
            model.addDataMeasure(LocalDate.now());
            model.addBodyMeasure("HEIGHT", height, false);
            model.addBodyMeasure("WEIGHT", w, false);
        });
        return newMeasure.isEmpty() || MEASURES.getDBService().create(newMeasure);
    }

    @Override
    public boolean deleteRoutine() {
        final String routineName = view.getSelectRoutineView().getSelectedRoutine();
        final Map<String, Object> deleteParams = currentUsernameAsQueryParams();
        deleteParams.put("name", routineName);
        final DBService routines = ROUTINES.getDBService();
        routines.getOneByParams(deleteParams)
                .map(m -> (int) m.get("routineId"))
                .ifPresent(model::removeRoutine);
        return routines.deleteByParams(deleteParams) > 0;
    }

    @Override
    public Map<String, List<Pair<String, Number>>> getChartsData() {
        final Map<String, List<Pair<String, Number>>> chartsData = new HashMap<>();
        final List<Double> bmi = model.trendBodyBMI(); // Current BMI
        final List<Double> bmr = model.trendBodyBMR(); // Current BMR
        final List<Double> lbm = model.trendBodyLBM(); // Current LBM

        final Function<List<Double>, List<Pair<String, Number>>> listToPairList = l -> l.stream()
                .map(d -> new ImmutablePair<>("", (Number) d))
                .collect(Collectors.toList());

        chartsData.put("Thrend BMI", listToPairList.apply(bmi));
        chartsData.put("Thrend BMR", listToPairList.apply(bmr));
        chartsData.put("Thrend LBM", listToPairList.apply(lbm));

        final List<Pair<String, Number>> weightChart = MEASURES.getDBService()
                .getByParams(currentUsernameAsQueryParams())
                .stream()
                .map(m -> (Pair<String, Number>) new ImmutablePair<>(
                        (String) m.get("date"),
                        (Number) m.get("weight")))
                .sorted((e1, e2) -> {
                    final Date p = DateFormats.parseUTC(e1.getLeft());
                    final Date q = DateFormats.parseUTC(e2.getLeft());
                    return p.before(q) ? -1
                            : p.after(q) ? 1 : 0;
                })
                .collect(Collectors.toList());
        chartsData.put("Weight Chart", weightChart);
        chartsData.put("bodyZone performance", model.timeBodyZone().entrySet().stream()
                .map(e -> new ImmutablePair<>(
                        e.getKey(),
                        (Number) e.getValue()))
                .collect(Collectors.toList()));
        return chartsData;
    }

    private String getCurrentUsername() {
        return model.getCurrentUserName().get();
    }

    /**
     * This method gets called in {@link Controller#loginUser}. Resets the current user body.
     */
    @SuppressWarnings("unchecked")
    private void resetCurrentUserBody() {
        final double defaultPercentage = 50.0;
        BODY_ZONES.getDBService().getAll().stream()
                .forEach(m -> {
                    final String bodyZone = (String) m.get("name");
                    ((List<String>) m.get("bodyParts")).forEach(p -> model.setBody(p, bodyZone));
                });
        EXERCISES.getDBService().getAll().stream()
                .forEach(m -> {
                    model.addBodyPart(
                            (String) m.get("gymTool"),
                            (String) m.get("mainTarget"),
                            defaultPercentage); // percentage not used
                });
    }

    @SuppressWarnings("unchecked")
    void addCurrentUserWorkouts() {
        ROUTINES.getDBService()
                .getByParams(currentUsernameAsQueryParams())
                .stream()
                .map(r -> (List<Map<String, Object>>) r.get("workouts"))
                .forEach(l -> {
                    l.forEach(w -> {
                        final String currentWorkoutName = (String) w.get("name");
                        model.addWorkout(currentWorkoutName, currentWorkoutName, ""); // Not used
                        ((List<Map<String, Object>>) w.get("exercises")).forEach(ex -> {
                            EXERCISES.getDBService()
                                    .getOneByParams(newParameter("name", ex.get("exerciseName")))
                                    .ifPresent(e -> {
                                model.addGymExcercise(
                                        currentWorkoutName,
                                        (String) e.get("exerciseGoal"),
                                        (String) e.get("gymTool"),
                                        (List<Integer>) ex.get("repetitions"));
                            });
                        });
                    });
                });
    }

    /**
     * This method gets called in {@link Controller#loginUser}. Passes to the model all the routines and the workouts
     * that the current user has done.
     */
    @SuppressWarnings("unchecked")
    private void addCurrentUserResults() {
        final List<Map<String, Object>> results = RESULTS.getDBService()
                .getByParams(currentUsernameAsQueryParams());
        results.forEach(r -> { // A single result
            final String currentWorkoutName = (String) r.get("workoutName");
            model.addWorkout(currentWorkoutName, currentWorkoutName, ""); // Not used
            final Date date = DateFormats.parseUTC((String) r.get("date"));
            final LocalDate when = DateConverter.dateToLocalDate(date);
            final List<Integer> valueList = ((List<Integer>) r.get("repetitions")).stream()
                    .map(d -> (int) r.get("weight"))
                    .collect(Collectors.toList());
            addCurrentUserResult(
                    (int) r.get("routineId"),
                    currentWorkoutName,
                    when,
                    valueList);
        });
        System.out.println(model.getWorkoutList());
        System.out.println(model.getRoutineList());
    }

    /**
     * This method gets called in {@link Controller#loginUser}. Passes to the model all the measures of the current user
     * body.
     */
    private void addCurrentUserMeasures() {
        final Optional<Boolean> firstTime = Optional.of(true);
        MEASURES.getDBService()
                .getByParams(currentUsernameAsQueryParams())
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
    }

    private void addCurrentUserResult(
            final int routineId,
            final String workoutName,
            final LocalDate date,
            final List<Integer> valueList) {
        model.addRoutine(
                routineId,
                workoutName,
                date);
        model.addExerciseValue(valueList);
    }

    private static Map<String, Object> usernameAsQueryParam(final String username) {
        return newParameter("username", username);
    }

    private Map<String, Object> currentUsernameAsQueryParams() {
        return usernameAsQueryParam(getCurrentUsername());
    }

    private static Optional<Map<String, Object>> getUserData(final String username) {
        return USERS.getDBService()
                .getOneByParams(usernameAsQueryParam(username));
    }

    private static Map<String, Object> newParameter(final String name, final Object value) {
        final Map<String, Object> param = new HashMap<>();
        param.put(name, value);
        return param;
    }

}
