package adrian.tfm.crossfit.classes.controller;

import adrian.tfm.crossfit.classes.dto.request.ClassRequest;
import adrian.tfm.crossfit.classes.dto.response.ClassExerciseUserResponse;
import adrian.tfm.crossfit.classes.dto.response.ClassResponse;
import adrian.tfm.crossfit.classes.service.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);

    private ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/")
    public List<ClassResponse> getAllClasses() {
        logger.info("### getAllClasses ###");
        return this.classService.getAllClasses();
    }

    @GetMapping("/user/{nif}")
    public List<ClassResponse> getClassesByUser(@PathVariable("nif") String nif) {
        logger.info("### getClassesByUser ###");
        return this.classService.getClassesByUser(nif);
    }

    @PostMapping("/user/{nif}")
    public ResponseEntity bookClass(@RequestBody ClassRequest classRequest, @PathVariable("nif") String nif) {
        logger.info("### bookClass ###");
        try {
            this.classService.bookClass(classRequest, nif);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/user/{nif}")
    public ResponseEntity removeClass(@RequestBody ClassRequest classRequest, @PathVariable("nif") String nif) {
        logger.info("### removeClass ###");
        try {
            this.classService.removeClass(classRequest, nif);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/class/{id}/user/{nif}")
    public ResponseEntity changeBookClass(@RequestBody ClassRequest classRequest,
                                          @PathVariable("id") Long id,
                                          @PathVariable("nif") String nif) {
        logger.info("### changeBookClass ###");
        try {
            this.classService.changeBookClass(classRequest, id, nif);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
