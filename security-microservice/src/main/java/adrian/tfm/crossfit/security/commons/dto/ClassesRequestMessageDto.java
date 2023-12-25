package adrian.tfm.crossfit.security.commons.dto;

public class ClassesRequestMessageDto {
    String nif;

    public ClassesRequestMessageDto() {
    }

    public ClassesRequestMessageDto(String nif) {
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
