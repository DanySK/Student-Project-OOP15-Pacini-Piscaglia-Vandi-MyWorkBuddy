package it.unibo.oop.myworkoutbuddy;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.controller.Service;
import it.unibo.oop.myworkoutbuddy.controller.db.MongoService;

public class InsertExercises {

    private static final String STRENGTH = "Strength";
    private static final String MASS = "Mass";
    private static final String ENDURANCE = "Endurance";

    private static final String CALISTHENICS = "Calisthenics";
    private static final String STRONGMAN = "Strongman";
    private static final String POWERLIFTING = "Powerlifting";
    private static final String WEIGHTLIFTING = "Weightlifting";
    private static final String BODYBUILDING = "Bodybuilding";

    private static final String CHEST = "Chest";
    private static final String BACK = "Back";
    private static final String TRAPS = "Traps";
    private static final String SHOULDERS = "Shoulders";
    private static final String BICEPS = "Biceps";
    private static final String TRICEPS = "Triceps";
    private static final String FOREARMS = "Forearms";
    private static final String ABDOMINALS = "Abdominals";
    private static final String LEGS = "Legs";
    private static final String CALVES = "Calves";

    private static final String BODYWEIGHT = "Bodyweight";
    private static final String BARBELL = "Barbell";
    private static final String DUMBBELL = "Dumbbell";
    private static final String CHINUP_BAR = "Chin-Up Bar";
    private static final String DIP_BAR = "Dip Bar";
    private static final String LAT_MACHINE = "Lat Machine";
    private static final String LEG_PRESS_MACHINE = "Leg Press Machine";
    private static final String LEG_CURL_MACHINE = "Leg Curl Machine";
    private static final String LEG_EXTENSION_MACHINE = "Leg Extension Machine";
    private static final String CABLES = "Cables";
    private static final String SMITH_MACHINE = "Smith Machine";
    private static final String ROMAN_CHAIR = "Roman Chair";

    private static final List<Map<String, Object>> EXERCISES;

    @Test
    public void insertExercisesTest() {
        System.out.println(EXERCISES.size());
        final Service exercises = new MongoService("exercises");
        exercises.deleteAll();
        exercises.create(EXERCISES);
    }

    static {
        // CHEST EXERCISES
        final Map<String, Object> pushUps = new HashMap<>();
        pushUps.put("name", "Push-Ups");
        pushUps.put("description",
                "A push-up (or press-up) is a common calisthenics exercise performed in a prone position by raising and lowering the body using the arms. Push-ups exercise the pectoral muscles, triceps, and anterior deltoids, with ancillary benefits to the rest of the deltoids, serratus anterior, coracobrachialis and the midsection as a whole. Push-ups are a basic exercise used in civilian athletic training or physical education and commonly in military physical training.");
        pushUps.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40811m.mp4");
        pushUps.put("mainTarget", CHEST);
        pushUps.put("otherTargets", Arrays.asList(SHOULDERS, TRICEPS));
        pushUps.put("exerciseGoals", Arrays.asList(MASS, ENDURANCE));
        pushUps.put("exerciseTypes", Arrays.asList(CALISTHENICS, BODYBUILDING));
        pushUps.put("gymTools", Arrays.asList(BODYWEIGHT));
        final Map<String, Object> benchPress = new HashMap<>();
        benchPress.put("name", "Bench Press");
        benchPress.put("description",
                "The bench press is an upper body strength training exercise that consists of pressing a weight upwards from a supine position. The exercise works the pectoralis major as well as supporting chest, arm, and shoulder muscles such as the anterior deltoids, serratus anterior, coracobrachialis, scapulae fixers, trapezii, and the triceps. A barbell is generally used to hold the weight, but a pair of dumbbells can also be used. The barbell bench press is one of three lifts in the sport of powerlifting and is used extensively in weight training, bodybuilding, and other types of lifting fitness training to develop the chest muscles.");
        benchPress.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/52000/53781m.mp4");
        benchPress.put("mainTarget", CHEST);
        benchPress.put("otherTargets", Arrays.asList(TRICEPS, SHOULDERS));
        benchPress.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        benchPress.put("exerciseTypes", Arrays.asList(POWERLIFTING, BODYBUILDING));
        benchPress.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));
        final Map<String, Object> inclineBenchPress = new HashMap<>();
        inclineBenchPress.put("name", "Incline Bench Press");
        inclineBenchPress.put("description",
                "A variation of the bench press that elevates the shoulders and lowers the pelvis as if reclining in a chair; this variation emphasizes anterior deltoids with little emphasis at the upper (clavicular) head of the pectoralis major.");
        inclineBenchPress.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/116000/117671m.mp4");
        inclineBenchPress.put("mainTarget", CHEST);
        inclineBenchPress.put("otherTargets", Arrays.asList(TRICEPS, SHOULDERS));
        inclineBenchPress.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        inclineBenchPress.put("exerciseTypes", Arrays.asList(POWERLIFTING, BODYBUILDING));
        inclineBenchPress.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));
        final Map<String, Object> declineBenchPress = new HashMap<>();
        declineBenchPress.put("name", "Decline Bench Press");
        declineBenchPress.put("description",
                "A variation of the bench press that elevates the pelvis and lowers the head, and emphasizes the lower portion of the pectoralis major.");
        declineBenchPress.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/38881m.mp4");
        declineBenchPress.put("mainTarget", CHEST);
        declineBenchPress.put("otherTargets", Arrays.asList(TRICEPS, SHOULDERS));
        declineBenchPress.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        declineBenchPress.put("exerciseTypes", Arrays.asList(POWERLIFTING, BODYBUILDING));
        declineBenchPress.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));
        final Map<String, Object> chestFly = new HashMap<>();
        chestFly.put("name", "Chest Fly");
        chestFly.put("description",
                "The chest fly is an exercise used to stimulate the pectoral muscles in the chest. It is performed by holding dumbbells (one in each hand) or cables, lying back on a bench or flat surface, and using the pectoral and deltoid muscles to adduct the weights inwards.");
        chestFly.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/39021m.mp4");
        chestFly.put("mainTarget", CHEST);
        chestFly.put("otherTargets", Arrays.asList(SHOULDERS));
        chestFly.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        chestFly.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        chestFly.put("gymTools", Arrays.asList(DUMBBELL, CABLES));
        final Map<String, Object> inclineChestFly = new HashMap<>();
        inclineChestFly.put("name", "Incline Chest Fly");
        inclineChestFly.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40261m.mp4");
        inclineChestFly.put("description",
                "A variation of the chest fly that stimulates the upper pectoral muscles.");
        inclineChestFly.put("mainTarget", CHEST);
        inclineChestFly.put("otherTargets", Arrays.asList(SHOULDERS));
        inclineChestFly.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        inclineChestFly.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        inclineChestFly.put("gymTools", Arrays.asList(DUMBBELL, CABLES));
        final Map<String, Object> declineChestFly = new HashMap<>();
        declineChestFly.put("name", "Decline Chest Fly");
        declineChestFly.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/38911m.mp4");
        declineChestFly.put("description",
                "A variation of the chest fly that stimulates the lower pectoral muscles.");
        declineChestFly.put("mainTarget", CHEST);
        declineChestFly.put("otherTargets", Arrays.asList(SHOULDERS));
        declineChestFly.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        declineChestFly.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        declineChestFly.put("gymTools", Arrays.asList(DUMBBELL, CABLES));
        final Map<String, Object> pullOver = new HashMap<>();
        pullOver.put("name", "Pull-Over");
        pullOver.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/52000/53901m.mp4");
        pullOver.put("description",
                "The pullover is an exercise that is performed with either a dumbbell or a barbell. Pullovers can be made to affect either the chest or the back depending on how wide the grip is (barbell) and the position of the shoulders. A research done on the pullover movement using a barbell suggested more effect on the Pectoralis major muscle as compare to the Latisimus dorsi.");
        pullOver.put("mainTarget", CHEST);
        pullOver.put("otherTargets", Arrays.asList(BACK));
        pullOver.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        pullOver.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        pullOver.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));
        final Map<String, Object> chestDip = new HashMap<>();
        chestDip.put("name", "Chest Dip");
        chestDip.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/38971m.mp4");
        chestDip.put("description",
                "The dip is an exercise used in strength training. Narrow, shoulder-width dips primarily train the triceps, with major synergists being the anterior deltoid, the pectoralis muscles (sternal, clavicular, and minor), and the rhomboid muscles of the back (in that order). Wide arm training places additional emphasis on the pectoral muscles, similar in respect to the way a wide grip bench press would focus more on the pectorals and less on the triceps. This exercise can either be executed using a dip bar or two benches.");
        chestDip.put("mainTarget", CHEST);
        chestDip.put("otherTargets", Arrays.asList(TRICEPS, SHOULDERS));
        chestDip.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        chestDip.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        chestDip.put("gymTools", Arrays.asList(DIP_BAR));

        // BACK EXERCISES
        final Map<String, Object> pullUp = new HashMap<>();
        pullUp.put("name", "Pull-Up");
        pullUp.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40861m.mp4");
        pullUp.put("description",
                "A pull-up is an upper-body compound pulling exercise. Although it can be performed with any grip, in recent years some have used the term to refer more specifically to a pull-up performed with a palms-forward position.");
        pullUp.put("mainTarget", BACK);
        pullUp.put("otherTargets", Arrays.asList(BICEPS, SHOULDERS, ABDOMINALS, FOREARMS));
        pullUp.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        pullUp.put("exerciseTypes", Arrays.asList(CALISTHENICS, BODYBUILDING));
        pullUp.put("gymTools", Arrays.asList(CHINUP_BAR, BODYWEIGHT));
        final Map<String, Object> chinUp = new HashMap<>();
        chinUp.put("name", "Chin-Up");
        chinUp.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/38771m.mp4");
        chinUp.put("description",
                "The chin-up (also known as a chin or chinup) is a strength training exercise. People frequently do this exercise with the intention of strengthening muscles such as the latissimus dorsi and biceps, which extend the shoulder and flex the elbow, respectively.");
        chinUp.put("mainTarget", BACK);
        chinUp.put("otherTargets", Arrays.asList(BICEPS, SHOULDERS, ABDOMINALS, FOREARMS));
        chinUp.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        chinUp.put("exerciseTypes", Arrays.asList(CALISTHENICS, BODYBUILDING));
        chinUp.put("gymTools", Arrays.asList(CHINUP_BAR, BODYWEIGHT));
        final Map<String, Object> wideGripLatPulldown = new HashMap<>();
        wideGripLatPulldown.put("name", "Wide-Grip Lat-Pulldown");
        wideGripLatPulldown.put("videoURL", "");
        wideGripLatPulldown.put("description",
                "The pulldown exercise is a strength training exercise designed to develop the latissimus dorsi muscle. It performs the functions of downward rotation and depression of the scapulae combined with adduction and extension of the shoulder joint. The cable lat pulldown is done where the handle is moved via a cable pulley, as opposed to doing pulldowns on a leverage machine.");
        wideGripLatPulldown.put("mainTarget", BACK);
        wideGripLatPulldown.put("otherTargets", Arrays.asList(BICEPS, SHOULDERS));
        wideGripLatPulldown.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        wideGripLatPulldown.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        wideGripLatPulldown.put("gymTools", Arrays.asList(LAT_MACHINE));
        final Map<String, Object> reverseGripLatPullDown = new HashMap<>();
        reverseGripLatPullDown.put("name", "Reverse-Grip Lat-Pulldown");
        reverseGripLatPullDown.put("videoURL", "");
        reverseGripLatPullDown.put("description",
                "A variation of the Lat-Pulldown exercise where the bar is held in a supinated grip.");
        reverseGripLatPullDown.put("mainTarget", BACK);
        reverseGripLatPullDown.put("otherTargets", Arrays.asList(BICEPS, SHOULDERS));
        reverseGripLatPullDown.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        reverseGripLatPullDown.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        reverseGripLatPullDown.put("gymTools", Arrays.asList(LAT_MACHINE));
        final Map<String, Object> bentOverRow = new HashMap<>();
        bentOverRow.put("name", "Bent-Over Row");
        bentOverRow.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/38411m.mp4");
        bentOverRow.put("description",
                "A bent-over row (or barbell row) is a weight training exercise that targets a variety of back muscles. Which ones are targeted varies on form. The bent over row is often used for both bodybuilding and powerlifting. It is a good exercise for increasing strength and size. The bar is held in a pronated grip.");
        bentOverRow.put("mainTarget", BACK);
        bentOverRow.put("otherTargets", Arrays.asList(BICEPS, SHOULDERS));
        bentOverRow.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        bentOverRow.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        bentOverRow.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));
        final Map<String, Object> hyperextension = new HashMap<>();
        hyperextension.put("name", "Hyperextension");
        hyperextension.put("videoURL", "");
        hyperextension.put("description",
                "A hyperextension or back extension is an exercise that works the lower back as well as the mid and upper back, specifically the erector spinae.");
        hyperextension.put("mainTarget", BACK);
        hyperextension.put("otherTargets", Arrays.asList(LEGS));
        hyperextension.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        hyperextension.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        hyperextension.put("gymTools", Arrays.asList(ROMAN_CHAIR));

        final Map<String, Object> deadlift = new HashMap<>();
        deadlift.put("name", "Deadlift");
        deadlift.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/118000/118911m.mp4");
        deadlift.put("description",
                "Deadlift refers to the lifting of dead (without momentum) weight, such as weights lying on the ground. It is one of the few standard weight training exercises in which all repetitions begin with dead weight. There are two positions one can approach when doing the deadlift, which include the conventional deadlift and sumo-deadlift. In most other lifts there is an eccentric (lowering of the weight) phase followed by the concentric (lifting of the weight) phase.");
        deadlift.put("mainTarget", BACK);
        deadlift.put("otherTargets", Arrays.asList(LEGS, ABDOMINALS, FOREARMS));
        deadlift.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        deadlift.put("exerciseTypes", Arrays.asList(POWERLIFTING, STRONGMAN, BODYBUILDING));
        deadlift.put("gymTools", Arrays.asList(BARBELL));

        // LEGS EXERCISES
        final Map<String, Object> squat = new HashMap<>();
        squat.put("name", "Squat");
        squat.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/118000/118981m.mp4");
        squat.put("description",
                "In strength training and fitness, the squat is a compound, full body exercise that trains primarily the muscles of the thighs, hips and buttocks, quadriceps (vastus lateralis, vastus medialis, vastus intermedius and rectus femoris), hamstrings, as well as strengthening the bones, ligaments and insertion of the tendons throughout the lower body. Squats are considered a vital exercise for increasing the strength and size of the legs and buttocks, as well as developing core strength. Isometrically, the lower back, the upper back, the abdominals, the trunk muscles, the costal muscles, and the shoulders and arms are all essential to the exercise and thus are trained when squatting with the proper form.");
        squat.put("mainTarget", LEGS);
        squat.put("otherTargets", Arrays.asList(ABDOMINALS));
        squat.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        squat.put("exerciseTypes", Arrays.asList(POWERLIFTING, STRONGMAN, WEIGHTLIFTING, BODYBUILDING));
        squat.put("gymTools", Arrays.asList(BARBELL, BODYWEIGHT));
        final Map<String, Object> legPress = new HashMap<>();
        legPress.put("name", "Leg Press");
        legPress.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40571m.mp4");
        legPress.put("description",
                "The leg press is a weight training exercise in which the individual pushes a weight or resistance away from them using their legs. The term leg press also refers to the apparatus used to perform this exercise. The leg press can be used to evaluate an athlete's overall lower body strength (from knee joint to hip).");
        legPress.put("mainTarget", LEGS);
        legPress.put("otherTargets", Arrays.asList(CALVES));
        legPress.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        legPress.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        legPress.put("gymTools", Arrays.asList(LEG_PRESS_MACHINE));
        final Map<String, Object> legCurl = new HashMap<>();
        legCurl.put("name", "Leg Curl");
        legCurl.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40751m.mp4");
        legCurl.put("description",
                "The leg curl is an isolation exercise that targets the hamstring muscles. The exercise involves flexing the lower leg against resistance towards the buttocks.");
        legCurl.put("mainTarget", LEGS);
        legCurl.put("otherTargets", Arrays.asList());
        legCurl.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        legCurl.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        legCurl.put("gymTools", Arrays.asList(LEG_CURL_MACHINE));
        final Map<String, Object> legExtension = new HashMap<>();
        legExtension.put("name", "Leg Extension");
        legExtension.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40541m.mp4");
        legExtension.put("description",
                "The leg extension is a resistance weiht training exercise that targets the quadriceps muscle in the legs. The exercise is done using a machine called the Leg Extension Machine. The leg extension is an isolated exercise targeting one specific muscle group, the quadriceps. It should not be considered as a total leg workout, such as the squat or deadlift. The exercise consists of bending the leg at the knee and extending the legs, then lowering them back to the original position.");
        legExtension.put("mainTarget", LEGS);
        legExtension.put("otherTargets", Arrays.asList());
        legExtension.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        legExtension.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        legExtension.put("gymTools", Arrays.asList(LEG_EXTENSION_MACHINE));
        final Map<String, Object> calfRaise = new HashMap<>();
        calfRaise.put("name", "Calf Raise");
        calfRaise.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/38731m.mp4");
        calfRaise.put("description",
                "Calf raises are a method of exercising the gastrocnemius, tibialis posterior and soleus muscles of the lower leg. The movement performed is plantar flexion, aka ankle extension.");
        calfRaise.put("mainTarget", CALVES);
        calfRaise.put("otherTargets", Arrays.asList(LEGS));
        calfRaise.put("exerciseGoals", Arrays.asList(MASS, ENDURANCE));
        calfRaise.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        calfRaise.put("gymTools", Arrays.asList(BARBELL, DUMBBELL, LEG_PRESS_MACHINE));

        // SHOULDERS EXERCISES
        final Map<String, Object> overheadPress = new HashMap<>();
        overheadPress.put("name", "Overhead Press");
        overheadPress.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/39131m.mp4");
        overheadPress.put("description",
                "The press, overhead press or shoulder press is a weight training exercise, typically performed while standing, in which a weight is pressed straight upwards from the shoulders until the arms are locked out overhead.");
        overheadPress.put("mainTarget", SHOULDERS);
        overheadPress.put("otherTargets", Arrays.asList(TRICEPS));
        overheadPress.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        overheadPress.put("exerciseTypes", Arrays.asList(WEIGHTLIFTING, STRONGMAN, BODYBUILDING));
        overheadPress.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));
        final Map<String, Object> frontRaise = new HashMap<>();
        frontRaise.put("name", "Front Raise");
        frontRaise.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/39911m.mp4");
        frontRaise.put("description",
                "The front raise is a weight training exercise. This exercise is an isolation exercise which isolates shoulder flexion. It primarily works the anterior deltoid, with assistance from the serratus anterior, biceps brachii and clavicular portions of the pectoralis major. The front raise is normally carried out in three to five sets during a shoulder workout. Repetitions depend on the a lifter's training program and goals.");
        frontRaise.put("mainTarget", SHOULDERS);
        frontRaise.put("otherTargets", Arrays.asList());
        frontRaise.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        frontRaise.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        frontRaise.put("gymTools", Arrays.asList(DUMBBELL, BARBELL));
        final Map<String, Object> lateralRaise = new HashMap<>();
        lateralRaise.put("name", "Lateral Raise");
        lateralRaise.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40921m.mp4");
        lateralRaise.put("description",
                "The shoulder fly (also known as a lateral raise) works the deltoid muscle of the shoulder. The movement starts with the arms straight, and the hands holding weights at the sides or in front of the body. Arms are kept straight or slightly bent, and raised through an arc of movement in the coronal plane that terminates when the hands are at approximately shoulder height. Weights are lowered to the starting position, completing one \"rep\".");
        lateralRaise.put("mainTarget", SHOULDERS);
        lateralRaise.put("otherTargets", Arrays.asList());
        lateralRaise.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        lateralRaise.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        lateralRaise.put("gymTools", Arrays.asList(DUMBBELL));
        final Map<String, Object> rearDeltRaise = new HashMap<>();
        rearDeltRaise.put("name", "Rear-Delt Raise");
        rearDeltRaise.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40741m.mp4");
        rearDeltRaise.put("description",
                "The rear delt raise, also known as the rear deltoid raise, or rear shoulder raise is an exercise in weight training. This exercise is an isolation exercise that heavily works the posterior deltoid muscle. The movement is primarily limited to the two shoulder joints: the glenohumeral joint and the scapulothoracic joint. Scapular movement will also cause movement in the sternoclavicular joint and acromioclavicular joint. If the elbow bends during the extension exercises, it gravitates into a rowing motion.");
        rearDeltRaise.put("mainTarget", SHOULDERS);
        rearDeltRaise.put("otherTargets", Arrays.asList());
        rearDeltRaise.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        rearDeltRaise.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        rearDeltRaise.put("gymTools", Arrays.asList(DUMBBELL));
        final Map<String, Object> shrug = new HashMap<>();
        shrug.put("name", "Shrug");
        shrug.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/38331m.mp4");
        shrug.put("description",
                "The shoulder shrug (usually called simply shrug) is an exercise in weight training used to develop the upper trapezius muscle.");
        shrug.put("mainTarget", TRAPS);
        shrug.put("otherTargets", Arrays.asList(SHOULDERS));
        shrug.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        shrug.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        shrug.put("gymTools", Arrays.asList(BARBELL, DUMBBELL, SMITH_MACHINE));

        // BICEPS EXERCISES
        final Map<String, Object> bicepsCurl = new HashMap<>();
        bicepsCurl.put("name", "Biceps Curl");
        bicepsCurl.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/38000/38991m.mp4");
        bicepsCurl.put("description",
                "The biceps curl is a 'curling' motion, where a weight (attached to, or used in conjunction with, an item of equipment listed above) is lifted up until the forearms are vertical with the elbows and upper arm remaining close to the body.. This exercise is used to work the short head of the bicep.");
        bicepsCurl.put("mainTarget", BICEPS);
        bicepsCurl.put("otherTargets", Arrays.asList(FOREARMS));
        bicepsCurl.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        bicepsCurl.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        bicepsCurl.put("gymTools", Arrays.asList(DUMBBELL, BARBELL));
        final Map<String, Object> hammerBicepsCurl = new HashMap<>();
        hammerBicepsCurl.put("name", "Hammer Biceps Curl");
        hammerBicepsCurl.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/52000/53751m.mp4");
        hammerBicepsCurl.put("description",
                "A variation of the biceps curl where the palm of the hands is facing the torso of the lifter's body during the entire movement. This exercise is used to work the long head of the bicep.");
        hammerBicepsCurl.put("mainTarget", BICEPS);
        hammerBicepsCurl.put("otherTargets", Arrays.asList(FOREARMS));
        hammerBicepsCurl.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        hammerBicepsCurl.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        hammerBicepsCurl.put("gymTools", Arrays.asList(DUMBBELL));
        final Map<String, Object> preacherBicepsCurl = new HashMap<>();
        preacherBicepsCurl.put("name", "Preacher Biceps Curl");
        preacherBicepsCurl.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40901m.mp4");
        preacherBicepsCurl.put("description",
                "A variation of the biceps curl where the elbows  rest upon a sloped bench. This exercise is used to work the short head of the bicep.");
        preacherBicepsCurl.put("mainTarget", BICEPS);
        preacherBicepsCurl.put("otherTargets", Arrays.asList(FOREARMS));
        preacherBicepsCurl.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        preacherBicepsCurl.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        preacherBicepsCurl.put("gymTools", Arrays.asList(DUMBBELL, BARBELL));

        // TRICEPS EXERCISES
        final Map<String, Object> closeGripBenchPress = new HashMap<>();
        closeGripBenchPress.put("name", "Close-Grip Bench Press");
        closeGripBenchPress.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/58000/59071m.mp4");
        closeGripBenchPress.put("description",
                "The close grip bench press is a variation of the bench press that is best performed with arms in a near-vertical position to reduce strain placed upon the wrists, elbows and shoulders.");
        closeGripBenchPress.put("mainTarget", TRICEPS);
        closeGripBenchPress.put("otherTargets", Arrays.asList(CHEST, SHOULDERS));
        closeGripBenchPress.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        closeGripBenchPress.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        closeGripBenchPress.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));
        final Map<String, Object> lyingTricepsExtension = new HashMap<>();
        lyingTricepsExtension.put("name", "Lying Triceps Extension");
        lyingTricepsExtension.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/42000/42301m.mp4");
        lyingTricepsExtension.put("description",
                "Lying triceps extensions, also known as skull crushers and French extensions or French presses, are a strength exercise used in many different forms of strength training. Lying triceps extensions are one of the most stimulating exercises to the entire triceps muscle group in the upper arm. It works the triceps from the elbow all the way to the latissimus dorsi. Due to its full use of the Triceps muscle group, the lying triceps extensions are used by many as part of their training regimen.");
        lyingTricepsExtension.put("mainTarget", TRICEPS);
        lyingTricepsExtension.put("otherTargets", Arrays.asList(SHOULDERS));
        lyingTricepsExtension.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        lyingTricepsExtension.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        lyingTricepsExtension.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));
        final Map<String, Object> tricepsPushdown = new HashMap<>();
        tricepsPushdown.put("name", "Triceps Pushdown");
        tricepsPushdown.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/41861m.mp4");
        tricepsPushdown.put("description",
                "A pushdown is a strength training exercise used for strengthening the triceps muscles in the back of the arm. The exercise is completed by pushing an object downward against resistance. This exercise is an example of the primary function of the triceps, extension of the elbow joint. It is a little-known fact that doing the triceps pushdown also works the biceps muscle as well.");
        tricepsPushdown.put("mainTarget", TRICEPS);
        tricepsPushdown.put("otherTargets", Arrays.asList());
        tricepsPushdown.put("exerciseGoals", Arrays.asList(MASS, STRENGTH));
        tricepsPushdown.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        tricepsPushdown.put("gymTools", Arrays.asList(LAT_MACHINE, CABLES));
        final Map<String, Object> tricepsDip = new HashMap<>();
        tricepsDip.put("name", "Triceps Dip");
        tricepsDip.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/52000/53961m.mp4");
        tricepsDip.put("description",
                "The dip is an exercise used in strength training. Narrow, shoulder-width dips primarily train the triceps, with major synergists being the anterior deltoid, the pectoralis muscles (sternal, clavicular, and minor), and the rhomboid muscles of the back (in that order). Wide arm training places additional emphasis on the pectoral muscles, similar in respect to the way a wide grip bench press would focus more on the pectorals and less on the triceps. This exercise can either be executed using a dip bar or two benches.");
        tricepsDip.put("mainTarget", TRICEPS);
        tricepsDip.put("otherTargets", Arrays.asList(CHEST, SHOULDERS));
        tricepsDip.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        tricepsDip.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        tricepsDip.put("gymTools", Arrays.asList(DIP_BAR));

        // FOREARMS EXERCISES
        final Map<String, Object> palmsDownWristCurl = new HashMap<>();
        palmsDownWristCurl.put("name", "Palms-Down Wrist Curl");
        palmsDownWristCurl.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/41041m.mp4");
        palmsDownWristCurl.put("description",
                "The wrist curl is a weight training exercise for developing just the wrist flexor muscles of the forearm. It is therefore an isolation exercise.");
        palmsDownWristCurl.put("mainTarget", FOREARMS);
        palmsDownWristCurl.put("otherTargets", Arrays.asList());
        palmsDownWristCurl.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        palmsDownWristCurl.put("exerciseTypes", Arrays.asList(STRONGMAN, BODYBUILDING));
        palmsDownWristCurl.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));
        final Map<String, Object> palmsUpWristCurl = new HashMap<>();
        palmsUpWristCurl.put("name", "Palms-Up Wrist Curl");
        palmsUpWristCurl.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/41031m.mp4");
        palmsUpWristCurl.put("description",
                "The reverse wrist curl is a weight training exercise for developing just the wrist extensor muscles of the forearm. It is therefore an isolation exercise.");
        palmsUpWristCurl.put("mainTarget", FOREARMS);
        palmsUpWristCurl.put("otherTargets", Arrays.asList());
        palmsUpWristCurl.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        palmsUpWristCurl.put("exerciseTypes", Arrays.asList(STRONGMAN, BODYBUILDING));
        palmsUpWristCurl.put("gymTools", Arrays.asList(BARBELL, DUMBBELL));

        // ABDOMINALS EXERCISES
        final Map<String, Object> sitUp = new HashMap<>();
        sitUp.put("name", "Sit-Up");
        sitUp.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/41631m.mp4");
        sitUp.put("description",
                "The sit-up is an abdominal strength training exercise commonly performed to strengthen the abdominal muscles. Sit-ups target the hip flexors, rectus abdominus and also work the iliopsoas, tensor fasciae latae, rectus femoris, sartorius, and, to a very small degree, the obliques.");
        sitUp.put("mainTarget", ABDOMINALS);
        sitUp.put("otherTargets", Arrays.asList());
        sitUp.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        sitUp.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        sitUp.put("gymTools", Arrays.asList(BODYWEIGHT));
        final Map<String, Object> legRaise = new HashMap<>();
        legRaise.put("name", "Leg Raise");
        legRaise.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/54000/54131m.mp4");
        legRaise.put("description",
                "The leg raise is a strength training exercise which targets the iliopsoas (the interior hip flexors). Because the abdominal muscles are used isometrically to stabilize the body during the motion, leg raises are also often used to strengthen the rectus abdominis muscle and the internal and external oblique muscles.");
        legRaise.put("mainTarget", ABDOMINALS);
        legRaise.put("otherTargets", Arrays.asList());
        legRaise.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        legRaise.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        legRaise.put("gymTools", Arrays.asList(BODYWEIGHT));
        final Map<String, Object> russianTwist = new HashMap<>();
        russianTwist.put("name", "Russian Twist");
        russianTwist.put("videoURL", "http://videocdn.bodybuilding.com/video/mp4/40000/40371m.mp4");
        russianTwist.put("description",
                "The Russian Twist is a type of exercise that is used to work the abdomen muscles by performing a twisting motion on the abdomen. The exercise is believed by those who practice it to build explosiveness in the upper torso, which may help in sports such as swimming, baseball, track & field, hockey, golf, lacrosse, or boxing.");
        russianTwist.put("mainTarget", ABDOMINALS);
        russianTwist.put("otherTargets", Arrays.asList());
        russianTwist.put("exerciseGoals", Arrays.asList(STRENGTH, MASS));
        russianTwist.put("exerciseTypes", Arrays.asList(BODYBUILDING));
        russianTwist.put("gymTools", Arrays.asList(BODYWEIGHT));

        EXERCISES = Collections.unmodifiableList(Arrays.asList(pushUps, benchPress, inclineBenchPress,
                declineBenchPress, chestFly, inclineChestFly, declineChestFly, tricepsDip, pullUp, chinUp,
                wideGripLatPulldown,
                reverseGripLatPullDown, bentOverRow, hyperextension, deadlift, pullOver, squat, legPress, legCurl,
                legExtension, calfRaise, overheadPress, frontRaise, lateralRaise, rearDeltRaise, shrug, bicepsCurl,
                hammerBicepsCurl, preacherBicepsCurl, closeGripBenchPress, lyingTricepsExtension, tricepsPushdown,
                palmsDownWristCurl, palmsUpWristCurl, sitUp, legRaise, russianTwist));
    }

}
