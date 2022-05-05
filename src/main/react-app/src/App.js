import { ThemeProvider } from '@mui/material';
import { createTheme } from '@mui/material/styles';
import { LayoutGroup, MotionConfig } from "framer-motion";
import React from "react";
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './App.css';
import HomeLineChartContainer from "./components/charts/HomeLineChartContainer";
import ResponsiveAppBar from './components/header/ResponsiveAppBar';
import ShowCase from './components/showcase/ShowCase';

function App() {


  var animate = 'never'
  if (window.innerWidth <= 800)
    animate = 'always'


  return (
    <ThemeProvider theme={theme}>
    <div className="App">
      <MotionConfig reducedMotion={animate}>
        <LayoutGroup type="crossfade">
          <Router>
            <header>
              <ResponsiveAppBar />
              <Route path={["/:statName", "/"]} render={(props) => <ShowCase {...props} />} />
            </header>
            <HomeLineChartContainer />
          </Router>
        </LayoutGroup>
      </MotionConfig>

    </div >
    </ThemeProvider>
  );
}

const theme = createTheme({
  palette: {
    primary: {
      main: 'rgb(208, 168, 92);',
      // darker: '#053e85',
    },
    secondary: {
      main: '#FFFF',
    },

  }
});

export default App;
