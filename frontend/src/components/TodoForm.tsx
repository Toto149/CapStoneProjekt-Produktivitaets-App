import React, {ChangeEvent, FormEvent} from "react";



export type TodoSubmit = {
    title: string,
    description: string,
    startDate: Date,
    deadline: Date,
    submitHandler: (event:FormEvent<HTMLFormElement>) => void,
    setTitle: React.Dispatch<React.SetStateAction<string>>,
    setDes : React.Dispatch<React.SetStateAction<string>>,
    setStart : React.Dispatch<React.SetStateAction<Date>>,
    setDateDeadline : React.Dispatch<React.SetStateAction<Date>>
}

export default function TodoForm(props: Readonly<TodoSubmit>){

    function formatDate(date: Date){
        return date.toISOString().slice(0,16);
    }
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
                <div className="form-element">
                    <label htmlFor="todo-title" > Choose a title:   </label>
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
                    <label htmlFor="start-date-time" > Choose a starting Date:     </label>
                    <input type="datetime-local"
                       value={formatDate(props.startDate)}
                       onChange={event => handleStartDateChange(event)}
                    />
                </div>
                <div className="form-element">
                    <label htmlFor="deadline-time"> Choose a deadline:     </label>
                    <input type="datetime-local"
                       id="deadline-time"
                       name="deadline-time"
                       value={formatDate(props.deadline)}
                       onChange={event => handleDeadlineChange(event)}
                    />
                </div>
                <button onSubmit={event=> props.submitHandler(event)}> Submit </button>
            </form>
            </div>
          </div>
    );
}