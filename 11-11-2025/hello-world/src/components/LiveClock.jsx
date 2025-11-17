import React from 'react';

export default function LiveClock() {
  const days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
  const [date, setDate] = React.useState(new Date());
  React.useEffect(() => {
    const id = setInterval(() => setDate(new Date()), 1000);
    return () => clearInterval(id);
  }, []);
  return (
    <div style={{ margin: 16, background: "#8f7b09ff", borderRadius: 6 }}>
      <h3>Hello, world!</h3>
      <h4>
        {days[date.getDay()]}, {date.toLocaleDateString()}, {date.toLocaleTimeString()}.
      </h4>
    </div>
  );
}
