package pl.com.tt.restapp.feignclient.domainfeignclient;

import lombok.Data;

@Data
public class TaskFeign {

    private Integer id;
    private String name;
    private String description;
    private String priority;
    private String endDate;
    private Integer status;
    private Integer occupied;

}
