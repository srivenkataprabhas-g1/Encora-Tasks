import React from 'react';

class EventHandlingDemo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isToggleOn: true,
            message: 'Welcome to Event Handling Demo'
        };
        this.handleToggleClick = this.handleToggleClick.bind(this);
    }

    handleToggleClick() {
        this.setState(state => ({
            isToggleOn: !state.isToggleOn,
            message: `Toggle is now ${!state.isToggleOn ? 'ON' : 'OFF'}`
        }));
    }

    handleLinkClick = (e) => {
        e.preventDefault();
        this.setState({ message: 'The link was clicked.' });
    }
    handleHover = (e) => {
        this.setState({ message: 'You hovered over the element.' });
    }
    handleEnter = (e) => {
        this.setState({ message: 'Mouse entered the element.' });
    }
    handleExit = (e) => {   
        this.setState({ message: 'Mouse exited the element.' }); 
    }
    deleteRow = (id, e) => {
        e.preventDefault();
        this.setState({ message: `Deleting row with ID: ${id}` });
    }
    drag = (e) => {
        this.setState({ message: 'Element is being dragged.' });
    }
    dragExit = (e) => {
        this.setState({ message: 'Element drag exited.' });
    }   
    render() {
        const id = 123;
        return (
            <div style={{ padding: '20px' }}>
                <h2>Event Handling Examples</h2>
                <p style={{ fontSize: '18px', color: '#333' }}>{this.state.message}</p>

                <button onClick={this.handleToggleClick} onMouseEnter={this.handleEnter} onMouseLeave={this.handleExit} style={{ marginRight: '10px' }}>
                    Toggle ({this.state.isToggleOn ? 'ON' : 'OFF'})
                </button>

                <a href="#" onClick={this.handleLinkClick} onDrag={this.drag} onDragEndCapture={this.dragExit} onMouseOver={this.handleHover} style={{ marginRight: '10px' }}>
                    Click Link
                </a>

                <button onClick={(e) => this.deleteRow(id, e)}  style={{ marginRight: '10px' }}>
                    Delete Row
                </button>
            </div>
        );
    }
}

export default EventHandlingDemo;
