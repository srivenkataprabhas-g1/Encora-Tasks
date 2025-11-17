export default function ProfileCard({ name, email, role }) {
  return (
    <div className="component-card" style={{ marginBottom: 10 }}>
      <h3>👤 {name}</h3>
      <p><strong>Email:</strong> {email}</p>
      <p><strong>Role:</strong> {role}</p>
    </div>
  );
}