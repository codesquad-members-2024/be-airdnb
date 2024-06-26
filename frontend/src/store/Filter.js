import {get, writable} from "svelte/store";
import {addDays, format} from "date-fns";

const setFilters = () => {

    let initValues = {
        guestCount: 1,
        infantCount: 0,
        checkInDate: format(new Date(), 'yyyy-MM-dd'),
        checkOutDate: format(addDays(new Date(), 2), 'yyyy-MM-dd'),
        latitude: 37.490823,
        longitude: 127.033435,
    }

    const {subscribe, update, set} = writable({...initValues});

    const updatePeopleCount = (guestCount, infantCount=0) => {
        update(data => {
            data.guestCount = guestCount;
            data.infantCount = infantCount;
            return data;
        })
    }

    const updateCheckInCheckOut = (checkInDate, checkOutDate) => {
        update(data => {
            data.checkInDate = format(checkInDate, 'yyyy-MM-dd');
            data.checkOutDate = format(checkOutDate, 'yyyy-MM-dd');
            return data;
        })
    }

    const updateCoordinate = (latitude, longitude) => {
        update(data => {
            data.latitude = latitude;
            data.longitude = longitude;
            return data;
        })
    }

    const resetOptions = () => {
        update(data => {
            data.guestCount = 1;
            data.infantCount = 1;
            data.checkInDate = format(new Date(), 'yyyy-MM-dd');
            data.checkOutDate =  format(addDays(new Date(), 2), 'yyyy-MM-dd');
            data.latitude = 0;
            data.longitude = 0;
        })
    }

    const generateFilterQueryString = () => {
        let queryParams = [];

        subscribe(values => {
            const { guestCount, infantCount, checkInDate, checkOutDate, latitude, longitude } = values;

            const guestCountParam = `guestCount=${guestCount}`;
            const infantCountParam = (infantCount === 0) ? `infantCount=1` : `infantCount=${infantCount}`;
            const checkInCheckOutParam = `checkInDate=${checkInDate}&checkOutDate=${checkOutDate}`;
            const coordinateParam = `latitude=${latitude}&longitude=${longitude}`;

            queryParams = [guestCountParam, infantCountParam, checkInCheckOutParam, coordinateParam].filter(Boolean);
        })();

        return "?" + queryParams.join('&');
    }

    return {
        updateCheckInCheckOut,
        updatePeopleCount,
        updateCoordinate,
        resetOptions,
        generateFilterQueryString,
        subscribe,
        set,
    }
}

export const filter = setFilters();