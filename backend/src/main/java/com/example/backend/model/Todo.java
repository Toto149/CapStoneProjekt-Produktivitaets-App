package com.example.backend.model;

import java.time.LocalDateTime;
import java.util.*;

public record Todo (String id,
                    String title,
                    String description,
                    LocalDateTime startDate,
                    LocalDateTime deadline,
                    Importance gradeOfImportance,
                    CompletionTime timeToComplete) implements Comparable<Todo>{


    @Override
    public int compareTo(Todo o) {
        Map<Importance,Integer> scoreOfImportance = new HashMap<>();
        scoreOfImportance.put(Importance.BARELY_IMPORTANT,1);
        scoreOfImportance.put(Importance.MODERATELY_IMPORTANT,2);
        scoreOfImportance.put(Importance.IMPORTANT,3);
        scoreOfImportance.put(Importance.QUITE_IMPORTANT,4);
        scoreOfImportance.put(Importance.VERY_IMPORTANT,5);
        if(Objects.equals(scoreOfImportance.get(this.gradeOfImportance), scoreOfImportance.get(o.gradeOfImportance))) {
            if(this.deadline.equals(o.deadline)){
                return Integer.compare(this.timeToComplete.amount(), o.timeToComplete.amount());
            }
            else return this.deadline.compareTo(o.deadline);
        }
        else if(scoreOfImportance.get(this.gradeOfImportance)>scoreOfImportance.get(o.gradeOfImportance)){
            return -1;
        }
        return 1;
    }


}
