import axios from "axios";
import authHeader from "../login/auth-header";
import { BASE_URL } from "../url";

const API_URL = BASE_URL +"/api/drivercorrection";

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