export const getShowCaseDetails = () => {
    const data = fetch('http://localhost:8080/summoner/getShowCaseDetails', { mode: 'cors' })
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