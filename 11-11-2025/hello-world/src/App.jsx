import React from 'react';

// Import all components from components folder
import Header from "./components/Header.jsx";
import Counter from "./components/Counter.jsx";
import TodoList from "./components/TodoList.jsx";
import EffectDemo from "./components/EffectDemo.jsx";
import ProfileList from "./components/ProfileList.jsx";
import ConditionalRender from "./components/ConditionalRender.jsx";
import LiveClock from "./components/LiveClock.jsx";
import {WelcomeList} from './components/Welcome.jsx';
import ListAndKeys from './components/ListAndKeys.jsx';
import HandlingEvents from './components/HandlingEvents.jsx';
import Form from './components/Form.jsx';
import LiftingStateUp from './components/LiftingStateUp.jsx';
import Inheritance from './components/Inheritance.jsx';

function App() {
  return (
    <div className="container">
      <Header
        title="React Components Explained"
        subtitle="A comprehensive guide to React components, hooks, and best practices"
      />

      <main className="app-content">
        <section className="section">
          <h2>Welcome to React!</h2>
          <p>
            This app demonstrates all important React concepts with live examples and practical code.
          </p>
          <div className="alert alert-info">
            ℹ️ Open the browser console to see useEffect logs. All components below are reactive and state-driven.
          </div>
        </section>
        <h2>Hello World -&gt; UseState,UseEffect,Props used &amp; Components and Props</h2>
        <WelcomeList />
        <h2>Rendering Elements -&gt; Rendering,UseEffect and Date Used</h2>
        <LiveClock />
        <h2>State and Event Handling -&gt; UseState used</h2>
        <Counter />
        <h2>Handling Events</h2>
        <HandlingEvents />
        <h2>Conditional Rendering</h2>
        <ConditionalRender />
        <h2>List and Keys</h2>
        <ListAndKeys />
        <h2>Forms</h2>
        <Form /> 
        <h2>Lifting state up</h2>
        <LiftingStateUp />
        <h2>Inheritance</h2>
        <Inheritance />
        <TodoList />
        <EffectDemo />
        <ProfileList />
        <WelcomeList />
        <section className="section">
          <h2>Key Concepts Covered</h2>
          <ul>
            <li><strong>Components:</strong> Reusable UI parts built as functions.</li>
            <li><strong>JSX:</strong> HTML-like syntax within JavaScript.</li>
            <li><strong>Hooks:</strong> useState, useEffect for state and side-effects.</li>
            <li><strong>Props & State:</strong> Data passed to (props) and stored by (state) a component.</li>
            <li><strong>Composition:</strong> Large UIs built from small, testable parts.</li>
            <li><strong>Event Handling:</strong> Interactive logic (clicks, input changes, etc.).</li>
            <li><strong>List Rendering:</strong> Dynamic rendering with .map() and key.</li>
            <li><strong>Conditional Rendering:</strong> Toggling what is shown by state.</li>
            <li><strong>Side Effects:</strong> Effects/cleanup with useEffect.</li>
          </ul>
        </section>

        <section className="section">
          <h2>React Hooks Summary</h2>
          <ul>
            <li><strong>useState:</strong> Adds state to your function components.</li>
            <li><strong>useEffect:</strong> Handles side effects like data fetching, subscriptions.</li>
            <li><strong>useRef:</strong> Stores mutable value that does not cause re-render.</li>
          </ul>
        </section>
      </main>

      <footer className="app-footer">
        <p>React Application Demo | Learn React Components and Hooks</p>
        <p style={{ fontSize: '0.9em', marginTop: '10px' }}>
          © {new Date().getFullYear()} React Learning Example | Built with Vite and React 18
        </p>
      </footer>
    </div>
  );
}

export default App;