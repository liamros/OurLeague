import { combineReducers } from 'redux';
import {showCaseRankings} from './showcaseReducer'
import {winrateLineChart} from './linechartReducer'

export default combineReducers({
    showCaseRankings,
    winrateLineChart
  });
