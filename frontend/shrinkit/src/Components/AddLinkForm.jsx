import React, { useState, useEffect } from "react";

function AddLinkForm({ onAddLink }) {
  const [alias, setAlias] = useState("");
  const [originalLink, setOriginalLink] = useState("");
  const [notification, setNotification] = useState(null);
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    if (notification) {
      setIsVisible(true);
      const timer = setTimeout(() => {
        setIsVisible(false);
        setTimeout(() => {
          setNotification(null);
        }, 300); // Esperar a que termine la animación de fade out
      }, 2000);

      return () => clearTimeout(timer);
    }
  }, [notification]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (alias && originalLink) {
      onAddLink({ alias, originalLink });
      setNotification("Link agregado exitosamente");
      setAlias("");
      setOriginalLink("");
    } else {
      setNotification("Por favor completa todos los campos");
    }
  };

  return (
    <div className="mb-8 flex flex-col items-center relative">
      <h2 className="text-2xl font-semibold text-white mb-4 text-center">
        ShrinkIt!
        <h5 className="text-sm font-normal text-white opacity-70 pt-4 mb-4 text-center">
          Making easy shorting your links
        </h5>
      </h2>

      {/* Notification Toast */}
      {notification && (
        <div
          className={`
          fixed top-4 right-4 
          ${
            isVisible
              ? "animate-fade-in opacity-100"
              : "animate-fade-out opacity-0"
          }
          transition-opacity duration-300
          ${
            notification.includes("exitosamente")
              ? "bg-green-500"
              : "bg-red-500"
          }
          text-white px-6 py-3 rounded-lg shadow-lg
          flex items-center gap-2
        `}
        >
          <span className="text-sm font-medium">{notification}</span>
        </div>
      )}

      <form
        onSubmit={handleSubmit}
        className="flex items-center justify-center gap-6"
      >
        <div className="flex items-center gap-2">
          <label className="text-white">Alias for the link:</label>
          <input
            type="text"
            value={alias} 
            onChange={(e) => setAlias(e.target.value)}
            className="bg-transparent border-b border-white/30 focus:border-white px-2 py-1 outline-none text-white"
          />
        </div>
        <div className="flex items-center gap-2">
          <label className="text-white">Original Url:</label>
          <input
            type="text"
            value={originalLink}
            onChange={(e) => setOriginalLink(e.target.value)}
            className="bg-transparent border-b border-white/30 focus:border-white px-2 py-1 outline-none text-white"
          />
        </div>
        <button
          type="submit"
          className="px-6 py-2 border border-white/30 rounded-full hover:bg-white/10 text-white transition-colors"
        >
          ShrinkIt!
        </button>
      </form>
    </div>
  );
}

export default AddLinkForm;
