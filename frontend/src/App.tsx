import { useState } from 'react'
import './App.css'
import Calendar from "react-calendar";
import TodoCard from "./components/TodoCard.tsx";
import {Todo} from "./model/Todo.ts";

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];



function App() {
  const [value, setValue] = useState<Value>(new Date());

    const listOfTodos: Todo[]= [
        {
            id: "1",
            title: "test",
            description: "test",
            startDate: new Date("2023-01-02"),
            deadline: new Date("2024-01-17")


        }
    ];
  return (
    <div>
      <Calendar onChange={setValue} value={value} />
        {
            listOfTodos.map(
                todo => {
                    if(!(value) || "getDate" in value
                    && todo.startDate.getDate() <= value.getDate()
                    && todo.deadline.getDate() >= value.getDate())
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
