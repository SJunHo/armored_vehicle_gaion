import axios from "axios";
import authHeader from "../login/auth-header";
import { BASE_URL } from "../url";

const API_URL = BASE_URL +"/api/partsreplacementcycle";

class partsreplacementcycleService {

  getDivsList() {
    return axios.get(API_URL + `/divsList/`, { headers: authHeader(),  });
  }

  getBnList(data) {
    return axios.get(API_URL + `/bnList/${data}`, { headers: authHeader(),'Content-type': "application/json",});
  }

  getVnList(data) {
    return axios.get(API_URL + `/vnList/${data}`, { headers: authHeader(), 'Content-type': "application/json",});
  }

  getSnsrList(data) {
    return axios.get(API_URL + `/snsrList/${data}`, {headers: authHeader(), 'Content-type': "application/json",});
  }

  getCmncdList(data) {
    return axios.get(API_URL + `/cmncdList/${data}`, {headers: authHeader(), 'Content-type': "application/json",});
  }

  getPehicle(id){
    return axios.get( API_URL + `/info/${id}`, { headers: authHeader() , "Content-type": "application/json",});
  }

  search(data) {
    return axios.post( API_URL + "/search", data , { headers: authHeader() , "Content-type": "application/json", });
  }

  add(data) {
    return axios.post( API_URL + "/add", data , { headers: authHeader() , "Content-type": "application/json", });
  }
  
  getHistory(data) {
    return axios.post( API_URL + `/history`, data, { headers: authHeader() , "Content-type": "application/json", });
  }

}

export default new partsreplacementcycleService();