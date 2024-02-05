import "./TodoCard.css"
import React, {ChangeEvent, FormEvent, useState} from "react";
import {TimeToComplete} from "../model/Todo.ts";
import { formatDate } from "./UtilFunctions.ts"


export type propsTodo = {
    id:string,
    title:string,
    description:string,
    startDate: Date,
    deadline: Date,
    gradeOfImportance: string,
    timeToComplete: TimeToComplete,
    delete: (id:string) => void,
    submitHandler: (id:string, event:FormEvent<HTMLFormElement>) => void,
    setTitle: React.Dispatch<React.SetStateAction<string>>,
    setDes : React.Dispatch<React.SetStateAction<string>>,
    setStart : React.Dispatch<React.SetStateAction<Date>>,
    setDateDeadline : React.Dispatch<React.SetStateAction<Date>>,
    setGradeOfImportanceEdit : React.Dispatch<React.SetStateAction<number>>,
    setTimeToCompleteEdit : React.Dispatch<React.SetStateAction<string>>,
    titleEdit: string,
    descriptionEdit : string,
    startDateEdit: Date,
    deadlineEdit: Date
    gradeOfImportanceEdit: number,
    timeToCompleteEdit: string
}

export default function TodoCard(props : Readonly<propsTodo>){
    const [isEditClicked, setIsEditClicked] = useState<boolean>(false);
    const [isSubmitClicked, setIsSubmitClicked] = useState<boolean>(false);
    const [isDetailClicked, setIsDetailClicked] = useState<boolean>(false);

    function handleStartDateChange(event : ChangeEvent<HTMLInputElement>) {
        props.setStart(new Date(event.target.value));
        event.preventDefault();
    }
    function handleDeadlineChange(event : ChangeEvent<HTMLInputElement>) {
        props.setDateDeadline(new Date(event.target.value));
        event.preventDefault();
    }
    const handleEdit = () => {
        setIsEditClicked(!isEditClicked);
        setIsSubmitClicked(false);
    }

    const handleDetail = () => {
        setIsDetailClicked(!isDetailClicked);
    }

    const handleDelete = () => {
        props.delete(props.id)
    }

    const gradeOfImportanceToText = (gradeOfImportance: string):string => {

        switch (gradeOfImportance){
            case "BARELY_IMPORTANT":
                return "Barely important (1)";
            case "MODERATELY_IMPORTANT":
                return "Moderately important (2)";
            case "IMPORTANT":
                return "Important (3)";
            case "QUITE_IMPORTANT":
                return "Quite important (4)";
            case "VERY_IMPORTANT":
                return "Very important (5)";
            default:
                return "No grade of importance provided";
        }
    }


    return(
        <div>
            {!isDetailClicked &&
                <div>
                    <div className="button-title-container" style={{ position: 'relative' }}>
                        <div className="title-container">
                            <h3>{props.title}</h3>
                        </div>
                        <div className="detail-button-container" >
                            <button onClick={handleDetail}>+</button>
                        </div>
                    </div>
                    <div>
                        <p> Start: {props.startDate.toLocaleDateString() + " " + props.startDate.toLocaleTimeString()} <br/>
                            Deadline: {props.deadline.toLocaleDateString() + " " + props.deadline.toLocaleTimeString()}</p>
                    </div>
                </div>}
        {isDetailClicked && <div className="container" >
            <div className="card" >
                    <div className="button-title-container" style={{ position: 'relative' }}>
                        <div className="title-container" >
                            <h3>{props.title}</h3>
                         </div>
                        <div className="detail-button-container">
                            <button onClick={handleDetail}>-</button>
                        </div>
                    </div>
                    <p>{props.description}</p>
                    <ul>
                        <li>Start: {props.startDate.toLocaleDateString() + " " + props.startDate.toLocaleTimeString()}</li>
                        <li>Deadline: {props.deadline.toLocaleDateString() + " " + props.deadline.toLocaleTimeString()}</li>
                        <li>Grade of Importance: {gradeOfImportanceToText(props.gradeOfImportance.toString())}</li>
                        <li>How long it takes: {props.timeToComplete.amount +" " + props.timeToComplete.timeUnit.toString().toLowerCase()} </li>
                    </ul>
                {isEditClicked && !isSubmitClicked &&
                    <form onSubmit={event => props.submitHandler(props.id,event)} className="todo-form">
                        <div className="form-element">
                            <p>  Choose a title:   </p>
                            <input
                                type="text"
                                value={props.titleEdit}
                                onChange={event => {props.setTitle(event.target.value)}}/>
                        </div>
                        <div className="form-element">
                            <p >What do you want to be done? Write an apt description here:</p>
                            <input
                                value={props.descriptionEdit}
                                onChange={event => {props.setDes(event.target.value)}}
                            />
                        </div>
                        <div className="form-element">
                            <p>  Choose a starting Date:     </p>
                            <input type="datetime-local"
                                   value={formatDate(props.startDateEdit)}
                                   onChange={event => handleStartDateChange(event)}
                            />
                        </div>
                        <div className="form-element">
                            <p> Choose a deadline:     </p>
                            <input type="datetime-local"
                                   id="deadline-time"
                                   name="deadline-time"
                                   value={formatDate(props.deadlineEdit)}
                                   onChange={event => handleDeadlineChange(event)}
                            />

                        </div>
                        <div className="form-element">
                            <p> Choose a grade of importance ((low) 1-5 (high)):     </p>
                            <input type="number"
                                   id="importance-number"
                                   name="importance-number"
                                   value={props.gradeOfImportanceEdit}
                                   onChange={event =>
                                   {if(parseInt(event.target.value)>5 || parseInt(event.target.value)<0){ alert("Value must between 1 and 5 inclusively")
                                   } else props.setGradeOfImportanceEdit(parseInt(event.target.value))}}
                            />
                        </div>
                        <div className="form-element">
                            <p> How long will the task take? (write it like e.g.: "10 hours"):     </p>
                            <input type="text"
                                   id="completion-time-number"
                                   name="completion-time-number"
                                   value={props.timeToCompleteEdit}
                                   onChange={event => {props.setTimeToCompleteEdit(event.target.value)}}
                            />
                        </div>
                        <p> </p>
                        <button className="submit-button" onSubmit={event => props.submitHandler(props.id, event)}>Submit</button>
                        <p>  </p>
                    </form>}
                <button className="edit-button" onClick={handleEdit}>Edit</button>
                <div className="button-footer">
                    {<button className="delete-button" onClick={handleDelete}>Delete</button>}

                </div>
            </div>
        </div>}
        </div>
    );
}