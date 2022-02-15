import { getShowCaseDetails, getSummonerIcon } from './api'

export const initShowCase = () => ({
    type: 'INIT_SHOWCASE',
})

export const initShowCaseSuccess = (showCaseDetails) => ({
    type: 'INIT_SHOWCASE_SUCCESS',
    payload: showCaseDetails,
})


export function fetchShowCaseDetails() {
    return (dispatch) => {
        dispatch(initShowCase())
        getShowCaseDetails()
            .then((data) => {
                var promises = data.map((element) => {
                    return getSummonerIcon(element.profileIconNum)
                        .then((img) => {
                            element.profileIcon = img
                            return element
                        })
                })
                Promise.all(promises).then((data) => dispatch(initShowCaseSuccess(data)))

            })
    }
}