package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberSessionObject {
    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private Integer memCountryId;
    private Integer organizationId;

    public MemberSessionObject(String username, String email, String password, String name,String surname, LocalDate dateOfBirth, String address, String phone, Integer memCountryId, Integer organizationId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.memCountryId = memCountryId;
        this.organizationId = organizationId;
        this.surname = surname;
    }
}
