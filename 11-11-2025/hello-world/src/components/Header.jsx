export default function Header({ title, subtitle }) {
  return (
    <header className="app-header">
      <h1>{title}</h1>
      <p>{subtitle}</p>
    </header>
  );
}
