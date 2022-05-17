import { LinearProgress, Typography } from "@mui/material";
import { AnimatePresence } from "framer-motion";
import React from 'react';
import { connect } from "react-redux";
import { fetchShowCaseRankings } from "../../actions";
import ShowCaseItem from "./ShowCaseItem";
import ShowCaseList from "./ShowCaseList";

const ShowCase = ({ match, showCaseRankings, isFetching, fetchShowCaseRankings, selectedQueue }) => {



    React.useEffect(() => {
        fetchShowCaseRankings()
    }, [])

    let statName = null
    if (match)
        statName = match.params.statName


    return (
        <>
            {!isFetching && showCaseRankings ? (
                <>
                        <Typography className="typography showcase title">{selectedQueue}</Typography>
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
    const selectedQueue = state.showCaseRankings.selected
    if (selectedQueue)
        return {
            showCaseRankings: state.showCaseRankings.data[selectedQueue],
            selectedQueue: selectedQueue,
            isFetching: state.isFetching,
        }
    else 
        return {} 
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