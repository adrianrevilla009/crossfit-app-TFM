package adrian.tfm.crossfit.classes.service.impl;

import adrian.tfm.crossfit.classes.domain.ClassUseCase;
import adrian.tfm.crossfit.classes.domain.mapper.ClassResponseAndDtoMapper;
import adrian.tfm.crossfit.classes.domain.port.ClassDto;
import adrian.tfm.crossfit.classes.dto.response.ClassResponse;
import adrian.tfm.crossfit.classes.service.ClassService;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    private Mapper mapper;

    private ClassUseCase classUseCase;

    private ClassResponseAndDtoMapper classResponseAndDtoMapper;

    public ClassServiceImpl(ClassUseCase classUseCase, Mapper mapper, ClassResponseAndDtoMapper classResponseAndDtoMapper) {
        this.classUseCase = classUseCase;
        this.mapper = mapper;
        this.classResponseAndDtoMapper = classResponseAndDtoMapper;
    }

    @Override
    public List<ClassResponse> getAllClasses() {
        List<ClassDto> classDtoList = this.classUseCase.getAllClasses();

        List<ClassResponse> classResponseList = new ArrayList<>();
        for (ClassDto classDto : classDtoList) {
            classResponseList.add(this.classResponseAndDtoMapper.fromClassDtoToResponse(classDto));
        }
        return classResponseList;
    }


}
