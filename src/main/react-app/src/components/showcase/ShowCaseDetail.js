import { Card, CardMedia, Typography, typographyClasses } from "@mui/material";
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
            <Card 
            elevation={10} 
            style={styles.card}
            className="showcaseDetail"              
            >
                {
                    this.state.profileIcon ?
                    (<CardMedia
                        component="img"
                        image={this.state.profileIcon}
                        style={styles.cardMedia}
                    />) : null
                }
                <Typography style={styles.typography}>{summonerName}</Typography>
                <Typography style={styles.typography}>{statName}</Typography>
                {value ? 
                <Typography style={styles.typography}>{value}</Typography> 
                : <Typography style={styles.typography}>{description}</Typography>}
                
                
            </Card>
        );
    }
}

const styles = {
    typography: {
        margin: "2%",
        color: "rgb(208, 168, 92)",
        fontWeight: "bold"
    },
    card: {
        width: '29%',
        display: "inline-block",
        margin: "2%",
        backgroundColor: "rgb(6, 28, 37)"
    },
    cardMedia: {
        borderRadius: "50%", 
        width: "20%", 
        margin: "auto", 
        marginTop: "5%", 
        border: "2px solid rgb(208, 168, 92)"
    } 
}

export default ShowCaseDetail