import { Container, Typography } from "@mui/material";
import * as React from 'react';



class ShowCaseDetail extends React.Component {

    constructor(props) {
        super(props);
        this.state = { stats: props.stats };
    }

    render() {
        var statName = this.state.stats.statName
        var description = this.state.stats.description
        var summonerName = this.state.stats.summonerName
        var value = this.state.stats.value
        return (
            <Container>
                <Typography>{summonerName}</Typography>
                <Typography>{statName}</Typography>
                <Typography>{value}</Typography>
                <Typography>{description}</Typography>
            </Container>
        );
    }
}

export default ShowCaseDetail