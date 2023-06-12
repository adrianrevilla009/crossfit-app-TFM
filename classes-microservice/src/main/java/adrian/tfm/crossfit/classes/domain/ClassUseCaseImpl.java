package adrian.tfm.crossfit.classes.domain;

import adrian.tfm.crossfit.classes.domain.port.ClassDto;
import adrian.tfm.crossfit.classes.domain.port.ClassRepository;
import adrian.tfm.crossfit.classes.domain.port.UserDto;
import adrian.tfm.crossfit.classes.domain.port.UserRepository;

import java.util.List;

public class ClassUseCaseImpl implements ClassUseCase{

    private ClassRepository classRepository;

    private UserRepository userRepository;

    public ClassUseCaseImpl(ClassRepository classRepository, UserRepository userRepository) {
        this.classRepository = classRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ClassDto> getAllClasses() {
        return this.classRepository.getAllClasses();
    }

    @Override
    public void bookClass(ClassDto classDto, String nif) throws Exception {
        UserDto userDto = this.userRepository.findByNif(nif);

        this.classRepository.bookClass(classDto, userDto);
    }

    @Override
    public void removeClass(ClassDto classDto, String nif) throws Exception {
        UserDto userDto = this.userRepository.findByNif(nif);

        this.classRepository.removeClass(classDto, userDto);
    }

    @Override
    public void changeBookClass(ClassDto classDto, Long id, String nif) throws Exception {
        UserDto userDto = this.userRepository.findByNif(nif);

        this.classRepository.changeBookClass(classDto, id, userDto);
    }
}
