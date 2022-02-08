import { Card, CardMedia, Typography } from "@mui/material";
import * as React from 'react';



class ShowCaseDetail extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            stats: props.stats,
            profileIconNum: props.stats.profileIconNum,
            profileIcon: null
        };
    }

    componentDidMount() {
        this.getSummonerIcon()
    }

    getSummonerIcon() {
        let url = `http://localhost:8080/match/getProfileIcon?profileIconNumber=${this.state.profileIconNum}`
        fetch(url, { mode: 'cors' })
            .then(response =>
                response.blob())
            .then(imgBlob => {
                const img = URL.createObjectURL(imgBlob)
                this.setState({ profileIcon: img })
            })
            .catch(error =>
                console.log(error))
    }

    render() {
        var statName = this.state.stats.statName
        var description = this.state.stats.description
        var summonerName = this.state.stats.summonerName
        var value = this.state.stats.value
        return (
            <Card style={{ width: '33%', display: "inline-block", margin: "2%" }}  >
                {
                    this.state.profileIcon ?
                    (<CardMedia
                        component="img"
                        image={this.state.profileIcon}
                        style={{borderRadius: "50%", width: "20%", marginLeft: "auto", marginRight: "auto"}}
                    />) : null
                }
                <Typography style={styles}>{summonerName}</Typography>
                <Typography style={styles}>{statName}</Typography>
                <Typography style={styles}>{value}</Typography>
                <Typography style={styles}>{description}</Typography>
            </Card>
        );
    }
}

const styles = {
    marginTop: "2%"
}

export default ShowCaseDetail