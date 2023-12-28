package adrian.tfm.crossfit.classes.service;

import adrian.tfm.crossfit.classes.domain.port.ClassDto;
import adrian.tfm.crossfit.classes.dto.request.ClassRequest;
import adrian.tfm.crossfit.classes.dto.response.ClassExerciseUserResponse;
import adrian.tfm.crossfit.classes.dto.response.ClassResponse;

import java.util.List;

public interface ClassService {
    List<ClassResponse> getAllClasses();

    List<ClassResponse> getClassesByUser(String nif);

    void bookClass(ClassRequest classRequest, String nif) throws Exception;

    void removeClass(ClassRequest classRequest, String nif) throws Exception;

    void changeBookClass(ClassRequest classRequest, Long id, String nif) throws Exception;
}
