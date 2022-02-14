import { LinearProgress } from "@mui/material";
import { AnimatePresence } from "framer-motion";
import React from 'react';
import { connect } from "react-redux";
import { fetchShowCaseDetails } from "../../actions";
import ShowCaseItem from "./ShowCaseItem";
import ShowCaseList from "./ShowCaseList";

const ShowCase = ({ match, showCaseDetails, isFetching, fetchShowCaseDetails }) => {

    

    React.useEffect(() => {
        fetchShowCaseDetails()
    }, [])

    let statName = null
    if (match)
        statName = match.params.statName


    console.log(statName)
    return (
        <>
            {!isFetching && showCaseDetails ? (
                <>
                    <ShowCaseList selectedId={statName} showCaseDetails={showCaseDetails} />
                    <AnimatePresence>
                        {statName && <ShowCaseItem id={statName} key="item" />}
                    </AnimatePresence>
                </>
            ) : <LinearProgress />
            }
        </>
    );
}

function mapStateToProps(state) {
    return {
        showCaseDetails: state.showCaseDetails,
        isFetching: state.isFetching,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        fetchShowCaseDetails: () => dispatch(fetchShowCaseDetails())
    }
}


export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ShowCase)