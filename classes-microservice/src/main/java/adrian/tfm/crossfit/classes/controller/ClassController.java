package adrian.tfm.crossfit.classes.controller;

import adrian.tfm.crossfit.classes.dto.request.ClassRequest;
import adrian.tfm.crossfit.classes.dto.response.ClassExerciseUserResponse;
import adrian.tfm.crossfit.classes.dto.response.ClassResponse;
import adrian.tfm.crossfit.classes.service.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/{nif}")
    public List<ClassExerciseUserResponse> getClassesByUser(@PathVariable("nif") String nif) {
        return this.classService.getClassesByUser(nif);
    }

    @PostMapping("/user/{nif}")
    public ResponseEntity bookClass(@RequestBody ClassRequest classRequest, @PathVariable("nif") String nif) {
        try {
            this.classService.bookClass(classRequest, nif);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/user/{nif}")
    public ResponseEntity removeClass(@RequestBody ClassRequest classRequest, @PathVariable("nif") String nif) {
        try {
            this.classService.removeClass(classRequest, nif);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/class/{id}/user/{nif}")
    public ResponseEntity changeBookClass(@RequestBody ClassRequest classRequest,
                                          @PathVariable("id") Long id,
                                          @PathVariable("nif") String nif) {
        try {
            this.classService.changeBookClass(classRequest, id, nif);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
