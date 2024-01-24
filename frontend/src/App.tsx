import { useEffect, useState } from 'react'
import './App.css'
import Calendar from "react-calendar";
import TodoCard from "./components/TodoCard.tsx";
import { Todo } from "./model/Todo.ts";
import axios from "axios";
import TodoForm from "./components/TodoForm.tsx";
import './Calendar.css'


type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];



function App() {
    const [value, setValue] = useState<Value>(new Date());
    const [todos, setTodos] = useState<Todo[]>([])

    const [titleValue, setTitleValue] = useState<string>("");
    const [description, setDescription ] = useState<string>("");
    const [startDate, setStartDate] = useState<Date>(new Date());
    const [deadline, setDeadline] = useState<Date>(new Date());

    const [titleValueEdit, setTitleValueEdit] = useState<string>("");
    const [descriptionEdit, setDescriptionEdit ] = useState<string>("");
    const [startDateEdit, setStartDateEdit] = useState<Date>(new Date());
    const [deadlineEdit, setDeadlineEdit] = useState<Date>(new Date());

    function formatToLocalDateTimeString(date: Date): string {
        return date.toISOString().slice(0,16);

    }
    const handleSubmit = () => {
        axios.post("/api/todo/save",
            {
                title: titleValue,
                description: description,
                startDate: formatToLocalDateTimeString(startDate),
                deadline: formatToLocalDateTimeString(deadline)
            })
            .then(response => setTodos([...todos, response.data]))
            .catch(error => console.error("Error posting Data: ", error)
            );

    };
    const handleUpdate = (id:string) => {
        axios.put("/api/todo/save/" + id,
            {
                title:titleValueEdit,
                description: descriptionEdit,
                startDate: formatToLocalDateTimeString(startDateEdit),
                deadline: formatToLocalDateTimeString(deadlineEdit)
            })
            .then(response => response.status === 200 ? alert("Update Successful") : alert("Update was unsuccessful"))
            .catch(error => console.error("Error updating Data: ", error)
            );
    }
    const handleDelete = (id:string) => {
        axios.delete("/api/todo/delete/" + id)
            .then(response => {if(response.data){
                alert("Todo removed succesfully!")
            }})
            .catch(err => alert(err.message));
    };
    const fetchTodos =  () => {
        axios.get("/api/todo/get")
            .then(response => {
                const todos = response.data.map(
                     (todo :Todo) => {
                        return ({
                            id: todo.id,
                            title: todo.title,
                            description: todo.description,
                            startDate: new Date(todo.startDate),
                            deadline: new Date(todo.deadline)
                        });
                    });
                setTodos(todos)
            })
            .catch( error => console.error("Error fetching data: ", error));
    };

    useEffect(() => {
            fetchTodos();
    }, [todos]);




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
                                    delete={handleDelete}
                                    submitHandler={handleUpdate}
                                    setTitle={setTitleValueEdit}
                                    setDes={setDescriptionEdit}
                                    setStart={setStartDateEdit}
                                    setDateDeadline={setDeadlineEdit}
                                    titleEdit={titleValueEdit}
                                    descriptionEdit={descriptionEdit}
                                    startDateEdit={startDateEdit}
                                    deadlineEdit={deadlineEdit}

                                />
                            );
                    }
                }
            )
    }
    <TodoForm title={titleValue}
              description={description}
              startDate={startDate}
              deadline={deadline}
              submitHandler={handleSubmit}
              setTitle={setTitleValue}
              setDes={setDescription}
              setStart={setStartDate}
              setDateDeadline={setDeadline}
    />
    </div>
  )
}

export default App
