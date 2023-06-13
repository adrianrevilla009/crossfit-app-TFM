package adrian.tfm.crossfit.classes.service.impl;

import adrian.tfm.crossfit.classes.domain.ClassUseCase;
import adrian.tfm.crossfit.classes.domain.mapper.ClassRequestAndDtoMapper;
import adrian.tfm.crossfit.classes.domain.mapper.ClassResponseAndDtoMapper;
import adrian.tfm.crossfit.classes.domain.port.ClassDto;
import adrian.tfm.crossfit.classes.dto.request.ClassRequest;
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

    private ClassRequestAndDtoMapper classRequestAndDtoMapper;

    public ClassServiceImpl(ClassUseCase classUseCase, Mapper mapper, ClassResponseAndDtoMapper classResponseAndDtoMapper,
                            ClassRequestAndDtoMapper classRequestAndDtoMapper) {
        this.classUseCase = classUseCase;
        this.mapper = mapper;
        this.classResponseAndDtoMapper = classResponseAndDtoMapper;
        this.classRequestAndDtoMapper = classRequestAndDtoMapper;
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

    @Override
    public List<ClassResponse> getClassesByUser(String nif) {
        List<ClassDto> classDtoList = this.classUseCase.getClassesByUser(nif);

        List<ClassResponse> classResponseList = new ArrayList<>();
        for (ClassDto classDto : classDtoList) {
            classResponseList.add(this.classResponseAndDtoMapper.fromClassDtoToResponse(classDto));
        }
        return classResponseList;
    }

    @Override
    public void bookClass(ClassRequest classRequest, String nif) throws Exception {
        ClassDto classDto = this.classRequestAndDtoMapper.fromClassRequestToDto(classRequest);
        this.classUseCase.bookClass(classDto, nif);
    }

    @Override
    public void removeClass(ClassRequest classRequest, String nif) throws Exception {
        ClassDto classDto = this.classRequestAndDtoMapper.fromClassRequestToDto(classRequest);
        this.classUseCase.removeClass(classDto, nif);
    }

    @Override
    public void changeBookClass(ClassRequest classRequest, Long id, String nif) throws Exception {
        ClassDto classDto = this.classRequestAndDtoMapper.fromClassRequestToDto(classRequest);
        this.classUseCase.changeBookClass(classDto, id, nif);
    }


}
