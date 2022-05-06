export const getShowCaseRankings = () => {
    const data = fetch('/app/getShowCaseRankings')
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}


export const getSummonerIcon = (profileIconNum) => {
    let url = `/summoner/getProfileIcon?profileIconNumber=${profileIconNum}`
    const img = fetch(url)
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

export const getWrLineChart = () => {
    const data = fetch('/app/getWinrateAllLineCharts')
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}


export const getVisionScoreLineChart = () => {
    const data = fetch('/app/getVisionPerMinuteChart')
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}

export const getGamesPerMinuteChart = () => {
    const data = fetch('/app/getGamesPerMinuteChart')
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}

export const getAllHomeCharts = () => {
    const data = fetch('/app/getAllHomeCharts')
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}