package adrian.tfm.crossfit.classes.domain;

import adrian.tfm.crossfit.classes.domain.port.ClassDto;

import java.util.List;

public interface ClassUseCase {
    List<ClassDto> getAllClasses();
}
