package adrian.tfm.crossfit.classes.domain.port;

import java.util.List;

public interface ClassRepository {
    void saveClassList(List<ClassDto> classDtoList);

    List<ClassDto> getAllClasses();
}
