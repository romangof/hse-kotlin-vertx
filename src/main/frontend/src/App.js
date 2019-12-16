import React, { useEffect, useState } from 'react';

import './App.css';

function App() {
    const [message, setMessage] = useState({})

    useEffect(() => {
        fetch("/api/message")
            .then(response => response.text())
            .then(text => setMessage({ message: text }));

        console.log(message);
    }, []);

    return (
        <div className="App">
            <header className="App-header">
                1111
                I'm here
                {console.log(1111, message)}
                {message.message}
            </header>
        </div>
    );
}

export default App;