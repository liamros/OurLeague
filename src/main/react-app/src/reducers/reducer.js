
const initialState = {
    showCaseDetails : null,
    isFetching : false
}

const showCaseDetails = (state = initialState, action) => {
    switch (action.type) {
        case 'INIT_SHOWCASE':
            return {
                ...state,
                isFetching: true
            }
        case 'INIT_SHOWCASE_SUCCESS':

            const obj = {}
            action.payload.map((element) => {
                obj[element.statName] = {
                    summonerName: element.summonerName,
                    value: element.value,
                    description: element.description,
                    profileIcon: element.profileIcon
                }
            })

            return {
                ...state,
                showCaseDetails: obj,
                isFetching: false
            }
        default:
            return state
    }
}

const getSummonerIcon = (profileIconNum) => {
    let url = `http://localhost:8080/match/getProfileIcon?profileIconNumber=${profileIconNum}`
    const img = fetch(url, { mode: 'cors' })
        .then(response =>
            response.blob())
        .then(imgBlob => {
            const img = URL.createObjectURL(imgBlob)
            return img
        })
        .catch(error =>
            console.log(error))
    return img
}

export default showCaseDetails