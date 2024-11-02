import React, { useState } from 'react';

function AddLinkForm({ onAddLink }) {
  const [alias, setAlias] = useState('');
  const [originalLink, setOriginalLink] = useState('');
  const [notification, setNotification] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (alias && originalLink) {
      onAddLink({ alias, originalLink });
      setNotification('Link agregado exitosamente');
      setAlias('');
      setOriginalLink('');
    } else {
      setNotification('Por favor completa todos los campos');
    }
  };

  return (
    <div>
      <h2>Agregar Nuevo Link</h2>
      {notification && <p style={{ color: 'green' }}>{notification}</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Alias:</label>
          <input
            type="text"
            value={alias}
            onChange={(e) => setAlias(e.target.value)}
          />
        </div>
        <div>
          <label>Link Original:</label>
          <input
            type="text"
            value={originalLink}
            onChange={(e) => setOriginalLink(e.target.value)}
          />
        </div>
        <button type="submit">Agregar Link</button>
      </form>
    </div>
  );
}

export default AddLinkForm;
