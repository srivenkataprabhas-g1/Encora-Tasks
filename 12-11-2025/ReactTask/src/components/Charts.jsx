import React from 'react';
import { PieChart, Pie, Cell, BarChart, Bar, XAxis, YAxis, Tooltip, Legend } from 'recharts';

const pieData = [
  { name: 'Completed', value: 400, color: '#2563EB' },
  { name: 'Active', value: 300, color: '#14B8A6' },
  { name: 'Pending', value: 100, color: '#F59E0B' },
];

const barData = [
  { name: 'Jan', Completed: 400, Active: 240 },
  { name: 'Feb', Completed: 300, Active: 139 },
  { name: 'Mar', Completed: 200, Active: 980 },
  { name: 'Apr', Completed: 278, Active: 390 },
  { name: 'May', Completed: 189, Active: 480 },
];

export default function Charts() {
  return (
    <div style={{ flex: 1, display: 'flex', flexDirection: 'column', gap: '2rem' }}>
      <div style={{ backgroundColor: 'white', borderRadius: 12, padding: '1rem', boxShadow: '0 1px 2px rgb(0 0 0 / 0.1)' }}>
        <h3>Tasks by Status</h3>
        <PieChart width={300} height={250}>
          <Pie
            data={pieData}
            cx="50%"
            cy="50%"
            outerRadius={80}
            dataKey="value"
            label
          >
            {pieData.map((entry, index) => (
              <Cell key={`cell-${index}`} fill={entry.color} />
            ))}
          </Pie>
          <Legend />
        </PieChart>
      </div>

      <div style={{ backgroundColor: 'white', borderRadius: 12, padding: '1rem', boxShadow: '0 1px 3px rgb(0 0 0 / 0.1)' }}>
        <h3>Completed vs Active</h3>
        <BarChart width={300} height={250} data={barData}>
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey="Completed" fill="#2563EB" />
          <Bar dataKey="Active" fill="#14B8A6" />
        </BarChart>
      </div>
    </div>
  );
}