import { BrowserRouter as Router } from 'react-router-dom';
import './App.css';
import ResponsiveAppBar from './components/header/ResponsiveAppBar';
import ShowCase from './components/showcase/ShowCase';

function App() {
  return (
    <div className="App">
      <Router>
        <header>  
        <ResponsiveAppBar/>
        <ShowCase/>
        </header>
        
      </Router>
    </div>
  );
}

export default App;
