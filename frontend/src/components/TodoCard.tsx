import "./TodoCard.css"
import React, {ChangeEvent, FormEvent, useState} from "react";

export type propsTodo = {
    id:string,
    title:string,
    description:string,
    startDate: Date,
    deadline: Date,
    delete: (id:string) => void,
    submitHandler: (id:string, event:FormEvent<HTMLFormElement>) => void,
    setTitle: React.Dispatch<React.SetStateAction<string>>,
    setDes : React.Dispatch<React.SetStateAction<string>>,
    setStart : React.Dispatch<React.SetStateAction<Date>>,
    setDateDeadline : React.Dispatch<React.SetStateAction<Date>>,
    setGradeOfImportanceEdit : React.Dispatch<React.SetStateAction<string>>,
    setTimeToCompleteEdit : React.Dispatch<React.SetStateAction<string>>,
    titleEdit: string,
    descriptionEdit : string,
    startDateEdit: Date,
    deadlineEdit: Date
    gradeOfImportanceEdit: string,
    timeToCompleteEdit:string
}

export default function TodoCard(props : Readonly<propsTodo>){
    const [isEditClicked, setIsEditClicked] = useState<boolean>(false);
    const [isSubmitClicked, setIsSubmitClicked] = useState<boolean>(false);
    const [isDetailClicked, setIsDetailClicked] = useState<boolean>(false);
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

    return(
        <div>
            {!isDetailClicked &&
                <div>
                    <div className="button-title-container">
                        <div className="title-container" >
                            <h3>{props.title}</h3>
                        </div>
                        <div className="detail-button-container">
                            <button onClick={handleDetail}>+</button>
                        </div>
                    </div>
                    <p>Beginn: {props.startDate.toLocaleDateString() + " " + props.startDate.toLocaleTimeString()} <br/>
                        Deadline: {props.deadline.toLocaleDateString() + " " + props.deadline.toLocaleTimeString()}</p>

                </div>}
        {isDetailClicked && <div className="container">
            <div className="card">
                    <div className="button-title-container">
                        <div className="title-container" >
                            <h3>{props.title}</h3>
                         </div>
                        <div className="detail-button-container">
                            <button onClick={handleDetail}>-</button>
                        </div>
                    </div>
                    <p>{props.description}</p>
                    <ul>
                        <li>Beginn: {props.startDate.toLocaleDateString() + " " + props.startDate.toLocaleTimeString()}</li>
                        <li>Deadline: {props.deadline.toLocaleDateString() + " " + props.deadline.toLocaleTimeString()}</li>
                    </ul>
                {isEditClicked && !isSubmitClicked &&
                    <form onSubmit={event => props.submitHandler(props.id,event)} className="todo-form">
                        <div className="form-element">
                            <label htmlFor="todo-title" > Choose a title:   </label>
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
                            <label htmlFor="start-date-time" > Choose a starting Date:     </label>
                            <input type="datetime-local"
                                   value={formatDate(props.startDateEdit)}
                                   onChange={event => handleStartDateChange(event)}
                            />
                        </div>
                        <div className="form-element">
                            <label htmlFor="deadline-time"> Choose a deadline:     </label>
                            <input type="datetime-local"
                                   id="deadline-time"
                                   name="deadline-time"
                                   value={formatDate(props.deadlineEdit)}
                                   onChange={event => handleDeadlineChange(event)}
                            />

                        </div>
                        <button className="submit-button" onSubmit={event => props.submitHandler(props.id,event)}>Submit</button>
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