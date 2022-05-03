import { Card, CardMedia, CircularProgress, Typography } from "@mui/material";
import { motion } from "framer-motion";
import * as React from 'react';
import { Link } from "react-router-dom";
import StyledEngineProvider from '@mui/material/StyledEngineProvider';



const ShowCaseRanking = (props) => {


    const [stats] = React.useState(props.stats)




    var statName = props.name
    var description = stats.description
    var summonerName = stats.summonerName
    var profileIcon = stats.profileIcon
    return (
        <motion.div
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.9 }}
            layoutId={`card-${statName}`}
            style={{ position: "relative" }}
        >
            {
                profileIcon ?
                    (
                        <StyledEngineProvider injectFirst>
                            <Card
                                elevation={10}
                                className="showcaseDetail card"
                            >
                                <motion.div layoutId={`card-title-${statName}`}>
                                    <Typography className="card-typography-title">{statName}</Typography>
                                </motion.div>
                                <motion.div layoutId={`card-content-${statName}`}>
                                    <CardMedia
                                        component="img"
                                        image={profileIcon}
                                        className="card-media"

                                    />
                                </motion.div>
                                <motion.div layoutId={`card-name-${statName}`}>
                                    <Typography fontWeight={"bold"} className="card-typography">{summonerName}</Typography>
                                </motion.div>
                                <Typography className="card-typography">{description}</Typography>



                            </Card>
                            <Link to={statName} className={`card-open-link`} />
                            </StyledEngineProvider>) : <CircularProgress />
            }
        </motion.div>
    );

}



export default ShowCaseRanking