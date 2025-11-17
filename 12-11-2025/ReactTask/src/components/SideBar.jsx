import React from 'react';
import { FaHome, FaTasks, FaCog } from 'react-icons/fa';

const sidebarItems = [
  { icon: <FaHome />, label: 'Home' },
  { icon: <FaTasks />, label: 'Tasks' },
  { icon: <FaCog />, label: 'Settings' },
];

export default function Sidebar() {
  return (
    <aside style={{ width: 72, backgroundColor: '#1E293B', color: 'white', display: 'flex', flexDirection: 'column', alignItems: 'center', paddingTop: '1rem' }}>
      {sidebarItems.map((item, idx) => (
        <button
          key={idx}
          style={{
            margin: '1rem 0',
            backgroundColor: 'transparent',
            border: 'none',
            color: 'white',
            fontSize: '1.5rem',
            cursor: 'pointer',
            padding: '12px',
            borderRadius: '12px',
            transition: 'background-color 0.2s',
          }}
          onMouseEnter={e => e.currentTarget.style.backgroundColor = '#2563EB'}
          onMouseLeave={e => e.currentTarget.style.backgroundColor = 'transparent'}
          title={item.label}
        >
          {item.icon}
        </button>
      ))}
    </aside>
  );
}