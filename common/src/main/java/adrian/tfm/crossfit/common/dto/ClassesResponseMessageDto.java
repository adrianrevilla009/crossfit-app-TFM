package adrian.tfm.crossfit.common.dto;

public class ClassesResponseMessageDto {
    String classDtoList;

    public ClassesResponseMessageDto() {
    }

    public ClassesResponseMessageDto(String classDtoList) {
        this.classDtoList = classDtoList;
    }

    public String getClassDtoList() {
        return classDtoList;
    }

    public void setClassDtoList(String classDtoList) {
        this.classDtoList = classDtoList;
    }
}
