import React from 'react';
import { FaBell, FaUserCircle } from 'react-icons/fa';

export default function Header() {
  return (
    <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '0.75rem 2rem', backgroundColor: '#EFF6FF', borderBottom: '1px solid #E2E8F0' }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
        <img src="https://reactjs.org/logo-og.png" alt="React" style={{ width: 32, height: 32 }} />
        <h1 style={{ margin: 0, fontWeight: 'bold', fontSize: '1.5rem' }}>Task Manager</h1>
      </div>
      <div style={{ display: 'flex', alignItems: 'center', gap: '1.5rem' }}>
        <button style={{ position: 'relative', background: 'none', border: 'none', cursor: 'pointer' }}>
          <FaBell size={20} />
          <span style={{ position: 'absolute', top: 0, right: 0, backgroundColor: '#DC2626', borderRadius: '50%', width: 10, height: 10, border: '2px solid white' }}></span>
        </button>
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', cursor: 'pointer' }}>
          <FaUserCircle size={28} />
          <span>Username</span>
        </div>
      </div>
    </header>
  );
}