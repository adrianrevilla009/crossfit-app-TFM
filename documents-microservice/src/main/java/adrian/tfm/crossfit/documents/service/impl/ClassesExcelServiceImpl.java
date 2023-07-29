package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.dto.UserDto;
import adrian.tfm.crossfit.documents.mapper.UserDtoAndUserMapper;
import adrian.tfm.crossfit.documents.model.Document;
import adrian.tfm.crossfit.documents.repository.DocumentRepository;
import adrian.tfm.crossfit.documents.service.ClassesExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassesExcelServiceImpl implements ClassesExcelService {
    private final Logger logger = LoggerFactory.getLogger(ClassesExcelServiceImpl.class);

    @Value("${documents.generation.path}")
    private String documentsGenerationPath;

    @Value("${documents.generation.name}")
    private String documentsGenerationName;

    private UserDtoAndUserMapper userDtoAndUserMapper;
    private DocumentRepository documentRepository;

    public ClassesExcelServiceImpl(UserDtoAndUserMapper userDtoAndUserMapper,
                                   DocumentRepository documentRepository) {
        this.userDtoAndUserMapper = userDtoAndUserMapper;
        this.documentRepository = documentRepository;
    }

    @Override
    public Document createExcel(String nif, List<ClassDto> classDtoList) throws Exception {
        logger.info("[CREATE EXCEL] Excel creation started");

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Classes Data");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Time");
            headerRow.createCell(2).setCellValue("User List");
            headerRow.createCell(3).setCellValue("Exercise List");

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            int rowNum = 1;
            for (ClassDto classDto : classDtoList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(classDto.getName());
                row.createCell(1).setCellValue(classDto.getTime().format(dateTimeFormatter));

                Cell userListCell = row.createCell(2);
                userListCell.setCellValue(formatList(classDto.getUserList()));

                Cell exerciseListCell = row.createCell(3);
                exerciseListCell.setCellValue(formatList(classDto.getExerciseForClassDtoList()));
            }

            File folder = new File(documentsGenerationPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String docName = nif + "-" + UUID.randomUUID() + "-" + documentsGenerationName;

            try (FileOutputStream fileOut = new FileOutputStream(documentsGenerationPath + docName)) {
                workbook.write(fileOut);
            }

            Optional<UserDto> user = this.getUserFromClassDtoList(nif, classDtoList.get(0));
            if (user.isPresent()) {
                Document doc = this.createDocument(workbook, docName, user.get());
                this.documentRepository.save(doc);
            }

            logger.info("[CREATE EXCEL] Excel creation finished");

        } catch (Exception e) {
            logger.error("[CREATE EXCEL] Excel creation error");
            e.printStackTrace();
            throw new Exception();
        }
        return null;
    }

    private static String formatList(List<?> list) {
        return list.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(", ", ",\n");
    }

    private Document createDocument(Workbook workbook, String docName, UserDto user) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        String fileAsString = byteArrayOutputStream.toString();

        return Document.builder()
                .file(fileAsString)
                .name(docName)
                .extension(documentsGenerationName.split("\\.")[1])
                .user(this.userDtoAndUserMapper.userDtoToUserMap(user))
                .build();
    }

    private Optional<UserDto> getUserFromClassDtoList(String nif, ClassDto classDto) {
        return classDto.getUserList().stream().filter(u -> u.getNif().equals(nif)).findFirst();
    }
}
