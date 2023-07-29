package adrian.tfm.crossfit.documents.service;

import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.model.Document;

import java.util.List;

public interface ExcelService {
    Document createExcel(String nif, List<ClassDto> classDtoList);
}
