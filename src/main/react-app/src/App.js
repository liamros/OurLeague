import { LayoutGroup, motion, MotionConfig } from "framer-motion";
import React from "react";
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './App.css';
import HomeLineChartContainer from "./components/charts/HomeLineChartContainer";
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

  var animate = 'never'
  if (window.innerWidth <= 800)
    animate = 'always'


  return (
    <div className="App">
      <MotionConfig reducedMotion={animate}>
        <LayoutGroup type="crossfade">
          <Router>
            <header>
              <ResponsiveAppBar />
              <Route path={["/:statName", "/"]} render={(props) => <ShowCase {...props} />} />
            </header>

            <motion.div
              style={{ height: "50vh", marginTop: "1%", }}
              className={"container"}
              initial="hidden"
              animate="visible"
              variants={variants}
            >
              <HomeLineChartContainer />
            </motion.div>



          </Router>
        </LayoutGroup>
      </MotionConfig>

    </div >
  );
}

export default App;
