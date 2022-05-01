import { Button, ButtonGroup, Typography } from "@mui/material";
import React, { useState } from "react";
import { connect } from "react-redux";
import { fetchHomeLineCharts } from '../../actions';
import LineChart from "./LineChart";

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
                    key={key}
                    onClick={onClick}>
                    {key}
                </Button>)
        })
        )

    return (
        data &&
        <>
            <ButtonGroup variant="contained" aria-label="outlined primary button group">
                {jsx}
            </ButtonGroup>
            <Typography style={styles.typographyTitle}>{data[selected].name}</Typography>
            <LineChart data={data[selected]} />
        </>
    )

}

const styles = {
    typography: {
        margin: "2%",
        color: "rgb(208, 168, 92)",
        fontSize: "0.9vw",
    },
    typographyTitle: {
        margin: "1%",
        color: "rgb(208, 168, 92)",
        fontWeight: "bold",
        fontSize: "1.1vw",
        marginTop: "1%",
    },
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