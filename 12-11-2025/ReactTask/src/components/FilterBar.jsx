import React from 'react';

function FilterBar() {
  return (
    <div>
      <h2>Filters</h2>
      <select>
        <option>All</option>
        <option>Active</option>
        <option>Completed</option>
      </select>
      <select>
        <option>All</option>
        <option>Low</option>
        <option>Normal</option>
        <option>High</option>
      </select>
    </div>
  );
}

export default FilterBar;