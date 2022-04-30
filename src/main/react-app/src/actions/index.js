import { getShowCaseRankings, getSummonerIcon, getWrLineChart, getVisionScoreLineChart } from './api'

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

export const initHomeLineChart = () => ({
    type: 'INIT_HOME_LINECHART',
})

export const initHomeLineChartSuccess = (wrLineChart) => ({
    type: 'INIT_HOME_LINECHART_SUCCESS',
    payload: wrLineChart,
})

export function fetchHomeLineCharts() {
    return (dispatch) => {
        dispatch(initHomeLineChart())
        var obj = {}
        var promises = []
        promises.push(getWrLineChart()
            .then((response) => {
                obj[response.name] = response
            })
        )
        promises.push(getVisionScoreLineChart()
            .then((response) => {
                obj[response.name] = response
            })
        )
        Promise.all(promises).then(() => dispatch(initHomeLineChartSuccess(obj)))
    }
}