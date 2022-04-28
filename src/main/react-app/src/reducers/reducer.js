
const initialState = {
    showCaseRankings : null,
    isFetching : false
}

const showCaseRankings = (state = initialState, action) => {
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
            // action.payload.map((element) => {
            //     obj[element.statName] = {
            //         summonerName: element.summonerName,
            //         value: element.value,
            //         description: element.description,
            //         profileIcon: element.profileIcon,
            //         rank: {
            //             queueType: element.queueType,
            //             tier: element.tier,
            //             division: element.division,
            //             lp: element.lp,
            //             wins: element.wins,
            //             losses: element.losses
            //         }
            //     }
            // })

            return {
                ...state,
                showCaseRankings: obj,
                isFetching: false
            }
        default:
            return state
    }
}

const getSummonerIcon = (profileIconNum) => {
    let url = `http://localhost:8080/summoner/getProfileIcon?profileIconNumber=${profileIconNum}`
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

export default showCaseRankings