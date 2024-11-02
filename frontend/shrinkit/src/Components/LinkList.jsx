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
    <div className="p-8 space-y-8">
      {error && <p className="text-red-400">{error}</p>}

      <AddLinkForm onAddLink={handleAddLink} />

      <table className="w-full border-separate border-spacing-0">
        <thead>
          <tr className="bg-zinc-700">
            <th className="py-3 text-center text-white border-r border-zinc-600 font-medium">
              ID
            </th>
            <th className="py-3 text-center text-white border-r border-zinc-600 font-medium">
              Original Link
            </th>
            <th className="py-3 text-center text-white border-r border-zinc-600 font-medium">
              Short Link
            </th>
            <th className="py-3 text-center text-white border-r border-zinc-600 font-medium">
              Alias
            </th>
            <th className="py-3 text-center text-white border-r border-zinc-600 font-medium">
              Creation Date
            </th>
            <th className="py-3 text-center text-white border-r border-zinc-600 font-medium">
              Expiration Date
            </th>
            <th className="py-3 text-center text-white font-medium">Actions</th>
          </tr>
        </thead>
        <tbody>
          {links.map((link) => (
            <tr
              key={link.linkId}
              className="border-b border-zinc-600 hover:bg-zinc-700/50"
            >
              <td className="py-3 px-4 text-center text-white border-r border-zinc-600">
                {link.linkId}
              </td>
              <td className="py-3 px-4 text-center text-white border-r border-zinc-600">
                {link.originalLink}
              </td>
              <td className="py-3 px-4 text-center text-white border-r border-zinc-600">
                <a
                  href={link.shortLink}
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  {link.shortLink}
                </a>
              </td>
              <td className="py-3 px-4 text-center text-white border-r border-zinc-600">
                {editLinkId === link.linkId ? (
                  <input
                    type="text"
                    value={newAlias}
                    onChange={(e) => setNewAlias(e.target.value)}
                    className="bg-transparent border-b border-white/30 focus:border-white px-2 py-1 outline-none text-center text-white"
                  />
                ) : (
                  link.linkAlias || "N/A"
                )}
              </td>
              <td className="py-3 px-4 text-center text-white border-r border-zinc-600">
                {new Date(link.creationDate).toLocaleDateString()}
              </td>
              <td className="py-3 px-4 text-center text-white border-r border-zinc-600">
                {new Date(link.expirationDate).toLocaleDateString()}
              </td>
              <td className="py-3 px-4 text-center text-white flex justify-center gap-2">
                {editLinkId === link.linkId ? (
                  <button
                    onClick={handleUpdateLink}
                    className="px-4 py-1.5 border border-white/30 rounded-full hover:bg-white/10 text-white transition-colors"
                  >
                    Save
                  </button>
                ) : (
                  <button
                    onClick={() => handleEditClick(link.linkId, link.linkAlias)}
                    className="px-4 py-1.5 border border-white/30 rounded-full hover:bg-white/10 text-white transition-colors"
                  >
                    Edit
                  </button>
                )}
                <button
                  onClick={() => handleDeleteLink(link.linkId)}
                  className="px-4 py-1.5 border border-white/30 rounded-full hover:bg-white/10 text-white transition-colors"
                >
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
