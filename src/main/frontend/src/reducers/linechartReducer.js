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
            const obj = action.payload
            Object.keys(obj).forEach((key) => {
                Object.keys(obj[key]).forEach((subKey) => {
                    obj[key][subKey].charts.sort((a, b) => {
                        if (a.id > b.id)
                            return -1
                        else
                            return 1
                    })
                    obj[key][subKey].charts.push({ id: "ALL", data: [] })
                })
                
            })
            return {
                ...state,
                data: obj,
                isFetched: true
            }
        default:
            return state
    }
}
