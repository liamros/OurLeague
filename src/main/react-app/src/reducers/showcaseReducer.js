const initialState = {
    showCaseRankings: null,
    wrLineChart: null,
    isFetching: false
}

export const showCaseRankings = (state = initialState, action) => {
    switch (action.type) {
        case 'INIT_SHOWCASE':
            return {
                ...state,
                isFetching: true
            }
        case 'INIT_SHOWCASE_SUCCESS':

            const obj = {}
            const map = new Map(Object.entries(action.payload))

            map.forEach((value, key) => {
                obj[key] = value.map((element) => {
                    return {
                        summonerName: element.summonerName,
                        value: element.value,
                        description: element.description,
                        position: element.position,
                        prevPosition: element.prevPosition,
                        profileIcon: element.profileIcon,
                        rank: {
                            queueType: element.queueType,
                            tier: element.tier,
                            division: element.division,
                            lp: element.lp,
                            wins: element.wins,
                            losses: element.losses
                        }
                    }
                })
            })

            return {
                ...state,
                showCaseRankings: obj,
                isFetching: false
            }
        default:
            return state
    }
}
