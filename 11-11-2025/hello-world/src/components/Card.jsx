export default function Card({ title, description, children }) {
  return (
    <div className="component-card">
      <h3>{title}</h3>
      <p>{description}</p>
      {children}
    </div>
  );
}