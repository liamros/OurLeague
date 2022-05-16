import { Button } from "@mui/material";
import React from 'react';
import { connect } from "react-redux";
import { selectShowCase } from "../../actions";

const QueueButtons = ({ selectShowCase, showCases, selectedQueue }) => {

    const [selected, setSelected] = React.useState(selectedQueue)

    React.useEffect(() => {
        setSelected(selectedQueue)
    }, [selectShowCase])

    const onClick = (e) => {
        if (selected !== e.currentTarget.id){
            setSelected(e.currentTarget.id)
            selectShowCase(e.currentTarget.id)
        }
    }


    const jsx = []
    if (showCases) {
        const map = new Map(Object.entries(showCases))
        map.forEach(((_, key) => {
            var style = styles.button
            if (key === selectedQueue)
                style = styles.selectedButton
            jsx.push(
                <Button
                    id={key}
                    style={style}
                    className="typography"
                    key={key}
                    onClick={onClick}>
                    {key}
                </Button>)
        })
        )
    }
    return (
        <>
            {jsx}
        </>
    )
}

const styles = {
    button: {
        color: "#FFFF",
        backgroundColor: "rgba(6, 28, 37)",
        borderColor: "#FFFF",
    },
    selectedButton: {
        color: "rgba(6, 28, 37)",
        backgroundColor: "#FFFF",
        borderColor: "#FFFF",
    }
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
)(QueueButtons)