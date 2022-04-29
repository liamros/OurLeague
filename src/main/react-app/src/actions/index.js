import { getShowCaseRankings, getSummonerIcon, getWrLineChart } from './api'

export const initShowCase = () => ({
    type: 'INIT_SHOWCASE',
})

export const initShowCaseSuccess = (showCaseRankings) => ({
    type: 'INIT_SHOWCASE_SUCCESS',
    payload: showCaseRankings,
})


export function fetchShowCaseRankings() {
    return (dispatch) => {
        dispatch(initShowCase())
        getShowCaseRankings()
            .then((response) => {
                const map = new Map(Object.entries(response))
                var promises = Array.from(map).map((sortedRankings) => {
                    var elem = sortedRankings[1][0]
                    return getSummonerIcon(elem.profileIconNum).then((img) => {
                                    elem.profileIcon = img
                                    return elem
                                })
                })
                Promise.all(promises).then((solved) => {
                    solved.forEach(s => response[s.statName][0] = s)
                    dispatch(initShowCaseSuccess(response))
                })

            })
    }
}

export const initWrLineChart = () => ({
    type: 'INIT_WR_LINECHART',
})

export const initWrLineChartSuccess = (wrLineChart) => ({
    type: 'INIT_WR_LINECHART_SUCCESS',
    payload: wrLineChart,
})

export function fetchWrLineChart() {
    return (dispatch) => {
        dispatch(initWrLineChart())
        getWrLineChart()
            .then((response) => dispatch(initWrLineChartSuccess(response)))
    }
}