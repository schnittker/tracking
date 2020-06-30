package schnittker.tracking.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author markus schnittker
 */
@Getter
@Setter
public class SchedulerModel {
    private int id;
    private Integer projectsId;
    private String projectName;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private String task;
}
