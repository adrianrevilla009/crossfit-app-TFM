package adrian.tfm.crossfit.classes.infraestructure;

import adrian.tfm.crossfit.classes.domain.port.*;
import adrian.tfm.crossfit.classes.infraestructure.mapper.ClassDtoAndEntityMapper;
import adrian.tfm.crossfit.classes.infraestructure.model.ClassEntity;
import adrian.tfm.crossfit.classes.infraestructure.model.ExerciseEntity;
import adrian.tfm.crossfit.classes.infraestructure.model.ExerciseForClassEntity;
import adrian.tfm.crossfit.classes.infraestructure.model.UserEntity;
import adrian.tfm.crossfit.classes.infraestructure.repository.ClassJpaRepository;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassRepositoryAdapter implements ClassRepository {

    private ClassJpaRepository classJpaRepository;
    private Mapper mapper;

    private ClassDtoAndEntityMapper classDtoAndEntityMapper;

    public ClassRepositoryAdapter(ClassJpaRepository classJpaRepository, Mapper mapper,
                                  ClassDtoAndEntityMapper classDtoAndEntityMapper) {
        this.classJpaRepository = classJpaRepository;
        this.mapper = mapper;
        this.classDtoAndEntityMapper = classDtoAndEntityMapper;
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
}
