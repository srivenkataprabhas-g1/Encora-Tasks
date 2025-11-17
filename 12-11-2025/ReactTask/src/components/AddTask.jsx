import React from 'react';
import { FaSearch } from 'react-icons/fa';

export default function AddTask({ onAdd, searchTerm, setSearchTerm }) {
  return (
    <div style={{ padding: '1rem 2rem', display: 'flex', gap: '1rem', backgroundColor: 'white', alignItems: 'center', boxShadow: '0 1px 3px rgb(0 0 0 / 0.1)' }}>
      <button
        onClick={onAdd}
        style={{ backgroundColor: '#2563EB', color: 'white', border: 'none', padding: '0.5rem 1rem', borderRadius: '8px', cursor: 'pointer' }}
      >
        Add Task
      </button>
      <div style={{ position: 'relative', flex: 1 }}>
        <input
          type="text"
          placeholder="Enter task description"
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
          style={{
            width: '100%',
            padding: '0.5rem 2.5rem 0.5rem 1rem',
            borderRadius: '8px',
            border: '1px solid #CBD5E1',
            fontSize: '1rem',
            outline: 'none'
          }}
        />
        <FaSearch style={{ position: 'absolute', right: '0.75rem', top: '50%', transform: 'translateY(-50%)', color: '#64748B' }} />
      </div>
    </div>
  );
}