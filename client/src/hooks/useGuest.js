import { useState } from 'react';
import { LOG } from '../utils/constants';
import { sendAPIRequest } from '../utils/restfulAPI';

export function useGuest() {
    const [validity, setValidity] = useState(false);

    const guest = {
        validity: validity
    };

    const guestActions = {
        setValidity: setValidity,
    };

    const makeGuestRequest = async (piece, destination, board, serverURL) => {
        const requestBody = { requestType: "guestMove", piece: piece, destination: destination, board: board, validity: validity };
        const guestResponse = await sendAPIRequest(requestBody, serverURL);

        if (guestResponse) {
            // You may want to process the response further
            // return response data if necessary
            setValidity(guestResponse.validity);
            return guestResponse;
        } else {
            LOG.error(`Guest request to ${serverURL} failed. Check the log for more details.`, "error");
            return null;
        }
    };

    return { guest, guestActions, makeGuestRequest };
}
