const initialState = {
    data: null,
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
            var selected = null
            map.forEach((showcase, queueType) => {
                if (state.selected)
                    selected = state.selected
                else if (!selected)
                    selected = queueType
                const showcaseMap = new Map(Object.entries(showcase))
                obj[queueType] = {}
                Array.from(showcaseMap).map((showcaseRanking) => {
                    const out = []
                    showcaseRanking[1].forEach((element) => {
                        out.push(
                            {
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
                        )
                    })
                    obj[queueType][showcaseRanking[0]] = out
                })
                
            })

            return {
                ...state,
                selected: selected,
                data: obj,
                isFetching: false
            }
        case 'SELECT_SHOWCASE':
            return {
                ...state,
                selected: action.payload
            }
        default:
            return state
    }
}
