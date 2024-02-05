import {TimeToComplete} from "../model/Todo.ts";

export function formatDate(date: Date){
    return date.toISOString().slice(0,16);
}

export function formatTimeToComplete(timeToComplete : TimeToComplete){
    return timeToComplete.amount.toString() + " " + timeToComplete.timeUnit.toString().toLowerCase()
}