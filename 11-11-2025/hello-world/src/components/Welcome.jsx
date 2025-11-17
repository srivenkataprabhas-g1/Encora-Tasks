// // src/components/Welcome.jsx
// //Functional Based Component with useState and useEffect to create a welcome message that reveals the name one character at a time.
import { useEffect, useState } from "react";

export default function Welcome({ name,role,id }) {
  const [displayedName, setDisplayedName] = useState("");
  const [displayedRole, setDisplayedRole] = useState("");
  const [displayedId, setDisplayedId] = useState("");
  useEffect(() => {
    let i = 0;
    const interval = setInterval(() => {
      setDisplayedName(name.substring(0, i + 1));
      i++;
      if (i === name.length) clearInterval(interval);
    }, 100); // reveal one character every second

    return () => clearInterval(interval);
  }, [name]);
  useEffect(() => {
    let j = 0; 
    const interval = setInterval(() => {
      setDisplayedRole(role.substring(0, j + 1));
      j++;
      if (j === role.length) clearInterval(interval);
    }
    , 130); // reveal one character every 1.5 seconds\
  }, [role]);
  useEffect(() => {
    let k = 0; 
    const interval = setInterval(() => {
      setDisplayedId(id.substring(0, k + 1));
      k++;
      if (k === id.length) clearInterval(interval);
    }
    , 190); // reveal one character every 1.5 seconds\
  }, [id]);
  return <div>
    <h1>Hello, {displayedName}</h1>
    <h2>Your Role: {displayedRole}</h2>
    <h3>Your ID: {displayedId}</h3>
  </div>;
}

export function WelcomeList() {
  return (
    <div
      id="welcome-container"
      style={{
        margin: 16,
        background: "#00637cff",
        borderRadius: 6,
        padding: 16,
        color: "white"
      }}
    >
      <Welcome name="Guda Sri Venkata Prabhas" role="Software Consultant" id="ENC/16796" />
    </div>
  );
}
// src/components/Welcome.jsx
//Class Based Component to create a welcome message that reveals the name one character at a time.
// src/components/Welcome.jsx
// import React from "react";
// import "./Welcome.css"; // We'll define the animation here

// class Welcome extends React.Component {
//   constructor(props) {
//     super(props);
//     this.state = { displayedName: "" };
//   }

//   componentDidMount() {
//     let i = 0;
//     const { name } = this.props;
//     this.interval = setInterval(() => {
//       this.setState({ displayedName: name.substring(0, i + 1) });
//       i++;
//       if (i === name.length) clearInterval(this.interval);
//     }, 300);
//   }

//   componentWillUnmount() {
//     clearInterval(this.interval);
//   }

//   render() {
//     return (
//       <div className="welcome-text">
//         <h1>Hello, {this.state.displayedName}</h1>
//       </div>
//     );
//   }
// }

// export function WelcomeList() {
//   return (
//     <div
//       id="welcome-container"
//       style={{
//         margin: 16,
//         background: "#00637cff",
//         borderRadius: 6,
//         padding: 16,
//         color: "white",
//         overflow: "hidden", // ensures animation stays within box
//       }}
//     >
//       <Welcome name="Guda Sri Venkata Prabhas" />
//     </div>
//   );
// }