import {Todo} from "../model/Todo.ts";
import {ChangeEvent, useState} from "react";


export default function TodoForm(props: Readonly<Todo>){
    const [titleValue, setTitleValue] = useState<string>("");
    const [description, setDescription ] = useState<string>("");

    const handleSubmit = (event: ChangeEvent<HTMLFormElement> ) => {
        alert("A title was submitted: " + titleValue)
        event.preventDefault();
    }


    return (
        <>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={titleValue}
                    onChange={event => setTitleValue(event.target.value)}/>
                <input
                    type="text"
                    value={description}
                    onChange={event => setDescription(event.target.value)}
                    />



            </form>
        </>
    );
}