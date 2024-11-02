import React, { useEffect, useState } from "react";
import axios from "axios";
import BASE_URL from "../Config/Config";
import { fetchLinks } from "../Utils/ApiCallingService";

function LinkList() {
  const [links, setLinks] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchLinks()
      .then((data) => {
        if (data.status === 200) {
          setLinks(data.data);
        } else {
          setError("Error al obtener los datos");
        }
      })
      .catch((error) => {
        setError("Error de conexión");
        console.error(error);
      });
  }, []);

  return (
    <div className="LinkList">
      <h1>Shortened Links</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <table border="1">
        <thead>
          <tr>
            <th>ID</th>
            <th>Original Link</th>
            <th>Shortened Link</th>
            <th>Alias of the link</th>
            <th>Creatin Date</th>
            <th>Expiraation Date</th>
          </tr>
        </thead>
        <tbody>
          {links.map((link) => (
            <tr key={link.linkId}>
              <td>{link.linkId}</td>
              <td>{link.originalLink}</td>
              <td>{link.shortLink}</td>
              <td>{link.linkAlias || "N/A"}</td>
              <td>{new Date(link.creationDate).toLocaleDateString()}</td>
              <td>{new Date(link.expirationDate).toLocaleDateString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default LinkList;
