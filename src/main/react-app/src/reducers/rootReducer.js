import { combineReducers } from 'redux';
import {showCaseRankings} from './showcaseReducer'
import {homeLineCharts} from './linechartReducer'

export default combineReducers({
    showCaseRankings,
    homeLineCharts
  });
