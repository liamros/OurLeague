import { LinearProgress } from "@mui/material";
import { AnimatePresence } from "framer-motion";
import React from 'react';
import { connect } from "react-redux";
import { fetchShowCaseRankings } from "../../actions";
import ShowCaseItem from "./ShowCaseItem";
import ShowCaseList from "./ShowCaseList";

const ShowCase = ({ match, showCaseRankings, isFetching, fetchShowCaseRankings }) => {

    

    React.useEffect(() => {
        fetchShowCaseRankings()
    }, [])

    let statName = null
    if (match)
        statName = match.params.statName


    console.log(statName)
    return (
        <>
            {!isFetching && showCaseRankings ? (
                <>
                    <ShowCaseList selectedId={statName} showCaseRankings={showCaseRankings} />
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
        showCaseRankings: state.showCaseRankings.showCaseRankings,
        isFetching: state.isFetching,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        fetchShowCaseRankings: () => dispatch(fetchShowCaseRankings())
    }
}


export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ShowCase)