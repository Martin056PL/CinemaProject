package pl.com.tt.restapp.resttemplate.domainresttemplate;

import lombok.Data;

@Data
public class EmployeeRestTemplate {

    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private String sex;
    private String createDate;
    private String positionId;
}

