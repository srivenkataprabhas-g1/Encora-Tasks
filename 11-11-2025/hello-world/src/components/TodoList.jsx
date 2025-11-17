import React from 'react';
import Card from './Card.jsx';

export default function TodoList() {
  const [todos, setTodos] = React.useState([
    { id: 1, text: 'Learn React Basics' },
    { id: 2, text: 'Understand JSX' },
    { id: 3, text: 'Master Hooks' }
  ]);
  const [input, setInput] = React.useState('');

  const addTodo = () => {
    if (input.trim() === '') return;
    setTodos([...todos, { id: Date.now(), text: input }]);
    setInput('');
  };

  const removeTodo = id => setTodos(todos.filter(t => t.id !== id));

  return (
    <Card
      title="Todo List Component (Controlled Input, List Rendering)"
      description="Shows forms, controlled input, list rendering with .map(), and removing items."
    >
      <div>
        <input
          type="text"
          placeholder="Enter a new todo..."
          value={input}
          onChange={e => setInput(e.target.value)}
          onKeyDown={e => e.key === 'Enter' && addTodo()}
        />
        <button onClick={addTodo}>Add Todo</button>
      </div>
      <ul style={{ marginTop: 12, listStyle: 'none', padding: 0 }}>
        {todos.map(todo => (
          <li
            key={todo.id}
            style={{
              background: '#fff',
              padding: 8,
              marginBottom: 6,
              borderRadius: 5,
              display: 'flex',
              justifyContent: 'space-between'
            }}
          >
            <span>{todo.text}</span>
            <button onClick={() => removeTodo(todo.id)} style={{ background: '#e74c3c', color: "#fff" }}>Delete</button>
          </li>
        ))}
      </ul>
      <p style={{ fontSize: '0.9em' }}>
        <strong>Concepts: </strong>
        The input is controlled by React state, <code>.map()</code> renders the list, and keys keep rendering efficient.
      </p>
    </Card>
  );
}