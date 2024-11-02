import React, { useEffect, useState } from "react";
import {
  fetchLinks,
  addLink,
  updateLinkAlias,
  deleteLink,
} from "../Utils/ApiCallingService";
import AddLinkForm from "./AddLinkForm";

function LinkList() {
  const [links, setLinks] = useState([]);
  const [error, setError] = useState(null);
  const [editLinkId, setEditLinkId] = useState(null); // ID of the link being edited
  const [newAlias, setNewAlias] = useState(""); // New alias to update

  useEffect(() => {
    fetchLinks()
      .then((data) => {
        if (data.status === 200) {
          setLinks(data.data);
        } else {
          setError("Error retrieving data");
        }
      })
      .catch((error) => {
        setError("Connection error");
        console.error(error);
      });
  }, []);

  const handleAddLink = (newLink) => {
    addLink(newLink)
      .then((response) => {
        if (response.status === 200) {
          setLinks([...links, response.data]);
        } else {
          setError("Error adding the link");
        }
      })
      .catch((error) => {
        setError("Error adding the link");
        console.error(error);
      });
  };

  const handleEditClick = (linkId, currentAlias) => {
    setEditLinkId(linkId); // Set the ID of the link to edit
    setNewAlias(currentAlias || ""); // Initialize alias with current alias or empty
  };

  const handleUpdateLink = () => {
    if (!newAlias) {
      setError("Alias cannot be empty");
      return;
    }

    updateLinkAlias(editLinkId, newAlias)
      .then((response) => {
        if (response.status === 200) {
          setLinks(
            links.map((link) =>
              link.linkId === editLinkId
                ? { ...link, linkAlias: newAlias }
                : link
            )
          );
          setEditLinkId(null); // End editing
          setNewAlias("");
        } else {
          setError("Error updating the alias");
        }
      })
      .catch((error) => {
        setError("Error updating the alias");
        console.error(error);
      });
  };

  const handleDeleteLink = (linkId) => {
    deleteLink(linkId)
      .then((success) => {
        if (success) {
          setLinks(links.filter((link) => link.linkId !== linkId));
        } else {
          setError("Error deleting the link");
        }
      })
      .catch((error) => {
        setError("Error deleting the link");
        console.error(error);
      });
  };

  return (
    <div className="App">
      <h1>Links</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}

      <AddLinkForm onAddLink={handleAddLink} />

      <table border="1">
        <thead>
          <tr>
            <th>ID</th>
            <th>Original Link</th>
            <th>Short Link</th>
            <th>Alias</th>
            <th>Creation Date</th>
            <th>Expiration Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {links.map((link) => (
            <tr key={link.linkId}>
              <td>{link.linkId}</td>
              <td>{link.originalLink}</td>
              <td>{link.shortLink}</td>
              <td>
                {editLinkId === link.linkId ? (
                  <input
                    type="text"
                    value={newAlias}
                    onChange={(e) => setNewAlias(e.target.value)}
                  />
                ) : (
                  link.linkAlias || "N/A"
                )}
              </td>
              <td>{new Date(link.creationDate).toLocaleDateString()}</td>
              <td>{new Date(link.expirationDate).toLocaleDateString()}</td>
              <td>
                {editLinkId === link.linkId ? (
                  <button onClick={handleUpdateLink}>Save</button>
                ) : (
                  <button
                    onClick={() => handleEditClick(link.linkId, link.linkAlias)}
                  >
                    Edit
                  </button>
                )}
                <button onClick={() => handleDeleteLink(link.linkId)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default LinkList;
