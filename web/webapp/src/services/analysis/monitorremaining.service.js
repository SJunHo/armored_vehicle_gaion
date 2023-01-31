import axios from "axios";
import authHeader from "../login/auth-header";
import { BASE_URL } from "../url";

const API_URL = BASE_URL +"/api/monitorremaining";

class monitorRemainingService {
  
    searchBerlife(data){
        return axios.post( API_URL + "/searchBerlife", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    searchEnglife(data){
        return axios.post( API_URL + "/searchEnglife", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    searchGrblife(data){
        return axios.post( API_URL + "/searchGrblife", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    searchWhllife(data){
        return axios.post( API_URL + "/searchWhllife", data, {headers: authHeader(), "Content-type": "application/json", });
    }

}

export default new monitorRemainingService();