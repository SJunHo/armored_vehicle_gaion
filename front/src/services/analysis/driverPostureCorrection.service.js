import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8082/api/drivercorrection";

class driverPostureCorrectionService {

  getDivsList() {
    return axios.get(API_URL + `/divsList`, { headers: authHeader(),  });
  }

  getBnList(data) {
    return axios.get(API_URL + `/bnList/${data}`, { headers: authHeader(),'Content-type': "application/json",});
  }

  getSdaList(data) {
    return axios.get(API_URL + `/sdaList/${data}`, { headers: authHeader(), 'Content-type': "application/json",});
  }

  search(data) {
    return axios.post( API_URL + "/search", data , { headers: authHeader() , "Content-type": "application/json", });
  }

}

export default new driverPostureCorrectionService();