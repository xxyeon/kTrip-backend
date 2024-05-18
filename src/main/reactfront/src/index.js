import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'; // 불필요하면 이 줄을 제거합니다.
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App /> {/* 만약 App 컴포넌트를 사용하지 않는다면 이 줄을 제거합니다. */}
  </React.StrictMode>
);

reportWebVitals();