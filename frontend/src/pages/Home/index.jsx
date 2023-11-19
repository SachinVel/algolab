
import React from 'react';
const Register = lazy(() => import("../pages/Register"));

export default function Home() {
    const greeting = 'Hello Function Component!';

    return (
        <h1>{greeting}</h1>
    );
}