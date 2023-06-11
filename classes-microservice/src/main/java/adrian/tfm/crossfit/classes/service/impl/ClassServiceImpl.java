package adrian.tfm.crossfit.classes.service.impl;

import adrian.tfm.crossfit.classes.domain.ClassUseCase;
import adrian.tfm.crossfit.classes.dto.response.ClassResponse;
import adrian.tfm.crossfit.classes.service.ClassService;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    private Mapper mapper;

    private ClassUseCase classUseCase;

    public ClassServiceImpl(ClassUseCase classUseCase, Mapper mapper) {
        this.classUseCase = classUseCase;
        this.mapper = mapper;
    }

    @Override
    public List<ClassResponse> getAllClasses() {
        // TODO esto falla hay que montar el mapeo manual
        return this.classUseCase.getAllClasses().stream()
                .map(classDto -> this.mapper.map(classDto, ClassResponse.class))
                .collect(Collectors.toList());
    }


}
