package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.model.Document;
import adrian.tfm.crossfit.documents.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    private final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Override
    public Document createExcel(String nif, List<ClassDto> classDtoList) {
        logger.info("HA LLEGADO A SU DESTINO");
        return null;
    }
}
