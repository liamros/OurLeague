import { getAllHomeCharts, getShowCaseRankings, getSummonerIcon } from './api'

export const initShowCase = () => ({
    type: 'INIT_SHOWCASE',
})

export const initShowCaseSuccess = (showCaseRankings) => ({
    type: 'INIT_SHOWCASE_SUCCESS',
    payload: showCaseRankings,
})

export const selectShowCase = (selection) => ({
    type: 'SELECT_SHOWCASE',
    payload: selection,
})


export function fetchShowCaseRankings() {
    return (dispatch) => {
        dispatch(initShowCase())
        getShowCaseRankings()
            .then((response) => {
                const obj = {}
                response.forEach((elem) => {
                    obj[elem.queueType] = elem.showcaseRankingsByStatName
                })
                const map = new Map(Object.entries(obj))
                
                const promiseArrays = Array.from(map).map((showcase) => {
                    
                    const showcaseMap = new Map(Object.entries(showcase[1]))
                    return Array.from(showcaseMap).map((sortedRankings) => {
                        var elem = sortedRankings[1][0]
                        return getSummonerIcon(elem.profileIconNum).then((img) => {
                            const out = {}
                            out["profileIcon"] = img
                            out["queueType"] = showcase[0]
                            out["statName"] = elem.statName
                            return out
                        })
                    })

                })
                var promises = []
                promiseArrays.forEach((e) => promises = [...promises, ...e])
                Promise.all(promises).then((solved) => {
                    solved.forEach(s => {
                        obj[s.queueType][s.statName][0].profileIcon = s.profileIcon
                })
                    dispatch(initShowCaseSuccess(obj))
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

        var promise = getAllHomeCharts().then((response) => {
            const map = new Map(Object.entries(response))
            map.forEach((charts, queue) => {
                obj[queue] = {}
                charts.forEach((wrapper) => {
                    obj[queue][wrapper.name] = wrapper
                })
            })

        })
        Promise.resolve(promise).then(() => dispatch(initHomeLineChartSuccess(obj)))
    }
}