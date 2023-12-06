package adrian.tfm.crossfit.classes.domain;

import adrian.tfm.crossfit.classes.domain.port.*;

import java.util.List;
import java.util.Optional;

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
    public List<ClassDto> getClassesByUser(String nif) {
        return this.classRepository.getClassesByUser(nif);
    }

    @Override
    public void bookClass(ClassDto classDto, String nif) throws Exception {
        UserDto userDto = this.userRepository.findByNif(nif);

        List<ClassDto> classes = this.classRepository.getClassesDtoByUser(nif);
        Boolean found = false;
        for (ClassDto classDto1 : classes) {
            if (classDto1.getName().trim().equals(classDto.getName().trim())) {
                found = true;
            }
        }
        if (!found) {
            this.classRepository.bookClass(classDto, userDto);
        } else {
            throw new Exception("This user is already in this class");
        }
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
