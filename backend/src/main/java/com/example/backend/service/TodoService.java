package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.TodoRepository;
import com.example.backend.model.Importance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repo;
    private final IdService idService;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public List<Todo> getAllTodos() {
        List<TodoDB> todos = repo.findAll();
        List<Todo> todos1 = transformTodoDBToTodo(todos); //dequeToList(sortAfterTimeliness((transformTodoDBToTodo(todos))));
        List<Todo> sortableTodos = new ArrayList<>(todos1);
        sortableTodos.sort(Todo::compareTo);

        return sortableTodos;
    }

    public boolean deleteTodo(String id){
        repo.deleteById(id);
        return !repo.existsById(id);

    }

    private String transformIntGradeOfImportanceToString(int gradeOfImportance){

        return switch (gradeOfImportance) {
            case 1 -> "BARELY_IMPORTANT";
            case 2 -> "MODERATELY_IMPORTANT";
            case 3 -> "IMPORTANT";
            case 4 -> "QUITE_IMPORTANT";
            case 5 -> "VERY_IMPORTANT";
            default -> throw new IllegalArgumentException("Must be a integer between 1 and 5 inclusively");
        };
    }
    public Todo saveTodo(PostTodo todoToSave) {
        TodoDB todoSavedToDB = repo.save(
                new TodoDB(
                idService.generateId(),
                todoToSave.title(),
                todoToSave.description(),
                todoToSave.startDate().formatted(dateTimeFormatter),
                todoToSave.deadline().formatted(dateTimeFormatter),
                transformIntGradeOfImportanceToString(todoToSave.gradeOfImportance()),
                todoToSave.timeToComplete()
        ));
        List<TodoDB> list = List.of(todoSavedToDB);
        return transformTodoDBToTodo(list).get(0);
    }

    public Todo updateTodo(String id, PostTodo todoToUpdate) {

        TodoDB todoToUpdateDB = repo.save(new TodoDB(
                id,
                todoToUpdate.title(),
                todoToUpdate.description(),
                todoToUpdate.startDate().formatted(dateTimeFormatter),
                todoToUpdate.deadline().formatted(dateTimeFormatter),
                transformIntGradeOfImportanceToString(todoToUpdate.gradeOfImportance()),
                todoToUpdate.timeToComplete()
                ));
        List<TodoDB> list = List.of(todoToUpdateDB);
        return transformTodoDBToTodo(list).get(0);
    }
    public static CompletionTime transformStringToCompletionTime(String timeString){
        //The String contains of the int Value and the unit like "100 hours", "100 minutes" etc
        String[] timeAndUnitInArray = timeString.split(" ");
        int amount = Integer.parseInt(timeAndUnitInArray[0]);
        TimeUnit unit = TimeUnit.valueOf("HOURS");
        return new CompletionTime(amount,unit);
    }
    public static List<Todo> transformTodoDBToTodo(List<TodoDB> todos){
        return todos.stream().map(
                todoInter -> new Todo(
                        todoInter.id(),
                        todoInter.title(),
                        todoInter.description(),
                        LocalDateTime.parse(todoInter.startDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        LocalDateTime.parse(todoInter.deadline(), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        Importance.valueOf(todoInter.gradeOfImportance()),
                        transformStringToCompletionTime(todoInter.timeToComplete())
                )
                ).toList();
    }

     public static  Deque<Todo> sortAfterTimeliness(List<Todo> todos) {
         todos.sort(Todo::compareTo);
         Map<Importance, Integer> importanceIntegerMap = new HashMap<>();
         importanceIntegerMap.put(Importance.BARELY_IMPORTANT, 1);
         importanceIntegerMap.put(Importance.MODERATELY_IMPORTANT, 2);
         importanceIntegerMap.put(Importance.IMPORTANT, 4);
         importanceIntegerMap.put(Importance.QUITE_IMPORTANT, 10);
         importanceIntegerMap.put(Importance.VERY_IMPORTANT, 22);
         List<Integer> weightsForImportance = new ArrayList<>();
         List<Integer> numberOfVectorsEndBeforeCurrentInterval = new ArrayList<>();
         List<Integer> result = new ArrayList<>();


         weightsForImportance.add(0);
         numberOfVectorsEndBeforeCurrentInterval.add(0);
         result.add(0);
         /**for(To-do to-do : todos) {
             weightsForImportance.add(importanceIntegerMap.get(to-do.gradeOfImportance()));
             int numberOfVectorsBefore = 0;
             for(To-do todo2 : todos){
                 if(todo2.deadline().compareTo(to-do.deadline())==1){
                     numberOfVectorsBefore++;
                 }

             }
             numberOfVectorsEndBeforeCurrentInterval.add(numberOfVectorsBefore);
             for (int i = 1; i < todos.size() + 1; i++) {
                 intervalVectors[i][0]=to-do.startDate();
                 intervalVectors[i][1]=to-do.deadline();
             }
         }
         for ( int j =1; j<todos.size() + 1; j++ ){
             result.add(Math.max(numberOfVectorsEndBeforeCurrentInterval.get(j),result.get(j-1)));
         }
         return scheduler(todos.size(),weightsForImportance,numberOfVectorsEndBeforeCurrentInterval,result,finalSchedule,intervalVectors,todos,new ArrayList<>());
        **/
         int[] p = computePreviousIntervals(todos);

         // Step 3: Dynamic Programming to compute maximum weight for schedules ending at each to-do
         int[] dp = new int[todos.size() + 1];
         for (int i = 1; i <= todos.size(); i++) {
             int weight = importanceIntegerMap.get(todos.get(i - 1).gradeOfImportance());
             dp[i] = Math.max(dp[i - 1], weight + dp[p[i - 1]]);
         }

         // Step 4: Find solution set
         Deque<Todo> schedule = new LinkedList<>();
         for (int i = todos.size(); i > 0; ) {
             if (dp[i] == dp[i - 1]) {
                 i--;
             } else {

                 schedule.addFirst(todos.get(i - 1));
                 i = p[i - 1];
             }
         }

         return schedule;
     }
    private static int[] computePreviousIntervals(List<Todo> todos) {
        int[] p = new int[todos.size()];
        for (int i = 0; i < todos.size(); i++) {
            LocalDateTime todoStart = todos.get(i).startDate();
            p[i] = 0; // Default: no previous non-conflicting to-do
            for (int j = i - 1; j >= 0; j--) {
                if (todos.get(j).deadline().isBefore(todoStart)) {
                    p[i] = j + 1; // Found a non-conflicting to-do, adjust index to be 1-based
                    break;
                }
            }
        }
        return p;
    }
    /**
     public static List<To-do> dequeToList(Deque<To-do> todos){
        List<To-do> todosTransformed = new ArrayList<>();
        for(To-do ignored : todos){
            todosTransformed.add(todosTransformed.getFirst());
        }
        return todosTransformed;
     }
    public static Deque<To-do> scheduler(int arrayParameter, List<Integer> weights,List<Integer> numberOfIntervals, List<Integer> result ,Deque<To-do> finalSchedule, LocalDateTime[][] intervalVectors, List<To-do> todos,List<To-do> todoPosition){

        if(arrayParameter == 0){
            return finalSchedule;
        }
        else if( weights.get(arrayParameter) + result.get(numberOfIntervals.get(arrayParameter)) >= result.get(arrayParameter-1)) {
            for(To-do to-do : todos){
                for(LocalDateTime[] interval: intervalVectors) {
                    if (to-do.deadline().isEqual(interval[1])) {
                        todoPosition.add(to-do);
                        todos.remove(to-do);
                    }
                }
            }
    finalSchedule.addFirst(todoPosition.getFirst());
            scheduler(numberOfIntervals.get(arrayParameter),weights, numberOfIntervals,result,finalSchedule,intervalVectors,todos,todoPosition);


        else{
            scheduler(arrayParameter-1,weights,numberOfIntervals,result,finalSchedule,intervalVectors,todos,todoPosition);
        }
        return finalSchedule;
    }
    **/

}
