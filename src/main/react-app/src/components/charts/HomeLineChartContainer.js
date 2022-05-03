import { Button, ButtonGroup, Typography } from "@mui/material";
import React, { useState } from "react";
import { connect } from "react-redux";
import { fetchHomeLineCharts } from '../../actions';
import LineChart from "./LineChart";
import StyledEngineProvider from '@mui/material/StyledEngineProvider';

const HomeLineChartContainer = ({ data, fetchHomeLineCharts }) => {


    var [selected, setSelected] = useState("Games/Minute")

    React.useEffect(() => {
        fetchHomeLineCharts()
    }, [])

    const onClick = (e) => {
        if (selected !== e.currentTarget.id)
            setSelected(e.currentTarget.id)
    }

    var map = null
    if (data)
        map = new Map(Object.entries(data))
    var jsx = []
    if (map)
        map.forEach(((_, key) => {
            var style = styles.button
            if (key === selected)
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

    return (
        data &&
        <StyledEngineProvider injectFirst>
            <ButtonGroup className="typography" variant="contained" aria-label="outlined primary button group">
                {jsx}
            </ButtonGroup>
            <div className="typography-title">{data[selected].name}</div>
            <LineChart data={data[selected]} />
        </StyledEngineProvider>
    )

}

const styles = {
    button: {
        color: "rgb(208, 168, 92)",
        backgroundColor: "rgba(6, 28, 37)",
        borderColor: "rgb(208, 168, 92)",
    },
    selectedButton: {
        color: "rgba(6, 28, 37)",
        backgroundColor: "rgb(208, 168, 92)",
        borderColor: "rgb(208, 168, 92)",
    }
}


function mapStateToProps(state) {
    return {
        data: state.homeLineCharts.data,
        isFetching: state.isFetching,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        fetchHomeLineCharts: () => dispatch(fetchHomeLineCharts())
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(HomeLineChartContainer)