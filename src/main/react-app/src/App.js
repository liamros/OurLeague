import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import ResponsiveAppBar from './components/header/ResponsiveAppBar';
import ShowCase from './components/showcase/ShowCase';
import { AnimateSharedLayout } from "framer-motion";
import LineChart from './components/charts/LineChart';
import { Container } from '@nivo/core';

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
      <div style={{ height: "50vh", marginTop: "3%", padding: "0% 11.5%" }} className={"container"}>
        <LineChart />
      </div>

    </div>
  );
}

export default App;
