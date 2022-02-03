import { AppBar, Skeleton } from '@mui/material';
import { BrowserRouter as Router } from 'react-router-dom';
import './App.css';
import ResponsiveAppBar from './components/header/ResponsiveAppBar';

function App() {
  return (
    <div className="App">
      <Router>
        <header>
        <ResponsiveAppBar/>



        </header>
        
      </Router>
    </div>
  );
}

export default App;
