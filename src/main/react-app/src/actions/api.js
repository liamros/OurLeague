export const getShowCaseRankings = () => {
    const data = fetch('http://localhost:8080/app/getShowCaseRankings', { mode: 'cors' })
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}


export const getSummonerIcon = (profileIconNum) => {
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

export const getWrLineChart = () => {
    const data = fetch('http://localhost:8080/app/getWinrateAllLineCharts', { mode: 'cors' })
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}


export const getVisionScoreLineChart = () => {
    const data = fetch('http://localhost:8080/app/getVisionPerMinuteChart', { mode: 'cors' })
        .then(res => res.json())
        .then((data) => {
            return data
        }).catch(console.log)
    return data
}