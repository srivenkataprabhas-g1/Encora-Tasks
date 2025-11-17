import React from 'react';
import Card from './Card.jsx';

export default function Counter() {
  const [count, setCount] = React.useState(0);
  return (
    <Card
      title="Counter Component (useState)"
      description="Demonstrates local state management with the useState hook."
    >
      <div style={{ fontSize: '2em', margin: '20px 0', color: '#667eea' }}>
        Count: {count}
      </div>
      <div>
        <button onClick={() => setCount(count + 1)}>+ Increment</button>
        <button onClick={() => setCount(count - 1)}>- Decrement</button>
        <button onClick={() => setCount(0)}>Reset</button>
      </div>
      <p style={{ marginTop: 10, fontSize: '0.9em' }}>
        <strong>How it works:</strong> Each button calls setCount, updating the local state, and causes a re-render.
      </p>
    </Card>
  );
}