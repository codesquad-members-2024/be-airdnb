import { useEffect, useRef } from "react";

const useClickOutside = (onClose, dependencies = []) => {
  const ref = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (ref.current && !ref.current.contains(event.target)) {
        onClose();
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [onClose, ...dependencies]);

  return ref;
};

export default useClickOutside;
