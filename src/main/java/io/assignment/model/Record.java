package io.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Record {

    private Integer  personnelId;
    //Integer projectId;
    private LocalDate startDate;
    private LocalDate endDate;
}
