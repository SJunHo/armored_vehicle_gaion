//local
const BASE_URL = process.env.REACT_APP_BASE_SERVER_URL !== "/"
? `http://${process.env.REACT_APP_BASE_SERVER_URL}`
: `${window.location.protocol}//${window.location.hostname}:${window.location.port}`;
//build
// const BASE_URL = "http://221.148.11.152:3303";
export {BASE_URL}