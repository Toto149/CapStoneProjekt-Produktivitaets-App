import {Todo} from "../model/Todo.ts";

export default function TodoCard(props : Todo){

    function importanceColor(props : Todo){
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
    return(
        <>
            <div className="container">
                <div className="color-importance-idication-box" style={style2} >

                </div>
                <h3>{props.title}</h3>


            </div>
        </>
    );
}