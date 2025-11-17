import React from 'react';

export default function TaskFilters({ filterStatus, setFilterStatus }) {
  return (
    <div style={{ padding: '1rem 2rem', backgroundColor: 'white', display: 'flex', gap: '1rem', borderBottom: '1px solid #E2E8F0' }}>
      {['All', 'Active', 'Completed'].map(status => (
        <button
          key={status}
          onClick={() => setFilterStatus(status)}
          style={{
            padding: '0.4rem 1rem',
            borderRadius: '12px',
            border: filterStatus === status ? '2px solid #2563EB' : '1px solid #CBD5E1',
            backgroundColor: filterStatus === status ? '#DBEAFE' : 'white',
            cursor: 'pointer',
            fontWeight: filterStatus === status ? 'bold' : 'normal',
          }}
        >
          {status}
        </button>
      ))}
    </div>
  );
}