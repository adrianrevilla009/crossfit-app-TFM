package adrian.tfm.crossfit.classes.infraestructure;

import adrian.tfm.crossfit.classes.domain.port.*;
import adrian.tfm.crossfit.classes.infraestructure.mapper.ClassDtoAndEntityMapper;
import adrian.tfm.crossfit.classes.infraestructure.mapper.UserDtoAndEntityMapper;
import adrian.tfm.crossfit.classes.infraestructure.model.ClassEntity;
import adrian.tfm.crossfit.classes.infraestructure.model.UserEntity;
import adrian.tfm.crossfit.classes.infraestructure.repository.ClassJpaRepository;
import adrian.tfm.crossfit.classes.infraestructure.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClassRepositoryAdapter implements ClassRepository {

    private ClassJpaRepository classJpaRepository;
    private Mapper mapper;

    private ClassDtoAndEntityMapper classDtoAndEntityMapper;

    private UserDtoAndEntityMapper userDtoAndEntityMapper;

    private UserJpaRepository userJpaRepository;

    private ClassDao classDao;

    public ClassRepositoryAdapter(ClassJpaRepository classJpaRepository, Mapper mapper,
                                  ClassDtoAndEntityMapper classDtoAndEntityMapper,
                                  UserDtoAndEntityMapper userDtoAndEntityMapper,
                                  UserJpaRepository userJpaRepository) {
        this.classJpaRepository = classJpaRepository;
        this.mapper = mapper;
        this.classDtoAndEntityMapper = classDtoAndEntityMapper;
        this.userDtoAndEntityMapper = userDtoAndEntityMapper;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public void saveClassList(List<ClassDto> classDtoList) {
        List<ClassEntity> classEntityList = new ArrayList<>();
        for (ClassDto classDto : classDtoList) {
            ClassEntity classEntity = this.classDtoAndEntityMapper.mapFromClassDtoToClassEntity(classDto);
            classEntityList.add(classEntity);
        }

        this.classJpaRepository.saveAll(classEntityList);
    }

    public List<ClassDto> getAllClasses() {
        List<ClassEntity> classEntityList = this.classJpaRepository.findAll();
        List<ClassDto> classDtoList = new ArrayList<>();
        for (ClassEntity classEntity : classEntityList) {
            ClassDto classDto = this.classDtoAndEntityMapper.mapFromClassEntityToClassDto(classEntity);
            classDtoList.add(classDto);
        }
        return classDtoList;
    }

    @Override
    public List<ClassExerciseUserDto> getClassesByUser(String nif) {
        List<ClassExerciseUserDto> classesByUser = this.classDao.getClassesByUser(nif);
        return classesByUser;
    }

    @Override
    @Transactional
    public void bookClass(ClassDto classDto, UserDto userDto) throws Exception {
        Optional<ClassEntity> classEntity = this.classJpaRepository.findById(classDto.getId());

        if (classEntity.isPresent()) {
            UserEntity userEntity = this.userDtoAndEntityMapper.fromUserDtoToEntity(userDto);
            userEntity.setClassEntity(classEntity.get());
            classEntity.get().getUserList().add(userEntity);

            this.classJpaRepository.save(classEntity.get());
        } else {
            throw new Exception("No class found");
        }
    }

    @Override
    @Transactional
    public void removeClass(ClassDto classDto, UserDto userDto) throws Exception {
        Optional<ClassEntity> classEntity = this.classJpaRepository.findById(classDto.getId());

        if (classEntity.isPresent()) {

            Optional<UserEntity> userEntity = classEntity.get().getUserList().stream().filter(u -> u.getNif().equals(userDto.getNif())).findFirst();
            if (userEntity.isPresent()) {
                this.userJpaRepository.deleteByClassAndNif(classEntity.get().getId(), userEntity.get().getNif());
            } else {
                throw new Exception("The user is not in this class");
            }
        } else {
            throw new Exception("No class found");
        }

    }

    @Override
    @Transactional
    public void changeBookClass(ClassDto classDto, Long id, UserDto userDto) throws Exception {
        Optional<ClassEntity> classEntity = this.classJpaRepository.findById(id);

        if (classEntity.isPresent()) {
            UserEntity userEntity = this.userDtoAndEntityMapper.fromUserDtoToEntity(userDto);
            userEntity.setClassEntity(classEntity.get());

            // first delete class
            this.userJpaRepository.deleteByClassAndNif(classEntity.get().getId(), userEntity.getNif());

            // then save new one
            ClassEntity newClassToSave = this.classJpaRepository.findByTime(classDto.getTime());
            if (newClassToSave != null) {
                newClassToSave.getUserList().add(userEntity);
                this.classJpaRepository.save(newClassToSave);
            } else {
                throw new Exception("Class time is not scheduled");
            }
        } else {
            throw new Exception("No class found");
        }
    }

    @Override
    public List<ClassDto> getClassesDtoByUser(String nif) {
        List<ClassEntity> classesByUser = this.classJpaRepository.findByUserNif(nif);
        List<ClassDto> classDtoList = new ArrayList<>();
        for (ClassEntity classEntity : classesByUser) {
            ClassDto classDto = this.classDtoAndEntityMapper.mapFromClassEntityToClassDto(classEntity);
            classDtoList.add(classDto);
        }
        return classDtoList;
    }
}
