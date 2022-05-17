import { MenuItem, Typography } from '@mui/material';
import React from 'react';
import { connect } from "react-redux";
import { selectShowCase } from "../../actions";

const QueueMenuItems = ({ selectShowCase, showCases, selectedQueue, callback }) => {


    const onClick = (e, callback) => {
        if (selectedQueue !== e.currentTarget.id){
            selectShowCase(e.currentTarget.id)
        }
        callback()
    }


    const jsx = []
    if (showCases) {
        const map = new Map(Object.entries(showCases))
        map.forEach(((_, key) => {
            jsx.push(
                <MenuItem id={key} key={key} onClick={(e) => onClick(e, callback)}>
                  <Typography textAlign="center">{key}</Typography>
                </MenuItem>
            )
        })
        )
    }
    return (
        <>
            {jsx}
        </>
    )
}


function mapStateToProps(state) {
    return {
        showCases: state.showCaseRankings.data,
        selectedQueue: state.showCaseRankings.selected
    }
}

function mapDispatchToProps(dispatch) {
    return {
        selectShowCase: (selection) => dispatch(selectShowCase(selection)),
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(QueueMenuItems)