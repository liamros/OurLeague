import { AnimatePresence } from "framer-motion";
import React from 'react';
import { ShowCaseItem } from "./ShowCaseItem";
import ShowCaseList from "./ShowCaseList";

const ShowCase = ({ match }) => {


    const [showCaseDetails, setShowCaseDetails] = React.useState(null)

    React.useEffect(() => {
        getShowCaseDetails()
    }, [])

    const getShowCaseDetails = () => {
        fetch('http://localhost:8080/match/getShowCaseDetails', { mode: 'cors' })
            .then(res => res.json())
            .then((data) => {
                setShowCaseDetails(data)
            }).catch(console.log)
    }


    let statName = null
    if (match)
        statName = match.params.statName

    let stats = null
    if (showCaseDetails) {
        showCaseDetails.map((element) => {
            if (element.statName === statName)
                stats = element
        })
    }

    console.log(statName)
    return (
        <>
            {showCaseDetails ? (
                <>
                    <ShowCaseList selectedId={statName} showCaseDetails={showCaseDetails} />
                    <AnimatePresence>
                        {statName && <ShowCaseItem id={statName} key="item" stats={stats} />}
                    </AnimatePresence>
                </>
            ) : <h1>Loading...</h1>
            }
        </>
    );
}



export default ShowCase