import React, { useState, useRef, useEffect } from "react";

/**
 * Form.jsx
 * A compact collection of small form examples to teach React form concepts:
 * - Controlled text input (with optional UPPERCASE enforcement)
 * - Controlled textarea
 * - Controlled select
 * - Handling multiple inputs with one handler (checkbox + number)
 * - Uncontrolled file input using a ref
 * - Demonstration of controlled -> null value behavior
 *
 * Put this file in src/components/Form.jsx and import it in your app (e.g. App.jsx).
 */

function NameForm() {
    const [value, setValue] = useState("");
    const [forceUpper, setForceUpper] = useState(false);

    function handleChange(e) {
        const next = e.target.value;
        setValue(forceUpper ? next.toUpperCase() : next);
    }

    function handleSubmit(e) {
        e.preventDefault();
        alert("A name was submitted: " + value);
    }

    return (
        <section style={{ border: "1px solid #ddd", padding: 12, marginBottom: 12 }}>
            <h3>Controlled Text Input</h3>
            <form onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input type="text" value={value} onChange={handleChange} style={{ marginLeft: 8 }} />
                </label>
                <label style={{ marginLeft: 12 }}>
                    <input
                        type="checkbox"
                        checked={forceUpper}
                        onChange={(e) => setForceUpper(e.target.checked)}
                    />
                    Force UPPERCASE
                </label>
                <div style={{ marginTop: 8 }}>
                    <button type="submit">Submit</button>
                </div>
            </form>
            <div style={{ marginTop: 8, color: "#555" }}>
                Current value: <strong>{value}</strong>
            </div>
        </section>
    );
}

function EssayForm() {
    const [text, setText] = useState("Please write an essay about your favorite DOM element.");

    function handleSubmit(e) {
        e.preventDefault();
        alert("An essay was submitted: " + text);
    }

    return (
        <section style={{ border: "1px solid #ddd", padding: 12, marginBottom: 12 }}>
            <h3>Controlled Textarea</h3>
            <form onSubmit={handleSubmit}>
                <label>
                    Essay:
                    <br />
                    <textarea
                        value={text}
                        onChange={(e) => setText(e.target.value)}
                        rows={4}
                        cols={50}
                        style={{ display: "block", marginTop: 6 }}
                    />
                </label>
                <div style={{ marginTop: 8 }}>
                    <button type="submit">Submit Essay</button>
                </div>
            </form>
        </section>
    );
}

function FlavorForm() {
    const [flavor, setFlavor] = useState("coconut");

    function handleSubmit(e) {
        e.preventDefault();
        alert("Your favorite flavor is: " + flavor);
    }

    return (
        <section style={{ border: "1px solid #ddd", padding: 12, marginBottom: 12 }}>
            <h3>Controlled Select</h3>
            <form onSubmit={handleSubmit}>
                <label>
                    Pick your favorite flavor:
                    <select value={flavor} onChange={(e) => setFlavor(e.target.value)} style={{ marginLeft: 8 }}>
                        <option value="grapefruit">Grapefruit</option>
                        <option value="lime">Lime</option>
                        <option value="coconut">Coconut</option>
                        <option value="mango">Mango</option>
                    </select>
                </label>
                <div style={{ marginTop: 8 }}>
                    <button type="submit">Submit Flavor</button>
                </div>
            </form>
        </section>
    );
}

function ReservationForm() {
    const [state, setState] = useState({ isGoing: true, numberOfGuests: 2 });

    function handleInputChange(e) {
        const target = e.target;
        const value = target.type === "checkbox" ? target.checked : target.value;
        const name = target.name;

        setState((prev) => ({
            ...prev,
            [name]: target.type === "number" ? Number(value) : value,
        }));
    }

    return (
        <section style={{ border: "1px solid #ddd", padding: 12, marginBottom: 12 }}>
            <h3>Handling Multiple Inputs</h3>
            <form>
                <label>
                    Is going:
                    <input
                        name="isGoing"
                        type="checkbox"
                        checked={state.isGoing}
                        onChange={handleInputChange}
                        style={{ marginLeft: 8 }}
                    />
                </label>
                <br />
                <label style={{ marginTop: 8, display: "block" }}>
                    Number of guests:
                    <input
                        name="numberOfGuests"
                        type="number"
                        value={state.numberOfGuests}
                        onChange={handleInputChange}
                        style={{ marginLeft: 8, width: 60 }}
                        min={0}
                    />
                </label>
                <div style={{ marginTop: 8 }}>
                    <small>Form state: {JSON.stringify(state)}</small>
                </div>
            </form>
        </section>
    );
}

function FileInputForm() {
    // file inputs are uncontrolled in React because value is read-only.
    const fileRef = useRef(null);
    const [fileNames, setFileNames] = useState([]);

    function handleSubmit(e) {
        e.preventDefault();
        const files = fileRef.current?.files;
        if (!files || files.length === 0) {
            alert("No files selected");
            return;
        }
        const names = Array.from(files).map((f) => f.name);
        setFileNames(names);
        // In a real app you would upload these files here.
    }

    return (
        <section style={{ border: "1px solid #ddd", padding: 12, marginBottom: 12 }}>
            <h3>Uncontrolled File Input</h3>
            <form onSubmit={handleSubmit}>
                <label>
                    Upload files:
                    <input type="file" ref={fileRef} multiple style={{ display: "block", marginTop: 6 }} />
                </label>
                <div style={{ marginTop: 8 }}>
                    <button type="submit">Read Selected Files</button>
                </div>
            </form>
            {fileNames.length > 0 && (
                <div style={{ marginTop: 8 }}>
                    <strong>Selected:</strong>
                    <ul>
                        {fileNames.map((n) => (
                            <li key={n}>{n}</li>
                        ))}
                    </ul>
                </div>
            )}
        </section>
    );
}

function ControlledNullDemo() {
    // Demonstrates that setting value to null makes an input uncontrolled (editable)
    const [value, setValue] = useState("hi");

    useEffect(() => {
        const t = setTimeout(() => {
            // after 2s we set value to null to demonstrate the "controlled input null value" note
            // when value becomes null, React treats the input as uncontrolled (and it becomes editable
            // even if you were previously controlling it). This is often an accidental source of bugs.
            setValue(null);
        }, 2000);
        return () => clearTimeout(t);
    }, []);

    return (
        <section style={{ border: "1px solid #ddd", padding: 12, marginBottom: 12 }}>
            <h3>Controlled Input Null Value Demo</h3>
            <p style={{ marginTop: 0, marginBottom: 6, color: "#555" }}>
                Initially controlled with "hi". After 2s the value is set to null (becomes uncontrolled).
            </p>
            <input
                value={value}
                onChange={(e) => setValue(e.target.value)}
                placeholder="Will become uncontrolled after 2s"
                style={{ width: 300 }}
            />
            <div style={{ marginTop: 8 }}>
                <small>Current state value: {String(value)}</small>
            </div>
        </section>
    );
}

export default function FormExamples() {
    return (
        <div style={{ fontFamily: "system-ui, Arial, sans-serif", lineHeight: 1.4 }}>
            <h2>React Forms — Simple Examples</h2>
            <p style={{ color: "#555" }}>
                These short examples show controlled components (text, textarea, select), a combined inputs
                handler, an uncontrolled file input, and a demonstration of value=null behavior.
            </p>

            <NameForm />
            <EssayForm />
            <FlavorForm />
            <ReservationForm />
            <FileInputForm />
            <ControlledNullDemo />
        </div>
    );
}