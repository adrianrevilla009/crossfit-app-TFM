package adrian.tfm.crossfit.classes.domain.port;

import java.util.List;

public interface ClassRepository {
    void saveClassList(List<ClassDto> classDtoList);

    List<ClassDto> getAllClasses();

    List<ClassExerciseUserDto> getClassesByUser(String nif);

    void bookClass(ClassDto classDto, UserDto userDto) throws Exception;

    void removeClass(ClassDto classDto, UserDto userDto) throws Exception;

    void changeBookClass(ClassDto classDto, Long id, UserDto userDto) throws Exception;

    List<ClassDto> getClassesDtoByUser(String nif);
}
