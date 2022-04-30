import { Hidden } from "@mui/material";
import { LayoutGroup, motion } from "framer-motion";
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './App.css';
import HomeLineChartContainer from "./components/charts/HomeLineChartContainer";
import LineChart from './components/charts/LineChart';
import ResponsiveAppBar from './components/header/ResponsiveAppBar';
import ShowCase from './components/showcase/ShowCase';

function App() {
  const variants = {
    visible: {
        opacity: 1,
        transition: {
            delay: 2.0,
            duration: 1,
        },
    },
    hidden: { opacity: 0 },
}
  return (
    <div className="App">
      <LayoutGroup type="crossfade">
        <Router>
          <header>
            <ResponsiveAppBar />
            <Route path={["/:statName", "/"]} render={(props) => <ShowCase {...props} />} />
          </header>

          <motion.div
            style={{ height: "50vh", marginTop: "3%", }}
            className={"container"}
            initial="hidden"
            animate="visible"
            variants={variants}
            >
          <HomeLineChartContainer />
        </motion.div>



      </Router>
    </LayoutGroup>
    </div >
  );
}

export default App;
