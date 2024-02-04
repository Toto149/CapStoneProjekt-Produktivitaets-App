package com.example.backend.model;

import java.time.LocalDateTime;
import java.util.*;

public record Todo (String id,
                    String title,
                    String description,
                    LocalDateTime startDate,
                    LocalDateTime deadline,
                    Importance gradeOfImportance,
                    CompletionTime timeToComplete) implements Comparable<Todo>, Comparator<Todo> {


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
                if( this.timeToComplete.amount()> o.timeToComplete.amount()){
                    return 1;
                }
                else if (this.timeToComplete.amount()< o.timeToComplete.amount()){
                    return -1;
                }
                else return 0;
            }
            else return this.deadline.compareTo(o.deadline);
        }
        else if(scoreOfImportance.get(this.gradeOfImportance)>scoreOfImportance.get(o.gradeOfImportance)){
            return -1;
        }
        return 1;
    }
    @Override
    public int compare(Todo o1, Todo o2){
        return o1.compareTo(o2);
    }
}
