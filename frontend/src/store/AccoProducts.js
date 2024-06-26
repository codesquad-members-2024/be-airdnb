import {auth} from "./Auth.js";
import {get, writable} from "svelte/store";
import {getApi, postApi} from "../service/api.js";

const setAccoProductList = () => {
    let initValue = {
        loginId: get(auth).loginId,
        accoProductList: [],
    }

    const {subscribe, update, set} = writable({...initValue});

    const fetchAccoProducts = async (queryString= "") => {
        try{
            const options = {
                path: `/guest/accommodations${queryString}`,
                accessToken: get(auth).accessToken
            }

            const getDatas = await getApi(options);
            update(data => {
                data.accoProductList = [...getDatas]
                console.log('fetch product list: ', data.accoProductList);
                return data;
            })
        } catch (error) {
            console.log(error);
            alert(error.message);
        }
    }

    const openReservationModal = (accoId) => {
        update(data => {
            data.openReservationModal = accoId;
            return data;
        })
    }

    const closeReservationModal = () => {
        update(data => {
            data.openReservationModal = -1;
            return data;
        })
    }

    return {
        subscribe,
        set,
        fetchAccoProducts,
        openReservationModal,
        closeReservationModal,
    }
}

const setReservation = () => {
    let initValue = {
        loginId: get(auth).loginId,
        title: '',
        accoId: '',
        checkIn: '',
        checkOut: '',
        totalGuests: 0,
        totalPrice: 0,
        reservationModal: '', // 예약 팝업이 열려있는지 체크하는 필드
    }
    const { subscribe, set, update } = writable({ ...initValue });

    const updateReservationModal = (options) => {
        update(data => {
            data.title = options.title;
            data.accoId = options.accoId;
            data.checkIn = options.checkIn;
            data.checkOut = options.checkOut;
            data.totalGuests = options.totalGuests;
            data.totalPrice = options.totalPrice;

            console.log(data);
            return data;
        })
    }

    const handleReservation = () => {
        let data = {};
        subscribe(values => {
            const {accoId, totalGuests, checkIn, checkOut} = values;
            data = {
                accoId: accoId,
                adultCount: totalGuests,
                startDate: checkIn,
                endDate: checkOut,
            };
        })

        try{
            const options = {
                path: '/guest/accommodations/reserve',
                data: data,
                accessToken: get(auth).accessToken
            }
            const response = postApi(options);
            console.log(response);
        } catch (error) {
            console.log(error);
            alert(error.message);
        }
    }

    return {
        subscribe,
        set,
        update,
        handleReservation,
        updateReservationModal,
    };
}

export const accoProducts = setAccoProductList();
export const reservation = setReservation();

//[
//     {
//         "accoId": 1,
//         "title": "Cozy Cottage",
//         "placeCategory": "Cottage",
//         "maxGuestCount": 4,
//         "maxInfantCount": 1,
//         "bedroomCount": 2,
//         "bathroomCount": 1,
//         "coordinate": {
//             "latitude": 37.7749,
//             "longitude": -122.4194
//         },
//         "totalPrice": 200
//     },
//     {
//         "accoId": 3,
//         "title": "Beach House",
//         "placeCategory": "House",
//         "maxGuestCount": 6,
//         "maxInfantCount": 1,
//         "bedroomCount": 3,
//         "bathroomCount": 2,
//         "coordinate": {
//             "latitude": 25.7617,
//             "longitude": -80.1918
//         },
//         "totalPrice": 600
//     },
//     {
//         "accoId": 4,
//         "title": "Mountain Cabin",
//         "placeCategory": "Cabin",
//         "maxGuestCount": 5,
//         "maxInfantCount": 2,
//         "bedroomCount": 2,
//         "bathroomCount": 1,
//         "coordinate": {
//             "latitude": 39.7392,
//             "longitude": -104.9903
//         },
//         "totalPrice": 300
//     },
//     {
//         "accoId": 6,
//         "title": "Suburban Home",
//         "placeCategory": "House",
//         "maxGuestCount": 4,
//         "maxInfantCount": 1,
//         "bedroomCount": 2,
//         "bathroomCount": 2,
//         "coordinate": {
//             "latitude": 41.8781,
//             "longitude": -87.6298
//         },
//         "totalPrice": 360
//     },
//     {
//         "accoId": 7,
//         "title": "Riverside Bungalow",
//         "placeCategory": "Bungalow",
//         "maxGuestCount": 3,
//         "maxInfantCount": 1,
//         "bedroomCount": 1,
//         "bathroomCount": 1,
//         "coordinate": {
//             "latitude": 45.5051,
//             "longitude": -122.675
//         },
//         "totalPrice": 440
//     },
//     {
//         "accoId": 9,
//         "title": "Countryside Villa",
//         "placeCategory": "Villa",
//         "maxGuestCount": 8,
//         "maxInfantCount": 2,
//         "bedroomCount": 4,
//         "bathroomCount": 3,
//         "coordinate": {
//             "latitude": 37.5407,
//             "longitude": -77.436
//         },
//         "totalPrice": 800
//     },
//     {
//         "accoId": 10,
//         "title": "Historic Townhouse",
//         "placeCategory": "Townhouse",
//         "maxGuestCount": 5,
//         "maxInfantCount": 1,
//         "bedroomCount": 3,
//         "bathroomCount": 2,
//         "coordinate": {
//             "latitude": 42.3601,
//             "longitude": -71.0589
//         },
//         "totalPrice": 540
//     }
// ]