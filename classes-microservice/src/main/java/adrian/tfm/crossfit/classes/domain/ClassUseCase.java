package adrian.tfm.crossfit.classes.domain;

import adrian.tfm.crossfit.classes.domain.port.ClassDto;

import java.util.List;

public interface ClassUseCase {
    List<ClassDto> getAllClasses();

    List<ClassDto> getClassesByUser(String nif);

    void bookClass(ClassDto classDto, String nif) throws Exception;

    void removeClass(ClassDto classDto, String nif) throws Exception;

    void changeBookClass(ClassDto classDto, Long id, String nif) throws Exception;
}
