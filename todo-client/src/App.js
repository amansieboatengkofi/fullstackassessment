
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
