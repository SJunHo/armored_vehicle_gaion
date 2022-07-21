import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8082/api/board";

class TutorialDataService {
  getAll() {
    return axios.get( API_URL + "/tutorials", { headers: authHeader() , "Content-type": "application/json", });
  }

  get(id) {
    return axios.get( API_URL + `/tutorials/${id}`, { headers: authHeader() , "Content-type": "application/json", });
  }

  create(data) {
    return axios.post( API_URL + "/tutorials", data , { headers: authHeader() , "Content-type": "application/json", });
  }

  update(id, data) {
    return axios.put( API_URL + `/tutorials/${id}`, data , { headers: authHeader() , "Content-type": "application/json", });
  }

  delete(id) {
    return axios.delete( API_URL + `/tutorials/${id}` , { headers: authHeader() , "Content-type": "application/json", });
  }

  deleteAll() {
    return axios.delete( API_URL + `/tutorials` , { headers: authHeader() , "Content-type": "application/json", });
  }

  findByTitle(title) {
    return axios.get( API_URL + `/tutorials?title=${title}` , { headers: authHeader() , "Content-type": "application/json", });
  }
}

export default new TutorialDataService();