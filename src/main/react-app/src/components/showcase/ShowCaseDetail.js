import { Card, CardMedia, CircularProgress, Typography } from "@mui/material";
import { motion } from "framer-motion";
import * as React from 'react';



const ShowCaseDetail = (props) => {


    const [stats, setStats] = React.useState(props.stats)
    const [profileIcon, setProfileIcon] = React.useState(null)

    React.useEffect(() => {
        getSummonerIcon()
    }, [])

    const getSummonerIcon = () => {
        let url = `http://localhost:8080/match/getProfileIcon?profileIconNumber=${stats.profileIconNum}`
        fetch(url, { mode: 'cors' })
            .then(response =>
                response.blob())
            .then(imgBlob => {
                const img = URL.createObjectURL(imgBlob)
                setProfileIcon(img)
            })
            .catch(error =>
                console.log(error))
    }


    var statName = stats.statName
    var description = stats.description
    var summonerName = stats.summonerName
    var value = stats.value
    return (
        <motion.div 
            // drag="x"
            // dragConstraints={{ left: -100, right: 100 }}
            whileHover={{ scale: 1.1 }}
            // whileTap={{ scale: 0.9 }}
            style={styles.container}>
            {
                profileIcon ?
                    (<Card
                        elevation={10}
                        style={styles.card}
                        className="showcaseDetail"
                    >
                        <CardMedia
                            component="img"
                            image={profileIcon}
                            style={styles.cardMedia}
                        />
                        <Typography style={styles.typographyTitle}>{summonerName}</Typography>
                        <Typography style={styles.typography}>{statName}</Typography>
                        {value ?
                            <Typography style={styles.typography}>{value}</Typography>
                            : <Typography style={styles.typography}>{description}</Typography>}


                    </Card>) : <CircularProgress />}
        </motion.div>
    );

}

const styles = {
    typography: {
        margin: "2%",
        color: "rgb(208, 168, 92)",
    },
    typographyTitle: {
        margin: "2%",
        color: "rgb(208, 168, 92)",
        fontWeight: "bold",
        fontSize: "1.2rem"
    },
    card: {
        width: '100%',
        display: "inline-block",
        margin: "0%",
        backgroundColor: "rgb(6, 28, 37)",
        border: "3px solid rgb(208, 168, 92)"
    },
    cardMedia: {
        borderRadius: "50%",
        width: "20%",
        margin: "auto",
        marginTop: "5%",
        border: "2px solid rgb(208, 168, 92)"
    },
    container: {
        width: '100%',
        display: "inline-block",
        margin: "0%",
        padding: "0%"
    }
}

export default ShowCaseDetail