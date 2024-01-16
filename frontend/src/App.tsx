import {useEffect, useState} from 'react'
import './App.css'
import Calendar from "react-calendar";
import TodoCard from "./components/TodoCard.tsx";
import {Todo} from "./model/Todo.ts";
import "./Calender.css"
import axios from "axios";

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];



function App() {
    const [value, setValue] = useState<Value>(new Date());
    const [todos, setTodos] = useState<Todo[]>([{
        id: "1",
        title: "test",
        description: "testetstets",
        startDate: new Date(),
        deadline: new Date()
    }])

    const fetchTodos = () => {
        axios.get("/todo/")
            .then(response => setTodos(response.data))
            .catch( error => console.error("Error retrieving data: ", error));
    };

    useEffect(() => {
        alert("lol");
        fetchTodos();
        alert("lol2");
    }, []);




  return (
    <div>
      <Calendar onChange={setValue} value={value} />
        {
            todos.map(
                todo => {
                    if(!(value) || "getDate" in value
                        && value >= todo.startDate
                        && value <= todo.deadline
                    )
                    {
                        return (   <TodoCard
                                    key={todo.id}
                                    id={todo.id}
                                    title={todo.title}
                                    description={todo.description}
                                    startDate={todo.startDate}
                                    deadline={todo.deadline}
                                />
                            );
                    }
                }
            )
    }
    </div>
  )
}

export default App
