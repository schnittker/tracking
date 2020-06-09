package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerModel {
    private int id;
    private String projectName;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
}
