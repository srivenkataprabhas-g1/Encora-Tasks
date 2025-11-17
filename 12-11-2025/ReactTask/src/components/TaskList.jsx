import React from 'react';

export default function TaskList({ tasks, setTasks }) {
  const handleChange = (id, field, value) => {
    setTasks(prevTasks =>
      prevTasks.map(task =>
        task.id === id ? { ...task, [field]: value } : task
      )
    );
  };

  const toggleStatus = (id) => {
    setTasks(prevTasks =>
      prevTasks.map(task =>
        task.id === id
          ? { ...task, status: task.status === 'Completed' ? 'Active' : 'Completed' }
          : task
      )
    );
  };

return (
    <div
        style={{
            flex: 82,
            backgroundColor: 'white',
            borderRadius: 12,
            padding: '1rem',
            boxShadow: '0 1px 3px rgba(0,0,0,0.1)',
            overflowY: 'auto',
        }}
    >
        <table style={{ width: '100%',border: '1px solid black', borderRadius: '8px', borderCollapse: 'collapse' }}>
            <thead>
                <tr style={{ borderBottom: '1px solid #CBD5E1', textAlign: 'center    ' }}>
                    <th></th>
                    <th>ID</th>
                    <th>Task Name</th>
                    <th>Description</th>
                    <th>Due Date</th>
                    <th>Status</th>
                    <th>Priority</th>
                </tr>
            </thead>
            <tbody>
                {!tasks || tasks.length === 0 ? (
                    <tr>
                        <td colSpan="1" style={{ padding: '1rem', textAlign: 'center' }}>
                            No tasks found
                        </td>
                    </tr>
                ) : (
                    tasks.map(task => (
                        <tr key={task.id} style={{ borderBottom: '1px solid #E2E8F0' }}>
                            <td>
                                <input type="checkbox" aria-label={`Select ${task.title || 'task'}`} />
                            </td>
                            <td>{task.id}</td>
                            <td>
                                <input
                                    type="text"
                                    value={task.title}
                                    onChange={(e) => handleChange(task.id, 'title', e.target.value)}
                                    style={{ width: '96%' }}
                                />
                            </td>
                            <td>
                                <input
                                    type="text"
                                    value={task.description}
                                    onChange={(e) => handleChange(task.id, 'description', e.target.value)}
                                    style={{ width: '96%' }}
                                />
                            </td>
                            <td>
                                <input
                                    type="datetime-local"
                                    value={task.dueDate}
                                    onChange={(e) => handleChange(task.id, 'dueDate', e.target.value)}
                                    style={{ width: '96%' }}
                                />
                            </td>
                            <td style={{ padding: 0 }}>
                                <button
                                    type="button"
                                    onClick={() => value=toggleStatus(task.id)}
                                    style={{
                                          display: 'block',
                                        width: '96%',
                                        height: '96%',
                                        padding: '0.5rem',
                                        boxSizing: 'border-box',
                                    }}
                                >
                                    {task.status}
                                </button>
                            </td>
                            <td>
                                <select
                                    value={task.priority}
                                    onChange={(e) => handleChange(task.id, 'priority', e.target.value)}
                                >
                                    <option value="Low">Low</option>
                                    <option value="Normal">Normal</option>
                                    <option value="High">High</option>
                                </select>
                            </td>
                        </tr>
                    ))
                )}
            </tbody>
        </table>
    </div>
);
}