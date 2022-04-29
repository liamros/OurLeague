import { Card, CardMedia, CircularProgress, Typography } from "@mui/material";
import { motion } from "framer-motion";
import * as React from 'react';
import { Link } from "react-router-dom";



const ShowCaseRanking = (props) => {


    const [stats] = React.useState(props.stats)




    var statName = props.name
    var description = stats.description
    var summonerName = stats.summonerName
    var value = stats.value
    var profileIcon = stats.profileIcon
    return (
        <motion.div
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.9 }}
            style={styles.container}
            layoutId={`card-${statName}`}
        >
            {
                profileIcon ?
                    (
                        <div style={{ position: "relative" }}>
                            <Card
                                elevation={10}
                                style={styles.card}
                                className="showcaseDetail"
                            >
                                <Typography style={styles.typographyTitle}>{statName}</Typography>
                                <motion.div layoutId={`card-content-${statName}`}>
                                    <CardMedia
                                        component="img"
                                        image={profileIcon}
                                        style={styles.cardMedia}

                                    />

                                    
                                    
                                    <Typography fontWeight={"bold"} style={styles.typography}>{summonerName}</Typography>
                                    <Typography style={styles.typography}>{description}</Typography>
                                </motion.div>


                            </Card>
                            <Link to={statName} className={`card-open-link`} />
                        </div>) : <CircularProgress />
            }
        </motion.div>
    );

}

const styles = {
    typography: {
        margin: "2%",
        color: "rgb(208, 168, 92)",
        fontSize: "0.9vw",
    },
    typographyTitle: {
        margin: "2%",
        color: "rgb(208, 168, 92)",
        fontWeight: "bold",
        fontSize: "1.1vw",
        marginTop: "3%",
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
        marginTop: "4%",
        border: "2px solid rgb(208, 168, 92)"
    },
    container: {
        width: '100%',
        display: "inline-block",
        margin: "0%",
        padding: "0%"
    }
}

export default ShowCaseRanking