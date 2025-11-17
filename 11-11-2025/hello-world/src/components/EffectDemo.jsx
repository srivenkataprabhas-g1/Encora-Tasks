import React from 'react';
import Card from './Card.jsx';

export default function EffectDemo() {
  const [effectRan, setEffectRan] = React.useState(false);

  React.useEffect(() => {
    setEffectRan(true);
    console.log('Effect ran (mounted)');
    return () => {
      console.log('Effect cleaned up (unmounted)');
    };
  }, []);

  return (
    <Card
      title="useEffect Hook Demo"
      description="Runs a side-effect on mount and cleans up on unmount. Check your browser console."
    >
      <p>{effectRan ? "Effect ran. (see console)" : "Waiting..."}</p>
      <p style={{ fontSize: '0.9em' }}>
        <strong>useEffect patterns:</strong> Run once on mount, with dependencies, or after every render.
      </p>
    </Card>
  );
}