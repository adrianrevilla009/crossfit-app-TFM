package adrian.tfm.crossfit.classes.infraestructure;

import adrian.tfm.crossfit.classes.domain.port.ClassDto;
import adrian.tfm.crossfit.classes.domain.port.ClassRepository;
import adrian.tfm.crossfit.classes.infraestructure.model.ClassEntity;
import adrian.tfm.crossfit.classes.infraestructure.repository.ClassJpaRepository;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassRepositoryAdapter implements ClassRepository {

    private ClassJpaRepository classJpaRepository;
    private Mapper mapper;

    public ClassRepositoryAdapter(ClassJpaRepository classJpaRepository, Mapper mapper) {
        this.classJpaRepository = classJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void saveClassList(List<ClassDto> classDtoList) {
        List<ClassEntity> classEntityList = new ArrayList<>();
        for (ClassDto classDto : classDtoList) {
            ClassEntity classEntity = this.mapper.map(classDto, ClassEntity.class);
            classEntityList.add(classEntity);
        }

        this.classJpaRepository.saveAll(classEntityList);
    }
}
