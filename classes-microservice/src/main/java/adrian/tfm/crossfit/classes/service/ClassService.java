package adrian.tfm.crossfit.classes.service;

import adrian.tfm.crossfit.classes.dto.response.ClassResponse;

import java.util.List;

public interface ClassService {
    List<ClassResponse> getAllClasses();
}
