import { CircularProgress } from "@mui/material";
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import React from "react";
import { connect } from "react-redux";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { fetchHomeLineCharts, fetchShowCaseRankings } from "../../actions";


const UpdateButton = ({ fetchShowCaseRankings, fetchHomeLineCharts }) => {

    const [updating, setUpdating] = React.useState(false)
    

    var stompClient

    const [wsURI, setWsURI] = React.useState()
    /**
     * required because in dev the proxy for websocets does not work
     */
    React.useEffect(() => {
        const protocolPrefix = window.location.protocol;
        let { host } = window.location; // nb: window location contains the port, so host will be localhost:3000 in dev
        if (host === "localhost:3000")
            host = "localhost:5000"
        setWsURI(`${protocolPrefix}//${host}/ws`)
    }, [])

    const onClick = () => {
        setUpdating(true)
        var socket = new SockJS(wsURI);
        stompClient = Stomp.over(socket)
        stompClient.connect({}, onConnected)
    }

    const onConnected = () => {
        console.log("onConnected");
        stompClient.subscribe("/topic/dataRefresh", onMessageReceived);
        stompClient.send(
            "/requestRefreshData",
            {}
        );
    }

    const onMessageReceived = (payload) => {
        console.log("onMessageReceived");
        var message = JSON.parse(payload.body);
        console.log(message)
        stompClient.disconnect()
        setUpdating(false)

        fetchShowCaseRankings()
        fetchHomeLineCharts()
    }
    return (
        <>
            {updating ? <Box style={{minWidth: "70.83px"}}><CircularProgress color="secondary" /></Box> :
                <Button
                    sx={{ my: 2, color: 'white', display: 'block' }}
                    onClick={onClick}
                    style={{margin: 0}}
                >
                    UPDATE
                </Button>}
        </>

    )


}

function mapDispatchToProps(dispatch) {
    return {
        fetchShowCaseRankings: () => dispatch(fetchShowCaseRankings()),
        fetchHomeLineCharts: () => dispatch(fetchHomeLineCharts())
    }
}

export default connect(
    null,
    mapDispatchToProps
)(UpdateButton)