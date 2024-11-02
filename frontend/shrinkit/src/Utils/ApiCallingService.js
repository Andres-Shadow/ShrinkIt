import axios from "axios";
import BASE_URL from "../Config/Config";

export const fetchLinks = () => {
  return axios.get(`${BASE_URL}/link`).then((response) => response.data);
};
