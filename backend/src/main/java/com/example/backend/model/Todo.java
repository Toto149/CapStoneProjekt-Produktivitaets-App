package com.example.backend.model;

import java.time.LocalDateTime;
import java.util.Comparator;

public record Todo (String id,
                    String title,
                    String description,
                    LocalDateTime startDate,
                    LocalDateTime deadline,
                    Importance gradeOfImportance,
                    CompletionTime timeToComplete) implements Comparable<Todo>, Comparator<Todo> {
    @Override
    public int compareTo(Todo o) {
        if(this.deadline.compareTo(o.deadline) == 0){
          if(this.timeToComplete.amount()>o.timeToComplete.amount()) return -1;
          if(this.timeToComplete.amount()==o.timeToComplete.amount()) return 0;
          if(this.timeToComplete.amount()<o.timeToComplete.amount()) return 1;
        }
        return -this.deadline.compareTo(o.deadline);
    }
    @Override
    public int compare(Todo o1, Todo o2){
        return o1.compareTo(o2);
    }
}
