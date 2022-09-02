import axios from "axios";

export const baseURL =
  process.env.REACT_APP_BASE_SERVER_URL !== "/"
    ? `${process.env.REACT_APP_BASE_SERVER_URL}`
    : "";
console.log(baseURL);
export const restfulApi = axios.create({
  baseURL: `${window.location.protocol}//${baseURL}`,
});

export * from "./gen";
