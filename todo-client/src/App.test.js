import { render, screen } from '@testing-library/react';
import TodoList from './App';

test('renders test id elements', () => {
  render(<TodoList />);
  
  const newTodo = screen.getByTestId("newTodoText");
  expect(newTodo).toBeInTheDocument();

  const checkbox = screen.getByTestId("checkbox");
  expect(checkbox).toBeInTheDocument();

  const removeBtn = screen.getByTestId("removeBtn");
  expect(removeBtn).toBeInTheDocument();
  
  const editBtn = screen.getByTestId("editBtn");
  expect(editBtn).toBeInTheDocument();
});

