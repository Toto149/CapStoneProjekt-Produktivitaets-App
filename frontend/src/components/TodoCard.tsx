import {Todo} from "../model/Todo.ts";

export default function TodoCard(props : Readonly<Todo>){

    /**function importanceColor(props ){
        switch(props.gradeOfImportance) {
            case "Barely_Important":
                return "Yellow"
            case "Moderately_Important":
                return "SandyBrown"
            case "Important":
                return "Peru"
            case "Quite_Important":
                return "Siena"
            case "Very_Important":
                return "SaddleBrown"
        }
    }
    const color = importanceColor(props);
    const style2 = {
        backgroundColor: color
    }
        **/
    //<div className="color-importance-idication-box" style={style2} > -->

// </div> -->
    return(

        <div className="container">
                <h3>{props.title}</h3>
                <p>{props.description}</p>
                <ul>
                    <li>Beginn: {props.startDate.toDateString()}</li>
                    <li>Deadline: {props.deadline.toDateString()}</li>
                </ul>
        </div>

    );
}