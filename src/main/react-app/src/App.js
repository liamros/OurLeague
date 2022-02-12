import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import ResponsiveAppBar from './components/header/ResponsiveAppBar';
import ShowCase from './components/showcase/ShowCase';
import { AnimateSharedLayout } from "framer-motion";

function App() {
  return (
    <div className="App">
      <AnimateSharedLayout type="crossfade">
        <Router>
          <header>
            <ResponsiveAppBar />
            <Route path={["/:statName", "/"]} render={(props) => <ShowCase {...props} />} />
          </header>
          

          
        </Router>
      </AnimateSharedLayout>

    </div>
  );
}

export default App;
