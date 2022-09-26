package io.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair {

    private Integer personnelId1;
    private Integer personnelId2;
    private Integer projectIdWorkedOn;
    private Integer daysWorkedWith;
}
