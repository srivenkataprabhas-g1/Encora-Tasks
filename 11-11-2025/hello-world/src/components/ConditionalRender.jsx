import React from 'react';
import Card from './Card.jsx';

export default function ConditionalRender() {
  const [visible, setVisible] = React.useState(false);
  const [userType, setUserType] = React.useState('guest');

  return (
    <Card
      title="Conditional Rendering"
      description="Shows techniques: if/else (pre-return), ternary, logical-AND, switch."
    >
      <button onClick={() => setVisible(v => !v)}>
        {visible ? 'Hide' : 'Show'} Secret
      </button>
      {visible && (
        <div style={{ background: '#030302ff', margin: '10px 0', borderRadius: 4 }}>
          his secret appears when 'visible' is true!
        </div>
      )}
      <div style={{ marginTop: 10 }}>
        <button onClick={() => setUserType('guest')}>Guest</button>
        <button onClick={() => setUserType('user')}>User</button>
        <button onClick={() => setUserType('admin')}>Admin</button>
      </div>
      <div style={{ marginTop: 10, padding: 10, borderRadius: 5, background: '#000000ff' }}>
        {userType === 'guest' && "Welcome Guest! Log in for more features."}
        {userType === 'user' && "Welcome User! You have standard access."}
        {userType === 'admin' && "Welcome Admin! You have full access."}
      </div>
      <p style={{ fontSize: '0.9em', marginTop: 10 }}>
        <strong>Techniques:</strong> Inline <code>&&</code> operator, ternary, and state-driven rendering.
      </p>
    </Card>
  );
}