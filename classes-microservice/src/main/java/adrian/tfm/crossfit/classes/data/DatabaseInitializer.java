package adrian.tfm.crossfit.classes.data;

import adrian.tfm.crossfit.classes.domain.port.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
public class DatabaseInitializer {
    private ClassRepository classRepository;
    private ExerciseRepository exerciseRepository;
    private Mapper mapper;
    private Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    public DatabaseInitializer(ClassRepository classRepository, ExerciseRepository exerciseRepository, Mapper mapper) {
        this.classRepository = classRepository;
        this.exerciseRepository = exerciseRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    @Transactional
    public void init() {
        logger.info("INITIALIZING DB DATA");

        ExerciseDto exerciseDto1 = new ExerciseDto(1L, "DUMBBELL WALKING LUNGE");
        ExerciseDto exerciseDto2 = new ExerciseDto(2L,"BENT-OVER REAR-DELT PLATE RAISE");
        ExerciseDto exerciseDto3 = new ExerciseDto(3L,"RUNNING SPRINT");
        ExerciseDto exerciseDto4 = new ExerciseDto(4L,"TOES TO BAR");
        ExerciseDto exerciseDto5 = new ExerciseDto(5L,"OVERHEAD DUMBBELL REVERSE LUNGE");
        ExerciseDto exerciseDto6 = new ExerciseDto(6L,"BARBELL GOOD MORNING");
        ExerciseDto exerciseDto7 = new ExerciseDto(7L,"OVERHEAD BARBELL PRESS");
        ExerciseDto exerciseDto8 = new ExerciseDto(8L,"BACK SQUAT");
        ExerciseDto exerciseDto9 = new ExerciseDto(9L,"BARBELL STRAIGHT-LEG DEADLIFT");
        ExerciseDto exerciseDto10 = new ExerciseDto(10L,"POWER SNATCH");
        ExerciseDto exerciseDto11 = new ExerciseDto(11L,"BACK SQUAT");
        ExerciseDto exerciseDto12 = new ExerciseDto(12L,"POWER SNATCH");
        ExerciseDto exerciseDto13 = new ExerciseDto(13L,"BARBELL BENCH PRESS");
        ExerciseDto exerciseDto14 = new ExerciseDto(14L,"BARBELL BENT-OVER ROW");
        ExerciseDto exerciseDto15 = new ExerciseDto(15L,"GENERAL PUSHUP");
        ExerciseDto exerciseDto16 = new ExerciseDto(16L,"SIDE PLANK LIFT");
        ExerciseDto exerciseDto17 = new ExerciseDto(17L,"BURPEE");
        ExerciseDto exerciseDto18 = new ExerciseDto(18L,"KETTLEBELL SWING");
        ExerciseDto exerciseDto19 = new ExerciseDto(19L,"DOUBLE UNDER");
        ExerciseDto exerciseDto20 = new ExerciseDto(20L,"KETTLEBELL SUMO DEADLIFT");
        ExerciseDto exerciseDto21 = new ExerciseDto(21L,"BODYWEIGHT SQUAT");
        ExerciseDto exerciseDto22 = new ExerciseDto(22L,"BARBELL FRONT SQUAT");
        ExerciseDto exerciseDto23 = new ExerciseDto(23L,"HANDSTAND PUSHUP");
        ExerciseDto exerciseDto24 = new ExerciseDto(24L,"BARBELL PUSH PRESS");
        ExerciseDto exerciseDto25 = new ExerciseDto(25L,"MOUNTAIN CLIMBER");
        ExerciseDto exerciseDto26 = new ExerciseDto(26L,"THRUSTERS");
        ExerciseDto exerciseDto27 = new ExerciseDto(27L,"TOES TO BAR");
        ExerciseDto exerciseDto28 = new ExerciseDto(28L,"BARBELL POWER CLEAN");

        List<ExerciseDto> exerciseDtoList = new ArrayList<>(Arrays.asList(
                exerciseDto1, exerciseDto2, exerciseDto3, exerciseDto4, exerciseDto5, exerciseDto6, exerciseDto7, exerciseDto8,
                exerciseDto9, exerciseDto10, exerciseDto11, exerciseDto12, exerciseDto13, exerciseDto14, exerciseDto15, exerciseDto16,
                exerciseDto17, exerciseDto18, exerciseDto19, exerciseDto20, exerciseDto21, exerciseDto22, exerciseDto23, exerciseDto24,
                exerciseDto25, exerciseDto26, exerciseDto27, exerciseDto28
        ));

        // day 1
        ExerciseForClassDto exerciseForClassDto1 = new ExerciseForClassDto(exerciseDto1, 3, 10);
        ExerciseForClassDto exerciseForClassDto2 = new ExerciseForClassDto(exerciseDto2, 3, 10);
        ExerciseForClassDto exerciseForClassDto3 = new ExerciseForClassDto(exerciseDto3, 3, 10);
        ExerciseForClassDto exerciseForClassDto4 = new ExerciseForClassDto(exerciseDto4, 1, "200");
        // day 2
        ExerciseForClassDto exerciseForClassDto5 = new ExerciseForClassDto(exerciseDto5, 1, 200);
        ExerciseForClassDto exerciseForClassDto6 = new ExerciseForClassDto(exerciseDto6, 1, 200);
        ExerciseForClassDto exerciseForClassDto7 = new ExerciseForClassDto(exerciseDto7, 3, 10);
        ExerciseForClassDto exerciseForClassDto8 = new ExerciseForClassDto(exerciseDto8, 3, 10);
        // day 3
        ExerciseForClassDto exerciseForClassDto9 = new ExerciseForClassDto(exerciseDto9, 3, 10);
        ExerciseForClassDto exerciseForClassDto10 = new ExerciseForClassDto(exerciseDto10, 3, 10);
        ExerciseForClassDto exerciseForClassDto11 = new ExerciseForClassDto(exerciseDto11, 15, 1);
        ExerciseForClassDto exerciseForClassDto12 = new ExerciseForClassDto(exerciseDto12, 15, 1);
        // day 4
        ExerciseForClassDto exerciseForClassDto13 = new ExerciseForClassDto(exerciseDto13, 1, 75);
        ExerciseForClassDto exerciseForClassDto14 = new ExerciseForClassDto(exerciseDto14, 1, 75);
        ExerciseForClassDto exerciseForClassDto15 = new ExerciseForClassDto(exerciseDto15, 3, 10);
        ExerciseForClassDto exerciseForClassDto16 = new ExerciseForClassDto(exerciseDto16, 3, 10);
        // day 5
        ExerciseForClassDto exerciseForClassDto17 = new ExerciseForClassDto(exerciseDto17, 3, 10);
        ExerciseForClassDto exerciseForClassDto18 = new ExerciseForClassDto(exerciseDto18, 3, 10);
        ExerciseForClassDto exerciseForClassDto19 = new ExerciseForClassDto(exerciseDto19, 1, "ALL");
        ExerciseForClassDto exerciseForClassDto20 = new ExerciseForClassDto(exerciseDto20, 1, "ALL");
        // day 6
        ExerciseForClassDto exerciseForClassDto21 = new ExerciseForClassDto(exerciseDto21, 3, 10);
        ExerciseForClassDto exerciseForClassDto22 = new ExerciseForClassDto(exerciseDto22, 3, 10);
        ExerciseForClassDto exerciseForClassDto23 = new ExerciseForClassDto(exerciseDto23, 5, 20);
        ExerciseForClassDto exerciseForClassDto24 = new ExerciseForClassDto(exerciseDto24, 5, 20);
        // day 7
        ExerciseForClassDto exerciseForClassDto25 = new ExerciseForClassDto(exerciseDto25, 1, "ALL");
        ExerciseForClassDto exerciseForClassDto26 = new ExerciseForClassDto(exerciseDto26, 1, "ALL");
        ExerciseForClassDto exerciseForClassDto27 = new ExerciseForClassDto(exerciseDto27, 3, 10);
        ExerciseForClassDto exerciseForClassDto28 = new ExerciseForClassDto(exerciseDto28, 3, 10);

        // exercise list per day
        List<ExerciseForClassDto> exerciseList1 = new ArrayList<>(Arrays.asList(exerciseForClassDto1, exerciseForClassDto2, exerciseForClassDto3, exerciseForClassDto4));
        List<ExerciseForClassDto> exerciseList2 = new ArrayList<>(Arrays.asList(exerciseForClassDto5, exerciseForClassDto6, exerciseForClassDto7, exerciseForClassDto8));
        List<ExerciseForClassDto> exerciseList3 = new ArrayList<>(Arrays.asList(exerciseForClassDto9, exerciseForClassDto10, exerciseForClassDto11, exerciseForClassDto12));
        List<ExerciseForClassDto> exerciseList4 = new ArrayList<>(Arrays.asList(exerciseForClassDto13, exerciseForClassDto14, exerciseForClassDto15, exerciseForClassDto16));
        List<ExerciseForClassDto> exerciseList5 = new ArrayList<>(Arrays.asList(exerciseForClassDto17, exerciseForClassDto18, exerciseForClassDto19, exerciseForClassDto20));
        List<ExerciseForClassDto> exerciseList6 = new ArrayList<>(Arrays.asList(exerciseForClassDto21, exerciseForClassDto22, exerciseForClassDto23, exerciseForClassDto24));
        List<ExerciseForClassDto> exerciseList7 = new ArrayList<>(Arrays.asList(exerciseForClassDto25, exerciseForClassDto26, exerciseForClassDto27, exerciseForClassDto28));
        List<ExerciseForClassDto> exerciseList8 = new ArrayList<>(Arrays.asList(exerciseForClassDto1, exerciseForClassDto2, exerciseForClassDto3, exerciseForClassDto4));
        List<ExerciseForClassDto> exerciseList9 = new ArrayList<>(Arrays.asList(exerciseForClassDto5, exerciseForClassDto6, exerciseForClassDto7, exerciseForClassDto8));
        List<ExerciseForClassDto> exerciseList10 = new ArrayList<>(Arrays.asList(exerciseForClassDto9, exerciseForClassDto10, exerciseForClassDto11, exerciseForClassDto12));
        List<ExerciseForClassDto> exerciseList11 = new ArrayList<>(Arrays.asList(exerciseForClassDto13, exerciseForClassDto14, exerciseForClassDto15, exerciseForClassDto16));
        List<ExerciseForClassDto> exerciseList12 = new ArrayList<>(Arrays.asList(exerciseForClassDto17, exerciseForClassDto18, exerciseForClassDto19, exerciseForClassDto20));
        List<ExerciseForClassDto> exerciseList13 = new ArrayList<>(Arrays.asList(exerciseForClassDto21, exerciseForClassDto22, exerciseForClassDto23, exerciseForClassDto24));
        List<ExerciseForClassDto> exerciseList14 = new ArrayList<>(Arrays.asList(exerciseForClassDto25, exerciseForClassDto26, exerciseForClassDto27, exerciseForClassDto28));
        List<ExerciseForClassDto> exerciseList15 = new ArrayList<>(Arrays.asList(exerciseForClassDto1, exerciseForClassDto2, exerciseForClassDto3, exerciseForClassDto4));

        List<List<ExerciseForClassDto>> exerciseForClassList = new ArrayList<>(Arrays.asList(
                exerciseList1, exerciseList2, exerciseList3, exerciseList4, exerciseList5, exerciseList6, exerciseList7,
                exerciseList8, exerciseList9, exerciseList10, exerciseList11, exerciseList12, exerciseList13, exerciseList14, exerciseList15
        ));

        int[][] tabla = new int[12][7];

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, LocalDateTime.now().getYear());
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        while (calendar.get(Calendar.YEAR) == LocalDateTime.now().getYear()) {
            int month = calendar.get(Calendar.MONTH);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            tabla[month][dayOfWeek]++;

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        List<LocalTime> times = new ArrayList<>(Arrays.asList(
                LocalTime.of(7,0),
                LocalTime.of(8,0),
                LocalTime.of(9,0),
                LocalTime.of(10,0),
                LocalTime.of(11,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(14,0),
                LocalTime.of(15,0),
                LocalTime.of(16,0),
                LocalTime.of(17,0),
                LocalTime.of(18,0),
                LocalTime.of(19,0),
                LocalTime.of(20,0),
                LocalTime.of(21,0)
        ));

        List<ClassDto> classDtoList = new ArrayList<>();

        int classCounter = 0;
        for (int i = 0; i < tabla.length - 1; i++) {
            int[] months = tabla[i];
            for (int j = 0; j < months.length - 1; j++) {
                int day = months[j];
                LocalDateTime date = LocalDateTime.of(LocalDateTime.now().getYear(), i, day, times.get(classCounter).getHour(), times.get(classCounter).getMinute());

                ClassDto classDto = new ClassDto(
                        "Clase del " + date.getDayOfMonth() + " de " + date.getMonthValue() + " del " + date.getYear(),
                        date,
                        null,
                        false,
                        exerciseForClassList.get(classCounter));
                classDtoList.add(classDto);

                classCounter++;
                if (classCounter == times.size()) classCounter = 0;
            }
        }

        this.exerciseRepository.saveExerciseList(exerciseDtoList);
        this.classRepository.saveClassList(classDtoList);
    }
}
