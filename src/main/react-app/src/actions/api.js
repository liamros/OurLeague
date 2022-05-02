export const getShowCaseRankings = () => {
    const data = fetch('/app/getShowCaseRankings'/*, { mode: 'cors' }*/)
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}


export const getSummonerIcon = (profileIconNum) => {
    let url = `/summoner/getProfileIcon?profileIconNumber=${profileIconNum}`
    const img = fetch(url, /*, { mode: 'cors' }*/)
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
    const data = fetch('/app/getWinrateAllLineCharts'/*, { mode: 'cors' }*/)
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}


export const getVisionScoreLineChart = () => {
    const data = fetch('/app/getVisionPerMinuteChart'/*, { mode: 'cors' }*/)
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}

export const getGamesPerMinuteChart = () => {
    const data = fetch('/app/getGamesPerMinuteChart'/*, { mode: 'cors' }*/)
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}