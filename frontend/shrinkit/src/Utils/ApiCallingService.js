import axios from "axios";
import BASE_URL from "../Config/Config";

export const fetchLinks = (page, size) => {
  return axios.get(`${BASE_URL}/link`, {
    params: {
      page: page,
      size: size,
    },
  }).then((response) => response.data);
};

export const addLink = (newLink) => {
  const payload = {
    linkAlias: newLink.alias,
    link: newLink.originalLink,
  };

  return axios
    .post(`${BASE_URL}/link`, payload)
    .then((response) => response.data);
};

export async function updateLinkAlias(linkId, newAlias) {
  const response = await fetch(`${BASE_URL}/link`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      linkId: linkId,
      linkAlias: newAlias,
    }),
  });
  return response.json();
}

export async function deleteLink(linkId) {
  const response = await fetch(`${BASE_URL}/link/${linkId}`, {
    method: "DELETE",
  });
  return response.ok; // Devuelve true si la eliminación fue exitosa
}
