import React, { useState } from "react";
import axios from "axios";

const AccommodationForm = () => {
  const [formData, setFormData] = useState({
    type: "RESORT",
    address: {
      address: "",
      zipCode: "",
      longitude: "",
      latitude: "",
    },
    roomInformation: {
      bedCount: 1,
      bedroomCount: 1,
      bathroomCount: 1,
      private: true,
      maxOccupancy: 1,
    },
    name: "",
    description: "",
    pictures: [],
    basePricePerDay: 0,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleNestedChange = (e, category) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [category]: {
        ...prevState[category],
        [name]: value,
      },
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("/api/accommodation", formData, {
        headers: {
          "Content-Type": "application/json",
        },
      });
      console.log("숙소 등록 성공:", response.data);
    } catch (error) {
      console.error("숙소 등록 실패:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Name:
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
        />
      </label>
      <label>
        Description:
        <input
          type="text"
          name="description"
          value={formData.description}
          onChange={handleChange}
        />
      </label>
      <label>
        Address:
        <input
          type="text"
          name="address"
          value={formData.address.address}
          onChange={(e) => handleNestedChange(e, "address")}
        />
      </label>
      <label>
        Zip Code:
        <input
          type="text"
          name="zipCode"
          value={formData.address.zipCode}
          onChange={(e) => handleNestedChange(e, "address")}
        />
      </label>
      <label>
        Longitude:
        <input
          type="number"
          name="longitude"
          value={formData.address.longitude}
          onChange={(e) => handleNestedChange(e, "address")}
        />
      </label>
      <label>
        Latitude:
        <input
          type="number"
          name="latitude"
          value={formData.address.latitude}
          onChange={(e) => handleNestedChange(e, "address")}
        />
      </label>
      <label>
        Bed Count:
        <input
          type="number"
          name="bedCount"
          value={formData.roomInformation.bedCount}
          onChange={(e) => handleNestedChange(e, "roomInformation")}
        />
      </label>
      <label>
        Bedroom Count:
        <input
          type="number"
          name="bedroomCount"
          value={formData.roomInformation.bedroomCount}
          onChange={(e) => handleNestedChange(e, "roomInformation")}
        />
      </label>
      <label>
        Bathroom Count:
        <input
          type="number"
          name="bathroomCount"
          value={formData.roomInformation.bathroomCount}
          onChange={(e) => handleNestedChange(e, "roomInformation")}
        />
      </label>
      <label>
        Private:
        <input
          type="checkbox"
          name="private"
          checked={formData.roomInformation.private}
          onChange={(e) =>
            handleNestedChange(
              { target: { name: "private", value: e.target.checked } },
              "roomInformation"
            )
          }
        />
      </label>
      <label>
        Max Occupancy:
        <input
          type="number"
          name="maxOccupancy"
          value={formData.roomInformation.maxOccupancy}
          onChange={(e) => handleNestedChange(e, "roomInformation")}
        />
      </label>
      <label>
        Base Price Per Day:
        <input
          type="number"
          name="basePricePerDay"
          value={formData.basePricePerDay}
          onChange={handleChange}
        />
      </label>
      <button type="submit">Register Accommodation</button>
    </form>
  );
};

export default AccommodationForm;
