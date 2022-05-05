import { CircularProgress } from "@mui/material";
import Button from '@mui/material/Button';
import React from "react";
import { connect } from "react-redux";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { fetchShowCaseRankings, fetchHomeLineCharts } from "../../actions";


const UpdateButton = ({fetchShowCaseRankings ,fetchHomeLineCharts}) => {

    const [updating, setUpdating] = React.useState(false)

    var stompClient
    const onClick = () => {
        setUpdating(true)
        var socket = new SockJS('http://localhost:5000/ws');
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
            {updating ? <CircularProgress /> :
                <Button
                    sx={{ my: 2, color: 'white', display: 'block' }}
                    onClick={onClick}

                >
                    Update
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