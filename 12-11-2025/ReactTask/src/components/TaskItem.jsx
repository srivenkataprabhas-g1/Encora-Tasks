import React from 'react';

function TaskItem({ task }) {
  return (
    <div>
      <h3>{task.title}</h3>
      <p>{task.description}</p>
      <p>Priority: {task.priority}</p>
    </div>
  );
}

export default TaskItem;