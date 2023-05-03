// import logo from './logo.svg';
// import './App.css';

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
        
//         </p>
//       </header>
//     </div>
//   );
// }

// export default App;

//=============
// import React, { useState, useEffect } from 'react';
// import ApiService from './ApiService';

// const TodoList = () => {
//   const [todos, setTodos] = useState([]);
//   const [newTodo, setNewTodo] = useState('');

//   useEffect(() => {
//     const fetchTodos = async () => {
//       const response = await ApiService.getTodos();
//       setTodos(response.data);
//     };
//     fetchTodos();
//   }, []);

//   const handleAddTodo = async e => {
//     e.preventDefault();
//     const todo = { title: newTodo, completed: false };
//     //const todo = {id:1, title: newTodo, completed: false };//hardcode id for now
//     const response = await ApiService.createTodo(todo);
//     setTodos([...todos, response.data]);
//     setNewTodo('');
//   };

//   const handleDeleteTodo = async id => {
//     await ApiService.deleteTodo(id);
//     const newTodos = todos.filter(todo => todo.id !== id);
//     setTodos(newTodos);
//   };

//   const handleEditTodo = async (id, updates) => {
//     await ApiService.updateTodo(id, updates);
//     const updatedTodos = todos.map(todo =>
//       todo.id === id ? { ...todo, ...updates } : todo
//     );
//     setTodos(updatedTodos);
//   };

//   const handleCompleteTodo = async (id, completed) => {
//     await ApiService.updateTodo(id, { completed });
//     const updatedTodos = todos.map(todo =>
//       todo.id === id ? { ...todo, completed } : todo
//     );
//     setTodos(updatedTodos);
//   };

//   return (
//     <div>
//       <h1>Todo List</h1>
//       <form onSubmit={handleAddTodo}>
//         <input
//           type="text"
//           value={newTodo}
//           onChange={e => setNewTodo(e.target.value)}
//           placeholder="Add a new Todo item..."
//         />
//         <button type="submit">Add Todo</button>
//       </form>
//       <ul>
//         {todos.map(todo => (
//           <li key={todo.id}>
//             <input
//               type="checkbox"
//               checked={todo.completed}
//               onChange={e => handleCompleteTodo(todo.id, e.target.checked)}
//             />
//             <span>{todo.title}</span>
//             <button onClick={() => handleDeleteTodo(todo.id)}>Delete</button>
//             <button onClick={() => handleEditTodo(todo.id, { title: 'New Title' })}>Edit</button>
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// };

// export default TodoList;

//============

// import React, { useState, useEffect } from 'react';
// import ApiService from './ApiService';
// import TodoItem from './TodoItem';

// const TodoList = () => {
//   const [todos, setTodos] = useState([]);
//   const [newTodo, setNewTodo] = useState('');

//   useEffect(() => {
//     ApiService.getTodos()
//       .then((response) => {
//         setTodos(response.data);
//       })
//       .catch((error) => {
//         console.log(error);
//       });
//   }, []);

//   const handleNewTodo = (event) => {
//     setNewTodo(event.target.value);
//   };

//   const handleAddTodo = (event) => {
//     event.preventDefault();
//     const todo = {
//       title: newTodo,
//       completed: false,
//     };
//     ApiService.createTodo(todo)
//       .then((response) => {
//         setTodos([...todos, response.data]);
//         setNewTodo('');
//       })
//       .catch((error) => {
//         console.log(error);
//       });
//   };

//   const handleDeleteTodo = (id) => {
//     ApiService.deleteTodo(id)
//       .then(() => {
//         setTodos(todos.filter((todo) => todo.id !== id));
//       })
//       .catch((error) => {
//         console.log(error);
//       });
//   };

//   const handleUpdateTodo = (updatedTodo) => {
//     const newTodos = todos.map((todo) =>
//       todo.id === updatedTodo.id ? updatedTodo : todo
//     );
//     setTodos(newTodos);
//   };

//   return (
//     <div className='todo-list'>
//       <form onSubmit={handleAddTodo}>
//         <input
//           type='text'
//           placeholder='Add a new todo'
//           value={newTodo}
//           onChange={handleNewTodo}
//         />
//         <button type='submit'>Add</button>
//       </form>
//       <ul>
//         {todos.map((todo) => (
//           <TodoItem
//             key={todo.id}
//             todo={todo}
//             onDeleteTodo={handleDeleteTodo}
//             onUpdateTodo={handleUpdateTodo}
//           />
//         ))}
//       </ul>
//     </div>
//   );
// };

// export default TodoList;



import React, { useState, useEffect } from 'react';
import ApiService from './ApiService';

const TodoList = () => {
  const [todos, setTodos] = useState([]);
  const [newTodo, setNewTodo] = useState('');
  const [editingTodoId, setEditingTodoId] = useState(null);
  const [hoveredTodoId, setHoveredTodoId] = useState(null);

  useEffect(() => {
    const fetchTodos = async () => {
      const response = await ApiService.getTodos();
      setTodos(response.data);
    };
    fetchTodos();
  }, []);

  const handleAddTodo = async e => {
    e.preventDefault();
    const todo = { title: newTodo, completed: false };
    const response = await ApiService.createTodo(todo);
    setTodos([...todos, response.data]);
    setNewTodo('');
  };

  const handleDeleteTodo = async id => {
    await ApiService.deleteTodo(id);
    const newTodos = todos.filter(todo => todo.id !== id);
    setTodos(newTodos);
  };

  const handleEditTodo = async (id, updates) => {
    await ApiService.updateTodo(id, updates);
    const updatedTodos = todos.map(todo =>
      todo.id === id ? { ...todo, ...updates } : todo
    );
    setTodos(updatedTodos);
    setEditingTodoId(null);
  };

  const handleCompleteTodo = async (id, completed) => {
    await ApiService.updateTodo(id, { completed });
    const updatedTodos = todos.map(todo =>
      todo.id === id ? { ...todo, completed } : todo
    );
    setTodos(updatedTodos);
  };

  const handleDoubleClickTodo = id => {
    setEditingTodoId(id);
  };

  const handleHoverTodo = id => {
    setHoveredTodoId(id);
  };

  const handleLeaveTodo = () => {
    setHoveredTodoId(null);
  };

  const getActiveTodosCount = () => {
    return todos.filter(todo => !todo.completed).length;
  };

  return (
    <div>
      <h1>Todo List</h1>
      <form onSubmit={handleAddTodo}>
        <input
          type="text"
          value={newTodo}
          onChange={e => setNewTodo(e.target.value)}
          placeholder="Add a new Todo item..."
          data-testid="newTodoText"
        />
        <button type="submit">Add Todo</button>
      </form>
      <div>Active Todos: {getActiveTodosCount()}</div>
      <ul>
        {todos.map(todo => (
          <li
            key={todo.id}
            onDoubleClick={() => handleDoubleClickTodo(todo.id)}
            onMouseEnter={() => handleHoverTodo(todo.id)}
            onMouseLeave={handleLeaveTodo}
          >
            {editingTodoId === todo.id ? (
              <form onSubmit={() => handleEditTodo(todo.id, { title: newTodo })}>
                <input
                  type="text"
                  value={newTodo}
                  onChange={e => setNewTodo(e.target.value)}
                />
                <button type="submit">Save</button>
              </form>
            ) : (
              <>
                <input
                  type="checkbox"
                  checked={todo.completed}
                  onChange={e => handleCompleteTodo(todo.id, e.target.checked)}
                  data-testid="checkbox"
                />
                <span>{todo.title}</span>
                {hoveredTodoId === todo.id && (
                  <>
                    <button data-testid="removeBtn" onClick={() => handleDeleteTodo(todo.id)}>Remove</button>
                    <button data-testid="editBtn" onClick={() => setEditingTodoId(todo.id)}>Edit</button>
                  </>
                )}
              </>
            )}
          </li>
        ))}
      </ul>
      </div>
      );
  };
      
    export default TodoList;
