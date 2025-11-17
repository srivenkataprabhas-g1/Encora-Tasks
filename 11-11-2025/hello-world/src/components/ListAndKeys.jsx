import React from "react";

export function ListItem(props) {
  const { number } = props;
  const square = number * number;

  return (
    <li>
      {number} → {square}
    </li>
  );
}

export function NumberList(props) {
  const numbers = props.numbers;

  const listItems = numbers.map((number) => (
    <ListItem key={number.toString()} number={number} />
  ));

  return <ul>{listItems}</ul>;
}

export default function ListAndKeys() {
  const numbers = [1, 2, 3, 4, 5];

  return (
    <div
      id="list-container"
      style={{
        margin: 16,
        background: "#00637cff",
        borderRadius: 6,
        padding: 16,
        color: "white",
        fontFamily: "sans-serif"
      }}
    >
      <h3>Numbers and Their Squares</h3>
      <NumberList numbers={numbers} />
    </div>
  );
}