import React from "react";
import './Inheritance.css';

// 🧩 FancyBorder Component — Containment
function FancyBorder({ color, children }) {
  return (
    <div className={`FancyBorder FancyBorder-${color}`}>
      {children}
    </div>
  );
}

// 🪐 WelcomeDialog — Uses FancyBorder via Containment
function WelcomeDialog() {
  return (
    <FancyBorder color="blue">
      <h1 className="Dialog-title">Welcome</h1>
      <p className="Dialog-message">
        Thank you for visiting our spacecraft!
      </p>
    </FancyBorder>
  );
}

// 🧭 SplitPane Component — Multiple Containment Slots
function SplitPane({ left, right }) {
  return (
    <div className="SplitPane">
      <div className="SplitPane-left">{left}</div>
      <div className="SplitPane-right">{right}</div>
    </div>
  );
}

// 💬 Dialog Component — Specialization
function Dialog({ title, message, children }) {
  return (
    <FancyBorder color="blue">
      <h1 className="Dialog-title">{title}</h1>
      <p className="Dialog-message">{message}</p>
      {children}
    </FancyBorder>
  );
}

// 🚀 SignUpDialog — Specialized Version of Dialog
class SignUpDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = { login: "" };
  }

  handleChange = (e) => {
    this.setState({ login: e.target.value });
  };

  handleSignUp = () => {
    alert(`Welcome aboard, ${this.state.login}!`);
  };

  render() {
    return (
      <Dialog
        title="Mars Exploration Program"
        message="How should we refer to you?"
      >
        <input
          value={this.state.login}
          onChange={this.handleChange}
          placeholder="Enter your name"
        />
        <button onClick={this.handleSignUp}>Sign Me Up!</button>
      </Dialog>
    );
  }
}

// 🌍 CompositionExample Component — Combines Everything
export default function CompositionExample() {
  return (
    <div className="CompositionExample">
      <h2>🚀 React Composition Example</h2>

      <WelcomeDialog />

      <hr />

      <SignUpDialog />

      <hr />

      <SplitPane
        left={<div style={{ padding: 10 }}>🧑‍🚀 Left Pane Content</div>}
        right={<div style={{ padding: 10 }}>🚀 Right Pane Content</div>}
      />
    </div>
  );
}