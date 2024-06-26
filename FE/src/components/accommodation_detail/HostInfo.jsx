import React from 'react';
import '../../styles/HostInfo.css';

const HostInfo = ({ host }) => (
  <div className="host-info">
    <img src={host.profileImage} alt={host.name} />
    <div>
      <p>{host.name}</p>
    </div>
  </div>
);

export default HostInfo;