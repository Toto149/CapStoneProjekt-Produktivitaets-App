export type TodoPost = {
    id: string,
    title: string,
    description: string,
    startDate: Date,
    deadline: Date,
    gradeOfImportance:  1|2|3|4|5,
    timeToComplete: string
}
