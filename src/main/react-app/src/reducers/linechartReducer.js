const initialState = {
    showCaseRankings: null,
    wrLineChart: null,
    // isFetching: false
}

export const winrateLineChart = (state = initialState, action) => {
    switch (action.type) {
        case 'INIT_WR_LINECHART':
            return {
                ...state,
                // isFetching: true
            }
        case 'INIT_WR_LINECHART_SUCCESS':
            return {
                ...state,
                wrLineChart: action.payload,
                // isFetching: false
            }
        default:
            return state
    }
}
