package adrian.tfm.crossfit.classes.domain;

import adrian.tfm.crossfit.classes.domain.port.ClassDto;
import adrian.tfm.crossfit.classes.domain.port.ClassRepository;

import java.util.List;

public class ClassUseCaseImpl implements ClassUseCase{

    private ClassRepository classRepository;

    public ClassUseCaseImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public List<ClassDto> getAllClasses() {
        return this.classRepository.getAllClasses();
    }
}
