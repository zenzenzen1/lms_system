import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
// import './index.css'

import Tailwind from "primereact/passthrough/tailwind";

// import "primereact/resources/themes/bootstrap4-light-blue/theme.css";
import { PrimeReactProvider } from 'primereact/api';
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";
import { twMerge } from 'tailwind-merge';
import "primeflex/primeflex.css";


ReactDOM.createRoot(document.getElementById('root')).render(
  // <React.StrictMode>
  //   <App />
  // </React.StrictMode>,
  <React.StrictMode>
    <PrimeReactProvider
      value={{ ptOptions: { mergeSections: true, mergeProps: true, classNameMergeFunction: twMerge } }}
    >
      <App />
    </PrimeReactProvider>
  </React.StrictMode>
)
