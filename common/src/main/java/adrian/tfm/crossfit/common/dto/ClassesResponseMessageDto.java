package adrian.tfm.crossfit.common.dto;

public class ClassesResponseMessageDto {
    String classDtoList; // is sent as json not to reply all classes in common module

    String nif;

    public ClassesResponseMessageDto() {
    }

    public ClassesResponseMessageDto(String classDtoList, String nif) {
        this.classDtoList = classDtoList;
        this.nif = nif;
    }

    public String getClassDtoList() {
        return classDtoList;
    }

    public void setClassDtoList(String classDtoList) {
        this.classDtoList = classDtoList;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
