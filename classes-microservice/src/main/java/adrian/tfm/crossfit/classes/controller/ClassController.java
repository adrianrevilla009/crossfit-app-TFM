package adrian.tfm.crossfit.classes.controller;

import adrian.tfm.crossfit.classes.dto.response.ClassResponse;
import adrian.tfm.crossfit.classes.service.ClassService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/")
    public List<ClassResponse> getAllClasses() {
        return this.classService.getAllClasses();
    }
}
