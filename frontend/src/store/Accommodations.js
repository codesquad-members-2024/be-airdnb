import { subscribe, get, writable } from "svelte/store";
import FilePond, { registerPlugin, supported } from 'svelte-filepond';
import FilePondPluginImageExifOrientation from 'filepond-plugin-image-exif-orientation';
import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
registerPlugin(FilePondPluginImageExifOrientation, FilePondPluginImagePreview);

const setAccommodationCreation = () => {
    let initValues = {
        placeCategory: "",
        amenities: [],
        floorPlans: {
            "게스트": 4,
            "침실": 1,
            "침대": 1,
            "욕실": 1,
        },
        locations: {
            coordinateX: 0,
            coordinateY: 0,
            country: "",
            province: "",
            city: "",
            district: "",
            streetAddress: "",
            apartment: "",
            postalCode: "",
        },
        imageUrls: [],
        title: "",
        description: "",
        price: 0,
    }
    const { subscribe, set, update } = writable(initValues);

    const updatePlaceCategory = (selectedCategory) => {
        update(data => {
            data.placeCategory = selectedCategory;
            return data;
        })
    };

    const updateAmenities = (amenityName) => {
        update(data => {
            if(data.amenities.includes(amenityName)) {
                data.amenities = data.amenities.filter(item => item !== amenityName);
            } else {
                data.amenities = [...data.amenities, amenityName];
            }
            return data;
        })
    }

    const updateTitle = (title) => {
        update(store => {
            store.title = title;
            return store;
        });
    }

    const updateDescription = (description) => {
        update(store => {
            store.description = description;
            return store;
        });
    }

    const updatePrice = (price) => {
        update(store => {
            store.price = price;
            return store;
        });
    }

    const updateLocation = (coordinateX, coordinateY) => {
        update(store => {
            store.locations.coordinateX = coordinateX;
            store.locations.coordinateY = coordinateY;
        })
    }


    return {
        subscribe,
        set,
        updateLocation,
        updatePrice,
        updateDescription,
        updateTitle,
        updateAmenities,
        updatePlaceCategory,
    };
};

export const accommodationStore = setAccommodationCreation();