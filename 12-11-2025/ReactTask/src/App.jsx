import React, { useState } from 'react';
import Sidebar from './components/SideBar';
import Header from './components/Header';
import AddTask from './components/AddTask';
import TaskFilters from './components/TaskFilters';
import TaskList from './components/TaskList';
import Charts from './components/Charts';

export default function App() {
  const [filterStatus, setFilterStatus] = useState('All');
  const [searchTerm, setSearchTerm] = useState('');
  const [tasks, setTasks] = useState([
    { id: 1, title: 'Task 1', description: 'Description 1', status: 'Active', dueDate: '2025-11-15T14:30', priority: 'Normal' },
    { id: 2, title: 'Task 2', description: 'Description 2', status: 'Completed', dueDate: '2025-11-19T11:30', priority: 'Normal' },
    { id: 3, title: 'Task 3', description: 'Description 3', status: 'Active', dueDate: '2025-11-20T14:30', priority: 'Normal' },
  ]);

  // Filter tasks based on filterStatus and search term
  const filteredTasks = tasks.filter(task => {
    const matchesStatus = filterStatus === 'All' || task.status === filterStatus;
    const matchesSearch = task.title.toLowerCase().includes(searchTerm.toLowerCase()) || task.description.toLowerCase().includes(searchTerm.toLowerCase());
    return matchesStatus && matchesSearch;
  });

  const handleAddTask = () => {
    const newTask = {
      id: tasks.length + 1,
      title: `Task ${tasks.length + 1}`,
      description: 'New task description',
      status: 'Active',
      dueDate: new Date().toISOString().slice(0, 10),
    };
    setTasks([newTask, ...tasks]);
  };

  return (
    <div style={{ display: 'flex', height: '100vh', fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif" }}>
      <Sidebar />
      <div style={{ flex: 1, backgroundColor: '#F8FAFC', display: 'flex', flexDirection: 'column' }}>
        <Header />
        <AddTask onAdd={handleAddTask} searchTerm={searchTerm} setSearchTerm={setSearchTerm} />
        <TaskFilters filterStatus={filterStatus} setFilterStatus={setFilterStatus} />
        <div style={{ display: 'flex', flex: 1, padding: '1rem 1rem', gap: '1rem', overflowY: 'auto' }}>
          <TaskList tasks={filteredTasks} />
          <Charts />
        </div>
      </div>
    </div>
  );
}