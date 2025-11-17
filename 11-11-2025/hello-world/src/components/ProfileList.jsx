import React from 'react';
import ProfileCard from './ProfileCard.jsx';
import Card from './Card.jsx';

export default function ProfileList() {
  const profiles = [
    { id: 1, name: 'Alice Johnson', email: 'alice@example.com', role: 'Frontend Developer' },
    { id: 2, name: 'Bob Smith', email: 'bob@example.com', role: 'Backend Developer' },
    { id: 3, name: 'Carol White', email: 'carol@example.com', role: 'Full Stack Developer' }
  ];
  return (
    <Card title="Profile List (Props & Composition)" description="Renders ProfileCard components from an array using map.">
      {profiles.map(profile => (
        <ProfileCard
          key={profile.id}
          name={profile.name}
          email={profile.email}
          role={profile.role}
        />
      ))}
    </Card>
  );
}