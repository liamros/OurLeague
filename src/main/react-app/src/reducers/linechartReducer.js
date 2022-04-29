const initialState = {
    wrLineChart: null,
    isFetched: false
}

export const winrateLineChart = (state = initialState, action) => {
    switch (action.type) {
        case 'INIT_WR_LINECHART':
            return {
                ...state,
                isFetched: false
            }
        case 'INIT_WR_LINECHART_SUCCESS':
            return {
                ...state,
                wrLineChart: action.payload,
                isFetched: true
            }
        default:
            return state
    }
}
