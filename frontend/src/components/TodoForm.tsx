import React, {ChangeEvent, FormEvent} from "react";
import { formatDate } from "./UtilFunctions.ts"


export type TodoSubmit = {
    title: string,
    description: string,
    startDate: Date,
    deadline: Date,
    gradeOfImportance: number,
    timeToComplete: string,
    submitHandler: (event:FormEvent<HTMLFormElement>) => void,
    setTitle: React.Dispatch<React.SetStateAction<string>>,
    setDes : React.Dispatch<React.SetStateAction<string>>,
    setStart : React.Dispatch<React.SetStateAction<Date>>,
    setDateDeadline : React.Dispatch<React.SetStateAction<Date>>,
    setGradeOfImportancePost : React.Dispatch<React.SetStateAction<number>>,
    setTimeToCompletePost : React.Dispatch<React.SetStateAction<string>>
}

export default function TodoForm(props: Readonly<TodoSubmit>){



    function handleStartDateChange(event : ChangeEvent<HTMLInputElement>) {
        props.setStart(new Date(event.target.value));
        event.preventDefault();
    }
    function handleDeadlineChange(event : ChangeEvent<HTMLInputElement>) {
        props.setDateDeadline(new Date(event.target.value));
        event.preventDefault();
    }


    return (
        <div className="form-container">
            <div>
            <form onSubmit={props.submitHandler} className="todo-form">
                <p> Add a Todo: </p>
                <div className="form-element">
                    <p> Choose a title:   </p>
                <input
                    type="text"
                    value={props.title}
                    onChange={event => {props.setTitle(event.target.value); event.preventDefault();}}/>
                </div>
                <div className="form-element">
                    <p >What do you want to be done? Write an apt description here:</p>
                    <input
                    value={props.description}
                    onChange={event => {props.setDes(event.target.value); event.preventDefault()}}
                    />
                </div>
                <div className="form-element">
                    <p> Choose a starting Date:     </p>
                    <input type="datetime-local"
                       value={formatDate(props.startDate)}
                       onChange={event => handleStartDateChange(event)}
                    />
                </div>
                <div className="form-element">
                    <p> Choose a deadline:     </p>
                    <input type="datetime-local"
                       id="deadline-time"
                       name="deadline-time"
                       value={formatDate(props.deadline)}
                       onChange={event => handleDeadlineChange(event)}
                    />
                </div>
                <div className="form-element">
                    <p> Choose a grade of importance (1-5):     </p>
                    <input type="number"
                           id="importance-number"
                           name="importance-number"
                           value={props.gradeOfImportance}
                           onChange={event => {if(parseInt(event.target.value)>5 || parseInt(event.target.value)<0){ alert("Value must between 1 and 5 inclusively")
                           } else props.setGradeOfImportancePost(parseInt(event.target.value))}}
                    />
                </div>
                <div className="form-element">
                    <p> How long will the completion of the task take (in hours):     </p>
                    <input type="text"
                           id="completion-time-number"
                           name="completion-time-number"
                           value={props.timeToComplete}
                           onChange={event => {props.setTimeToCompletePost(event.target.value)}}
                    />
                </div>
                <button onSubmit={event=> props.submitHandler(event)}> Submit </button>

            </form>
                </div>
            </div>
    );
}