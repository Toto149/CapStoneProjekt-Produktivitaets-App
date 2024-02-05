export type Todo = {
    id: string,
    title: string,
    description: string,
    startDate: Date,
    deadline: Date,
    gradeOfImportance:  "BARELY_IMPORTANT"|
        "MODERATELY_IMPORTANT"|
        "IMPORTANT" |
        "QUITE_IMPORTANT"|
        "VERY_IMPORTANT",
    timeToComplete: TimeToComplete
}
export type TimeToComplete = {
    amount : number,
    timeUnit : string
}