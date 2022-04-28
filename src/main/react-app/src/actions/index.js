import { getShowCaseRankings, getSummonerIcon } from './api'

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
            .then((data) => {
                const map = new Map(Object.entries(data));
                var promises = Array.from(map).map((value) => {
                    var elem = value[1][0]
                    return getSummonerIcon(elem.profileIconNum).then((img) => {
                                    elem.profileIcon = img
                                    return elem
                                })
                    // var promises = value[1].map((element) => {
                    //     return getSummonerIcon(element.profileIconNum)
                    //         .then((img) => {
                    //             element.profileIcon = img
                    //             return element
                    //         })
                    // })
                })
                // provisory
                // var firstRankings = []
                // firstRankings.push(promises[0][0],promises[1][0],promises[2][0])
                Promise.all(promises).then((data) => dispatch(initShowCaseSuccess(data)))

            })
    }
}