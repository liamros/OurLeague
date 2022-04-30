const initialState = {
    data: null,
    isFetched: false
}

export const homeLineCharts = (state = initialState, action) => {
    switch (action.type) {
        case 'INIT_HOME_LINECHART':
            return {
                ...state,
                isFetched: false
            }
        case 'INIT_HOME_LINECHART_SUCCESS':
            return {
                ...state,
                data: action.payload,
                isFetched: true
            }
        default:
            return state
    }
}
